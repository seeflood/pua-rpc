package toy.rpc.demo.server;

import toy.rpc.framework.provider.annotation.ToyRPC;

@ToyRPC(HelloService.class)
public class HelloServiceImpl implements HelloService {

    @Override
    public String hello(String name) {
        return "Hello  " + name;
    }
}