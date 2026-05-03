package com.suplink.wallet.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TxInputDto {
    // The address providing the funds.
    // Note: This can be null if not resolved, as it requires fetching the previous transaction.
    private String address;

    // The amount of the UTXO being spent.
    // Note: This can be null if not resolved.
    private BigDecimal amount;

    // The transaction ID of the previous output being spent.
    private String prevTxId;

    // The index of the output in the previous transaction.
    private Integer prevOutputIndex;
}