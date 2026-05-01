package com.suplink.wallet.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public abstract class AbstractChainService implements IChainService {

    private static final Logger logger = LoggerFactory.getLogger(AbstractChainService.class);

    /**
     * Abstract method to be implemented by subclasses to provide their specific thread pool.
     * @return The executor for the specific chain.
     */
    protected abstract Executor getExecutor();

    /**
     * Template method to execute time-consuming RPC calls asynchronously.
     * It uses the executor provided by the subclass to achieve thread pool isolation.
     *
     * @param action The business logic to execute.
     * @param <T> The return type of the action.
     * @return The result of the action.
     */
    protected <T> T executeAsync(Supplier<T> action) {
        long startTime = System.nanoTime();
        // Get the specific executor from the subclass
        Executor executor = getExecutor();
        CompletableFuture<T> future = CompletableFuture.supplyAsync(action, executor);

        try {
            // Block and get the result.
            T result = future.get(); // Consider adding a timeout, e.g., future.get(30, TimeUnit.SECONDS);

            long duration = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);
            logger.info("RPC call to {} succeeded in {} ms on thread pool {}", getChainType(), duration, executor);

            return result;
        } catch (Exception e) {
            long duration = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);
            logger.error("RPC call to {} failed after {} ms on thread pool {}", getChainType(), duration, executor, e);

            throw new RuntimeException("Failed to execute RPC call for chain " + getChainType(), e);
        }
    }
}