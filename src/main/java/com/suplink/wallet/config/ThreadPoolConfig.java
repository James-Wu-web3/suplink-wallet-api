package com.suplink.wallet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class ThreadPoolConfig {

    private ThreadPoolTaskExecutor createExecutor(String threadNamePrefix, int corePoolSize, int maxPoolSize, int queueCapacity) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds(60);
        executor.setThreadNamePrefix(threadNamePrefix);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

    @Bean("btcTaskExecutor")
    public Executor btcTaskExecutor() {
        // Assuming BTC is a low-frequency chain
        return createExecutor("btc-rpc-", 5, 10, 20);
    }

    @Bean("ethTaskExecutor")
    public Executor ethTaskExecutor() {
        // Assuming ETH is a high-frequency chain
        return createExecutor("eth-rpc-", 10, 20, 50);
    }

    @Bean("solTaskExecutor")
    public Executor solTaskExecutor() {
        // Assuming SOL is a high-frequency chain with fast responses
        return createExecutor("sol-rpc-", 10, 20, 50);
    }
}