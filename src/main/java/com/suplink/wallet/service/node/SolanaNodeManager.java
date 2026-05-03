package com.suplink.wallet.service.node;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.p2p.solanaj.rpc.RpcClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Component
@ConfigurationProperties(prefix = "solana")
public class SolanaNodeManager {

    @Data
    public static class ClusterProperties {
        private String alias;
        private String url;
    }

    private ClusterProperties primary;
    private ClusterProperties standby;

    @Value("${solana.health-check.max-block-height-difference:20}")
    private long maxBlockHeightDifference;

    private RpcClient primaryClient;
    private RpcClient standbyClient;

    private final AtomicReference<RpcClient> activeClient = new AtomicReference<>();
    private final AtomicReference<String> activeClientAlias = new AtomicReference<>();

    public void setCluster(Map<String, ClusterProperties> cluster) {
        this.primary = cluster.get("primary");
        this.standby = cluster.get("standby");
    }

    @PostConstruct
    public void init() {
        if (primary == null || standby == null) {
            log.error("Primary and/or standby Solana cluster properties are not configured. Check 'solana.cluster.*'");
            throw new IllegalStateException("Solana cluster configuration is incomplete.");
        }

        this.primaryClient = new RpcClient(primary.getUrl());
        this.standbyClient = new RpcClient(standby.getUrl());

        log.info("Initialized RpcClient for primary ({}) and standby ({}) Solana clusters.", primary.getAlias(), standby.getAlias());

        activeClient.set(primaryClient);
        activeClientAlias.set(primary.getAlias());
        log.info("Initial active Solana cluster set to: {}", primary.getAlias());

        performHealthCheck();
    }

    @Scheduled(fixedRateString = "${solana.health-check.interval-ms:15000}")
    public void performHealthCheck() {
        log.debug("Performing Solana cluster health check...");
        long primarySlot = -1;
        long standbySlot = -1;

        try {
            primarySlot = primaryClient.getApi().getSlot();
            log.debug("Primary SOL cluster ({}) slot: {}", primary.getAlias(), primarySlot);
        } catch (Exception e) {
            log.warn("Health check failed for primary SOL cluster ({}): {}", primary.getAlias(), e.getMessage());
        }

        try {
            standbySlot = standbyClient.getApi().getSlot();
            log.debug("Standby SOL cluster ({}) slot: {}", standby.getAlias(), standbySlot);
        } catch (Exception e) {
            log.warn("Health check failed for standby SOL cluster ({}): {}", standby.getAlias(), e.getMessage());
        }

        String currentActiveAlias = activeClientAlias.get();

        if (primary.getAlias().equals(currentActiveAlias)) {
            boolean primaryIsUnhealthy = (primarySlot == -1) || (standbySlot > primarySlot && (standbySlot - primarySlot) > maxBlockHeightDifference);
            if (primaryIsUnhealthy && standbySlot != -1) {
                log.warn("Primary SOL cluster '{}' is unhealthy or lagging. Failing over to standby cluster '{}'.", primary.getAlias(), standby.getAlias());
                activeClient.set(standbyClient);
                activeClientAlias.set(standby.getAlias());
            }
        } else if (standby.getAlias().equals(currentActiveAlias)) {
            if (primarySlot != -1) {
                log.info("Primary SOL cluster '{}' is back online. Failing back to primary.", primary.getAlias());
                activeClient.set(primaryClient);
                activeClientAlias.set(primary.getAlias());
            }
        }
    }

    public RpcClient getActiveClient() {
        RpcClient client = activeClient.get();
        if (client == null) {
            throw new IllegalStateException("No active Solana cluster available.");
        }
        return client;
    }
}