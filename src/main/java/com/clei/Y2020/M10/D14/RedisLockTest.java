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
        PrintUtil.dateLine("request begin.");
        request("http://localhost:8888/redis/setNx", 600);
        PrintUtil.dateLine("request end.");
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
                        interruptedException.printStackTrace();
                    }
                }
        );

        AtomicInteger a = new AtomicInteger(0);
        AtomicInteger b = new AtomicInteger(0);
        AtomicInteger c = new AtomicInteger(0);
        AtomicInteger d = new AtomicInteger(0);

        long begin = System.currentTimeMillis();
        CountDownLatch latch = new CountDownLatch(times);
        while (times > 0) {
            executor.submit(() -> {
                String result = OkHttpUtil.doGetRequest(url);

                if (StringUtil.isEmpty(result)) {
                    c.getAndAdd(1);
                } else if (result.equals("处理中...")) {
                    b.getAndAdd(1);
                } else if (result.equals("处理成功...")) {
                    a.getAndAdd(1);
                } else if (result.equals("处理出错...")) {
                    d.getAndAdd(1);
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
        PrintUtil.dateLine("处理成功... {}", a.get());
        PrintUtil.dateLine("处理中... {}", b.get());
        PrintUtil.dateLine("处理失败... {}", c.get());
        PrintUtil.dateLine("处理出错... {}", d.get());
    }

}
