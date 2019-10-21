package com.clei.Y2018.M11.D09;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class RandTest {
    public static void main(String[] args){
        Random rand=new Random();
        int sum=0;
        for(int i=0;i<100;i++){
            // [0,2) = [0,1]
            sum+=rand.nextInt(2);
        }
        System.out.println(sum);

        //<>
        Map<String,String> aaa=new HashMap<>();
        //no <>,用的匿名内部类
        Map<String,String> map=new HashMap(){
            {
                put("aa","a");
                put("bb","b");
                put("cc","c");
                put("dd","d");
                put("ee","e");
            }
        };

        Iterator<Map.Entry<String,String>> it=map.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<String,String> entry=it.next();
            System.out.println("key: "+entry.getKey()+"\tvalue: "+entry.getValue());
        }
    }
}
