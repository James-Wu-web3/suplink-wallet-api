package com.suplink.wallet.service;

import com.suplink.wallet.enums.ChainType;
import com.suplink.wallet.model.BlockDto;
import com.suplink.wallet.model.TransactionDto;

import java.math.BigDecimal;
import java.util.List;

public interface IChainService {

    ChainType getChainType();

    String convertAddress(String publicKey);

    boolean validAddress(String address);

    BlockDto getLatestBlock();

    BlockDto getBlockWithTransactions(Long height);

    TransactionDto getTransactionByHash(String txHash);

    List<TransactionDto> getTransactionsByAddress(String address);

    BigDecimal getAccountBalance(String address);
}