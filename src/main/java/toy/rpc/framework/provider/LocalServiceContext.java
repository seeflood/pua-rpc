package toy.rpc.framework.provider;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import toy.rpc.framework.provider.annotation.ToyRPC;

import java.util.HashMap;
import java.util.Map;

public class LocalServiceContext implements ApplicationContextAware{

    protected Map<String, Object> handlerMap = new HashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
//    1, local service export
//        1.1. with annotation
        Map<String, Object> serviceBeanMap = ctx.getBeansWithAnnotation(ToyRPC.class);
        if (serviceBeanMap != null && !serviceBeanMap.isEmpty()) {
            for (Object serviceBean : serviceBeanMap.values()) {
                String interfaceName = serviceBean.getClass().getAnnotation(ToyRPC.class).value().getName();
                handlerMap.put(interfaceName, serviceBean);
            }
        }
    }

    public Map<String, Object> getHandlerMap() {
        return handlerMap;
    }
}
