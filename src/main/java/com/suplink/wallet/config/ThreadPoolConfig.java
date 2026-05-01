package com.suplink.wallet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class ThreadPoolConfig {

    /**
     * Dedicated thread pool for handling external RPC requests.
     * This isolates I/O-intensive tasks from the main Tomcat web thread pool,
     * preventing thread pool exhaustion and improving system resilience.
     */
    @Bean("rpcTaskExecutor")
    public Executor rpcTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // Core number of threads
        executor.setCorePoolSize(10);
        // Max number of threads
        executor.setMaxPoolSize(20);
        // Queue capacity
        executor.setQueueCapacity(50);
        // Thread idle time
        executor.setKeepAliveSeconds(60);
        // Thread name prefix for easy identification in logs
        executor.setThreadNamePrefix("rpc-exec-");
        // Rejection policy: Caller runs the task, providing a back-pressure mechanism
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // Initialize the thread pool
        executor.initialize();
        return executor;
    }
}