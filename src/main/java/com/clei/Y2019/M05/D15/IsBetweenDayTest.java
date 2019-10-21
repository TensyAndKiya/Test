package com.clei.Y2019.M05.D15;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class IsBetweenDayTest {
    private static int num = 0;
    public static void main(String[] args) {

        try{
            Thread.sleep(15000);
        }catch (Exception e){
            e.printStackTrace();
        }

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                50,
                101,
                5,
                TimeUnit.MINUTES,
                new ArrayBlockingQueue<>(15),
                new IsBetweenDayTest.CustomThreadFactory(),
                new IsBetweenDayTest.CustomRejectedExecutionHandler()
        );

        for (int i = 1; i < 100000001; i++) {
            System.out.println("提交第 " + i + " 个任务！");
            executor.execute(new MyRunnable());
        }
        //executor.shutdown();
    }

    public synchronized static void over(){
        System.out.println("第" + (++num) + "个任务执行完毕！");
    }


    public static boolean isBetweenDay(Calendar cal, String dayStartTime, String dayEndTime) {

        boolean isBetween = false;

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

        Date dayStartDate = null;
        Date dayEndDate = null;

        try {
            dayStartDate = sdf.parse(dayStartTime);
            dayEndDate = sdf.parse(dayEndTime);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }

        Calendar startTime = Calendar.getInstance();
        Calendar endTime = Calendar.getInstance();
        startTime.setTime(dayStartDate);
        endTime.setTime(dayEndDate);

        startTime.set(Calendar.YEAR, cal.get(Calendar.YEAR));
        startTime.set(Calendar.MONTH, cal.get(Calendar.MONTH));
        startTime.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH));
        startTime.add(Calendar.MILLISECOND, -1);

        endTime.set(Calendar.YEAR, cal.get(Calendar.YEAR));
        endTime.set(Calendar.MONTH, cal.get(Calendar.MONTH));
        endTime.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH));
        endTime.add(Calendar.MILLISECOND, -1);

        long startTimeInMillis = startTime.getTimeInMillis();
        long endTimeInMillis = endTime.getTimeInMillis();
        long sourceTimeInMillis = cal.getTimeInMillis();

        if (sourceTimeInMillis >= startTimeInMillis && sourceTimeInMillis <= endTimeInMillis) {
            isBetween = true;
        }
        return isBetween;
    }

    private static class MyRunnable implements Runnable{
        @Override
        public void run() {
            Calendar cal = Calendar.getInstance();
            String date1 = "07:00:00";
            String date2 = "22:00:00";
            isBetweenDay(cal,date1,date2);
            over();
        }
    }

    private static class CustomThreadFactory implements ThreadFactory {
        private AtomicInteger ai = new AtomicInteger(0);
        @Override
        public Thread newThread(Runnable r) {
            String name = "KIyA_ThreadFactory_Thread_"+ai.addAndGet(1);
            System.out.println("创建了线程 ： "+name);
            return new Thread(r, name);
        }
    }

    private static class CustomRejectedExecutionHandler implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            try {
                //阻塞方法
                executor.getQueue().put(r);
            } catch (InterruptedException e) {
                System.out.println("放入阻塞队列失败！！！");
                e.printStackTrace();
            }
        }
    }
}
