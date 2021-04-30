package com.clei.algorithm.sort;

import com.clei.utils.ArrayUtil;
import com.clei.utils.PrintUtil;

import java.util.Arrays;

/**
 * 归并排序
 *
 * @author KIyA
 */
public class MergeSort {

    public static void main(String[] args) {
        int[] arr = ArrayUtil.shuffleArray(10, 1, 50, false);
        PrintUtil.log(Arrays.toString(arr));
        sort(arr);
        PrintUtil.log(Arrays.toString(arr));
    }

    public static void sort(int[] arr) {
        sort(arr, 0, arr.length - 1);
    }

    private static void sort(int[] arr, int start, int end) {
        if (start < end) {
            int mid = (start + end) / 2;
            sort(arr, start, mid);
            sort(arr, mid + 1, end);
            merge(arr, start, mid, end);
        }
    }

    private static void merge(int[] arr, int start, int mid, int end) {
        // new int[end - start + 1] 而不是 new int[arr.length] 节约空间
        int[] temp = new int[end - start + 1];
        int p1 = start, p2 = mid + 1, k = 0;

        while (p1 <= mid && p2 <= end) {
            if (arr[p1] < arr[p2]) {
                temp[k++] = arr[p1++];
            } else {
                temp[k++] = arr[p2++];
            }
        }

        while (p1 <= mid) {
            temp[k++] = arr[p1++];
        }

        while (p2 <= end) {
            temp[k++] = arr[p2++];
        }

        for (int i = start; i < end + 1; i++) {
            arr[i] = temp[i - start];
        }
    }
}
