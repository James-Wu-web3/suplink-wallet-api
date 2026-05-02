package com.suplink.wallet.service.indexer;

import com.suplink.wallet.model.TransactionDto;
import com.suplink.wallet.enums.ChainType;

import java.math.BigDecimal;
import java.util.List;

/**
 * Interface for interacting with a blockchain indexer service (e.g., Alchemy, QuickNode, or a custom one).
 * These services provide advanced APIs that are not available on standard nodes,
 * such as querying transactions by address.
 */
public interface IBlockchainIndexer {

    /**
     * Gets the balance of a specific account.
     *
     * @param chainType The blockchain to query.
     * @param address The address of the account.
     * @return The balance of the account.
     */
    BigDecimal getAccountBalance(ChainType chainType, String address);

    /**
     * Gets the transaction history for a specific address.
     *
     * @param chainType The blockchain to query.
     * @param address The address to get transactions for.
     * @return A list of transactions.
     */
    List<TransactionDto> getTransactionsByAddress(ChainType chainType, String address);
    
    /**
     * Gets the chain types supported by this indexer implementation.
     */
    List<ChainType> getSupportedChains();
}