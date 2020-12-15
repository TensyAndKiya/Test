package com.clei.leetcode;

import com.clei.utils.PrintUtil;

/**
 * 寻找两个有序数组的中位数
 *
 * 给定两个大小为 m 和 n 的有序数组nums1 和nums2。
 *
 * 请你找出这两个有序数组的中位数，并且要求算法的时间复杂度为O(log(m + n))。
 *
 * 你可以假设nums1和nums2不会同时为空。
 *
 * 示例 1:
 *
 * nums1 = [1, 3]
 * nums2 = [2]
 *
 * 则中位数是 2.0
 * 示例 2:
 *
 * nums1 = [1, 2]
 * nums2 = [3, 4]
 *
 * 则中位数是 (2 + 3)/2 = 2.5
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/median-of-two-sorted-arrays
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Algorithm004 {

    public static void main(String[] args) {

        int[] arr1 = {};
        int[] arr2 = {};

        double median = getMedian(arr1,arr2);

        PrintUtil.dateLine("中位数: " + median);

    }


    public static double getMedian(int[] a,int [] b){
        // 长度1 与 长度2
        int l1 = a.length;
        int l2 = b.length;

        if(0 == l1 && 0 == l2){
            return 0;
        }

        // 保证 l1 <= l2
        if(l1 > l2){
            return getMedian(b,a);
        }

        int iMin = 0, iMax = l1;
        while (iMin <= iMax){
            int i = (iMin + iMax) / 2;
            int j = (l1 + l2 + 1) / 2 - i;

            if(j != 0 && i != l1 && b[j - 1] > a[i]){
                // 增大i
                iMin = i + 1;
            }else if(i != 0 && j != l2 && a[i - 1] > b[j]){
                // 减小i
                iMax = i - 1;
            }else {
                // 左最大
                int leftMax = 0;
                if(i == 0){
                    leftMax = b[j - 1];
                }else if(j == 0){
                    leftMax = a[i - 1];
                }else {
                    leftMax = Math.max(a[i - 1],b[j - 1]);
                }

                // 奇数个的话
                if(0 == ((l1 + l2 + 1) & 1)){
                    return leftMax;
                }

                // 右最小
                int rightMin = 0;
                if(i == l1){
                    rightMin = b[j];
                }else if(j == l2){
                    rightMin = a[i];
                }else {
                    rightMin = Math.min(a[i],b[j]);
                }

                return (leftMax + rightMin) / 2;
            }
        }

        return 0;
    }

}
