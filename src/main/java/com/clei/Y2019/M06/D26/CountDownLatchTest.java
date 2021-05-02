package com.clei.Y2019.M06.D26;

import com.clei.utils.PrintUtil;
import com.clei.utils.ThreadUtil;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 并发工具类之CountDownLatch
 *
 * @author KIyA
 */
public class CountDownLatchTest {

    private static final CountDownLatch COUNT_DOWN_LATCH = new CountDownLatch(2);

    public static void main(String[] args) {
        ThreadPoolExecutor pool = ThreadUtil.pool();

        pool.execute(() -> {
            try {
                Thread.sleep(2500);
            } catch (Exception e) {
                PrintUtil.log("sleep出错", e);
            }
            PrintUtil.log("吃饭");
            COUNT_DOWN_LATCH.countDown();
        });

        pool.execute(() -> {
            try {
                Thread.sleep(500);
            } catch (Exception e) {
                PrintUtil.log("sleep出错", e);
            }
            PrintUtil.log("学习");
            COUNT_DOWN_LATCH.countDown();
        });

        pool.execute(() -> {
            try {
                // 一直要吃饭后学习后才睡觉
                // countDownLatch.await();
                // 就等一段时间 有返回boolean值的哦。。
                // 就等2秒钟。。没搞完老子也要睡觉
                COUNT_DOWN_LATCH.await(2, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                PrintUtil.log("异常！！！", e);
            }
            PrintUtil.log("睡觉");
        });

        pool.shutdown();
    }
}
