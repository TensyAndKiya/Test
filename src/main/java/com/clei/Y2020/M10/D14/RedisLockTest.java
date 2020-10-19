package com.clei.Y2020.M10.D14;

import com.clei.utils.OkHttpUtil;
import com.clei.utils.PrintUtil;
import com.clei.utils.StringUtil;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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

        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();

        long begin = System.currentTimeMillis();
        CountDownLatch latch = new CountDownLatch(times);
        while (times > 0) {
            executor.submit(() -> {
                String result = OkHttpUtil.doGetRequest(url);

                // 0处理失败 1处理中 2处理成功 3处理出错
                if (StringUtil.isEmpty(result)) {
                    list.add(0);
                } else if (result.equals("处理中...")) {
                    list.add(1);
                } else if (result.equals("处理成功...")) {
                    list.add(2);
                } else if (result.equals("处理出错...")) {
                    list.add(3);
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

        int a = 0, b = 0, c = 0, d = 0;
        for (Integer i : list) {
            switch (i) {
                case 0:
                    a++;
                    break;
                case 1:
                    b++;
                    break;
                case 2:
                    c++;
                    break;
                case 3:
                    d++;
                    break;
                default:
                    PrintUtil.dateLine("未知结果类型");
            }
        }

        PrintUtil.println("耗时 ： {}ms", end - begin);
        PrintUtil.dateLine("处理成功... {}", c);
        PrintUtil.dateLine("处理中... {}", b);
        PrintUtil.dateLine("处理失败... {}", a);
        PrintUtil.dateLine("处理出错... {}", d);
    }

}
