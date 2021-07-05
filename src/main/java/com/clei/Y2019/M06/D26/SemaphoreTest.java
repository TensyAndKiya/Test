package com.clei.Y2019.M06.D26;

import com.clei.utils.PrintUtil;
import com.clei.utils.ThreadUtil;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 并发工具类之Semaphore
 * 例子 一个公厕只有两个位置 同时只有两个人能上厕所
 *
 * @author KIyA
 */
public class SemaphoreTest {

    /**
     * 信号量
     */
    private static final Semaphore SEMAPHORE = new Semaphore(2);

    public static void main(String[] args) {
        ThreadPoolExecutor pool = ThreadUtil.pool();
        for (int i = 'a'; i < 'a' + 5; i++) {
            pool.execute(new Task(String.valueOf((char) i)));
        }
        pool.shutdown();
    }

    private static class Task implements Runnable {

        private final String name;

        private Task(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            PrintUtil.log(name + "等待上厕所");
            try {
                // semaphore.tryAcquire(2, TimeUnit.SECONDS);
                SEMAPHORE.acquire();
                // semaphore.acquire(2); 屁股大 占两个位置
                PrintUtil.log(name + "开始上厕所");
                try {
                    Thread.sleep((long) (Math.random() * 3000));
                } catch (Exception e) {
                    PrintUtil.log("sleep出错", e);
                }
                PrintUtil.log(name + "上完了");
                SEMAPHORE.release();
            } catch (Exception e) {
                PrintUtil.log("信号量使用出错", e);
            }
        }
    }
}
