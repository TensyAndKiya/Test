package com.clei.Y2019.M05.D15;

import com.clei.utils.PrintUtil;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class IsBetweenDayTest {

    private static AtomicInteger num = new AtomicInteger(0);

    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                50,
                101,
                5,
                TimeUnit.MINUTES,
                new ArrayBlockingQueue<>(15),
                new IsBetweenDayTest.CustomThreadFactory(),
                new IsBetweenDayTest.CustomRejectedExecutionHandler()
        );

        int times = 100000001;
        for (int i = 1; i < times; i++) {
            PrintUtil.log("提交第 " + i + " 个任务！");
            executor.execute(new MyRunnable());
        }
        // executor.shutdown();
    }

    /**
     * 判断 一个时刻是否在两个时刻之间 HH:mm:ss格式的
     *
     * @param dayTime
     * @param dayStartTime
     * @param dayEndTime
     * @return
     */
    private static boolean isBetween(String dayTime, String dayStartTime, String dayEndTime) {
        return dayTime.compareTo(dayStartTime) > -1 && dayTime.compareTo(dayEndTime) < 1;
    }

    private static class MyRunnable implements Runnable {

        @Override
        public void run() {
            String date = "07:01:01";
            String date1 = "07:00:00";
            String date2 = "22:00:00";
            isBetween(date, date1, date2);
            PrintUtil.log("第" + num.addAndGet(1) + "个任务执行完毕！");
        }
    }

    private static class CustomThreadFactory implements ThreadFactory {

        private AtomicInteger ai = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable r) {
            String name = "KIyA_ThreadFactory_Thread_" + ai.addAndGet(1);
            PrintUtil.log("创建了线程 ： {}", name);
            return new Thread(r, name);
        }
    }

    private static class CustomRejectedExecutionHandler implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            try {
                //阻塞方法
                executor.getQueue().put(r);
            } catch (InterruptedException e) {
                PrintUtil.log("放入阻塞队列失败！！！", e);
            }
        }
    }
}
