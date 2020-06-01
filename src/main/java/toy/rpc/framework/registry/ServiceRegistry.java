package toy.rpc.framework.registry;

import java.util.Collection;

public interface ServiceRegistry {
    void register(Collection<String> interfaceNames, String serverAddress);
}
