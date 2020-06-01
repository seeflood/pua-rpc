package toy.rpc.framework.registry.impl;

import toy.rpc.framework.registry.ServiceRegistry;

import java.util.Collection;
import java.util.Set;

public abstract class AbstractServiceRegistry implements ServiceRegistry {
    protected String registryAddress;

    public AbstractServiceRegistry(String registryAddress) {
        this.registryAddress = registryAddress;
    }

}
