package com.clei.Y2018.M11.D08;


import com.clei.utils.PrintUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LittleTest {
    public static void main(String[] args){
        Integer[] arr=new Integer[]{9,8,9,7,6,5,7,4,3,2,1};

        List<Integer> list=new ArrayList<>();
        List<Integer> list2=Arrays.asList(arr);
        //  list2.add(3); bug bug

        //list.addAll(Arrays.asList(arr));
        Collections.addAll(list, arr);

        //subList may bug bug
        List<Integer> list3 = list.subList(1, 4);
        PrintUtil.dateLine(list3);

        List<Integer> list4 = new ArrayList<>(list3);

        list3.add(18);
        PrintUtil.dateLine(list3);
        PrintUtil.dateLine(list4);
        PrintUtil.dateLine(list);

        PrintUtil.dateLine(Arrays.toString(arr));

        for (int i = 0; i < arr.length - 1; i++) {
            boolean change = false;
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] < arr[j + 1]) {
                    change = true;
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j+1]=temp;
                }
            }
            if (!change) {
                break;
            }
        }

        PrintUtil.dateLine(Arrays.toString(arr));

        String x = new String("xxx");
        String y = "xxx";
        String z = "xxx";
        PrintUtil.dateLine(x == y);
        PrintUtil.dateLine(y == z);
        PrintUtil.dateLine(x.equals(y));
    }
}
