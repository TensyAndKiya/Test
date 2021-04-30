package com.clei.algorithm.search.array;

import com.clei.algorithm.sort.QuickSort;
import com.clei.utils.ArrayUtil;
import com.clei.utils.PrintUtil;

import java.util.Arrays;
import java.util.Random;

/**
 * 二分查找
 *
 * @author KIyA
 */
public class BinarySearch {

    public static void main(String[] args) {
        int[] arr = ArrayUtil.shuffleArray(10, 1, 50, false);
        QuickSort.sort(arr);
        PrintUtil.log(Arrays.toString(arr));
        int num = arr[new Random().nextInt(arr.length)];
        PrintUtil.log(num);
        PrintUtil.log(search(arr, num));
    }

    public static int search(int[] arr, int num) {
        return search(arr, num, 0, arr.length - 1);
    }

    private static int search(int[] arr, int num, int start, int end) {
        if (num < arr[start] || num > arr[end] || start > end) {
            return -1;
        }

        int mid = (start + end) / 2;

        if (num > arr[mid]) {
            return search(arr, num, mid + 1, end);
        } else if (num < arr[mid]) {
            return search(arr, num, start, mid - 1);
        } else {
            return mid;
        }
    }
}
