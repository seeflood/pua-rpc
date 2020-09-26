package fubao.rpc.demo.server;

import fubao.rpc.framework.provider.annotation.FubaoRPC;

@FubaoRPC(HelloService.class)
public class HelloServiceImpl implements HelloService {

    @Override
    public String hello(String name) {
        return "Hello  " + name;
    }
}