package toy.rpc.framework.registry;

import toy.rpc.framework.common.model.URL;

public interface ServiceDiscovery {
    URL discover(String serviceName);
}
