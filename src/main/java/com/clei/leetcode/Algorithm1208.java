package com.clei.leetcode;

import com.clei.utils.PrintUtil;

/**
 * 尽可能使字符串相等
 * 给你两个长度相同的字符串，s 和 t。
 * <p>
 * 将 s 中的第 i 个字符变到 t 中的第 i 个字符需要 |s[i] - t[i]| 的开销（开销可能为 0），也就是两个字符的 ASCII 码值的差的绝对值。
 * <p>
 * 用于变更字符串的最大预算是 maxCost。在转化字符串时，总开销应当小于等于该预算，这也意味着字符串的转化可能是不完全的。
 * <p>
 * 如果你可以将 s 的子字符串转化为它在 t 中对应的子字符串，则返回可以转化的最大长度。
 * <p>
 * 如果 s 中没有子字符串可以转化成 t 中对应的子字符串，则返回 0。
 * <p>
 * <p>
 * <p>
 * 示例 1：
 * <p>
 * 输入：s = "abcd", t = "bcdf", cost = 3
 * 输出：3
 * 解释：s 中的 "abc" 可以变为 "bcd"。开销为 3，所以最大长度为 3。
 * 示例 2：
 * <p>
 * 输入：s = "abcd", t = "cdef", cost = 3
 * 输出：1
 * 解释：s 中的任一字符要想变成 t 中对应的字符，其开销都是 2。因此，最大长度为 1。
 * 示例 3：
 * <p>
 * 输入：s = "abcd", t = "acde", cost = 0
 * 输出：1
 * 解释：你无法作出任何改动，所以最大长度为 1。
 * <p>
 * <p>
 * 提示：
 * <p>
 * 1 <= s.length, t.length <= 10^5
 * 0 <= maxCost <= 10^6
 * s 和 t 都只含小写英文字母。
 *
 * @author KIyA
 */
public class Algorithm1208 {

    public static void main(String[] args) {
        PrintUtil.log(equalSubstring("abcd", "cdef", 1));
    }

    public static int equalSubstring(String s, String t, int maxCost) {
        char[] arr1 = s.toCharArray();
        char[] arr2 = t.toCharArray();
        int length1 = s.length();
        int length2 = t.length();
        length1 = Math.min(length1, length2);
        //
        int begin = 0, end;
        int cost = 0;
        int maxLength = 0;
        int[] result = new int[length1];
        for (int i = 0; i < length1; i++) {
            int c = arr1[i] - arr2[i];
            if (c < 0) {
                c = -c;
            }
            // 滑动窗口走你
            cost += c;
            end = i;
            while (cost > maxCost && begin < i) {
                cost -= result[begin];
                begin++;
            }
            // 保证有匹配才计算最大值
            if (cost <= maxCost) {
                maxLength = Math.max(end - begin + 1, maxLength);
            }
            result[i] = c;
        }
        return maxLength;
    }
}
