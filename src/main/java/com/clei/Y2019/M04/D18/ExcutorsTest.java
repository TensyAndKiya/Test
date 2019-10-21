package com.clei.Y2019.M04.D18;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ExcutorsTest {
    public static void main(String[] args) throws InterruptedException {
        //cachedPoolTest();
        //fixedPoolTest();
        //scheduledPoolTest();
        workStealingPoolTest();
        while(true){

        }
    }
    private static void cachedPoolTest() throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            executorService.submit(new MyRunnable());
            //注释下面这行代码，会创建10个线程，不注释，则复用旧的，只需1个线程就够了
            Thread.sleep(1);
        }
        executorService.shutdown();
    }
    private static void fixedPoolTest() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 10; i++) {
            executorService.submit(new MyRunnable());
        }
        executorService.shutdown();
    }
    private static void scheduledPoolTest() throws InterruptedException {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(3);
        executorService.scheduleAtFixedRate(new MyRunnable(),1,1, TimeUnit.SECONDS);
        //executorService.shutdown();
    }
    private static void workStealingPoolTest() throws InterruptedException {
        ExecutorService executorService = Executors.newWorkStealingPool(2);
        for (int i = 0; i < 10; i++) {
            executorService.submit(new MyRunnable());
        }
        executorService.shutdown();
    }
}


class MyRunnable implements Runnable{
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+" 执行了任务！");
    }
}
