package com.clei.algorithm.sort;

import com.clei.utils.ArrayUtil;
import com.clei.utils.PrintUtil;

import java.util.Arrays;

/**
 * 插入排序
 *
 * @author KIyA
 */
public class InsertionSort {

    public static void main(String[] args) {
        int[] arr = ArrayUtil.shuffleArray(10, 1, 50, false);
        PrintUtil.println(Arrays.toString(arr));
        sort(arr);
        PrintUtil.println(Arrays.toString(arr));
    }

    public static void sort(int[] arr) {
        int length = arr.length;
        for (int i = 1; i < length; i++) {
            for (int j = i; j > 0; j--) {
                if (arr[j] < arr[j - 1]) {
                    ArrayUtil.swap(arr, j - 1, j);
                }
            }
        }
    }
}
