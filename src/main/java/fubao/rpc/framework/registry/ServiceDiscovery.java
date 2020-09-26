package fubao.rpc.framework.registry;

import fubao.rpc.framework.common.model.URL;

public interface ServiceDiscovery {
    URL discover(String serviceName);
}
