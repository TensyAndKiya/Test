package com.clei.Y2019.M06.D26;

import java.util.concurrent.Exchanger;
import static com.clei.utils.PrintUtil.println;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 并发工具类之Exchanger
 * 例子 儿子放学回家，母亲下班回家
 */
public class ExchangerTest {
    private static Exchanger<String> exchanger = new Exchanger<>();
    // permits isFair
    // private static Semaphore semaphore = new Semaphore(3,true);
    public static void main(String[] args) {
        new Thread(new SonGoHome()).start();
        new Thread(new MotherGoHome()).start();
    }

    private static class SonGoHome implements Runnable{
        @Override
        public void run() {
            println("儿子上完学开始回家");
            try{
                Thread.sleep((long) (Math.random() * 5000));
            }catch (Exception e){
                // 吃掉异常
            }
            println("儿子到家了");
            try {
                // String message = exchanger.exchange("啊哈！");
                String message = exchanger.exchange("我要出去玩",2, TimeUnit.SECONDS);
                println(message);
                println("你打牌我打游戏。。");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                //时间等完了
                println("老妈再不回来就要饿死了。。啊呜呜呜呜");
            }

        }
    }

    private static class MotherGoHome implements Runnable{
        @Override
        public void run() {
            println("母亲上完班开始回家");
            try{
                Thread.sleep((long) (Math.random() * 5000));
            }catch (Exception e){
                // 吃掉异常
            }
            println("母亲到家了");
            try {
                String message  = exchanger.exchange("老娘出去打牌了",2, TimeUnit.SECONDS);
                println(message);
                println("玩锤子你玩，滚去做作业！");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                //时间等完了
                println("这小兔崽子还没到家，去找到并打一顿");
            }
        }
    }
}
