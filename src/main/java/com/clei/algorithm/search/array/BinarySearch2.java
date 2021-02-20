package com.clei.algorithm.search.array;

import com.clei.utils.ArrayUtil;
import com.clei.utils.PrintUtil;

/**
 * 返回查找次数的二分查找
 *
 * @author KIyA
 */
public class BinarySearch2 {

    public static void main(String[] args) {
        int length = 100;
        int[] arr = ArrayUtil.intArray(length, 1);
        int num = 100;
        int times = binarySearch(arr, num, 0, length - 1, 1);
        PrintUtil.log("耗时：{}", times);
    }

    private static int binarySearch(int[] arr, int num, int start, int end, int times) {
        if (start < 0 || end > arr.length - 1 || start > end) {
            throw new RuntimeException("参数错误");
        }
        if (num < arr[start] || num > arr[end]) {
            throw new RuntimeException("找不到");
        }
        int mid = (start + end) / 2;
        if (num > arr[mid]) {
            return binarySearch(arr, num, mid + 1, end, ++times);
        }
        if (num < arr[mid]) {
            return binarySearch(arr, num, start, mid - 1, ++times);
        }
        return times;
    }
}
