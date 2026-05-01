package com.suplink.wallet.grpc;

import com.suplink.wallet.factory.ChainServiceFactory;
import com.suplink.wallet.model.BlockDto;
import com.suplink.wallet.model.TransactionDto;
import com.suplink.wallet.service.IChainService;
import com.suplink.wallet.service.impl.BitcoinServiceImpl;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.lognet.springboot.grpc.GRpcService;

import java.util.List;

@GRpcService
@RequiredArgsConstructor
public class WalletGrpcService extends WalletServiceGrpc.WalletServiceImplBase {

    private final ChainServiceFactory chainServiceFactory;
    private final GrpcMapper grpcMapper;

    @Override
    public void convertAddress(ConvertAddressRequest request, StreamObserver<ConvertAddressResponse> responseObserver) {
        IChainService service = chainServiceFactory.getService(convertChainType(request.getChainType()));
        String address = "";

        if (service instanceof BitcoinServiceImpl) {
            // Use the specialized method for Bitcoin
            address = ((BitcoinServiceImpl) service).convertAddress(request.getPublicKey(), request.getAddressType());
        } else {
            // Use the generic method for other chains
            address = service.convertAddress(request.getPublicKey());
        }

        ConvertAddressResponse response = ConvertAddressResponse.newBuilder()
                .setAddress(address != null ? address : "")
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void validAddress(ValidAddressRequest request, StreamObserver<ValidAddressResponse> responseObserver) {
        IChainService service = chainServiceFactory.getService(convertChainType(request.getChainType()));
        boolean isValid = service.validAddress(request.getAddress());

        ValidAddressResponse response = ValidAddressResponse.newBuilder()
                .setIsValid(isValid)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getLatestBlock(GetLatestBlockRequest request, StreamObserver<Block> responseObserver) {
        IChainService service = chainServiceFactory.getService(convertChainType(request.getChainType()));
        BlockDto blockDto = service.getLatestBlock();
        responseObserver.onNext(grpcMapper.toGrpcBlock(blockDto));
        responseObserver.onCompleted();
    }

    @Override
    public void getBlockWithTransactions(GetBlockRequest request, StreamObserver<Block> responseObserver) {
        IChainService service = chainServiceFactory.getService(convertChainType(request.getChainType()));
        BlockDto blockDto = service.getBlockWithTransactions(request.getHeight());
        responseObserver.onNext(grpcMapper.toGrpcBlock(blockDto));
        responseObserver.onCompleted();
    }

    @Override
    public void getTransactionByHash(GetTransactionByHashRequest request, StreamObserver<Transaction> responseObserver) {
        IChainService service = chainServiceFactory.getService(convertChainType(request.getChainType()));
        TransactionDto txDto = service.getTransactionByHash(request.getTxHash());
        responseObserver.onNext(grpcMapper.toGrpcTransaction(txDto));
        responseObserver.onCompleted();
    }

    @Override
    public void getTransactionsByAddress(GetTransactionsByAddressRequest request, StreamObserver<Transaction> responseObserver) {
        IChainService service = chainServiceFactory.getService(convertChainType(request.getChainType()));
        List<TransactionDto> txDtos = service.getTransactionsByAddress(request.getAddress());

        if (txDtos != null) {
            for (TransactionDto dto : txDtos) {
                responseObserver.onNext(grpcMapper.toGrpcTransaction(dto));
            }
        }
        responseObserver.onCompleted();
    }

    private com.suplink.wallet.enums.ChainType convertChainType(ChainType grpcChainType) {
        if (grpcChainType == ChainType.UNRECOGNIZED) {
            throw new IllegalArgumentException("Unrecognized chain type");
        }
        return com.suplink.wallet.enums.ChainType.valueOf(grpcChainType.name());
    }
}