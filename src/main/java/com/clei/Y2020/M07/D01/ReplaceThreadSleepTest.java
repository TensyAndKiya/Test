package com.clei.Y2020.M07.D01;

import com.clei.utils.PrintUtil;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 使用Thread.sleep不是很优雅
 * 休眠的时间可以用于让其它线程完成工作，也可以减少CPU占用时间
 * 所以，了解一哈
 *
 * @author KIyA
 */
public class ReplaceThreadSleepTest {

    public static void main(String[] args) throws Exception{

        // useTimer();

        useScheduledExecutorService();
    }

    /**
     * 使用Timer [ 还是使用ScheduledExecutorService吧 ]
     *
     * Timer是内部是单一线程
     * Timer对调度的支持是基于绝对时间,而不是相对时间
     * 如果TimerTask抛出未检查的异常，Timer将会产生无法预料的行为
     * Timer线程并不捕获异常，所以TimerTask抛出的未检查的异常会终止timer线程
     */
    private static void useTimer() {
        Timer timer = new Timer();

        TimerTask task1 = new MyTimerTask(timer);

        TimerTask task2 = new MyTimerTask(timer);

        PrintUtil.log(System.currentTimeMillis() + " - Timer");

        timer.schedule(task1,1000L);

        timer.schedule(task2,2000L);

        // 不能立即执行 会抛弃其任务
        // timer.cancel();
    }

    /**
     * 使用ScheduledExecutorService
     */
    private static void useScheduledExecutorService(){
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

        PrintUtil.log(System.currentTimeMillis() + " - ScheduledExecutorService");

        executor.schedule(() -> {
            PrintUtil.log(System.currentTimeMillis() + " - ScheduledExecutorService Hello World!");
        }, 8000L, TimeUnit.MILLISECONDS);

        // 关闭
        executor.shutdown();

    }

}

class MyTimerTask extends TimerTask{

    private Timer timer;

    public MyTimerTask(Timer timer){
        this.timer = timer;
    }

    @Override
    public void run() {

        PrintUtil.log(System.currentTimeMillis() + " - Timer - Hello World!");

        // 一个TimerTask的耗时操作会影响另一个TimerTask的开始时间
        /*try {
            Thread.sleep(20000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        // 发生异常会导致整个Timer被取消掉
        // throw new RuntimeException("哈哈哈");

        // 任务结束后关闭 如果只有单个 task可以这样做，多个task又需要关闭的话需要一个计数器
        // timer.cancel();
    }
}
