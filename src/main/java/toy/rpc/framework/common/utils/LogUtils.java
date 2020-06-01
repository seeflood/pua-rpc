package toy.rpc.framework.common.utils;

public class LogUtils {

    public static void info(Object obj,String msg){
        System.out.println(msg);
    }
    public static void error(Object obj,String msg,Throwable e){
        System.out.println(msg);
        e.printStackTrace();
    }
}
