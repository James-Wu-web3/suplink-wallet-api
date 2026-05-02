package com.suplink.wallet.service.impl;

import com.suplink.wallet.enums.ChainType;
import com.suplink.wallet.grpc.BtcAddressType;
import com.suplink.wallet.model.BlockDto;
import com.suplink.wallet.model.TransactionDto;
import com.suplink.wallet.model.TxInputDto;
import com.suplink.wallet.model.TxOutputDto;
import com.suplink.wallet.service.AbstractChainService;
import com.suplink.wallet.service.indexer.IBlockchainIndexer;
import com.suplink.wallet.service.node.BitcoinNodeManager;
import com.suplink.wallet.service.node.BitcoinRpcApi;
import lombok.extern.slf4j.Slf4j;
import org.bitcoinj.core.*;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.script.Script;
import org.bitcoinj.script.ScriptBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

@Slf4j
@Service("BTC")
public class BitcoinServiceImpl extends AbstractChainService {

    private final Executor executor;
    private final NetworkParameters networkParameters;
    private final BitcoinNodeManager bitcoinNodeManager;
    private final IBlockchainIndexer indexerClient;

    @Autowired
    public BitcoinServiceImpl(
            @Qualifier("btcTaskExecutor") Executor executor,
            BitcoinNodeManager bitcoinNodeManager,
            IBlockchainIndexer indexerClient) {
        this.executor = executor;
        this.bitcoinNodeManager = bitcoinNodeManager;
        this.indexerClient = indexerClient;
        this.networkParameters = MainNetParams.get();
    }

    @Override
    protected Executor getExecutor() {
        return this.executor;
    }

    @Override
    public ChainType getChainType() {
        return ChainType.BTC;
    }

    @Override
    public BigDecimal getAccountBalance(String address) {
        return indexerClient.getAccountBalance(getChainType(), address);
    }

    @Override
    public List<TransactionDto> getTransactionsByAddress(String address) {
        return indexerClient.getTransactionsByAddress(getChainType(), address);
    }

    public String convertAddress(String publicKeyHex, BtcAddressType addressType) {
        try {
            ECKey pubKey = ECKey.fromPublicOnly(Utils.HEX.decode(publicKeyHex));
            switch (addressType) {
                case P2PKH: return LegacyAddress.fromKey(networkParameters, pubKey).toBase58();
                case P2WPKH: return SegwitAddress.fromKey(networkParameters, pubKey).toBech32();
                case P2SH:
                    Script p2wpkhScript = ScriptBuilder.createP2WPKHOutputScript(pubKey);
                    Address p2shAddress = ScriptBuilder.createP2SHOutputScript(p2wpkhScript).getToAddress(networkParameters);
                    return p2shAddress.toString();
                case P2TR:
                    log.error("Taproot (P2TR) address generation is not supported in bitcoinj-core version 0.16.2.");
                    throw new UnsupportedOperationException("Taproot (P2TR) not supported in current library version.");
                default:
                    log.warn("Unsupported or unknown BTC address type requested: {}", addressType);
                    return null;
            }
        } catch (Exception e) {
            log.error("Failed to convert BTC public key to address. PublicKey: {}, Type: {}", publicKeyHex, addressType, e);
            return null;
        }
    }

    @Override
    public String convertAddress(String publicKeyHex) {
        return convertAddress(publicKeyHex, BtcAddressType.P2WPKH);
    }

    @Override
    public boolean validAddress(String address) {
        try {
            Address.fromString(networkParameters, address);
            return true;
        } catch (AddressFormatException e1) {
            try {
                SegwitAddress.fromBech32(networkParameters, address);
                return true;
            } catch (AddressFormatException e2) {
                log.warn("Invalid BTC address provided. Failed to parse as Base58 and Bech32: {}", address);
                return false;
            }
        }
    }

