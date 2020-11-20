package com.clei.Y2019.M06.D13;

import com.clei.utils.PrintUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class CallableTest {
    public static void main(String[] args) throws Exception{

        //Callable使用
        MyThread3 callable = new MyThread3();
        FutureTask<String> futureTask = new FutureTask<>(callable);
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.submit(futureTask);
        PrintUtil.dateLine("结果1： " + futureTask.get(1, TimeUnit.SECONDS));

        Future<String> future = executor.submit(callable);
        PrintUtil.dateLine("结果2： " + future.get(1, TimeUnit.SECONDS));

        executor.shutdown();
    }
}


