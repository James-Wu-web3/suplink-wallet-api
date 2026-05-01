package com.suplink.wallet.grpc.security;

import io.grpc.*;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcGlobalInterceptor;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
@GRpcGlobalInterceptor
public class GrpcAuthInterceptor implements ServerInterceptor {

    @Value("${api.security.consumer-token}")
    private String requiredToken;

    private static final Metadata.Key<String> TOKEN_HEADER =
            Metadata.Key.of("x-consumer-token", Metadata.ASCII_STRING_MARSHALLER);

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
            ServerCall<ReqT, RespT> call,
            Metadata headers,
            ServerCallHandler<ReqT, RespT> next) {

        String providedToken = headers.get(TOKEN_HEADER);

        if (providedToken == null || !providedToken.equals(requiredToken)) {
            log.warn("Authentication failed. Invalid or missing X-Consumer-Token. Provided: '{}'", providedToken);
            call.close(Status.UNAUTHENTICATED.withDescription("Invalid or missing X-Consumer-Token"), new Metadata());
            return new ServerCall.Listener<>() {
                // No-op listener
            };
        }

        log.info("Authentication successful for method: {}", call.getMethodDescriptor().getFullMethodName());
        return next.startCall(call, headers);
    }
}