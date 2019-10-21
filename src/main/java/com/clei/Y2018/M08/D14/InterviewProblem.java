package com.clei.Y2018.M08.D14;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InterviewProblem {
    public static void main(String[] args){
        List<Integer> result=getZhishu(100);
        System.out.println(result);

        char[] chars="abcdefabcadeffbghxx".toCharArray();
        outChar(chars);
    }

    //获得1到max范围内的质数
    public static List<Integer> getZhishu(int max){
        List<Integer> result=new ArrayList<>();
        for(int i=2;i<max+1;i++){
            if(i==2||i==3){
                result.add(i);
                continue;
            }
            for(int j=2;j<(int)Math.sqrt(i)+1;j++){
                if(i%j==0){
                    break;
                }else if(j==(int)Math.sqrt(i)){
                    result.add(i);
                }
            }
        }
        return result;
    }

    //输出出现次数最多的字母
    public static void outChar(char[] chars){
        Map<Character,Integer> map=new HashMap<>();
        int max=0;
        for(char c:chars){
            int number=0;
            if(!map.containsKey(c)){
                map.put(c,number);
            }else{
                continue;
            }
            for(char cc:chars){
                if(c==cc){
                    number=map.get(c)+1;
                    map.put(c,number);
                }
            }
            if(number>max){
                max=number;
            }
        }
        for(Map.Entry<Character,Integer> entry:map.entrySet()){
            if(entry.getValue().equals(max)){
                System.out.println("KEY: " +entry.getKey()+"\tVALUE: "+entry.getValue());
            }
        }
    }
}
