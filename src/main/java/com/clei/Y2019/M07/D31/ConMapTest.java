package com.clei.Y2019.M07.D31;

import com.alibaba.fastjson.JSONObject;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConMapTest {
    static ConcurrentHashMap<String,Long> map = new ConcurrentHashMap<>();
    public static void main(String[] args) throws InterruptedException {
        /*Runnable1 r = new Runnable1(map);
        for (int i = 0; i < 100; i++) {
            System.out.println(JSONObject.toJSONString(map));
            Thread.sleep((long)(Math.random() * 30) + 10);
            new Thread(r).start();
        }*/
        System.out.println(map.put("aa",1L));
        System.out.println(map.put("aa",2L));
    }
}

class Runnable1 implements Runnable{
    private ConcurrentHashMap<String,Long> map;
    public Runnable1(ConcurrentHashMap<String,Long> map){
        this.map = map;
    }
    @Override
    public void run() {
        int i = (int) (Math.random()*26) + 65;
        String k = (char)i + "";
        if(map.containsKey(k)){
            System.out.println("已经有key: " + k);
            return;
        }
        map.put(k,new Date().getTime());
        try{
            Iterator<Map.Entry<String,Long>> it = map.entrySet().iterator();
            LinkedList<String> list = new LinkedList<>();
            while (it.hasNext()){
                Map.Entry<String,Long> entry = it.next();
                // 放入时间大于3分钟
                if(System.currentTimeMillis() - entry.getValue() > 300){
                    list.add(entry.getKey());
                }
            }
            for(String key : list){
                map.remove(key);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
