package com.clei.Y2019.M06.D26;

import com.clei.utils.DateUtil;

import java.util.concurrent.Semaphore;

import static com.clei.utils.PrintUtil.println;

/**
 * 并发工具类之Semaphore
 * 例子 一个公厕只有两个位置 同时只有两个人能上厕所
 *
 * @author KIyA
 */
public class SemaphoreTest {

    private static final Semaphore SEMAPHORE = new Semaphore(2);

    // permits isFair
    // private static Semaphore semaphore = new Semaphore(3,true);
    public static void main(String[] args) {
        for (int i = 'a'; i < 'a' + 5; i++) {
            new Thread(new Task(String.valueOf((char) i))).start();
        }
    }

    private static class Task implements Runnable {

        private final String name;

        private Task(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            println(name + "等待上厕所" + DateUtil.currentDateTime(true));
            try {
                // semaphore.tryAcquire(2, TimeUnit.SECONDS);
                SEMAPHORE.acquire();
                // semaphore.acquire(2); 屁股大 占两个位置
                println(name + "开始上厕所" + DateUtil.currentDateTime(true));
                try {
                    Thread.sleep((long) (Math.random() * 3000));
                } catch (Exception e) {
                    // 吃掉异常
                }
                println(name + "上完了" + DateUtil.currentDateTime(true));
                SEMAPHORE.release();
            } catch (Exception e) {
                println("异常！！！");
            }
        }
    }
}
