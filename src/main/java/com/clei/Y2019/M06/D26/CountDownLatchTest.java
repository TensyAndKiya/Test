package com.clei.Y2019.M06.D26;

import com.clei.utils.ThreadUtil;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.clei.utils.PrintUtil.println;

/**
 * 并发工具类之CountDownLatch
 */
public class CountDownLatchTest {

    private static CountDownLatch countDownLatch = new CountDownLatch(2);

    public static void main(String[] args) {
        ThreadPoolExecutor pool = ThreadUtil.pool();

        pool.execute(() -> {
            try {
                Thread.sleep(2500);
            } catch (Exception e) {
                // 吃掉异常
            }
            println("吃饭");
            countDownLatch.countDown();
        });

        pool.execute(() -> {
            try {
                Thread.sleep(500);
            } catch (Exception e) {
                // 吃掉异常
            }
            println("学习");
            countDownLatch.countDown();
        });

        pool.execute(() -> {
            try {
                // 一直要吃饭后学习后才睡觉
                // countDownLatch.await();
                // 就等一段时间 有返回boolean值的哦。。
                // 就等2秒钟。。没搞完老子也要睡觉
                countDownLatch.await(2, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                // 吃掉异常
                println("异常！！！");
            }
            println("睡觉");
        });

        pool.shutdown();
    }
}
