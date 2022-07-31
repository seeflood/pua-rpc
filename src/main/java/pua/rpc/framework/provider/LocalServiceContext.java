package pua.rpc.framework.provider;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import pua.rpc.framework.provider.annotation.RPC;

import java.util.HashMap;
import java.util.Map;

public class LocalServiceContext implements ApplicationContextAware {

    protected Map<String, Object> handlerMap = new HashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        // 1, local service export
        // 1.1. find all beans with the annotation
        Map<String, Object> serviceBeanMap = ctx.getBeansWithAnnotation(RPC.class);
        if (serviceBeanMap != null && !serviceBeanMap.isEmpty()) {
            for (Object serviceBean : serviceBeanMap.values()) {
                String interfaceName = serviceBean.getClass().getAnnotation(RPC.class).value().getName();
                handlerMap.put(interfaceName, serviceBean);
            }
        }
    }

    public Map<String, Object> getHandlerMap() {
        return handlerMap;
    }
}
