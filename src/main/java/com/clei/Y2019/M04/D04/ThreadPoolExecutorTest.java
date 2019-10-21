package com.clei.Y2019.M04.D04;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolExecutorTest {
    /**
     * 线程池初始化方法
     *
     * corePoolSize 核心线程池大小----10
     * maximumPoolSize 最大线程池大小----30
     * keepAliveTime 线程池中超过corePoolSize数目的空闲线程最大存活时间----30+单位TimeUnit
     * TimeUnit keepAliveTime时间单位----TimeUnit.MINUTES
     * workQueue 阻塞队列----new ArrayBlockingQueue<Runnable>(10)====10容量的阻塞队列
     * threadFactory 新建线程工厂----new CustomThreadFactory()====定制的线程工厂
     * rejectedExecutionHandler 当提交任务数超过maxmumPoolSize+workQueue之和时,
     * 						    任务会交给RejectedExecutionHandler来处理
     */
    public static void main(String[] args){
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                4,
                9,
                1,
                TimeUnit.MINUTES,
                new LinkedBlockingQueue<>(10),
                new CustomThreadFactory(),
                new CustomRejectedExecutionHandler()
        );

        for (int i = 0; i < 100; i++) {
            System.out.println("提交第 " + i + " 个任务！");
            executor.execute(()-> {
                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+"执行完毕。。。");
            });
        }
    }

    private static class CustomThreadFactory implements ThreadFactory{
        private AtomicInteger ai = new AtomicInteger(0);
        @Override
        public Thread newThread(Runnable r) {
            String name = "KIyA_ThreadFactory_Thread_"+ai.addAndGet(1);
            System.out.println("创建了线程 ： "+name);
            return new Thread(r, name);
        }
    }

    private static class CustomRejectedExecutionHandler implements RejectedExecutionHandler{
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            try {
                //阻塞方法
                executor.getQueue().put(r);
            } catch (InterruptedException e) {
                System.out.println("放入阻塞队列失败！！！");
                e.printStackTrace();
            }
        }
    }
}
