package com.clei.Y2019.M08.D15;

import com.clei.utils.PrintUtil;

import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 堵塞队列
 */
public class BlockingQueueTest {
    public static void main(String[] args){
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>(3);
        int length = 10;
        new Thread(new MyRunnable2(queue,length)).start();
        new Thread(new MyRunnable1(queue,length)).start();
    }

    private static class MyRunnable2 implements Runnable{
        private LinkedBlockingQueue<String> queue;
        private int length;
        public MyRunnable2(LinkedBlockingQueue queue,int length){
            this.queue = queue;
            this.length = length;
        }
        @Override
        public void run() {
            PrintUtil.dateLine("run2");
            Random random = new Random();
            try {
                Thread.sleep(random.nextInt(2000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < length; i++) {
                String str = "xxxxxx" + i;
                try {
                    queue.put(str);
                    PrintUtil.println("放第{}个，值为：{}",i,str);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class MyRunnable1 implements Runnable{
        private LinkedBlockingQueue<String> queue;
        private int length;
        public MyRunnable1(LinkedBlockingQueue queue,int length){
            this.queue = queue;
            this.length = length;
        }
        @Override
        public void run() {
            PrintUtil.dateLine("run1");
            Random random = new Random();
            try {
                Thread.sleep(random.nextInt(2000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < length; i++) {
                String str = queue.poll();
                if(null == str){
                    i = i - 1;
                }else{
                    PrintUtil.println("str : {} , length : {}",str,queue.size());
                }
            }
        }
    }
}
