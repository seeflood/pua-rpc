package pua.rpc.framework.consumer;

import pua.rpc.framework.common.model.Response;
import pua.rpc.framework.common.model.Invocation;
import pua.rpc.framework.common.model.Invocation;
import pua.rpc.framework.common.model.Response;

public interface Client {
    Response send(Invocation invocation, String host, String port) throws Exception;
}
