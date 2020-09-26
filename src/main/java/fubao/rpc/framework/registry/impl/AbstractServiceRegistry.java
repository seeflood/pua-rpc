package fubao.rpc.framework.registry.impl;

import fubao.rpc.framework.registry.ServiceRegistry;

public abstract class AbstractServiceRegistry implements ServiceRegistry {
    protected String registryAddress;

    public AbstractServiceRegistry(String registryAddress) {
        this.registryAddress = registryAddress;
    }

}
