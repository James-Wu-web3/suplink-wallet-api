package com.suplink.wallet.controller;

import com.suplink.wallet.enums.ChainType;
import com.suplink.wallet.factory.ChainServiceFactory;
import com.suplink.wallet.model.BlockDto;
import com.suplink.wallet.model.TransactionDto;
import com.suplink.wallet.service.IChainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wallet/{chainType}")
public class WalletController {

    private final ChainServiceFactory chainServiceFactory;

    @Autowired
    public WalletController(ChainServiceFactory chainServiceFactory) {
        this.chainServiceFactory = chainServiceFactory;
    }

    @GetMapping("/address/convert")
    public String convertAddress(@PathVariable ChainType chainType, @RequestParam String publicKey) {
        IChainService service = chainServiceFactory.getService(chainType);
        return service.convertAddress(publicKey);
    }

    @GetMapping("/address/validate")
    public boolean validAddress(@PathVariable ChainType chainType, @RequestParam String address) {
        IChainService service = chainServiceFactory.getService(chainType);
        return service.validAddress(address);
    }

    @GetMapping("/block/latest")
    public BlockDto getLatestBlock(@PathVariable ChainType chainType) {
        IChainService service = chainServiceFactory.getService(chainType);
        return service.getLatestBlock();
    }

    @GetMapping("/block/{height}/transactions")
    public BlockDto getBlockWithTransactions(@PathVariable ChainType chainType, @PathVariable Long height) {
        IChainService service = chainServiceFactory.getService(chainType);
        return service.getBlockWithTransactions(height);
    }

    @GetMapping("/transaction/hash/{txHash}")
    public TransactionDto getTransactionByHash(@PathVariable ChainType chainType, @PathVariable String txHash) {
        IChainService service = chainServiceFactory.getService(chainType);
        return service.getTransactionByHash(txHash);
    }

    @GetMapping("/transaction/address/{address}")
    public List<TransactionDto> getTransactionsByAddress(@PathVariable ChainType chainType, @PathVariable String address) {
        IChainService service = chainServiceFactory.getService(chainType);
        return service.getTransactionsByAddress(address);
    }
}