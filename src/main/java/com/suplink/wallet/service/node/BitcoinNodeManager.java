package com.suplink.wallet.service.node;

import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import com.googlecode.jsonrpc4j.ProxyUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.net.URL;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Component
@ConfigurationProperties(prefix = "bitcoin")
public class BitcoinNodeManager {

    @Data
    public static class ClusterProperties {
        private String alias;
        private String url;
        private String rpcUser;
        private String rpcPassword;
    }

    private ClusterProperties primary;
    private ClusterProperties standby;

    @Value("${bitcoin.health-check.max-block-height-difference:10}")
    private int maxBlockHeightDifference;

    private BitcoinRpcApi primaryClient;
    private BitcoinRpcApi standbyClient;

    private final AtomicReference<BitcoinRpcApi> activeClient = new AtomicReference<>();
    private final AtomicReference<String> activeClientAlias = new AtomicReference<>();

    // Setters for Spring Boot to inject properties
    public void setCluster(Map<String, ClusterProperties> cluster) {
        this.primary = cluster.get("primary");
        this.standby = cluster.get("standby");
    }

    @PostConstruct
    public void init() {
        if (primary == null || standby == null) {
            log.error("Primary and/or standby Bitcoin cluster properties are not configured. Check 'bitcoin.cluster.*'");
            throw new IllegalStateException("Bitcoin cluster configuration is incomplete.");
        }

        this.primaryClient = createClient(primary);
        this.standbyClient = createClient(standby);

        log.info("Initialized clients for primary ({}) and standby ({}) clusters.", primary.getAlias(), standby.getAlias());

        // Set initial active client to primary
        activeClient.set(primaryClient);
        activeClientAlias.set(primary.getAlias());
        log.info("Initial active Bitcoin cluster set to: {}", primary.getAlias());

        performHealthCheck();
    }

    private BitcoinRpcApi createClient(ClusterProperties props) {
        try {
            URL url = new URL(props.getUrl());
            JsonRpcHttpClient client = new JsonRpcHttpClient(url);
            String auth = props.getRpcUser() + ":" + props.getRpcPassword();
            String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", "Basic " + encodedAuth);
            client.setHeaders(headers);
            return ProxyUtil.createClientProxy(getClass().getClassLoader(), BitcoinRpcApi.class, client);
        } catch (Exception e) {
            log.error("Failed to create RPC client for cluster {}: {}", props.getAlias(), e.getMessage(), e);
            throw new RuntimeException("Failed to create RPC client for " + props.getAlias(), e);
        }
    }

    @Scheduled(fixedRateString = "${bitcoin.health-check.interval-ms:15000}")
    public void performHealthCheck() {
        log.debug("Performing Bitcoin cluster health check...");
        long primaryHeight = -1;
        long standbyHeight = -1;

        try {
            primaryHeight = primaryClient.getBlockCount();
            log.debug("Primary cluster ({}) height: {}", primary.getAlias(), primaryHeight);
        } catch (Exception e) {
            log.warn("Health check failed for primary cluster ({}): {}", primary.getAlias(), e.getMessage());
        }

        try {
            standbyHeight = standbyClient.getBlockCount();
            log.debug("Standby cluster ({}) height: {}", standby.getAlias(), standbyHeight);
        } catch (Exception e) {
            log.warn("Health check failed for standby cluster ({}): {}", standby.getAlias(), e.getMessage());
        }

        String currentActiveAlias = activeClientAlias.get();

        // Failover from Primary to Standby
        if (primary.getAlias().equals(currentActiveAlias)) {
            boolean primaryIsUnhealthy = (primaryHeight == -1) || (standbyHeight > primaryHeight && (standbyHeight - primaryHeight) > maxBlockHeightDifference);
            if (primaryIsUnhealthy && standbyHeight != -1) {
                log.warn("Primary cluster '{}' is unhealthy or lagging. Failing over to standby cluster '{}'.", primary.getAlias(), standby.getAlias());
                activeClient.set(standbyClient);
                activeClientAlias.set(standby.getAlias());
            }
        } 
        // Failback from Standby to Primary
        else if (standby.getAlias().equals(currentActiveAlias)) {
            if (primaryHeight != -1) { // If primary cluster comes back online
                log.info("Primary cluster '{}' is back online. Failing back to primary.", primary.getAlias());
                activeClient.set(primaryClient);
                activeClientAlias.set(primary.getAlias());
            }
        }
    }

    public BitcoinRpcApi getActiveClient() {
        BitcoinRpcApi client = activeClient.get();
        if (client == null) {
            throw new IllegalStateException("No active Bitcoin cluster available.");
        }
        return client;
    }
}