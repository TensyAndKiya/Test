package com.clei.Y2018.M11.D08;

import com.clei.utils.PrintUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 小测试
 * 注意Arrays.asList 和 subList的用法
 *
 * @author KIyA
 */
public class LittleTest {

    public static void main(String[] args) {
        Integer[] arr = new Integer[]{9, 8, 9, 7, 6, 5, 7, 4, 3, 2, 1};

        List<Integer> list = new ArrayList<>();
        List<Integer> list2 = Arrays.asList(arr);
        //  list2.add(3); bug bug

        //list.addAll(Arrays.asList(arr));
        Collections.addAll(list, arr);

        //subList may bug bug
        List<Integer> list3 = list.subList(1, 4);
        PrintUtil.log(list3);

        List<Integer> list4 = new ArrayList<>(list3);

        list3.add(18);
        PrintUtil.log(list3);
        PrintUtil.log(list4);
        PrintUtil.log(list);

        PrintUtil.log(Arrays.toString(arr));

        for (int i = 0; i < arr.length - 1; i++) {
            boolean change = false;
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] < arr[j + 1]) {
                    change = true;
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
            if (!change) {
                break;
            }
        }

        PrintUtil.log(Arrays.toString(arr));

        String x = new String("xxx");
        String y = "xxx";
        String z = "xxx";
        PrintUtil.log(x == y);
        PrintUtil.log(y == z);
        PrintUtil.log(x.equals(y));
    }
}
