package com.suplink.wallet.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public abstract class AbstractChainService implements IChainService {

    private static final Logger logger = LoggerFactory.getLogger(AbstractChainService.class);

    @Autowired
    @Qualifier("rpcTaskExecutor")
    private Executor rpcTaskExecutor;

    /**
     * Template method to execute time-consuming RPC calls asynchronously.
     * It handles threading, logging, timing, and exception handling.
     *
     * @param action The business logic to execute.
     * @param <T> The return type of the action.
     * @return The result of the action.
     */
    protected <T> T executeAsync(Supplier<T> action) {
        long startTime = System.nanoTime();
        CompletableFuture<T> future = CompletableFuture.supplyAsync(action, rpcTaskExecutor);

        try {
            // Block and get the result. In a real async scenario, you might return the Future.
            // For this controller-service model, we block to await the result before responding.
            T result = future.get(); // You can specify a timeout here, e.g., future.get(30, TimeUnit.SECONDS);

            long duration = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);
            logger.info("RPC call to {} succeeded in {} ms", getChainType(), duration);

            return result;
        } catch (Exception e) {
            long duration = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);
            logger.error("RPC call to {} failed after {} ms", getChainType(), duration, e);
            // Depending on requirements, you might want to rethrow a custom exception
            // or return a default value.
            throw new RuntimeException("Failed to execute RPC call for chain " + getChainType(), e);
        }
    }
}