package com.suplink.wallet.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TxInputDto {
    private String address;
    private BigDecimal amount;
    // For UTXO models, you might also want to include the source transaction hash and index
    // private String txId;
    // private int vout;
}