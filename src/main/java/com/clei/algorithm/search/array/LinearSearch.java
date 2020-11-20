package com.clei.algorithm.search.array;

import com.clei.utils.ArrayUtil;
import com.clei.utils.PrintUtil;

import java.util.Arrays;
import java.util.Random;

/**
 * 线性查找
 *
 * @author KIyA
 */
public class LinearSearch {

    public static void main(String[] args) {
        int[] arr = ArrayUtil.shuffleArray(10, 1, 50, false);
        PrintUtil.println(Arrays.toString(arr));
        int num = arr[new Random().nextInt(arr.length)];
        PrintUtil.println(num);
        PrintUtil.println(search(arr, num));
    }

    public static int search(int[] arr, int num) {
        for (int i = 0; i < arr.length; i++) {
            if (num == arr[i]) {
                return i;
            }
        }
        return -1;
    }
}
