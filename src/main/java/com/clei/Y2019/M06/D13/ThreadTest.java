package com.clei.Y2019.M06.D13;

import com.clei.utils.PrintUtil;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * @author KIyA
 */
public class ThreadTest {

    //哈哈哈
    public static void main(String[] args) {
        MyThread1 thread1 = new MyThread1("Thread1");
        thread1.start();
        MyThread2 thread2 = new MyThread2();
        new Thread(thread2, "Thread2").start();
        new Thread(thread2, "Thread3").start();
        new Thread(thread2, "Thread4").start();
        ExecutorService executor = Executors.newCachedThreadPool();
        MyThread3 thread3 = new MyThread3();
        FutureTask<String> futureTask = new FutureTask<>(thread3);
        executor.submit(futureTask);
        executor.shutdown();
        try {
            PrintUtil.log("Task执行结果： " + futureTask.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        PrintUtil.log("Main线程执行完毕！");
    }
}

class MyThread1 extends Thread {

    public MyThread1() {
        super();
    }

    public MyThread1(String name) {
        super(name);
    }

    @Override
    public void run() {
        PrintUtil.log("进程" + this.getName() + "运行中...");
    }
}

class MyThread2 implements Runnable {

    @Override
    public void run() {
        PrintUtil.log("进程" + Thread.currentThread().getName() + "运行中...");
    }
}

class MyThread3 implements Callable<String> {

    @Override
    public String call() {
        PrintUtil.log("进程" + Thread.currentThread().getName() + "运行中...");
        StringBuilder sb = new StringBuilder();

        try {
            //String filePath=System.getProperty("user.dir")+"\\DesignPrinciple.txt";
            String filePath = ThreadTest.class.getClassLoader().getResource("notes/work/pit.txt").getPath();
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));
            String str;
            while ((str = br.readLine()) != null) {
                sb.append(str + '\n');
            }
            br.close();
            PrintUtil.log("进程" + Thread.currentThread().getName() + "执行完毕!...");
        } catch (RuntimeException e) {
            sb.delete(0, sb.length());
        } catch (Exception e) {
            sb.delete(0, sb.length());
        }
        return sb.toString();
    }
}
