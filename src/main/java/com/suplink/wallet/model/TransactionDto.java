package com.suplink.wallet.model;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class TransactionDto {
    private String hash;
    private String fromAddress;
    private String toAddress;
    private BigDecimal amount;
    private String blockHash;
    private Long blockHeight;
    private Long timestamp;
    private String status;
}