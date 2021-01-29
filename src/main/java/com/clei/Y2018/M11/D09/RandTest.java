package com.clei.Y2018.M11.D09;

import com.clei.utils.PrintUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author KIyA
 */
public class RandTest {

    public static void main(String[] args) {
        Random rand = new Random();
        int sum = 0;
        int end = 100;
        for (int i = 0; i < end; i++) {
            // [0,2) = [0,1]
            sum += rand.nextInt(2);
        }
        PrintUtil.log(sum);

        //<>
        Map<String, String> temp = new HashMap<>(0);
        //no <>,用的匿名内部类
        Map<String, String> map = new HashMap<String, String>(8) {
            private static final long serialVersionUID = -5694805728914070746L;

            {
                put("aa", "a");
                put("bb", "b");
                put("cc", "c");
                put("dd", "d");
                put("ee", "e");
            }
        };

        for (Map.Entry<String, String> entry : map.entrySet()) {
            PrintUtil.log("key: " + entry.getKey() + "\tvalue: " + entry.getValue());
        }
    }
}
