package pua.rpc.demo.server;

import pua.rpc.framework.provider.annotation.RPC;

@RPC(HelloService.class)
public class HelloServiceImpl implements HelloService {

    @Override
    public String hello(String name) {
        return "Hello  " + name;
    }
}