package com.clei.algorithm.sort;

import com.clei.utils.ArrayUtil;
import com.clei.utils.PrintUtil;

import java.util.Arrays;

/**
 * 快速排序
 *
 * @author KIyA
 */
public class QuickSort {

    public static void main(String[] args) {
        int[] arr = ArrayUtil.shuffleArray(10, 1, 50, false);
        PrintUtil.println(Arrays.toString(arr));
        sort(arr);
        PrintUtil.println(Arrays.toString(arr));
    }

    public static void sort(int[] arr) {
        sort(arr, 0, arr.length - 1);
    }

    public static void sort(int[] arr, int low, int high) {
        if (low >= high) {
            return;
        }
        int i = low, j = high;
        int k = low;
        // k 基准点
        int t = arr[k];
        while (i < j) {
            // 因为基准点在最左边，所以先右再左
            // 先右走
            while (t <= arr[j] && i < j) {
                j--;
            }
            // 再左走
            while (t >= arr[i] && i < j) {
                i++;
            }
            // 如果满足条件则交换
            if (i < j) {
                ArrayUtil.swap(arr, i, j);
            }
        }
        // 最后将基准点与相遇点的数字交换
        ArrayUtil.swap(arr, k, j);
        sort(arr, low, j - 1);
        sort(arr, j + 1, high);
    }
}