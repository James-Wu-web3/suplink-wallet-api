package com.suplink.wallet.grpc;

import com.suplink.wallet.model.BlockDto;
import com.suplink.wallet.model.TransactionDto;
import org.springframework.stereotype.Component;
import com.suplink.wallet.grpc.Block;
import java.util.stream.Collectors;

@Component
public class GrpcMapper {

    public Block toGrpcBlock(BlockDto dto) {
        if (dto == null) {
            return Block.newBuilder().build(); // Return empty block
        }
        return Block.newBuilder()
                .setHash(dto.getHash() != null ? dto.getHash() : "")
                .setHeight(dto.getHeight() != null ? dto.getHeight() : 0)
                .setTimestamp(dto.getTimestamp() != null ? dto.getTimestamp() : 0)
                .addAllTransactions(
                        dto.getTransactions().stream()
                                .map(this::toGrpcTransaction)
                                .collect(Collectors.toList())
                )
                .build();
    }

    public Transaction toGrpcTransaction(TransactionDto dto) {
        if (dto == null) {
            return Transaction.newBuilder().build(); // Return empty transaction
        }
        return Transaction.newBuilder()
                .setHash(dto.getHash() != null ? dto.getHash() : "")
                .setFromAddress(dto.getFromAddress() != null ? dto.getFromAddress() : "")
                .setToAddress(dto.getToAddress() != null ? dto.getToAddress() : "")
                .setAmount(dto.getAmount() != null ? dto.getAmount().toPlainString() : "0")
                .setBlockHash(dto.getBlockHash() != null ? dto.getBlockHash() : "")
                .setBlockHeight(dto.getBlockHeight() != null ? dto.getBlockHeight() : 0)
                .setTimestamp(dto.getTimestamp() != null ? dto.getTimestamp() : 0)
                .setStatus(dto.getStatus() != null ? dto.getStatus() : "")
                .build();
    }
}