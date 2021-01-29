package com.clei.Y2019.M04.D29;

import com.clei.utils.PrintUtil;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class ThreadTest {
    private static final int LOOP = 10000;
    public static void main(String[] args) {
        /*for (int i = 0; i < LOOP; i++) {
            new Thread( () ->{
                PrintUtil.log(Thread.currentThread().getName());
            } ).start();
        }*/
        Set<String> set = new CopyOnWriteArraySet<>();
        for(int i = 0; i < LOOP; i ++){
            set.add("String " + i);
        }
        for (int i = 0; i < LOOP; i++) {
            new Thread(new MyRunnable(set,i)).start();
        }
    }

    private static class MyRunnable implements Runnable{
        private int i;
        Set<String> set;
        public MyRunnable(Set<String> set,int i){
            this.set = set;
            this.i = i;
        }
        @Override
        public void run() {
            for(String s : set){
                if(s.equals("String " + i)){
                    PrintUtil.log(s);
                    set.remove(s);
                    break;
                }
            }
        }
    }
}
