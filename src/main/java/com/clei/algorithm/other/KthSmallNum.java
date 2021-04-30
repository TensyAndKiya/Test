package com.clei.algorithm.other;

import com.clei.utils.PrintUtil;

/**
 * 在两个有序数组中获取第k小的数 (k >= 1)
 *
 * @author KIyA
 * @since 2020-01-05
 */
public class KthSmallNum {

    public static void main(String[] args) {
        int[] arr1 = {1,2,3};
        int[] arr2 = {4,5,6};

        int kSmall = getKthSmallNum(arr1,0,arr1.length - 1,arr2,0,arr2.length - 1,6);

        PrintUtil.log(kSmall);
    }

    public static int getKthSmallNum(int[] a,int s1,int e1,int[] b,int s2,int e2,int k){
        // 长度1 与 长度2
        int l1 = e1 - s1 + 1;
        int l2 = e2 - s2 + 1;

        // 保证空的数组是左边的a
        if(l1 > l2){
            return getKthSmallNum(b,s2,e2,a,s1,e1,k);
        }
        if(0 == l1){
            return b[s2 + k - 1];
        }

        // 最小那个直接找
        if(k == 1){
            return Math.min(a[s1],b[s2]);
        }

        // a数组的i b数组的j
        int i = s1 + Math.min(l1,k / 2) - 1;
        int j = s2 + Math.min(l2,k / 2) - 1;

        PrintUtil.println("i : {} j : {} k : {}",i,j,k);

        if(a[i] > b[j]){
            return getKthSmallNum(a,s1,e1,b,j + 1,e2,k - (j - s2 + 1));
        }else {
            return getKthSmallNum(a,i + 1,e1,b,s2,e2,k - (i - s1 + 1));
        }
    }

}
