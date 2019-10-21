package com.clei.Y2019.M06.D26;

import java.time.LocalDateTime;
import java.util.concurrent.Semaphore;
import static com.clei.utils.DateUtil.format;
import static com.clei.utils.PrintUtil.println;

/**
 * 并发工具类之Semaphore
 * 例子 一个公厕只有两个位置 同时只有两个人能上厕所
 */
public class SemaphoreTest {
    private static String PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";
    private static Semaphore semaphore = new Semaphore(2);
    // permits isFair
    // private static Semaphore semaphore = new Semaphore(3,true);
    public static void main(String[] args) {
        for(int i = 'a'; i < 'a' + 5; i ++){
            new Thread(new Task(String.valueOf((char)i))).start();
        }
    }

    private static class Task implements Runnable{
        private String name;
        private Task(String name){
            this.name = name;
        }
        @Override
        public void run() {
            println(name + "等待上厕所" + format(LocalDateTime.now(),PATTERN));
            try{
                // semaphore.tryAcquire(2, TimeUnit.SECONDS);
                semaphore.acquire();
                // semaphore.acquire(2); 屁股大 占两个位置
                println(name + "开始上厕所" + format(LocalDateTime.now(),PATTERN));
                try{
                    Thread.sleep((long) (Math.random() * 3000));
                }catch (Exception e){
                    // 吃掉异常
                }
                println(name + "上完了" + format(LocalDateTime.now(),PATTERN));
                semaphore.release();
            }catch (Exception e){
                println("异常！！！");
            }
        }
    }
}
