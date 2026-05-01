package com.suplink.wallet.grpc;

import com.suplink.wallet.model.BlockDto;
import com.suplink.wallet.model.TransactionDto;
import com.suplink.wallet.model.TxInputDto;
import com.suplink.wallet.model.TxOutputDto;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.stream.Collectors;

@Component
public class GrpcMapper {

    public Block toGrpcBlock(BlockDto dto) {
        if (dto == null) {
            return Block.newBuilder().build();
        }
        Block.Builder builder = Block.newBuilder()
                .setHash(dto.getHash() != null ? dto.getHash() : "")
                .setHeight(dto.getHeight() != null ? dto.getHeight() : 0)
                .setTimestamp(dto.getTimestamp() != null ? dto.getTimestamp() : 0);

        if (dto.getTransactions() != null) {
            builder.addAllTransactions(
                    dto.getTransactions().stream()
                            .map(this::toGrpcTransaction)
                            .collect(Collectors.toList())
            );
        }
        return builder.build();
    }

    public Transaction toGrpcTransaction(TransactionDto dto) {
        if (dto == null) {
            return Transaction.newBuilder().build();
        }
        Transaction.Builder builder = Transaction.newBuilder()
                .setHash(dto.getHash() != null ? dto.getHash() : "")
                .setBlockHash(dto.getBlockHash() != null ? dto.getBlockHash() : "")
                .setBlockHeight(dto.getBlockHeight() != null ? dto.getBlockHeight() : 0)
                .setTimestamp(dto.getTimestamp() != null ? dto.getTimestamp() : 0)
                .setStatus(dto.getStatus() != null ? dto.getStatus() : "");

        if (dto.getInputs() != null) {
            builder.addAllInputs(
                    dto.getInputs().stream()
                            .map(this::toGrpcTxInput)
                            .collect(Collectors.toList())
            );
        }

        if (dto.getOutputs() != null) {
            builder.addAllOutputs(
                    dto.getOutputs().stream()
                            .map(this::toGrpcTxOutput)
                            .collect(Collectors.toList())
            );
        }

        return builder.build();
    }

    private TxInput toGrpcTxInput(TxInputDto dto) {
        if (dto == null) {
            return TxInput.newBuilder().build();
        }
        return TxInput.newBuilder()
                .setAddress(dto.getAddress() != null ? dto.getAddress() : "")
                .setAmount(dto.getAmount() != null ? dto.getAmount().toPlainString() : "0")
                .build();
    }

    private TxOutput toGrpcTxOutput(TxOutputDto dto) {
        if (dto == null) {
            return TxOutput.newBuilder().build();
        }
        return TxOutput.newBuilder()
                .setAddress(dto.getAddress() != null ? dto.getAddress() : "")
                .setAmount(dto.getAmount() != null ? dto.getAmount().toPlainString() : "0")
                .build();
    }
}