package pua.rpc.framework.registry;

import pua.rpc.framework.common.model.URL;
import pua.rpc.framework.common.model.URL;

public interface ServiceDiscovery {
    URL discover(String serviceName);
}
