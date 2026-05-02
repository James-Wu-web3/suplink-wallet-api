package com.suplink.wallet.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.58.0)",
    comments = "Source: wallet.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class WalletServiceGrpc {

  private WalletServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "com.suplink.wallet.grpc.WalletService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.suplink.wallet.grpc.ConvertAddressRequest,
      com.suplink.wallet.grpc.ConvertAddressResponse> getConvertAddressMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ConvertAddress",
      requestType = com.suplink.wallet.grpc.ConvertAddressRequest.class,
      responseType = com.suplink.wallet.grpc.ConvertAddressResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.suplink.wallet.grpc.ConvertAddressRequest,
      com.suplink.wallet.grpc.ConvertAddressResponse> getConvertAddressMethod() {
    io.grpc.MethodDescriptor<com.suplink.wallet.grpc.ConvertAddressRequest, com.suplink.wallet.grpc.ConvertAddressResponse> getConvertAddressMethod;
    if ((getConvertAddressMethod = WalletServiceGrpc.getConvertAddressMethod) == null) {
      synchronized (WalletServiceGrpc.class) {
        if ((getConvertAddressMethod = WalletServiceGrpc.getConvertAddressMethod) == null) {
          WalletServiceGrpc.getConvertAddressMethod = getConvertAddressMethod =
              io.grpc.MethodDescriptor.<com.suplink.wallet.grpc.ConvertAddressRequest, com.suplink.wallet.grpc.ConvertAddressResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ConvertAddress"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.suplink.wallet.grpc.ConvertAddressRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.suplink.wallet.grpc.ConvertAddressResponse.getDefaultInstance()))
              .setSchemaDescriptor(new WalletServiceMethodDescriptorSupplier("ConvertAddress"))
              .build();
        }
      }
    }
    return getConvertAddressMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.suplink.wallet.grpc.ValidAddressRequest,
      com.suplink.wallet.grpc.ValidAddressResponse> getValidAddressMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ValidAddress",
      requestType = com.suplink.wallet.grpc.ValidAddressRequest.class,
      responseType = com.suplink.wallet.grpc.ValidAddressResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.suplink.wallet.grpc.ValidAddressRequest,
      com.suplink.wallet.grpc.ValidAddressResponse> getValidAddressMethod() {
    io.grpc.MethodDescriptor<com.suplink.wallet.grpc.ValidAddressRequest, com.suplink.wallet.grpc.ValidAddressResponse> getValidAddressMethod;
    if ((getValidAddressMethod = WalletServiceGrpc.getValidAddressMethod) == null) {
      synchronized (WalletServiceGrpc.class) {
        if ((getValidAddressMethod = WalletServiceGrpc.getValidAddressMethod) == null) {
          WalletServiceGrpc.getValidAddressMethod = getValidAddressMethod =
              io.grpc.MethodDescriptor.<com.suplink.wallet.grpc.ValidAddressRequest, com.suplink.wallet.grpc.ValidAddressResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ValidAddress"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.suplink.wallet.grpc.ValidAddressRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.suplink.wallet.grpc.ValidAddressResponse.getDefaultInstance()))
              .setSchemaDescriptor(new WalletServiceMethodDescriptorSupplier("ValidAddress"))
              .build();
        }
      }
    }
    return getValidAddressMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.suplink.wallet.grpc.GetLatestBlockRequest,
      com.suplink.wallet.grpc.Block> getGetLatestBlockMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetLatestBlock",
      requestType = com.suplink.wallet.grpc.GetLatestBlockRequest.class,
      responseType = com.suplink.wallet.grpc.Block.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.suplink.wallet.grpc.GetLatestBlockRequest,
      com.suplink.wallet.grpc.Block> getGetLatestBlockMethod() {
    io.grpc.MethodDescriptor<com.suplink.wallet.grpc.GetLatestBlockRequest, com.suplink.wallet.grpc.Block> getGetLatestBlockMethod;
    if ((getGetLatestBlockMethod = WalletServiceGrpc.getGetLatestBlockMethod) == null) {
      synchronized (WalletServiceGrpc.class) {
        if ((getGetLatestBlockMethod = WalletServiceGrpc.getGetLatestBlockMethod) == null) {
          WalletServiceGrpc.getGetLatestBlockMethod = getGetLatestBlockMethod =
              io.grpc.MethodDescriptor.<com.suplink.wallet.grpc.GetLatestBlockRequest, com.suplink.wallet.grpc.Block>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetLatestBlock"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.suplink.wallet.grpc.GetLatestBlockRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.suplink.wallet.grpc.Block.getDefaultInstance()))
              .setSchemaDescriptor(new WalletServiceMethodDescriptorSupplier("GetLatestBlock"))
              .build();
        }
      }
    }
    return getGetLatestBlockMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.suplink.wallet.grpc.GetBlockRequest,
      com.suplink.wallet.grpc.Block> getGetBlockWithTransactionsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetBlockWithTransactions",
      requestType = com.suplink.wallet.grpc.GetBlockRequest.class,
      responseType = com.suplink.wallet.grpc.Block.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.suplink.wallet.grpc.GetBlockRequest,
      com.suplink.wallet.grpc.Block> getGetBlockWithTransactionsMethod() {
    io.grpc.MethodDescriptor<com.suplink.wallet.grpc.GetBlockRequest, com.suplink.wallet.grpc.Block> getGetBlockWithTransactionsMethod;
    if ((getGetBlockWithTransactionsMethod = WalletServiceGrpc.getGetBlockWithTransactionsMethod) == null) {
      synchronized (WalletServiceGrpc.class) {
        if ((getGetBlockWithTransactionsMethod = WalletServiceGrpc.getGetBlockWithTransactionsMethod) == null) {
          WalletServiceGrpc.getGetBlockWithTransactionsMethod = getGetBlockWithTransactionsMethod =
              io.grpc.MethodDescriptor.<com.suplink.wallet.grpc.GetBlockRequest, com.suplink.wallet.grpc.Block>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetBlockWithTransactions"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.suplink.wallet.grpc.GetBlockRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.suplink.wallet.grpc.Block.getDefaultInstance()))
              .setSchemaDescriptor(new WalletServiceMethodDescriptorSupplier("GetBlockWithTransactions"))
              .build();
        }
      }
    }
    return getGetBlockWithTransactionsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.suplink.wallet.grpc.GetTransactionByHashRequest,
      com.suplink.wallet.grpc.Transaction> getGetTransactionByHashMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetTransactionByHash",
      requestType = com.suplink.wallet.grpc.GetTransactionByHashRequest.class,
      responseType = com.suplink.wallet.grpc.Transaction.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.suplink.wallet.grpc.GetTransactionByHashRequest,
      com.suplink.wallet.grpc.Transaction> getGetTransactionByHashMethod() {
    io.grpc.MethodDescriptor<com.suplink.wallet.grpc.GetTransactionByHashRequest, com.suplink.wallet.grpc.Transaction> getGetTransactionByHashMethod;
    if ((getGetTransactionByHashMethod = WalletServiceGrpc.getGetTransactionByHashMethod) == null) {
      synchronized (WalletServiceGrpc.class) {
        if ((getGetTransactionByHashMethod = WalletServiceGrpc.getGetTransactionByHashMethod) == null) {
          WalletServiceGrpc.getGetTransactionByHashMethod = getGetTransactionByHashMethod =
              io.grpc.MethodDescriptor.<com.suplink.wallet.grpc.GetTransactionByHashRequest, com.suplink.wallet.grpc.Transaction>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetTransactionByHash"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.suplink.wallet.grpc.GetTransactionByHashRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.suplink.wallet.grpc.Transaction.getDefaultInstance()))
              .setSchemaDescriptor(new WalletServiceMethodDescriptorSupplier("GetTransactionByHash"))
              .build();
        }
      }
    }
    return getGetTransactionByHashMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.suplink.wallet.grpc.GetTransactionsByAddressRequest,
      com.suplink.wallet.grpc.Transaction> getGetTransactionsByAddressMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetTransactionsByAddress",
      requestType = com.suplink.wallet.grpc.GetTransactionsByAddressRequest.class,
      responseType = com.suplink.wallet.grpc.Transaction.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<com.suplink.wallet.grpc.GetTransactionsByAddressRequest,
      com.suplink.wallet.grpc.Transaction> getGetTransactionsByAddressMethod() {
    io.grpc.MethodDescriptor<com.suplink.wallet.grpc.GetTransactionsByAddressRequest, com.suplink.wallet.grpc.Transaction> getGetTransactionsByAddressMethod;
    if ((getGetTransactionsByAddressMethod = WalletServiceGrpc.getGetTransactionsByAddressMethod) == null) {
      synchronized (WalletServiceGrpc.class) {
        if ((getGetTransactionsByAddressMethod = WalletServiceGrpc.getGetTransactionsByAddressMethod) == null) {
          WalletServiceGrpc.getGetTransactionsByAddressMethod = getGetTransactionsByAddressMethod =
              io.grpc.MethodDescriptor.<com.suplink.wallet.grpc.GetTransactionsByAddressRequest, com.suplink.wallet.grpc.Transaction>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetTransactionsByAddress"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.suplink.wallet.grpc.GetTransactionsByAddressRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.suplink.wallet.grpc.Transaction.getDefaultInstance()))
              .setSchemaDescriptor(new WalletServiceMethodDescriptorSupplier("GetTransactionsByAddress"))
              .build();
        }
      }
    }
    return getGetTransactionsByAddressMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.suplink.wallet.grpc.GetAccountBalanceRequest,
      com.suplink.wallet.grpc.GetAccountBalanceResponse> getGetAccountBalanceMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetAccountBalance",
      requestType = com.suplink.wallet.grpc.GetAccountBalanceRequest.class,
      responseType = com.suplink.wallet.grpc.GetAccountBalanceResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.suplink.wallet.grpc.GetAccountBalanceRequest,
      com.suplink.wallet.grpc.GetAccountBalanceResponse> getGetAccountBalanceMethod() {
    io.grpc.MethodDescriptor<com.suplink.wallet.grpc.GetAccountBalanceRequest, com.suplink.wallet.grpc.GetAccountBalanceResponse> getGetAccountBalanceMethod;
    if ((getGetAccountBalanceMethod = WalletServiceGrpc.getGetAccountBalanceMethod) == null) {
      synchronized (WalletServiceGrpc.class) {
        if ((getGetAccountBalanceMethod = WalletServiceGrpc.getGetAccountBalanceMethod) == null) {
          WalletServiceGrpc.getGetAccountBalanceMethod = getGetAccountBalanceMethod =
              io.grpc.MethodDescriptor.<com.suplink.wallet.grpc.GetAccountBalanceRequest, com.suplink.wallet.grpc.GetAccountBalanceResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetAccountBalance"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.suplink.wallet.grpc.GetAccountBalanceRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.suplink.wallet.grpc.GetAccountBalanceResponse.getDefaultInstance()))
              .setSchemaDescriptor(new WalletServiceMethodDescriptorSupplier("GetAccountBalance"))
              .build();
        }
      }
    }
    return getGetAccountBalanceMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static WalletServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<WalletServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<WalletServiceStub>() {
        @java.lang.Override
        public WalletServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new WalletServiceStub(channel, callOptions);
        }
      };
    return WalletServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static WalletServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<WalletServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<WalletServiceBlockingStub>() {
        @java.lang.Override
        public WalletServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new WalletServiceBlockingStub(channel, callOptions);
        }
      };
    return WalletServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static WalletServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<WalletServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<WalletServiceFutureStub>() {
        @java.lang.Override
        public WalletServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new WalletServiceFutureStub(channel, callOptions);
        }
      };
    return WalletServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void convertAddress(com.suplink.wallet.grpc.ConvertAddressRequest request,
        io.grpc.stub.StreamObserver<com.suplink.wallet.grpc.ConvertAddressResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getConvertAddressMethod(), responseObserver);
    }

    /**
     */
    default void validAddress(com.suplink.wallet.grpc.ValidAddressRequest request,
        io.grpc.stub.StreamObserver<com.suplink.wallet.grpc.ValidAddressResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getValidAddressMethod(), responseObserver);
    }

    /**
     */
    default void getLatestBlock(com.suplink.wallet.grpc.GetLatestBlockRequest request,
        io.grpc.stub.StreamObserver<com.suplink.wallet.grpc.Block> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetLatestBlockMethod(), responseObserver);
    }

    /**
     */
    default void getBlockWithTransactions(com.suplink.wallet.grpc.GetBlockRequest request,
        io.grpc.stub.StreamObserver<com.suplink.wallet.grpc.Block> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetBlockWithTransactionsMethod(), responseObserver);
    }

    /**
     */
    default void getTransactionByHash(com.suplink.wallet.grpc.GetTransactionByHashRequest request,
        io.grpc.stub.StreamObserver<com.suplink.wallet.grpc.Transaction> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetTransactionByHashMethod(), responseObserver);
    }

    /**
     */
    default void getTransactionsByAddress(com.suplink.wallet.grpc.GetTransactionsByAddressRequest request,
        io.grpc.stub.StreamObserver<com.suplink.wallet.grpc.Transaction> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetTransactionsByAddressMethod(), responseObserver);
    }

    /**
     */
    default void getAccountBalance(com.suplink.wallet.grpc.GetAccountBalanceRequest request,
        io.grpc.stub.StreamObserver<com.suplink.wallet.grpc.GetAccountBalanceResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetAccountBalanceMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service WalletService.
   */
  public static abstract class WalletServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return WalletServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service WalletService.
   */
  public static final class WalletServiceStub
      extends io.grpc.stub.AbstractAsyncStub<WalletServiceStub> {
    private WalletServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected WalletServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new WalletServiceStub(channel, callOptions);
    }

    /**
     */
    public void convertAddress(com.suplink.wallet.grpc.ConvertAddressRequest request,
        io.grpc.stub.StreamObserver<com.suplink.wallet.grpc.ConvertAddressResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getConvertAddressMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void validAddress(com.suplink.wallet.grpc.ValidAddressRequest request,
        io.grpc.stub.StreamObserver<com.suplink.wallet.grpc.ValidAddressResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getValidAddressMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getLatestBlock(com.suplink.wallet.grpc.GetLatestBlockRequest request,
        io.grpc.stub.StreamObserver<com.suplink.wallet.grpc.Block> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetLatestBlockMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getBlockWithTransactions(com.suplink.wallet.grpc.GetBlockRequest request,
        io.grpc.stub.StreamObserver<com.suplink.wallet.grpc.Block> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetBlockWithTransactionsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getTransactionByHash(com.suplink.wallet.grpc.GetTransactionByHashRequest request,
        io.grpc.stub.StreamObserver<com.suplink.wallet.grpc.Transaction> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetTransactionByHashMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getTransactionsByAddress(com.suplink.wallet.grpc.GetTransactionsByAddressRequest request,
        io.grpc.stub.StreamObserver<com.suplink.wallet.grpc.Transaction> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getGetTransactionsByAddressMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getAccountBalance(com.suplink.wallet.grpc.GetAccountBalanceRequest request,
        io.grpc.stub.StreamObserver<com.suplink.wallet.grpc.GetAccountBalanceResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetAccountBalanceMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service WalletService.
   */
  public static final class WalletServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<WalletServiceBlockingStub> {
    private WalletServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected WalletServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new WalletServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.suplink.wallet.grpc.ConvertAddressResponse convertAddress(com.suplink.wallet.grpc.ConvertAddressRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getConvertAddressMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.suplink.wallet.grpc.ValidAddressResponse validAddress(com.suplink.wallet.grpc.ValidAddressRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getValidAddressMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.suplink.wallet.grpc.Block getLatestBlock(com.suplink.wallet.grpc.GetLatestBlockRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetLatestBlockMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.suplink.wallet.grpc.Block getBlockWithTransactions(com.suplink.wallet.grpc.GetBlockRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetBlockWithTransactionsMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.suplink.wallet.grpc.Transaction getTransactionByHash(com.suplink.wallet.grpc.GetTransactionByHashRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetTransactionByHashMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<com.suplink.wallet.grpc.Transaction> getTransactionsByAddress(
        com.suplink.wallet.grpc.GetTransactionsByAddressRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getGetTransactionsByAddressMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.suplink.wallet.grpc.GetAccountBalanceResponse getAccountBalance(com.suplink.wallet.grpc.GetAccountBalanceRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetAccountBalanceMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service WalletService.
   */
  public static final class WalletServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<WalletServiceFutureStub> {
    private WalletServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected WalletServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new WalletServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.suplink.wallet.grpc.ConvertAddressResponse> convertAddress(
        com.suplink.wallet.grpc.ConvertAddressRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getConvertAddressMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.suplink.wallet.grpc.ValidAddressResponse> validAddress(
        com.suplink.wallet.grpc.ValidAddressRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getValidAddressMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.suplink.wallet.grpc.Block> getLatestBlock(
        com.suplink.wallet.grpc.GetLatestBlockRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetLatestBlockMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.suplink.wallet.grpc.Block> getBlockWithTransactions(
        com.suplink.wallet.grpc.GetBlockRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetBlockWithTransactionsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.suplink.wallet.grpc.Transaction> getTransactionByHash(
        com.suplink.wallet.grpc.GetTransactionByHashRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetTransactionByHashMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.suplink.wallet.grpc.GetAccountBalanceResponse> getAccountBalance(
        com.suplink.wallet.grpc.GetAccountBalanceRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetAccountBalanceMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CONVERT_ADDRESS = 0;
  private static final int METHODID_VALID_ADDRESS = 1;
  private static final int METHODID_GET_LATEST_BLOCK = 2;
  private static final int METHODID_GET_BLOCK_WITH_TRANSACTIONS = 3;
  private static final int METHODID_GET_TRANSACTION_BY_HASH = 4;
  private static final int METHODID_GET_TRANSACTIONS_BY_ADDRESS = 5;
  private static final int METHODID_GET_ACCOUNT_BALANCE = 6;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CONVERT_ADDRESS:
          serviceImpl.convertAddress((com.suplink.wallet.grpc.ConvertAddressRequest) request,
              (io.grpc.stub.StreamObserver<com.suplink.wallet.grpc.ConvertAddressResponse>) responseObserver);
          break;
        case METHODID_VALID_ADDRESS:
          serviceImpl.validAddress((com.suplink.wallet.grpc.ValidAddressRequest) request,
              (io.grpc.stub.StreamObserver<com.suplink.wallet.grpc.ValidAddressResponse>) responseObserver);
          break;
        case METHODID_GET_LATEST_BLOCK:
          serviceImpl.getLatestBlock((com.suplink.wallet.grpc.GetLatestBlockRequest) request,
              (io.grpc.stub.StreamObserver<com.suplink.wallet.grpc.Block>) responseObserver);
          break;
        case METHODID_GET_BLOCK_WITH_TRANSACTIONS:
          serviceImpl.getBlockWithTransactions((com.suplink.wallet.grpc.GetBlockRequest) request,
              (io.grpc.stub.StreamObserver<com.suplink.wallet.grpc.Block>) responseObserver);
          break;
        case METHODID_GET_TRANSACTION_BY_HASH:
          serviceImpl.getTransactionByHash((com.suplink.wallet.grpc.GetTransactionByHashRequest) request,
              (io.grpc.stub.StreamObserver<com.suplink.wallet.grpc.Transaction>) responseObserver);
          break;
        case METHODID_GET_TRANSACTIONS_BY_ADDRESS:
          serviceImpl.getTransactionsByAddress((com.suplink.wallet.grpc.GetTransactionsByAddressRequest) request,
              (io.grpc.stub.StreamObserver<com.suplink.wallet.grpc.Transaction>) responseObserver);
          break;
        case METHODID_GET_ACCOUNT_BALANCE:
          serviceImpl.getAccountBalance((com.suplink.wallet.grpc.GetAccountBalanceRequest) request,
              (io.grpc.stub.StreamObserver<com.suplink.wallet.grpc.GetAccountBalanceResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getConvertAddressMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.suplink.wallet.grpc.ConvertAddressRequest,
              com.suplink.wallet.grpc.ConvertAddressResponse>(
                service, METHODID_CONVERT_ADDRESS)))
        .addMethod(
          getValidAddressMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.suplink.wallet.grpc.ValidAddressRequest,
              com.suplink.wallet.grpc.ValidAddressResponse>(
                service, METHODID_VALID_ADDRESS)))
        .addMethod(
          getGetLatestBlockMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.suplink.wallet.grpc.GetLatestBlockRequest,
              com.suplink.wallet.grpc.Block>(
                service, METHODID_GET_LATEST_BLOCK)))
        .addMethod(
          getGetBlockWithTransactionsMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.suplink.wallet.grpc.GetBlockRequest,
              com.suplink.wallet.grpc.Block>(
                service, METHODID_GET_BLOCK_WITH_TRANSACTIONS)))
        .addMethod(
          getGetTransactionByHashMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.suplink.wallet.grpc.GetTransactionByHashRequest,
              com.suplink.wallet.grpc.Transaction>(
                service, METHODID_GET_TRANSACTION_BY_HASH)))
        .addMethod(
          getGetTransactionsByAddressMethod(),
          io.grpc.stub.ServerCalls.asyncServerStreamingCall(
            new MethodHandlers<
              com.suplink.wallet.grpc.GetTransactionsByAddressRequest,
              com.suplink.wallet.grpc.Transaction>(
                service, METHODID_GET_TRANSACTIONS_BY_ADDRESS)))
        .addMethod(
          getGetAccountBalanceMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.suplink.wallet.grpc.GetAccountBalanceRequest,
              com.suplink.wallet.grpc.GetAccountBalanceResponse>(
                service, METHODID_GET_ACCOUNT_BALANCE)))
        .build();
  }

  private static abstract class WalletServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    WalletServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.suplink.wallet.grpc.WalletProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("WalletService");
    }
  }

  private static final class WalletServiceFileDescriptorSupplier
      extends WalletServiceBaseDescriptorSupplier {
    WalletServiceFileDescriptorSupplier() {}
  }

  private static final class WalletServiceMethodDescriptorSupplier
      extends WalletServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    WalletServiceMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (WalletServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new WalletServiceFileDescriptorSupplier())
              .addMethod(getConvertAddressMethod())
              .addMethod(getValidAddressMethod())
              .addMethod(getGetLatestBlockMethod())
              .addMethod(getGetBlockWithTransactionsMethod())
              .addMethod(getGetTransactionByHashMethod())
              .addMethod(getGetTransactionsByAddressMethod())
              .addMethod(getGetAccountBalanceMethod())
              .build();
        }
      }
    }
    return result;
  }
}
