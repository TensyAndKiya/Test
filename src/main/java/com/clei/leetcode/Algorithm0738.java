package com.clei.leetcode;

import com.clei.utils.PrintUtil;

/**
 * 单调递增的数字
 * 给定一个非负整数N，找出小于或等于N的最大的整数，同时这个整数需要满足其各个位数上的数字是单调递增。
 * <p>
 * （当且仅当每个相邻位数上的数字x和y满足x <= y时，我们称这个整数是单调递增的。）
 * <p>
 * 示例 1:
 * <p>
 * 输入: N = 10
 * 输出: 9
 * 示例 2:
 * <p>
 * 输入: N = 1234
 * 输出: 1234
 * 示例 3:
 * <p>
 * 输入: N = 332
 * 输出: 299
 * 说明: N是在[0, 10^9]范围内的一个整数。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/monotone-increasing-digits
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author KIyA
 */
public class Algorithm0738 {

    public static void main(String[] args) {
        PrintUtil.log(monotoneIncreasingDigits(332));
    }

    private static int monotoneIncreasingDigits(int N) {
        String str = String.valueOf(N);
        int length = str.length();
        StringBuilder sb = new StringBuilder(length);
        char[] arr = str.toCharArray();
        for (int i = 0; i < length - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                sb.append((char) (arr[i] - 1));
                // 判断前面的数字是不是大于后面改变的数字
                setSb(sb);
                for (int j = 0; j < length - 1 - i; j++) {
                    sb.append('9');
                }
                break;
            } else {
                sb.append(arr[i]);
            }
        }
        // 非break
        if (length != sb.length()) {
            sb.append(arr[length - 1]);
        }
        return Integer.parseInt(sb.toString());
    }

    private static void setSb(StringBuilder sb) {
        int length = sb.length();
        for (int j = length - 1; j > 0; j--) {
            char c1 = sb.charAt(j);
            char c2 = sb.charAt(j - 1);
            if (c1 < c2) {
                sb.setCharAt(j - 1, (char) (sb.charAt(j - 1) - 1));
                sb.setCharAt(j, '9');
            }
        }
    }
}
