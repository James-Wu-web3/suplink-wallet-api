package com.suplink.wallet.service.node;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import jakarta.annotation.PostConstruct;
import java.math.BigInteger;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Component
@ConfigurationProperties(prefix = "ethereum")
public class EthereumNodeManager {

    @Data
    public static class ClusterProperties {
        private String alias;
        private String url;
    }

    private ClusterProperties primary;
    private ClusterProperties standby;

    @Value("${ethereum.health-check.max-block-height-difference:10}")
    private int maxBlockHeightDifference;

    private Web3j primaryClient;
    private Web3j standbyClient;

    private final AtomicReference<Web3j> activeClient = new AtomicReference<>();
    private final AtomicReference<String> activeClientAlias = new AtomicReference<>();

    public void setCluster(Map<String, ClusterProperties> cluster) {
        this.primary = cluster.get("primary");
        this.standby = cluster.get("standby");
    }

    @PostConstruct
    public void init() {
        if (primary == null || standby == null) {
            log.error("Primary and/or standby Ethereum cluster properties are not configured. Check 'ethereum.cluster.*'");
            throw new IllegalStateException("Ethereum cluster configuration is incomplete.");
        }

        this.primaryClient = Web3j.build(new HttpService(primary.getUrl()));
        this.standbyClient = Web3j.build(new HttpService(standby.getUrl()));

        log.info("Initialized Web3j clients for primary ({}) and standby ({}) clusters.", primary.getAlias(), standby.getAlias());

        activeClient.set(primaryClient);
        activeClientAlias.set(primary.getAlias());
        log.info("Initial active Ethereum cluster set to: {}", primary.getAlias());

        performHealthCheck();
    }

    @Scheduled(fixedRateString = "${ethereum.health-check.interval-ms:15000}")
    public void performHealthCheck() {
        log.debug("Performing Ethereum cluster health check...");
        BigInteger primaryHeight = BigInteger.valueOf(-1);
        BigInteger standbyHeight = BigInteger.valueOf(-1);

        try {
            primaryHeight = primaryClient.ethBlockNumber().send().getBlockNumber();
            log.debug("Primary ETH cluster ({}) height: {}", primary.getAlias(), primaryHeight);
        } catch (Exception e) {
            log.warn("Health check failed for primary ETH cluster ({}): {}", primary.getAlias(), e.getMessage());
        }

        try {
            standbyHeight = standbyClient.ethBlockNumber().send().getBlockNumber();
            log.debug("Standby ETH cluster ({}) height: {}", standby.getAlias(), standbyHeight);
        } catch (Exception e) {
            log.warn("Health check failed for standby ETH cluster ({}): {}", standby.getAlias(), e.getMessage());
        }

        String currentActiveAlias = activeClientAlias.get();

        if (primary.getAlias().equals(currentActiveAlias)) {
            boolean primaryIsUnhealthy = (primaryHeight.longValue() == -1) ||
                    (standbyHeight.compareTo(primaryHeight) > 0 &&
                     standbyHeight.subtract(primaryHeight).longValue() > maxBlockHeightDifference);
            if (primaryIsUnhealthy && standbyHeight.longValue() != -1) {
                log.warn("Primary ETH cluster '{}' is unhealthy or lagging. Failing over to standby cluster '{}'.", primary.getAlias(), standby.getAlias());
                activeClient.set(standbyClient);
                activeClientAlias.set(standby.getAlias());
            }
        } else if (standby.getAlias().equals(currentActiveAlias)) {
            if (primaryHeight.longValue() != -1) {
                log.info("Primary ETH cluster '{}' is back online. Failing back to primary.", primary.getAlias());
                activeClient.set(primaryClient);
                activeClientAlias.set(primary.getAlias());
            }
        }
    }

    public Web3j getActiveClient() {
        Web3j client = activeClient.get();
        if (client == null) {
            throw new IllegalStateException("No active Ethereum cluster available.");
        }
        return client;
    }
}