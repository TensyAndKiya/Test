package com.clei.Y2019.M06.D26;

import java.time.LocalDateTime;
import java.util.concurrent.CyclicBarrier;
import static com.clei.utils.DateUtil.format;
import static com.clei.utils.PrintUtil.println;

/**
 * 并发工具类之CyclicBarrier
 * 例子 发任务 不能单人做 只能两人组队才能行
 */
public class CyclicBarrierTest {
    //一组一组的拦截
    // private static CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
    // 一组到达后先执行该task
    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(2, () -> println("出发！！！"));
    private static String PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";
    public static void main(String[] args) {
        for(int i = 'a'; i < 'a' + 6; i ++){
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
            try{
                Thread.sleep((long) (Math.random() * 3000));
            }catch (Exception e){
                // 吃掉异常
            }
            println(name + "到了" + format(LocalDateTime.now(),PATTERN));
            //到达屏障
            try {
                cyclicBarrier.await();
                // cyclicBarrier.await(2, TimeUnit.SECONDS);
            } catch (Exception e) {
                // 吃掉异常
                println(name + "异常");
            }
            println(name + "出发" + format(LocalDateTime.now(),PATTERN));
        }
    }
}
