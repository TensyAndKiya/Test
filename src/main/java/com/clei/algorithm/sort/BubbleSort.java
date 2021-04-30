package com.clei.algorithm.sort;

import com.clei.utils.ArrayUtil;
import com.clei.utils.PrintUtil;

import java.util.Arrays;

/**
 * 冒泡排序
 *
 * @author KIyA
 */
public class BubbleSort {

    public static void main(String[] args) {
        int[] arr = ArrayUtil.shuffleArray(10, 1, 50, false);
        // int[] arr = ArrayUtil.intArray(10, 1, 50);
        PrintUtil.log(Arrays.toString(arr));
        sort(arr);
        PrintUtil.log(Arrays.toString(arr));
    }

    public static void sort(int[] arr) {
        int length = arr.length;
        for (int i = 0; i < length - 1; i++) {
            boolean changed = false;
            for (int j = 0; j < length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    changed = true;
                    ArrayUtil.swap(arr, j, j + 1);
                }
            }
            if (!changed) {
                break;
            }
        }
    }

}
