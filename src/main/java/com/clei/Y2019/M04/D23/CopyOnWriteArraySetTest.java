package com.clei.Y2019.M04.D23;

import java.util.HashSet;
import java.util.concurrent.CopyOnWriteArraySet;

public class CopyOnWriteArraySetTest {
    public static void main(String[] args) {
        CopyOnWriteArraySet<String> set = new CopyOnWriteArraySet<>();
        HashSet<String> set2 = new HashSet<>();
        set.add("String1");
        set.add("String2");
        set.add("String3");
        set2.add("String1");
        set2.add("String2");
        set2.add("String3");
        for(String s : set){
            if(s.equals("String2")){
                set.remove(s);
            }
        }
        for(String s : set2){
            if(s.equals("String2")){
                set2.remove(s);
            }
        }
    }
}
