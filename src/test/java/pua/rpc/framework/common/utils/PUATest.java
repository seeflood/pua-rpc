package pua.rpc.framework.common.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class PUATest {
    @Test
    public void testEncourage() {
        for (int i = 0; i < 10; i++) {
            String encourage = PUA.encourage();
            System.out.println(encourage);
        }
    }

    @Test
    public void testCriticize() {
        for (int i = 0; i < 10; i++) {
            String w = PUA.criticize();
            System.out.println(w);
        }
    }
}