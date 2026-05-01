package com.suplink.wallet.service;

import com.suplink.wallet.enums.ChainType;
import com.suplink.wallet.model.BlockDto;
import com.suplink.wallet.model.TransactionDto;

import java.util.List;

public interface IChainService {

    /**
     * Get the chain type for this service
     */
    ChainType getChainType();

    /**
     * Convert public key to address
     */
    String convertAddress(String publicKey);

    /**
     * Validate if an address is valid on this chain
     */
    boolean validAddress(String address);

    /**
     * Get the latest block header information
     */
    BlockDto getLatestBlock();

    /**
     * Get block with transactions by height/number
     */
    BlockDto getBlockWithTransactions(Long height);

    /**
     * Get a specific transaction by its hash
     */
    TransactionDto getTransactionByHash(String txHash);

    /**
     * Get a list of transactions associated with an address
     */
    List<TransactionDto> getTransactionsByAddress(String address);
}