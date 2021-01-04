package pua.rpc.framework.registry.impl;

import pua.rpc.framework.registry.ServiceRegistry;
import pua.rpc.framework.registry.ServiceRegistry;

public abstract class AbstractServiceRegistry implements ServiceRegistry {
    protected String registryAddress;

    public AbstractServiceRegistry(String registryAddress) {
        this.registryAddress = registryAddress;
    }

}
