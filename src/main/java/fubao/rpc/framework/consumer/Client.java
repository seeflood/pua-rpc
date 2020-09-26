package fubao.rpc.framework.consumer;

import fubao.rpc.framework.common.model.Response;
import fubao.rpc.framework.common.model.Invocation;

public interface Client {
    Response send(Invocation invocation, String host, String port) throws Exception;
}
