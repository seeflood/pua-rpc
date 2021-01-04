package pua.rpc.demo.client;


import pua.rpc.demo.server.HelloService;
import pua.rpc.framework.consumer.RpcProxyFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BootStrap {

    public static void main(String[] args) throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-client.xml");
        RpcProxyFactory rpcProxy = context.getBean(RpcProxyFactory.class);

        HelloService helloService = rpcProxy.getProxy(HelloService.class);
        System.out.println("proxy created");
        String result = helloService.hello("World");
        System.out.println(result);

        System.exit(0);
    }
}

