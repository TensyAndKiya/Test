package com.clei.Y2019.M06.D26;

import com.clei.utils.DateUtil;

import java.util.concurrent.CyclicBarrier;

import static com.clei.utils.PrintUtil.println;

/**
 * 并发工具类之CyclicBarrier
 * 例子 发任务 不能单人做 只能两人组队才能行
 *
 * @author KIyA
 */
public class CyclicBarrierTest {

    //一组一组的拦截
    // private static CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
    // 一组到达后先执行该task
    private final static CyclicBarrier CYCLIC_BARRIER = new CyclicBarrier(2, () -> println("出发！！！"));

    public static void main(String[] args) {
        for (int i = 'a'; i < 'a' + 6; i++) {
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
            try {
                Thread.sleep((long) (Math.random() * 3000));
            } catch (Exception e) {
                // 吃掉异常
            }
            println(name + "到了" + DateUtil.currentDateTime(true));
            //到达屏障
            try {
                CYCLIC_BARRIER.await();
                // cyclicBarrier.await(2, TimeUnit.SECONDS);
            } catch (Exception e) {
                // 吃掉异常
                println(name + "异常");
            }
            println(name + "出发" + DateUtil.currentDateTime(true));
        }
    }
}
