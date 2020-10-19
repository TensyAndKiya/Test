package com.clei.Y2019.M04.D29;

import com.clei.utils.PrintUtil;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

//本来是想用CopyOnArraySet或ConcurrentHashMap来代替HashSet的。。
//根据下面的测试结果来看。。还是用CopyOnArraySet吧
//原项目的场景Map并不适用
public class ConcurrentTest {
    private static final int LOOP = 5000000;
    public static void main(String[] args) {
        Set<String> set = new HashSet<>();
        Map<String, Object> map = new ConcurrentHashMap<>();
        putInSet(set);
        putInMap(map);
        traverseSet(set);
        traverseMap(map);
        setRemove(set);
        mapRemove(map);
        PrintUtil.dateLine(set.remove("998"));
        PrintUtil.dateLine(map.remove("998"));
    }


    private static void putInSet(Set<String> set){
        long begin = System.currentTimeMillis();
        for (int i = 0; i < LOOP; i++) {
            set.add("String " + i);
        }
        long end = System.currentTimeMillis();
        PrintUtil.dateLine("set耗时：" + (end - begin));
    }

    private static void putInMap(Map<String,Object> map){
        Object obj = new Object();
        long begin = System.currentTimeMillis();
        for (int i = 0; i < LOOP; i++) {
            map.put("String " + i,obj);
        }
        long end = System.currentTimeMillis();
        PrintUtil.dateLine("map耗时：" + (end - begin));
    }

    private static void traverseSet(Set<String> set){
        long begin = System.currentTimeMillis();
        for(String s: set){
            if(s.equals("")){

            }
        }
        long end = System.currentTimeMillis();
        PrintUtil.dateLine("set耗时：" + (end - begin));
    }

    private static void traverseMap(Map<String,Object> map){
        long begin = System.currentTimeMillis();
        map.forEach((k,v)->{
            if(k.equals("")){

            }
        });
        long end = System.currentTimeMillis();
        PrintUtil.dateLine("map耗时：" + (end - begin));
    }

    private static void setRemove(Set<String> set){
        long begin = System.currentTimeMillis();
        set.remove("String 1");
        set.remove("String 500000");
        set.remove("String 999999");
        long end = System.currentTimeMillis();
        PrintUtil.dateLine("set耗时：" + (end - begin));
    }

    private static void mapRemove(Map<String,Object> map){
        long begin = System.currentTimeMillis();
        map.remove("String 1");
        map.remove("String 500000");
        map.remove("String 999999");
        long end = System.currentTimeMillis();
        PrintUtil.dateLine("map耗时：" + (end - begin));
    }

}
