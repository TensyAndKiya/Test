package com.clei.Y2019.M09.D20;

import java.util.HashMap;
import java.util.Map;

public class PutGetTest {
    private final static Map<String,String> map = new HashMap();

    public static void main(String[] args) {
        Runnable r1 = () -> map.put("key","value" + Math.random());
        Runnable r2 = () -> System.out.println(map.get("key"));
        for (int i = 0; i < 100; i++) {
            new Thread(r1).start();
            new Thread(r2).start();
        }
    }
}
