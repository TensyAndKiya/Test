package com.clei.Y2019.M09.D16;

import com.clei.utils.PrintUtil;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RequestRateLimitTest {
    private static ConcurrentHashMap<String,Long> invoiceRequestMap = new ConcurrentHashMap<>(16);
    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < 10; i++) {

            System.out.println(invoiceRequestMap.keySet().size());
            long rand = (long) (Math.random() * 6);
            System.out.println(rand * 1000);
            Thread.sleep(rand * 1000);

            // 避免短时间内多次申请
            String key = "hasaki";
            Long curTime = System.currentTimeMillis();
            int limitTime = 3000;
            Long requestTime = invoiceRequestMap.get(key);
            putAndClean(key,curTime,limitTime);
            if(null != requestTime){
                // 10秒内同一个用户的对同一车场的开发票请求
                if(curTime - requestTime < limitTime){
                    PrintUtil.println("操作过于频繁，请10秒后重试 {}",i);
                }
            }
        }
    }
    private static void putAndClean(String key,Long value,int timeLimit){
        // 删除大于限制时间的，避免占空间
        Iterator<Map.Entry<String,Long>> it = invoiceRequestMap.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry<String,Long> entry = it.next();
            if(value - entry.getValue() > timeLimit){
                it.remove();
            }
        }
        // 放入
        invoiceRequestMap.put(key,value);
    }
}
