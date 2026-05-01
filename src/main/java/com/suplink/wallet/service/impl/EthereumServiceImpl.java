package com.suplink.wallet.service.impl;

import com.suplink.wallet.enums.ChainType;
import com.suplink.wallet.model.BlockDto;
import com.suplink.wallet.model.TransactionDto;
import com.suplink.wallet.service.AbstractChainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Executor;

@Service("ETH")
public class EthereumServiceImpl extends AbstractChainService {

    private final Executor executor;

    @Autowired
    public EthereumServiceImpl(@Qualifier("ethTaskExecutor") Executor executor) {
        this.executor = executor;
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
        return executeAsync(() -> {
            // TODO: Implement ETH public key to address conversion
            return null;
        });
    }

    @Override
    public boolean validAddress(String address) {
        return executeAsync(() -> {
            // TODO: Implement ETH address validation
            return false;
        });
    }

    @Override
    public BlockDto getLatestBlock() {
        return executeAsync(() -> {
            // TODO: Implement ETH get latest block
            return null;
        });
    }

    @Override
    public BlockDto getBlockWithTransactions(Long height) {
        return executeAsync(() -> {
            // TODO: Implement ETH get block with transactions
            return null;
        });
    }

    @Override
    public TransactionDto getTransactionByHash(String txHash) {
        return executeAsync(() -> {
            // TODO: Implement ETH get transaction by hash
            return null;
        });
    }

    @Override
    public List<TransactionDto> getTransactionsByAddress(String address) {
        return executeAsync(() -> {
            // TODO: Implement ETH get transactions by address (Usually requires an indexer like Etherscan API or an archive node)
            return null;
        });
    }
}