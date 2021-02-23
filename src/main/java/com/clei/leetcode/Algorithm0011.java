package com.clei.leetcode;

import com.clei.utils.PrintUtil;

/**
 * 盛最多水的容器
 * 给你 n 个非负整数 a1，a2，...，an，每个数代表坐标中的一个点 (i, ai) 。在坐标内画 n 条垂直线，垂直线 i 的两个端点分别为 (i, ai) 和 (i, 0) 。找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
 * <p>
 * 说明：你不能倾斜容器。
 * <p>
 * <p>
 * <p>
 * 示例 1：
 * <p>
 * <p>
 * <p>
 * 输入：[1,8,6,2,5,4,8,3,7]
 * 输出：49
 * 解释：图中垂直线代表输入数组 [1,8,6,2,5,4,8,3,7]。在此情况下，容器能够容纳水（表示为蓝色部分）的最大值为 49。
 * 示例 2：
 * <p>
 * 输入：height = [1,1]
 * 输出：1
 * 示例 3：
 * <p>
 * 输入：height = [4,3,2,1,4]
 * 输出：16
 * 示例 4：
 * <p>
 * 输入：height = [1,2,1]
 * 输出：2
 *
 * @author KIyA
 */
public class Algorithm0011 {

    public static void main(String[] args) {
        int[] arr = {1, 8, 6, 2, 5, 4, 8, 3, 7};
        PrintUtil.log(maxArea(arr));
    }

    public static int maxArea(int[] height) {
        int length = height.length;
        int start = 0;
        int end = length - 1;
        int width;
        int tempHeight;
        int tempArea;
        int max = 0;
        while (start < end) {
            width = end - start;
            if (height[start] > height[end]) {
                tempHeight = height[end];
                end--;
            } else {
                tempHeight = height[start];
                start++;
            }
            tempArea = width * tempHeight;
            if (tempArea > max) {
                max = tempArea;
            }
        }
        return max;
    }
}
