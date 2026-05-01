package com.suplink.wallet.model;

import lombok.Data;
import java.util.List;

@Data
public class BlockDto {
    private String hash;
    private Long height;
    private Long timestamp;
    private List<TransactionDto> transactions;
}