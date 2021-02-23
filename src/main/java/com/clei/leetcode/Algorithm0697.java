package com.clei.leetcode;

import com.clei.utils.PrintUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 数组的度
 * 给定一个非空且只包含非负数的整数数组 nums，数组的度的定义是指数组里任一元素出现频数的最大值。
 * <p>
 * 你的任务是在 nums 中找到与 nums 拥有相同大小的度的最短连续子数组，返回其长度。
 * <p>
 * <p>
 * <p>
 * 示例 1：
 * <p>
 * 输入：[1, 2, 2, 3, 1]
 * 输出：2
 * 解释：
 * 输入数组的度是2，因为元素1和2的出现频数最大，均为2.
 * 连续子数组里面拥有相同度的有如下所示:
 * [1, 2, 2, 3, 1], [1, 2, 2, 3], [2, 2, 3, 1], [1, 2, 2], [2, 2, 3], [2, 2]
 * 最短连续子数组[2, 2]的长度为2，所以返回2.
 * 示例 2：
 * <p>
 * 输入：[1,2,2,3,1,4,2]
 * 输出：6
 * <p>
 * <p>
 * 提示：
 * <p>
 * nums.length 在1到 50,000 区间范围内。
 * nums[i] 是一个在 0 到 49,999 范围内的整数。
 *
 * @author KIyA
 */
public class Algorithm0697 {

    public static void main(String[] args) {
        int[] array = {1, 2, 2, 3, 1, 4, 2};

        PrintUtil.log(findShortestSubArray(array));
    }

    private static int findShortestSubArray(int[] nums) {
        int length = nums.length;
        if (length < 2) {
            return length;
        }

        Map<Integer, Integer> timesMap = new HashMap<>();
        Map<Integer, Integer> startMap = new HashMap<>();
        Map<Integer, Integer> endMap = new HashMap<>();

        Integer tempTimes = 1;

        int maxTimes = 0;

        for (int i = 0; i < length; i++) {
            int n1 = nums[i];
            // 避免多次装箱
            Integer n2 = n1;
            Integer times = timesMap.get(n2);
            if (null == times) {
                times = tempTimes;
                startMap.put(n2, i);
            } else {
                times = times + 1;
                endMap.put(n2, i);
            }
            timesMap.put(n2, times);

            if (times > maxTimes) {
                maxTimes = times;
            }
        }

        if (maxTimes == 1) {
            return 1;
        }

        Set<Integer> integers = endMap.keySet();
        int min = Integer.MAX_VALUE;
        for (Integer i : integers) {
            int times = timesMap.get(i);
            if (times == maxTimes) {
                int temp = endMap.get(i) - startMap.get(i) + 1;
                if (temp < min) {
                    min = temp;
                }
            }
        }

        return min;
    }
}
