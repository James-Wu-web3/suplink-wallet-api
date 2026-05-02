package com.suplink.wallet.service.impl;

import com.suplink.wallet.enums.ChainType;
import com.suplink.wallet.model.BlockDto;
import com.suplink.wallet.model.TransactionDto;
import com.suplink.wallet.service.AbstractChainService;
import com.suplink.wallet.service.indexer.IBlockchainIndexer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.Executor;

@Service("ETH")
public class EthereumServiceImpl extends AbstractChainService {

    private final Executor executor;
    private final IBlockchainIndexer indexerClient;

    @Autowired
    public EthereumServiceImpl(@Qualifier("ethTaskExecutor") Executor executor, IBlockchainIndexer indexerClient) {
        this.executor = executor;
        this.indexerClient = indexerClient;
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
    public BigDecimal getAccountBalance(String address) {
        return indexerClient.getAccountBalance(getChainType(), address);
    }

    @Override
    public List<TransactionDto> getTransactionsByAddress(String address) {
        return indexerClient.getTransactionsByAddress(getChainType(), address);
    }

    @Override
    public String convertAddress(String publicKey) {
        // TODO: Implement ETH public key to address conversion
        return null;
    }

    @Override
    public boolean validAddress(String address) {
        // TODO: Implement ETH address validation
        return false;
    }

    @Override
    public BlockDto getLatestBlock() {
        // TODO: Implement ETH get latest block
        return null;
    }

    @Override
    public BlockDto getBlockWithTransactions(Long height) {
        // TODO: Implement ETH get block with transactions
        return null;
    }

    @Override
    public TransactionDto getTransactionByHash(String txHash) {
        // TODO: Implement ETH get transaction by hash
        return null;
    }
}