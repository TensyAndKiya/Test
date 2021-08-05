package com.clei.algorithm.sort;

import com.clei.utils.ArrayUtil;
import com.clei.utils.PrintUtil;

import java.util.Arrays;

/**
 * 堆排序
 *
 * @author KIyA
 * @date 2021-08-05
 */
public class HeapSort {

    public static void main(String[] args) {
        int[] arr = ArrayUtil.shuffleArray(5, 1, 50, false);
        PrintUtil.log(Arrays.toString(arr));
        sort(arr);
        PrintUtil.log(Arrays.toString(arr));
    }

    private static void sort(int[] arr) {
        // 构建大顶堆
        // 从第一个非叶子节点开始，从右至左，从下至上
        for (int i = arr.length / 2 - 1; i > -1; i--) {
            adjustHeap(arr, i, arr.length);
        }
        // 调整堆结构
        // 交换堆顶元素 堆尾元素
        for (int i = arr.length - 1; i > 0; i--) {
            ArrayUtil.swap(arr, 0, i);
            adjustHeap(arr, 0, i);
        }
    }

    private static void adjustHeap(int[] arr, int i, int length) {
        // 取出元素
        int temp = arr[i];
        // 从左开始
        for (int k = i * 2 + 1; k < length; k = k * 2 + 1) {
            // 如果左子结点小于右子结点，k指向右子结点
            if (k + 1 < length && arr[k] < arr[k + 1]) {
                k = k + 1;
            }
            //如果子节点大于父节点，将子节点值赋给父节点
            if (arr[k] > temp) {
                arr[i] = arr[k];
                i = k;
            } else {
                break;
            }
        }
        // 将temp值放到最终的位置
        arr[i] = temp;
    }
}