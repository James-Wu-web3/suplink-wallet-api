package com.suplink.wallet.factory;

import com.suplink.wallet.enums.ChainType;
import com.suplink.wallet.service.IChainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ChainServiceFactory {

    private final Map<String, IChainService> serviceMap;

    @Autowired
    public ChainServiceFactory(Map<String, IChainService> serviceMap) {
        this.serviceMap = serviceMap;
    }

    public IChainService getService(ChainType chainType) {
        IChainService service = serviceMap.get(chainType.name());
        if (service == null) {
            throw new IllegalArgumentException("Unsupported chain type: " + chainType);
        }
        return service;
    }
}