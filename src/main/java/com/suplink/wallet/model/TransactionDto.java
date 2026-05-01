package com.suplink.wallet.model;

import lombok.Data;
import java.util.List;

@Data
public class TransactionDto {
    private String hash;
    private String blockHash;
    private Long blockHeight;
    private Long timestamp;
    private String status;
    
    private List<TxInputDto> inputs;
    private List<TxOutputDto> outputs;
}