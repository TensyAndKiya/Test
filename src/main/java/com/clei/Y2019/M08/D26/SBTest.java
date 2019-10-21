package com.clei.Y2019.M08.D26;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class SBTest {


    private final static float SL = 0.05f;

    public static void main(String[] args) throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.parse("2019-09-02 14:00:48"));

        Map<String,Object> map = new HashMap<>();
        map.put("address","aaa");
        map.put("phone","bbb");
        map.put("bank","ccc");
        map.put("account","ddd");

        map.forEach((k,v) -> System.out.println("KEY: " + k +"\tVALUE: " + v));

        /*System.out.println(getRightName("我是你爹啊"));
        System.out.println(getRightName("我是你爹啊啊啊啊啊啊啊"));
        System.out.println(getRightName("我是你"));*/

        /*StringBuilder sb = new StringBuilder("0123456789");
        sb.delete(8,10);
        System.out.println(sb.toString());
        //
        String name1 = "为什么我是你的大爷爷呢？";
        String name2 = "赵钱孙李";
        String name3 = "周钊";
        System.out.println(getRightName(name1,8));
        System.out.println(getRightName(name2,8));
        System.out.println(getRightName(name3,8));*/
    }

    // 大象发票的开票员长度不能超过8 是GBK的8，汉字的话最多4个
    private static String getRightName(String name,int length) throws Exception {
        if(name.length() > 4){
            name = name.substring(name.length() - 4);
        }
        return name;
    }

    // 发票的开票员长度不能超过8 是GBK的8，汉字的话最多4个
    public static String getRightName(String name){

        try {
            if(name.getBytes("GBK").length < 9){
                return name;
            }
            int length = name.length();
            for (int i = 0; i < length; i++) {
                name = name.substring(1);
                if(name.getBytes("GBK").length < 9){
                    break;
                }
            }
            return name;
        } catch (Exception e) {
            if(name.length() > 4){
                name = name.substring(name.length() - 4);
            }
            return name;
        }
    }
}
