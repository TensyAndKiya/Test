package com.clei.leetcode;

import com.clei.utils.PrintUtil;

/**
 * 分割数组
 * 给定一个数组 A，将其划分为两个不相交（没有公共元素）的连续子数组 left 和 right， 使得：
 * <p>
 * left 中的每个元素都小于或等于 right 中的每个元素。
 * left 和 right 都是非空的。
 * left 要尽可能小。
 * 在完成这样的分组后返回 left 的长度。可以保证存在这样的划分方法。
 * <p>
 * <p>
 * <p>
 * 示例 1：
 * <p>
 * 输入：[5,0,3,8,6]
 * 输出：3
 * 解释：left = [5,0,3]，right = [8,6]
 * 示例 2：
 * <p>
 * 输入：[1,1,1,0,6,12]
 * 输出：4
 * 解释：left = [1,1,1,0]，right = [6,12]
 *
 * @author KIyA
 */
public class Algorithm0915 {

    public static void main(String[] args) {
        int[] arr = {32, 57, 24, 19, 0, 24, 49, 67, 87, 87};
        PrintUtil.log(partitionDisjoint(arr));
    }

    private static int partitionDisjoint(int[] A) {
        int length = A.length;
        int[] leftMax = new int[length];
        leftMax[0] = A[0];
        int[] rightMin = new int[length];
        rightMin[length - 1] = A[length - 1];
        for (int i = length - 2; i > -1; i--) {
            rightMin[i] = Math.min(rightMin[i + 1], A[i]);
        }
        for (int i = 1; i < length; i++) {
            if (leftMax[i - 1] <= rightMin[i]) {
                return i;
            }
            leftMax[i] = Math.max(leftMax[i - 1], A[i]);
        }
        return 1;
    }
}
