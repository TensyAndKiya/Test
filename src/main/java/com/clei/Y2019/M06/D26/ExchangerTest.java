package com.clei.Y2019.M06.D26;

import com.clei.utils.PrintUtil;
import com.clei.utils.ThreadUtil;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


/**
 * 并发工具类之Exchanger
 * 例子 儿子放学回家，母亲下班回家
 *
 * @author KIyA
 */
public class ExchangerTest {

    private static final Exchanger<String> EXCHANGER = new Exchanger<>();

    public static void main(String[] args) {
        ThreadPoolExecutor pool = ThreadUtil.pool();
        pool.execute(new SonGoHome());
        pool.execute(new MotherGoHome());
        pool.shutdown();
    }

    private static class SonGoHome implements Runnable {
        @Override
        public void run() {
            PrintUtil.log("儿子上完学开始回家");
            try {
                Thread.sleep((long) (Math.random() * 5000));
            } catch (Exception e) {
                PrintUtil.log("sleep出错", e);
            }
            PrintUtil.log("儿子到家了");
            try {
                // String message = exchanger.exchange("啊哈！");
                String message = EXCHANGER.exchange("我要出去玩", 2, TimeUnit.SECONDS);
                PrintUtil.log(message);
                PrintUtil.log("你打牌我打游戏。。");
            } catch (InterruptedException e) {
                PrintUtil.log("被打断", e);
            } catch (TimeoutException e) {
                // 时间等完了
                PrintUtil.log("老妈再不回来就要饿死了。。啊呜呜呜呜", e);
            }

        }
    }

    private static class MotherGoHome implements Runnable {
        @Override
        public void run() {
            PrintUtil.log("母亲上完班开始回家");
            try {
                Thread.sleep((long) (Math.random() * 5000));
            } catch (Exception e) {
                PrintUtil.log("sleep出错", e);
            }
            PrintUtil.log("母亲到家了");
            try {
                String message = EXCHANGER.exchange("老娘出去打牌了", 2, TimeUnit.SECONDS);
                PrintUtil.log(message);
                PrintUtil.log("玩锤子你玩，滚去做作业！");
            } catch (InterruptedException e) {
                PrintUtil.log("被打断", e);
            } catch (TimeoutException e) {
                // 时间等完了
                PrintUtil.log("这小兔崽子还没到家，去找到并打一顿", e);
            }
        }
    }
}
