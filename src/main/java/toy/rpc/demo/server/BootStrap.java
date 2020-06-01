package toy.rpc.demo.server;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BootStrap {
    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("spring-server.xml");
    }
}