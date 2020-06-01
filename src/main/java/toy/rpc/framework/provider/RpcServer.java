package toy.rpc.framework.provider;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import toy.rpc.framework.common.model.URL;
import toy.rpc.framework.common.utils.StringUtils;
import toy.rpc.framework.provider.annotation.ToyRPC;
import toy.rpc.framework.registry.ServiceRegistry;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class RpcServer implements InitializingBean {

    protected String serviceAddress;
    protected ServiceRegistry serviceRegistry;
    protected URL url;
    protected LocalServiceContext localServiceContext;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (serviceAddress == null || serviceRegistry == null) {
            throw new NullPointerException();
        }
        String[] split = serviceAddress.split(":");
        if (StringUtils.isEmpty(split[0]) || StringUtils.isEmpty(split[1])) {
            throw new IllegalArgumentException();
        }
        url = new URL(split[0], split[1]);
        try {
//          2. start server
            start();
//          3. service register
            Set<String> interfaceNames = localServiceContext.getHandlerMap().keySet();
            serviceRegistry.register(interfaceNames, serviceAddress);
//            4.after register hook
            afterRegister();
//            5.hang
            hang();
        } finally {
            close();
        }
    }

    protected void afterRegister() {

    }

    protected void hang() throws IOException, InterruptedException {
        System.in.read();
    }


    protected abstract void start() throws InterruptedException;

    protected abstract void close();

    public void setServiceAddress(String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }

    public void setServiceRegistry(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
    }

    public void setLocalServiceContext(LocalServiceContext localServiceContext) {
        this.localServiceContext = localServiceContext;
    }
}
