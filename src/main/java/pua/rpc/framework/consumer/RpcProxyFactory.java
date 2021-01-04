package pua.rpc.framework.consumer;

import pua.rpc.framework.common.model.Response;
import pua.rpc.framework.common.model.URL;
import pua.rpc.framework.common.utils.LogUtils;
import pua.rpc.framework.common.utils.StringUtils;
import pua.rpc.framework.common.model.Invocation;
import pua.rpc.framework.registry.ServiceDiscovery;
import pua.rpc.framework.common.model.Invocation;
import pua.rpc.framework.common.model.Response;
import pua.rpc.framework.common.model.URL;
import pua.rpc.framework.common.utils.LogUtils;
import pua.rpc.framework.common.utils.StringUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

public class RpcProxyFactory {
    private ServiceDiscovery serviceDiscovery;
    private Client client;

    public <T> T getProxy(Class<T> interfaceClass) {
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[]{interfaceClass},
                (Object proxy, Method method, Object[] args) -> {
//            1. prepare request object
                    Invocation invocation = new Invocation();
                    invocation.setRequestId(UUID.randomUUID().toString());
                    invocation.setInterfaceName(method.getDeclaringClass().getName());
                    invocation.setMethodName(method.getName());
                    invocation.setParameterTypes(method.getParameterTypes());
                    invocation.setParameters(args);
//            2.get address
                    String serviceName = interfaceClass.getName();
                    URL url = serviceDiscovery.discover(serviceName);
                    if (url == null || StringUtils.isEmpty(url.getHost()) || StringUtils.isEmpty(url.getPort())) {
                        throw new RuntimeException("URL is empty,can not send request.");
                    }
                    String host = url.getHost();
                    String port = url.getPort();
                    LogUtils.info(this, "Prepare to send request.host:" + host + " port:" + port);
//            3. send request
                    Response response = client.send(invocation, host, port);
                    if (response.hasException()) {
                        throw response.getException();
                    }
                    return response.getResult();
                });
    }

    public void setServiceDiscovery(ServiceDiscovery serviceDiscovery) {
        this.serviceDiscovery = serviceDiscovery;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
