package com.clei.leetcode;

import com.clei.utils.PrintUtil;

/**
 * 最长回文子串
 * 给你一个字符串 s，找到 s 中最长的回文子串。
 * <p>
 * <p>
 * <p>
 * 示例 1：
 * <p>
 * 输入：s = "babad"
 * 输出："bab"
 * 解释："aba" 同样是符合题意的答案。
 * 示例 2：
 * <p>
 * 输入：s = "cbbd"
 * 输出："bb"
 * 示例 3：
 * <p>
 * 输入：s = "a"
 * 输出："a"
 * 示例 4：
 * <p>
 * 输入：s = "ac"
 * 输出："a"
 * <p>
 * <p>
 * 提示：
 * <p>
 * 1 <= s.length <= 1000
 * s 仅由数字和英文字母（大写和/或小写）组成
 *
 * @author KIyA
 */
public class Algorithm005 {

    public static void main(String[] args) {
        PrintUtil.log(longestPalindrome("ac"));
    }

    private static String longestPalindrome(String s) {
        if (null == s || 0 == s.length()) {
            return "";
        }
        int length = s.length();
        int maxLength = 1, from = 0, end = 0;
        for (int i = 0; i < length; i++) {
            // 快速结束后面的 剩下的就算是回文也无法超过最大长度
            if (maxLength > (length - i - 1) * 2) {
                break;
            }
            int length1 = expand(s, length, i, i, true);
            int length2 = expand(s, length, i, i + 1, false);
            int tempMax = Math.max(length1, length2);
            // 大于之前的才计算重新计算开始结束 用于截取字符串
            if (tempMax > maxLength) {
                from = i - (tempMax - 1) / 2;
                end = i + tempMax / 2;
                maxLength = end - from + 1;
            }
        }
        return s.substring(from, end + 1);
    }

    private static int expand(String s, int length, int from, int end, boolean equals) {
        if (equals) {
            while (from - 1 > -1 && end + 1 < length && s.charAt(from - 1) == s.charAt(end + 1)) {
                from--;
                end++;
            }
            return end - from + 1;
        }
        if (end < length && s.charAt(from) == s.charAt(end)) {
            while (from - 1 > -1 && end + 1 < length && s.charAt(from - 1) == s.charAt(end + 1)) {
                from--;
                end++;
            }
            return end - from + 1;
        }
        return 1;
    }
}
