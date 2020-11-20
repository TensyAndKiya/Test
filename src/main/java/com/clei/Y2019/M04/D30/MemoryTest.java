package com.clei.Y2019.M04.D30;

import com.clei.utils.PrintUtil;

import java.util.ArrayList;
import java.util.List;

public class MemoryTest {
    public static void main(String[] args){
        new Thread( () -> {
            Runtime rt = Runtime.getRuntime();
            List<String> list = new ArrayList<>();
            PrintUtil.dateLine((rt.totalMemory() >> 10) + "K");
            PrintUtil.dateLine((rt.freeMemory() >> 10) + "K");
            for (int i = 0; i < 100000000; i++) {

                list.add(new String("hasaki"));
            }
            PrintUtil.dateLine((rt.totalMemory() >> 10) + "K");
            PrintUtil.dateLine((rt.freeMemory() >> 10) + "K");
        } ,"MemoryTestThread").start();
    }
}
