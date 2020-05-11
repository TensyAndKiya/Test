package com.clei.Y2020.M04.D16;

import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;
import java.util.*;

/**
 * 连续字符的开始结束索引
 *
 * @author KIyA
 * @date 2019-04-16
 */
public class Test {

    public static void main(String[] args)throws Exception {

        System.out.println(1000 * 60 * 60 * 24);

        BigDecimal a = new BigDecimal("-0.01");
        System.out.println(a.compareTo(BigDecimal.ZERO));

        Map<String,Object> map = new HashMap<>();
        map.put("no",1);
        map.put("assetsName","资产1");
        map.put("assetsIp","192.168.1.1");
        map.put("assetsHolder","张三");
        map.put("assetsCategory",0);
        map.put("assetsProducer",0);
        map.put("assetsSystem",0);
        map.put("unitName","跑腿");
        map.put("departmentName","工程部");
        map.put("assetsRunState",0);
        Map<String,Object> map2 = new HashMap<>();
        map2.put("no",1);
        map2.put("assetsName","资产2");
        map2.put("assetsIp","192.168.1.2");
        map2.put("assetsHolder","李四");
        map2.put("assetsCategory",1);
        map2.put("assetsProducer",1);
        map2.put("assetsSystem",1);
        map2.put("unitName","跑腿");
        map2.put("departmentName","工程部");
        map2.put("assetsRunState",1);

        List<Map<String,Object>> list = new ArrayList<>();
        list.add(map);
        list.add(map2);

        System.out.println(JSONObject.toJSONString(list));


        List<Integer> result = new LinkedList<>();
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        char[] c = s.toCharArray();
        StringBuffer sb = new StringBuffer();


        // 上个重复字符的连续初始index
        int last = 0;

        for(int i = 0; i<c.length; i++){

            if(i > last){

                if(c[last] == c[i]){

                    // 下一个字符还相等 不操作。
                    if(i < c.length - 1 && c[last] == c[i + 1]){

                    }else {

                        result.add(last);
                        result.add(i);
                    }

                }else {
                    last = i;
                }

            }

        }

        System.out.println("result: " + result);

    }

}
