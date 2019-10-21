package com.clei.Y2019.M04.D26;

import java.util.HashSet;
import java.util.Set;

public class HashSetTest {
    public static void main(String[] args) {
        Set<String> set = new HashSet<>();

        for(int i = 0;i < 100;i ++){
            set.add("String " + i);
        }
        try{
            for(String s : set){
                System.out.println(s);
//                set.add("String " + rand.nextInt(998));
                if(s.equals("String 89")){
                   set.add("String 100");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
