package com.clei.leetcode;

import com.clei.utils.PrintUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**两数之和
 *
 * 给定一个整数数组 nums和一个目标值 target，请你在该数组中找出和为目标值的那两个整数，并返回他们的数组下标。
 *
 * 你可以假设每种输入只会对应一个答案。但是，你不能重复利用这个数组中同样的元素。
 *
 * 示例:
 *
 * 给定 nums = [2, 7, 11, 15], target = 9
 *
 * 因为 nums[0] + nums[1] = 2 + 7 = 9
 * 所以返回 [0, 1]
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/two-sum
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @since 2019-12-28
 */
public class Algorithm001 {

    public static void main(String[] args) {
        int[] array = {3,3,3,5};

        PrintUtil.println(Arrays.toString(twoSum(array,8)));
    }


    public static int[] twoSum(int[] nums, int target) {
        Map<Integer,Integer> map = new HashMap<>();

        for(int i = 0; i < nums.length; i ++){
            int j = nums[i];

            int diff = target - j;

            if(map.containsKey(diff)){
                int index = map.get(diff);

                int[] array = {index,i};
                return array;
            }
            // 后 put  是因为 不能重复利用元素
            // 且 避免了相同数组两两个相同值 的
            if(!map.containsKey(j)){
                map.put(j,i);
            }
        }
        return null;
    }
}