    @Override
    public BlockDto getLatestBlock() {
        return executeAsync(() -> {
            BitcoinRpcApi rpcClient = bitcoinNodeManager.getActiveClient();
            String bestBlockHash = rpcClient.getBestBlockHash();
            Map<String, Object> blockMap = rpcClient.getBlock(bestBlockHash, 1);
            return convertToBlockDto(blockMap);
        });
    }

    @Override
    public BlockDto getBlockWithTransactions(Long height) {
        return executeAsync(() -> {
            BitcoinRpcApi rpcClient = bitcoinNodeManager.getActiveClient();
            String blockHash = rpcClient.getBlockHash(height);
            Map<String, Object> blockMap = rpcClient.getBlock(blockHash, 2);
            return convertToBlockDtoWithTxs(blockMap);
        });
    }

    @Override
    public TransactionDto getTransactionByHash(String txHash) {
        return executeAsync(() -> {
            BitcoinRpcApi rpcClient = bitcoinNodeManager.getActiveClient();
            Map<String, Object> txMap = rpcClient.getRawTransaction(txHash, true);
            return convertToTransactionDto(txMap);
        });
    }
    
    private BlockDto convertToBlockDto(Map<String, Object> blockMap) {
        if (blockMap == null || blockMap.isEmpty()) return null;
        BlockDto blockDto = new BlockDto();
        blockDto.setHash((String) blockMap.get("hash"));
        blockDto.setHeight(((Number) blockMap.get("height")).longValue());
        blockDto.setTimestamp(((Number) blockMap.get("time")).longValue());
        return blockDto;
    }

    private BlockDto convertToBlockDtoWithTxs(Map<String, Object> blockMap) {
        BlockDto blockDto = convertToBlockDto(blockMap);
        if (blockDto != null && blockMap.containsKey("tx")) {
            List<Map<String, Object>> txMaps = (List<Map<String, Object>>) blockMap.get("tx");
            List<TransactionDto> txs = txMaps.stream()
                    .map(this::convertToTransactionDto)
                    .collect(Collectors.toList());
            blockDto.setTransactions(txs);
        }
        return blockDto;
    }

    private TransactionDto convertToTransactionDto(Map<String, Object> txMap) {
        if (txMap == null || txMap.isEmpty()) return null;
        TransactionDto txDto = new TransactionDto();
        txDto.setHash((String) txMap.get("txid"));
        txDto.setBlockHash((String) txMap.get("blockhash"));
        if (txMap.containsKey("time")) {
            txDto.setTimestamp(((Number) txMap.get("time")).longValue());
        }
        if (txMap.containsKey("confirmations")) {
            txDto.setStatus(((Number) txMap.get("confirmations")).longValue() > 0 ? "CONFIRMED" : "PENDING");
        }

        List<TxInputDto> inputs = new ArrayList<>();
        if (txMap.containsKey("vin")) {
            List<Map<String, Object>> vin = (List<Map<String, Object>>) txMap.get("vin");
            for (Map<String, Object> inputMap : vin) {
                if (inputMap.containsKey("coinbase")) {
                    inputs.add(new TxInputDto("coinbase", BigDecimal.ZERO));
                } else {
                    inputs.add(new TxInputDto("unknown", BigDecimal.ZERO));
                }
            }
        }
        txDto.setInputs(inputs);

        List<TxOutputDto> outputs = new ArrayList<>();
        if (txMap.containsKey("vout")) {
            List<Map<String, Object>> vout = (List<Map<String, Object>>) txMap.get("vout");
            for (Map<String, Object> outputMap : vout) {
                Map<String, Object> scriptPubKey = (Map<String, Object>) outputMap.get("scriptPubKey");
                if (scriptPubKey != null && scriptPubKey.containsKey("address")) {
                    String address = (String) scriptPubKey.get("address");
                    BigDecimal amount = new BigDecimal(outputMap.get("value").toString());
                    outputs.add(new TxOutputDto(address, amount));
                }
            }
        }
        txDto.setOutputs(outputs);

        return txDto;
    }
}