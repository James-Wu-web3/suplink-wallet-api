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
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Component
@ConfigurationProperties(prefix = "bitcoin")
public class BitcoinNodeManager {

    @Data
    public static class BitcoinNodeProperties {
        private String alias;
        private String url;
        private String rpcUser;
        private String rpcPassword;
    }

    private List<BitcoinNodeProperties> nodes;
    private String primaryNodeAlias;

    @Value("${bitcoin.health-check.max-block-height-difference:5}")
    private int maxBlockHeightDifference;

    private final Map<String, BitcoinRpcApi> clients = new ConcurrentHashMap<>();
    private final Map<String, Long> nodeBlockHeights = new ConcurrentHashMap<>();
    private final AtomicReference<BitcoinRpcApi> activeClient = new AtomicReference<>();
    private final AtomicReference<String> activeClientAlias = new AtomicReference<>();

    public void setNodes(List<BitcoinNodeProperties> nodes) {
        this.nodes = nodes;
    }

    public void setPrimaryNodeAlias(String primaryNodeAlias) {
        this.primaryNodeAlias = primaryNodeAlias;
    }

    @PostConstruct
    public void init() {
        if (nodes == null || nodes.isEmpty()) {
            log.error("No Bitcoin nodes configured. Please check 'bitcoin.nodes' in application.properties.");
            throw new IllegalStateException("No Bitcoin nodes configured.");
        }

        for (BitcoinNodeProperties props : nodes) {
            try {
                URL url = new URL(props.getUrl());
                JsonRpcHttpClient client = new JsonRpcHttpClient(url);

                String auth = props.getRpcUser() + ":" + props.getRpcPassword();
                String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Basic " + encodedAuth);
                client.setHeaders(headers);

                BitcoinRpcApi rpcApi = ProxyUtil.createClientProxy(
                        getClass().getClassLoader(),
                        BitcoinRpcApi.class,
                        client);

                clients.put(props.getAlias(), rpcApi);
                log.info("Initialized Bitcoin RPC client for node: {}", props.getAlias());
            } catch (Exception e) {
                log.error("Failed to initialize Bitcoin node {}: {}", props.getAlias(), e.getMessage(), e);
                throw new IllegalArgumentException("Invalid Bitcoin node configuration", e);
            }
        }

        if (primaryNodeAlias != null && clients.containsKey(primaryNodeAlias)) {
            activeClient.set(clients.get(primaryNodeAlias));
            activeClientAlias.set(primaryNodeAlias);
            log.info("Initial primary Bitcoin node set to: {}", primaryNodeAlias);
        } else {
            BitcoinNodeProperties firstNode = nodes.get(0);
            activeClient.set(clients.get(firstNode.getAlias()));
            activeClientAlias.set(firstNode.getAlias());
            log.warn("Primary node alias '{}' not found or not specified. Falling back to first configured node: {}",
                    primaryNodeAlias, firstNode.getAlias());
        }

        performHealthCheck();
    }

    @Scheduled(fixedRateString = "${bitcoin.health-check.interval-ms:10000}")
    public void performHealthCheck() {
        log.debug("Performing Bitcoin node health check...");
        long maxObservedHeight = 0;
        String bestNodeAlias = null;

        for (Map.Entry<String, BitcoinRpcApi> entry : clients.entrySet()) {
            String alias = entry.getKey();
            BitcoinRpcApi client = entry.getValue();
            try {
                long currentHeight = client.getBlockCount();
                nodeBlockHeights.put(alias, currentHeight);
                log.debug("Node {} current block height: {}", alias, currentHeight);

                if (currentHeight > maxObservedHeight) {
                    maxObservedHeight = currentHeight;
                    bestNodeAlias = alias;
                }
            } catch (Exception e) {
                log.error("Health check failed for Bitcoin node {}: {}", alias, e.getMessage());
                nodeBlockHeights.remove(alias);
            }
        }

        String currentActiveAlias = activeClientAlias.get();
        if (currentActiveAlias != null && nodeBlockHeights.containsKey(currentActiveAlias)) {
            Long currentActiveHeight = nodeBlockHeights.get(currentActiveAlias);
            if (currentActiveHeight != null && (maxObservedHeight - currentActiveHeight) <= maxBlockHeightDifference) {
                log.debug("Current primary node {} is healthy.", currentActiveAlias);
                return;
            } else {
                log.warn("Current primary node {} is unhealthy or behind. Initiating failover.", currentActiveAlias);
            }
        } else if (currentActiveAlias != null) {
            log.warn("Current primary node {} is down. Initiating failover.", currentActiveAlias);
        }

        if (bestNodeAlias != null && !bestNodeAlias.equals(currentActiveAlias)) {
            activeClient.set(clients.get(bestNodeAlias));
            activeClientAlias.set(bestNodeAlias);
            log.info("Failover successful. New primary Bitcoin node set to: {}", bestNodeAlias);
        } else if (bestNodeAlias == null) {
            log.error("No healthy Bitcoin nodes found. Service might be degraded.");
            activeClient.set(null);
            activeClientAlias.set(null);
        }
    }

    public BitcoinRpcApi getActiveClient() {
        BitcoinRpcApi client = activeClient.get();
        if (client == null) {
            throw new IllegalStateException("No active Bitcoin RPC client available. All configured nodes might be down.");
        }
        return client;
    }
}