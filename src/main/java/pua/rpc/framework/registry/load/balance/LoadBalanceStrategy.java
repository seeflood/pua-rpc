package pua.rpc.framework.registry.load.balance;

import pua.rpc.framework.common.model.URL;
import pua.rpc.framework.common.model.URL;

import java.util.List;

public interface LoadBalanceStrategy {
    URL choose(List<URL> urls);
}
