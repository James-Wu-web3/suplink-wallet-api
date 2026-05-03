package com.suplink.wallet.service.impl;

import com.suplink.wallet.enums.ChainType;
import com.suplink.wallet.model.BlockDto;
import com.suplink.wallet.model.TransactionDto;
import com.suplink.wallet.model.TxInputDto;
import com.suplink.wallet.model.TxOutputDto;
import com.suplink.wallet.service.AbstractChainService;
import com.suplink.wallet.service.indexer.IBlockchainIndexer;
import com.suplink.wallet.service.node.SolanaNodeManager;
import lombok.extern.slf4j.Slf4j;
import org.p2p.solanaj.core.PublicKey;
import org.p2p.solanaj.rpc.RpcClient;
import org.p2p.solanaj.rpc.types.Block;
import org.p2p.solanaj.rpc.types.ConfirmedTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

@Slf4j
@Service("SOL")
public class SolanaServiceImpl extends AbstractChainService {

    private final Executor executor;
    private final IBlockchainIndexer indexerClient;
    private final SolanaNodeManager solanaNodeManager;
    private static final BigDecimal LAMPORTS_PER_SOL = new BigDecimal("1000000000");

    @Autowired
    public SolanaServiceImpl(
            @Qualifier("solTaskExecutor") Executor executor,
            IBlockchainIndexer indexerClient,
            SolanaNodeManager solanaNodeManager) {
        this.executor = executor;
        this.indexerClient = indexerClient;
        this.solanaNodeManager = solanaNodeManager;
    }

    @Override
    protected Executor getExecutor() {
        return this.executor;
    }

    @Override
    public ChainType getChainType() {
        return ChainType.SOL;
    }

    @Override
    public String convertAddress(String publicKey) {
        return publicKey;
    }

    @Override
    public boolean validAddress(String address) {
        try {
            new PublicKey(address);
            return true;
        } catch (Exception e) {
            log.warn("Invalid SOL address provided: {}", address);
            return false;
        }
    }

    @Override
    public BigDecimal getAccountBalance(String address) {
        return executeAsync(() -> {
            try {
                RpcClient rpcClient = solanaNodeManager.getActiveClient();
                long lamports = rpcClient.getApi().getBalance(new PublicKey(address));
                return new BigDecimal(lamports).divide(LAMPORTS_PER_SOL, 9, RoundingMode.HALF_UP);
            } catch (Exception e) {
                log.error("Failed to get SOL balance for address {}: {}", address, e.getMessage());
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
                RpcClient rpcClient = solanaNodeManager.getActiveClient();
                long latestSlot = rpcClient.getApi().getSlot();
                
                if (latestSlot > Integer.MAX_VALUE) {
                    log.error("Solana slot number {} exceeds Integer.MAX_VALUE. The solanaj library version needs an upgrade to support getBlock.", latestSlot);
                    throw new IllegalStateException("Cannot fetch block for slot " + latestSlot + " due to library limitations.");
                }

                Block block = rpcClient.getApi().getBlock((int) latestSlot);
                return convertToBlockDto(block);
            } catch (Exception e) {
                log.error("Failed to get latest SOL block: {}", e.getMessage());
                return null;
            }
        });
    }

    @Override
    public BlockDto getBlockWithTransactions(Long height) {
        return executeAsync(() -> {
            try {
                if (height > Integer.MAX_VALUE) {
                    log.error("Solana slot number {} exceeds Integer.MAX_VALUE. The solanaj library version needs an upgrade to support getBlock.", height);
                    throw new IllegalStateException("Cannot fetch block for slot " + height + " due to library limitations.");
                }
                
                RpcClient rpcClient = solanaNodeManager.getActiveClient();
                Block block = rpcClient.getApi().getBlock(height.intValue());
                return convertToBlockDto(block);
            } catch (Exception e) {
                log.error("Failed to get SOL block by height {}: {}", height, e.getMessage());
                return null;
            }
        });
    }

    @Override
    public TransactionDto getTransactionByHash(String txHash) {
        return executeAsync(() -> {
            try {
                RpcClient rpcClient = solanaNodeManager.getActiveClient();
                ConfirmedTransaction tx = rpcClient.getApi().getTransaction(txHash);
                // The blockhash is not available in the getTransaction response, so we pass null.
                return convertToTransactionDto(tx, null);
            } catch (Exception e) {
                log.error("Failed to get SOL transaction by hash {}: {}", txHash, e.getMessage());
                return null;
            }
        });
    }

    private BlockDto convertToBlockDto(Block block) {
        if (block == null) return null;
        BlockDto dto = new BlockDto();
        dto.setHash(block.getBlockHash());
        dto.setHeight((long) block.getBlockHeight());
        dto.setTimestamp((long) block.getBlockTime());
        if (block.getTransactions() != null) {
            List<TransactionDto> txs = block.getTransactions().stream()
                    // Pass the blockhash down to the transaction converter
                    .map(tx -> convertToTransactionDto(tx, block.getBlockHash()))
                    .collect(Collectors.toList());
            dto.setTransactions(txs);
        }
        return dto;
    }

    private TransactionDto convertToTransactionDto(ConfirmedTransaction tx, String blockhash) {
        if (tx == null || tx.getTransaction() == null) return null;
        TransactionDto dto = new TransactionDto();
        dto.setHash(tx.getTransaction().getSignatures().get(0));
        // Use the passed-in blockhash if available, otherwise it's null (from getTransactionByHash)
        dto.setBlockHash(blockhash);
        dto.setBlockHeight(tx.getSlot());
        dto.setTimestamp(tx.blockTime);
        dto.setStatus(tx.getMeta().getErr() == null ? "CONFIRMED" : "FAILED");

        List<String> accountKeys = tx.getTransaction().getMessage().getAccountKeys().stream()
                .map(PublicKey::toBase58)
                .collect(Collectors.toList());
        
        if (accountKeys.size() >= 2) {
            // This is a heuristic. Real parsing requires analyzing instructions.
            TxInputDto input = new TxInputDto(accountKeys.get(0), null, null, null);
            TxOutputDto output = new TxOutputDto(accountKeys.get(1), null);
            dto.setInputs(Collections.singletonList(input));
            dto.setOutputs(Collections.singletonList(output));
        }
        
        return dto;
    }
}