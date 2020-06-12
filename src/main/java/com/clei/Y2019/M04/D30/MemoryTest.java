package com.clei.Y2019.M04.D30;

import java.util.ArrayList;
import java.util.List;

public class MemoryTest {
    public static void main(String[] args){
        new Thread( () -> {
            Runtime rt = Runtime.getRuntime();
            List<String> list = new ArrayList<>();
            System.out.println((rt.totalMemory() >> 10) + "K");
            System.out.println((rt.freeMemory() >> 10) + "K");
            for (int i = 0; i < 100000000; i++) {

                list.add(new String("hasaki"));
            }
            System.out.println((rt.totalMemory() >> 10) + "K");
            System.out.println((rt.freeMemory() >> 10) + "K");
        } ,"MemoryTestThread").start();
    }
}
