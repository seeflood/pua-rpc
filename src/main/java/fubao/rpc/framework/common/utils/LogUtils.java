package fubao.rpc.framework.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class LogUtils {
    private static final String KEY_DELIMITER = "@";

    protected static class MonitorLog implements Runnable, Delayed {
        String key;
        Date second;
        Callable<MonitorLog> cleaner;
        AtomicInteger cnt = new AtomicInteger();
        AtomicLong totalRt = new AtomicLong();

        public MonitorLog(String key, Date second, Callable<MonitorLog> cleaner) {
            this.key = key;
            this.second = second;
            this.cleaner = cleaner;
        }

        @Override
        public void run() {
            int count = cnt.get();
            LogUtils.info(this, "Monitor:key=" + key + " second=" + second + " count=" + count + " average rt=" + totalRt.get() / count);
//            remove
            try {
                cleaner.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(second.getTime() + 1000 - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            MonitorLog other = (MonitorLog) o;
            return second.compareTo(other.second);
        }

        public void addCount(long rt) {
            cnt.incrementAndGet();
            totalRt.addAndGet(rt);
        }
    }

    private static final DelayQueue<MonitorLog> delayQueue = new DelayQueue<>();
    private static final Map<String, MonitorLog> key2MonitorLog = new ConcurrentHashMap<>();

    static{
        new Thread(()->{
            while(true){
                try {
                    MonitorLog take = delayQueue.take();
                    take.run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
            }
        }).start();
    }
    public static void monitor(Object obj, String key, int cnt, long rt) {
        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();// 获取当前时间
        String dateFormatted = sdf.format(date);
        String mapKey = key + KEY_DELIMITER + dateFormatted;
        MonitorLog monitorLog;
        try {
            if (key2MonitorLog.containsKey(mapKey)) {
                monitorLog = key2MonitorLog.get(mapKey);
            } else {
                monitorLog = new MonitorLog(key, sdf.parse(dateFormatted), () -> key2MonitorLog.remove(mapKey));
                key2MonitorLog.put(mapKey, monitorLog);
                delayQueue.add(monitorLog);
            }
            monitorLog.addCount(rt);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void info(Object obj, String msg) {
        System.out.println(msg + " " + PUA.encourage());
    }

    public static void error(Object obj, String msg, Throwable e) {
        System.out.println(msg + " " + PUA.criticize());
        e.printStackTrace();
    }
}
