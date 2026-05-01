package com.suplink.wallet.service.node;

import com.googlecode.jsonrpc4j.JsonRpcMethod;
import com.googlecode.jsonrpc4j.JsonRpcParam;

import java.util.Map;

/**
 * Interface defining the Bitcoin Core RPC methods we will use.
 * jsonrpc4j will create a proxy client based on this interface.
 */
public interface BitcoinRpcApi {

    @JsonRpcMethod("getblockchaininfo")
    Map<String, Object> getBlockchainInfo();

    @JsonRpcMethod("getblockcount")
    long getBlockCount();

    @JsonRpcMethod("getbestblockhash")
    String getBestBlockHash();

    @JsonRpcMethod("getblockhash")
    String getBlockHash(@JsonRpcParam("height") long height);

    @JsonRpcMethod("getblock")
    Map<String, Object> getBlock(@JsonRpcParam("blockhash") String blockhash, @JsonRpcParam("verbosity") int verbosity);

    @JsonRpcMethod("getrawtransaction")
    Map<String, Object> getRawTransaction(@JsonRpcParam("txid") String txid, @JsonRpcParam("verbose") boolean verbose);

}