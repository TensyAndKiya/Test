package com.clei.Y2019.M09.D17;

import com.clei.utils.PrintUtil;

import java.util.concurrent.ConcurrentHashMap;

public class SynchronizedTest {

    private static ConcurrentHashMap<String,Long> requestMap = new ConcurrentHashMap<>(16);
    private static ConcurrentHashMap<String,String> tokenMap = new ConcurrentHashMap<>(16);

    public static void main(String[] args) {
        MyRunnable runnable = new MyRunnable();
        for (int i = 0; i < 10; i++) {
            new Thread(runnable).start();
        }
    }

    private static String refreshToken(String key) throws InterruptedException {
        String lock = ("refreshToken" + key).intern();
        synchronized (lock){
            PrintUtil.log("lock1");
            Long curTime = System.currentTimeMillis();
            Long requestTime = requestMap.get(key);
            if(null != requestTime && curTime - requestTime < 30000){
                PrintUtil.log("lock2");
                return tokenMap.get(key);
            }
            Thread.sleep(1000);
            PrintUtil.log("lock3");
            String token = Math.random() + " token";
            tokenMap.put(key,token);
            requestMap.put(key,curTime);
            return token;
        }
    }

    private static class MyRunnable implements Runnable{
        @Override
        public void run() {
            try {
                PrintUtil.log(refreshToken("park1"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
