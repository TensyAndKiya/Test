package com.clei.algorithm.sort;

import com.clei.utils.ArrayUtil;
import com.clei.utils.PrintUtil;

import java.util.Arrays;

/**
 * 选择排序
 *
 * @author KIyA
 */
public class SelectionSort {

    public static void main(String[] args) {
        int[] arr = ArrayUtil.shuffleArray(10, 1, 50, false);
        PrintUtil.log(Arrays.toString(arr));
        sort(arr);
        PrintUtil.log(Arrays.toString(arr));
    }

    public static void sort(int[] arr) {
        int length = arr.length;
        for (int i = 0; i < length - 1; i++) {
            int minIndex = i;
            int min = arr[minIndex];
            for (int j = i + 1; j < length; j++) {
                if (arr[j] < min) {
                    minIndex = j;
                    min = arr[minIndex];
                }
            }
            if (minIndex != i) {
                ArrayUtil.swap(arr, i, minIndex);
            }
        }
    }

}
