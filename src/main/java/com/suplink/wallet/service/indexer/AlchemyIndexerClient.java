package com.suplink.wallet.service.indexer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.suplink.wallet.enums.ChainType;
import com.suplink.wallet.model.TransactionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class AlchemyIndexerClient implements IBlockchainIndexer {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    @Value("${indexer.alchemy.api-key}")
    private String apiKey;

    @Value("${indexer.alchemy.base-url.btc}")
    private String btcBaseUrl;

    @Value("${indexer.alchemy.base-url.eth}")
    private String ethBaseUrl;

    public AlchemyIndexerClient() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public BigDecimal getAccountBalance(ChainType chainType, String address) {
        if (chainType != ChainType.ETH) {
            log.warn("getAccountBalance via Alchemy is primarily supported for Ethereum (ETH). BTC balance requires transaction parsing.");
            // For BTC, a true balance requires summing UTXOs, which is a heavy operation via alchemy_getAssetTransfers.
            // Returning zero as a placeholder.
            return BigDecimal.ZERO;
        }

        String url = ethBaseUrl + apiKey;
        String requestBody = String.format(
            "{\"jsonrpc\":\"2.0\",\"id\":1,\"method\":\"eth_getBalance\",\"params\":[\"%s\",\"latest\"]}",
            address
        );

        try {
            Map<String, Object> response = sendRequest(url, requestBody);
            String balanceHex = (String) response.get("result");
            if (balanceHex != null) {
                // The result is a hex string, convert it to a BigDecimal representing Ether
                return new BigDecimal(new java.math.BigInteger(balanceHex.substring(2), 16)).divide(new BigDecimal("1e18"));
            }
        } catch (Exception e) {
            log.error("Failed to get ETH balance for address {} from Alchemy", address, e);
        }
        return BigDecimal.ZERO;
    }

    @Override
    public List<TransactionDto> getTransactionsByAddress(ChainType chainType, String address) {
        // Note: This is a simplified implementation. A full implementation would need to handle pagination.
        String url;
        String asset;
        switch (chainType) {
            case BTC:
                url = btcBaseUrl + apiKey;
                asset = "BTC";
                break;
            case ETH:
                url = ethBaseUrl + apiKey;
                asset = "ETH";
                break;
            default:
                log.warn("getTransactionsByAddress via Alchemy is not supported for chain: {}", chainType);
                return List.of();
        }

        String requestBody = String.format(
            "{\"jsonrpc\":\"2.0\",\"id\":1,\"method\":\"alchemy_getAssetTransfers\",\"params\":[{\"fromAddress\":\"%s\",\"category\":[\"external\",\"internal\"],\"withMetadata\":true,\"excludeZeroValue\":true,\"maxCount\":\"0x64\",\"order\":\"desc\"}]}",
            address
        );
        // A similar request for "toAddress" would also be needed for a complete history.

        try {
            Map<String, Object> response = sendRequest(url, requestBody);
            // TODO: Parse the complex response from alchemy_getAssetTransfers into List<TransactionDto>
            log.info("Received asset transfers from Alchemy for address {}: {}", address, response);
        } catch (Exception e) {
            log.error("Failed to get transactions for address {} from Alchemy", address, e);
        }

        return List.of(); // Placeholder
    }

    @Override
    public List<ChainType> getSupportedChains() {
        return List.of(ChainType.BTC, ChainType.ETH);
    }

    private Map<String, Object> sendRequest(String url, String requestBody) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            log.error("Alchemy request failed with status code {}: {}", response.statusCode(), response.body());
            throw new IOException("Alchemy request failed with status code " + response.statusCode());
        }

        return objectMapper.readValue(response.body(), Map.class);
    }
}