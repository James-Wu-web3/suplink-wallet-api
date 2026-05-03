package com.suplink.wallet.service.impl;

import com.suplink.wallet.enums.ChainType;
import com.suplink.wallet.model.BlockDto;
import com.suplink.wallet.model.TransactionDto;
import com.suplink.wallet.model.TxInputDto;
import com.suplink.wallet.model.TxOutputDto;
import com.suplink.wallet.service.AbstractChainService;
import com.suplink.wallet.service.indexer.IBlockchainIndexer;
import com.suplink.wallet.service.node.EthereumNodeManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.utils.Convert;
import org.web3j.crypto.Keys;
import org.web3j.crypto.WalletUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

@Slf4j
@Service("ETH")
public class EthereumServiceImpl extends AbstractChainService {

    private final Executor executor;
    private final IBlockchainIndexer indexerClient;
    private final EthereumNodeManager ethereumNodeManager;

    @Autowired
    public EthereumServiceImpl(
            @Qualifier("ethTaskExecutor") Executor executor,
            IBlockchainIndexer indexerClient,
            EthereumNodeManager ethereumNodeManager) {
        this.executor = executor;
        this.indexerClient = indexerClient;
        this.ethereumNodeManager = ethereumNodeManager;
    }

    @Override
    protected Executor getExecutor() {
        return this.executor;
    }

    @Override
    public ChainType getChainType() {
        return ChainType.ETH;
    }

    @Override
    public String convertAddress(String publicKey) {
        try {
            String address = Keys.getAddress(publicKey);
            return "0x" + address;
        } catch (Exception e) {
            log.error("Failed to convert ETH public key to address. PublicKey: {}", publicKey, e);
            return null;
        }
    }

    @Override
    public boolean validAddress(String address) {
        return WalletUtils.isValidAddress(address);
    }

    @Override
    public BigDecimal getAccountBalance(String address) {
        return executeAsync(() -> {
            try {
                Web3j web3j = ethereumNodeManager.getActiveClient();
                BigInteger wei = web3j.ethGetBalance(address, DefaultBlockParameter.valueOf("latest")).send().getBalance();
                return Convert.fromWei(new BigDecimal(wei), Convert.Unit.ETHER);
            } catch (Exception e) {
                log.error("Failed to get ETH balance for address {}: {}", address, e.getMessage());
                return BigDecimal.ZERO;
            }
        });
    }

    @Override
    public List<TransactionDto> getTransactionsByAddress(String address) {
        return indexerClient.getTransactionsByAddress(getChainType(), address);
    }

    @Override
    public BlockDto getLatestBlock() {
        return executeAsync(() -> {
            try {
                Web3j web3j = ethereumNodeManager.getActiveClient();
                EthBlock.Block block = web3j.ethGetBlockByNumber(DefaultBlockParameter.valueOf("latest"), true).send().getBlock();
                return convertToBlockDto(block);
            } catch (Exception e) {
                log.error("Failed to get latest ETH block: {}", e.getMessage());
                return null;
            }
        });
    }

    @Override
    public BlockDto getBlockWithTransactions(Long height) {
        return executeAsync(() -> {
            try {
                Web3j web3j = ethereumNodeManager.getActiveClient();
                EthBlock.Block block = web3j.ethGetBlockByNumber(DefaultBlockParameter.valueOf(BigInteger.valueOf(height)), true).send().getBlock();
                return convertToBlockDto(block);
            } catch (Exception e) {
                log.error("Failed to get ETH block by height {}: {}", height, e.getMessage());
                return null;
            }
        });
    }

    @Override
    public TransactionDto getTransactionByHash(String txHash) {
        return executeAsync(() -> {
            try {
                Web3j web3j = ethereumNodeManager.getActiveClient();
                org.web3j.protocol.core.methods.response.Transaction tx = web3j.ethGetTransactionByHash(txHash).send().getTransaction().orElse(null);
                return convertToTransactionDto(tx);
            } catch (Exception e) {
                log.error("Failed to get ETH transaction by hash {}: {}", txHash, e.getMessage());
                return null;
            }
        });
    }

    private BlockDto convertToBlockDto(EthBlock.Block block) {
        if (block == null) return null;
        BlockDto dto = new BlockDto();
        dto.setHash(block.getHash());
        dto.setHeight(block.getNumber().longValue());
        dto.setTimestamp(block.getTimestamp().longValue());
        if (block.getTransactions() != null) {
            List<TransactionDto> txs = block.getTransactions().stream()
                    .map(txResult -> convertToTransactionDto((org.web3j.protocol.core.methods.response.Transaction) txResult.get()))
                    .collect(Collectors.toList());
            dto.setTransactions(txs);
        }
        return dto;
    }

    private TransactionDto convertToTransactionDto(org.web3j.protocol.core.methods.response.Transaction tx) {
        if (tx == null) return null;
        TransactionDto dto = new TransactionDto();
        dto.setHash(tx.getHash());
        dto.setBlockHash(tx.getBlockHash());
        dto.setBlockHeight(tx.getBlockNumber() != null ? tx.getBlockNumber().longValue() : null);
        dto.setTimestamp(null); 
        dto.setStatus(tx.getBlockNumber() == null ? "PENDING" : "CONFIRMED");

        BigDecimal amount = Convert.fromWei(new BigDecimal(tx.getValue()), Convert.Unit.ETHER);
        TxInputDto input = new TxInputDto(tx.getFrom(), amount, null, null);
        TxOutputDto output = new TxOutputDto(tx.getTo(), amount);

        dto.setInputs(Collections.singletonList(input));
        dto.setOutputs(Collections.singletonList(output));
        
        return dto;
    }
}