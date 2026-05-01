package com.suplink.wallet.service.impl;

import com.suplink.wallet.enums.ChainType;
import com.suplink.wallet.model.BlockDto;
import com.suplink.wallet.model.TransactionDto;
import com.suplink.wallet.service.AbstractChainService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("SOL")
public class SolanaServiceImpl extends AbstractChainService {

    @Override
    public ChainType getChainType() {
        return ChainType.SOL;
    }

    @Override
    public String convertAddress(String publicKey) {
        return executeAsync(() -> {
            // TODO: Implement SOL public key to address conversion
            return null;
        });
    }

    @Override
    public boolean validAddress(String address) {
        return executeAsync(() -> {
            // TODO: Implement SOL address validation
            return false;
        });
    }

    @Override
    public BlockDto getLatestBlock() {
        return executeAsync(() -> {
            // TODO: Implement SOL get latest block
            return null;
        });
    }

    @Override
    public BlockDto getBlockWithTransactions(Long height) {
        return executeAsync(() -> {
            // TODO: Implement SOL get block with transactions
            return null;
        });
    }

    @Override
    public TransactionDto getTransactionByHash(String txHash) {
        return executeAsync(() -> {
            // TODO: Implement SOL get transaction by hash
            return null;
        });
    }

    @Override
    public List<TransactionDto> getTransactionsByAddress(String address) {
        return executeAsync(() -> {
            // TODO: Implement SOL get transactions by address
            return null;
        });
    }
}