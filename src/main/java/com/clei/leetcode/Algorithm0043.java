package com.clei.leetcode;

import com.clei.utils.PrintUtil;

/**
 * 字符串相乘
 * 给定两个以字符串形式表示的非负整数 num1 和 num2，返回 num1 和 num2 的乘积，它们的乘积也表示为字符串形式。
 * <p>
 * 示例 1:
 * <p>
 * 输入: num1 = "2", num2 = "3"
 * 输出: "6"
 * 示例 2:
 * <p>
 * 输入: num1 = "123", num2 = "456"
 * 输出: "56088"
 * 说明：
 * <p>
 * num1 和 num2 的长度小于110。
 * num1 和 num2 只包含数字 0-9。
 * num1 和 num2 均不以零开头，除非是数字 0 本身。
 * 不能使用任何标准库的大数类型（比如 BigInteger）或直接将输入转换为整数来处理。
 *
 * @author KIyA
 */
public class Algorithm0043 {


    public static void main(String[] args) {
        String a = "123";
        String b = "123";
        PrintUtil.log(multiply(a, b));
    }

    private static String multiply(String num1, String num2) {
        String zero = "0";
        if (zero.equals(num1) || zero.equals(num2)) {
            return zero;
        }

        char[] arr1 = num1.toCharArray();
        char[] arr2 = num2.toCharArray();

        char[] result = null;
        for (int i = arr1.length - 1; i > -1; i--) {
            int shift = arr1.length - 1 - i;
            char c = arr1[i];
            char[] tempArr = multiply(arr2, c);
            // 补充后面0
            if (shift > 1) {
                char[] newArr = new char[tempArr.length + shift - 1];
                for (int j = 0; j < tempArr.length; j++) {
                    newArr[j] = tempArr[j];
                }
                for (int j = tempArr.length; j < newArr.length; j++) {
                    newArr[j] = '0';
                }
                tempArr = newArr;
            }
            result = add(result, tempArr);
        }

        int offset = 0;
        for (int i = 0; i < result.length; i++) {
            if (result[i] != '0') {
                offset = i;
                break;
            }
        }
        return new String(result, offset, result.length - offset);
    }

    private static char[] multiply(char[] arr, char c) {
        int length = arr.length;
        int n = c - '0';
        char[] result = null;
        for (int i = length - 1; i > -1; i--) {
            int temp = n * (arr[i] - '0');
            int shift = length - 1 - i;
            char[] tempArr = String.valueOf(temp).toCharArray();
            // 补充后面0
            if (shift > 1) {
                char[] newArr = new char[tempArr.length + shift - 1];
                for (int j = 0; j < tempArr.length; j++) {
                    newArr[j] = tempArr[j];
                }
                for (int j = tempArr.length; j < newArr.length; j++) {
                    newArr[j] = '0';
                }
                tempArr = newArr;
            }
            result = add(result, tempArr);
        }
        return result;
    }

    /**
     * 1 2 3
     * + 4 5 6
     *
     * @param a 456
     * @param b 123
     * @return
     */
    private static char[] add(char[] a, char[] b) {
        if (null == a) {
            return b;
        }
        int aLength = a.length;
        int bLength = b.length;
        int length = Math.max(aLength, bLength) + 2;
        char[] result = new char[length];
        result[length - 1] = a[a.length - 1];
        boolean add = false;
        for (int i = length - 2; i > 0; i--) {
            int temp = 0;
            int aIndex = aLength - (length - i);
            if (aIndex > -1 && aIndex < aLength - 1) {
                temp = temp + a[aIndex] - '0';
            }
            int bIndex = bLength + 1 - (length - i);
            if (bIndex > -1 && bIndex < bLength) {
                temp = temp + b[bIndex] - '0';
            }
            if (add) {
                temp++;
                add = false;
            }
            if (temp > 9) {
                temp -= 10;
                add = true;
            }
            result[i] = (char) (temp + '0');
        }
        if (add) {
            result[0] = '1';
        } else {
            result[0] = '0';
        }
        return result;
    }
}
