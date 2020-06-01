package toy.rpc.framework.registry.load.balance;

import toy.rpc.framework.common.model.URL;

import java.util.List;

public interface LoadBalanceStrategy {
    URL choose(List<URL> urls);
}
