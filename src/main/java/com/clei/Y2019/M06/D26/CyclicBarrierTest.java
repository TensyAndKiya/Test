package com.clei.Y2019.M06.D26;

import com.clei.utils.PrintUtil;
import com.clei.utils.ThreadUtil;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadPoolExecutor;

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
    private final static CyclicBarrier CYCLIC_BARRIER = new CyclicBarrier(2, () -> PrintUtil.log("出发！！！"));

    public static void main(String[] args) {
        ThreadPoolExecutor pool = ThreadUtil.pool();
        for (int i = 'a'; i < 'a' + 6; i++) {
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
            try {
                Thread.sleep((long) (Math.random() * 3000));
            } catch (Exception e) {
                PrintUtil.log("sleep出错", e);
            }
            PrintUtil.log(name + "到了");
            //到达屏障
            try {
                CYCLIC_BARRIER.await();
                // CYCLIC_BARRIER.await(2, TimeUnit.SECONDS);
            } catch (Exception e) {
                PrintUtil.log(name + "异常", e);
            }
            PrintUtil.log(name + "出发");
        }
    }
}
