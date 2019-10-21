package com.clei.Y2019.M06.D13;

import java.util.concurrent.*;

public class CallableTest {
    public static void main(String[] args) throws Exception{

        //Callable使用
        MyThread3 callable = new MyThread3();
        FutureTask<String> futureTask = new FutureTask<>(callable);
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.submit(futureTask);
        System.out.println("结果1： " + futureTask.get(1, TimeUnit.SECONDS));

        Future<String> future = executor.submit(callable);
        System.out.println("结果2： " + future.get(1, TimeUnit.SECONDS));

        executor.shutdown();
    }
}


