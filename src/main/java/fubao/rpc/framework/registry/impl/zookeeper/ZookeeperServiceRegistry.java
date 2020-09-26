package fubao.rpc.framework.registry.impl.zookeeper;

import fubao.rpc.framework.registry.impl.AbstractServiceRegistry;

import java.util.Collection;

public class ZookeeperServiceRegistry extends AbstractServiceRegistry {

    public ZookeeperServiceRegistry(String registryAddress) {
        super(registryAddress);
    }

    @Override
    public void register(Collection<String> interfaceNames, String serverAddress) {
//        TODO
    }
}
