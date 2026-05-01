package com.suplink.wallet.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class TransactionDto {
    private String hash;
    private String blockHash;
    private Long blockHeight;
    private Long timestamp;
    private String status;
    private BigDecimal amount;
    private List<TxInputDto> inputs;
    private List<TxOutputDto> outputs;
}