package fubao.rpc.framework.transport.netty;

import fubao.rpc.framework.common.utils.PUA;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import fubao.rpc.framework.common.model.Invocation;
import fubao.rpc.framework.common.model.Response;
import fubao.rpc.framework.common.utils.LogUtils;
import fubao.rpc.framework.common.utils.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class ReflectionInvokeHandler extends SimpleChannelInboundHandler<Invocation> {
    private Map<String, Object> handlerMap;

    public ReflectionInvokeHandler(Map<String, Object> handlerMap) {
        this.handlerMap = handlerMap;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Invocation msg) throws Exception {
        if (msg == null) {
            LogUtils.info(this,"Recieved null response.Ignore it");
            return;
        }
        LogUtils.info(this,"Recieved msg,requestId:"+msg.getRequestId());
        Response response = new Response();
        response.setRequestId(msg.getRequestId());
        try {
            Object result = handle(msg);
            response.setResult(result);
        } catch (Exception e) {
            LogUtils.error(this,"Error when handle message.",e);
            response.setException(e);
        }
//        ctx.writeAndFlush(response);
//        一期先用短连接
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    private Object handle(Invocation msg) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String interfaceName = msg.getInterfaceName();
        String methodName = msg.getMethodName();
        if (StringUtils.isBlank(interfaceName) || StringUtils.isBlank(methodName)) {
            throw new NullPointerException();
        }
        Object bean = null;
        if (!handlerMap.containsKey(interfaceName) || (bean = handlerMap.get(interfaceName)) == null) {
            throw new RuntimeException("Can not find service bean " + interfaceName);
        }
        Class<?> clazz = bean.getClass();
        Method method = clazz.getMethod(methodName, msg.getParameterTypes());
        method.setAccessible(true);
        return method.invoke(bean, msg.getParameters());
    }
}
