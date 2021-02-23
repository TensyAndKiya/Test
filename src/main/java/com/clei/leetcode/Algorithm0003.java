package com.clei.leetcode;

import com.clei.utils.PrintUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 无重复字符的最长子串
 * <p>
 * 给定一个字符串，请你找出其中不含有重复字符的最长子串的长度。
 * <p>
 * 示例1:
 * <p>
 * 输入: "abcabcbb"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
 * 示例 2:
 * <p>
 * 输入: "bbbbb"
 * 输出: 1
 * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
 * 示例 3:
 * <p>
 * 输入: "pwwkew"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是"wke"，所以其长度为 3。
 * 请注意，你的答案必须是 子串 的长度，"pwke"是一个子序列，不是子串。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/longest-substring-without-repeating-characters
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Algorithm0003 {

    public static void main(String[] args) {

        PrintUtil.log(length("abcabcbb"));

    }

    /**
     * 伸缩滑块 一次循环
     * @param str
     * @return
     */
    public static int length(String str) {
        if(null == str || 0 == str.length()){
            return 0;
        }

        int length = str.length();
        int max = 0;

        int a = 0,b;

        Map<Character,Integer> map = new HashMap<>();
        for (int i = 0; i < length; i++) {
            b = i;
            Character c = str.charAt(i);

            // 遇到相同的字符后 左端向右收缩舍弃老字符
            Integer oldIndex = map.get(c);
            if(null != oldIndex && oldIndex >= a){
                a = oldIndex + 1;
            }

            map.put(c,i);

            int temp = b - a + 1;

            if(temp > max){
                max = temp;
            }
        }

        return max;
    }

}
