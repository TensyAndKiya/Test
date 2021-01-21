package com.clei.Y2019.M09.D20;

import com.clei.utils.PrintUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author KIyA
 */
public class PutGetTest {

    private final static Map<String, String> map = new HashMap<>();

    public static void main(String[] args) {
        Runnable r1 = () -> map.put("key", "value" + Math.random());
        Runnable r2 = () -> PrintUtil.dateLine(map.get("key"));
        int times = 100;
        for (int i = 0; i < times; i++) {
            new Thread(r1).start();
            new Thread(r2).start();
        }
    }
}
