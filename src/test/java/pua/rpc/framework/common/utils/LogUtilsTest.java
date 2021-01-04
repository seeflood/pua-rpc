package pua.rpc.framework.common.utils;

import org.junit.Test;

import java.util.Scanner;

import static org.junit.Assert.*;

public class LogUtilsTest {

    @Test
    public void monitor() throws InterruptedException {
        LogUtils.monitor(this,"haha",1,100);
        Thread.sleep(500);
        LogUtils.monitor(this,"haha",1,120);
        Thread.sleep(500);
        LogUtils.monitor(this,"haha",1,90);
        Thread.sleep(500);
        LogUtils.monitor(this,"haha",1,50);
        System.out.println("Input anything to exist");
        Scanner s=new Scanner(System.in);
        while(s.hasNext()){
            String next = s.next();
        }
    }
}