package com.clei.Y2020.M10.D14;

import com.clei.utils.OkHttpUtil;
import com.clei.utils.PrintUtil;
import com.clei.utils.StringUtil;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 疯狂地去访问redis锁接口看看可用性
 *
 * @author KIyA
 */
public class RedisLockTest {

    public static void main(String[] args) throws InterruptedException {
        PrintUtil.log("request begin.");
        request("http://localhost:8888/redis/setNx", 600);
        PrintUtil.log("request end.");
    }

    private static void request(String url, int times) throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                4,
                9,
                1,
                TimeUnit.MINUTES,
                new LinkedBlockingQueue<>(50),
                (r, e) -> {
                    try {
                        e.getQueue().put(r);
                    } catch (InterruptedException interruptedException) {
                        PrintUtil.log("将任务放入阻塞队列出错", e);
                    }
                }
        );

        long begin = System.currentTimeMillis();
        CountDownLatch latch = new CountDownLatch(times);

        AtomicInteger failInt = new AtomicInteger(0);
        AtomicInteger processingInt = new AtomicInteger(0);
        AtomicInteger successInt = new AtomicInteger(0);
        AtomicInteger errorInt = new AtomicInteger(0);

        while (times > 0) {
            executor.submit(() -> {
                String result = OkHttpUtil.doGet(url);
                // 0处理失败 1处理中 2处理成功 3处理出错
                if (StringUtil.isEmpty(result)) {
                    failInt.incrementAndGet();
                } else if ("处理中...".equals(result)) {
                    processingInt.incrementAndGet();
                } else if ("处理成功...".equals(result)) {
                    successInt.incrementAndGet();
                } else if ("处理出错...".equals(result)) {
                    errorInt.incrementAndGet();
                }
                // 一个任务执行完毕
                latch.countDown();

            });
            times--;
        }

        // 阻塞住 等待所有任务执行完毕
        latch.await();
        executor.shutdown();
        long end = System.currentTimeMillis();

        PrintUtil.println("耗时 ： {}ms", end - begin);
        PrintUtil.log("处理成功... {}", successInt.get());
        PrintUtil.log("处理中... {}", processingInt.get());
        PrintUtil.log("处理失败... {}", failInt.get());
        PrintUtil.log("处理出错... {}", errorInt.get());
    }
}
