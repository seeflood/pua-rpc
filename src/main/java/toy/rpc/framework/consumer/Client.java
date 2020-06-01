package toy.rpc.framework.consumer;

import toy.rpc.framework.common.model.Invocation;
import toy.rpc.framework.common.model.Response;

public interface Client {
    Response send(Invocation invocation, String host, String port) throws Exception;
}
