package com.clei.Y2018.M08.D14;

import com.clei.utils.NumUtil;
import com.clei.utils.PrintUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 同上个InterviewProblem类一样
 * 没有描述问题
 *
 * @author KIyA
 */
public class InterviewProblem {

    public static void main(String[] args) {
        List<Integer> result = getPrimeNum(100);
        PrintUtil.log(result);

        String str = "AAAAabcdefabcadeffbghxxZZZZ";
        outChar(str.toCharArray());
    }

    /**
     * 获取1到max区间内的质数
     * 左开右闭区间
     *
     * @param max
     * @return
     */
    private static List<Integer> getPrimeNum(int max) {
        List<Integer> result = new ArrayList<>();
        // 2 3肯定是质数
        if (max > 1) {
            result.add(2);
        }
        if (max > 2) {
            result.add(3);
        }
        // 大于3的的偶数肯定不是质数
        // 直接从5开始，并且只计算奇数
        max = max + 1;
        for (int i = 5; i < max; i += 2) {
            if (NumUtil.isPrime(i)) {
                result.add(i);
            }
        }
        return result;
    }

    /**
     * 输出出现次数最多的字母
     *
     * @param arr
     */
    private static void outChar(char[] arr) {
        // 既然只计算字母的话，那就只计算大小写字母的出现次数
        int length = 26;
        int[] lowerCaseArr = new int[length];
        int[] upperCaseArr = new int[length];

        int max = 0;
        for (char c : arr) {
            int i = c;
            if (i > 64 && i < 91) {
                int index = i - 65;
                int temp = lowerCaseArr[index] + 1;
                lowerCaseArr[index] = temp;
                if (temp > max) {
                    max = temp;
                }
            } else if (i > 96 && i < 122) {
                int index = i - 97;
                int temp = upperCaseArr[index] + 1;
                upperCaseArr[index] = temp;
                if (temp > max) {
                    max = temp;
                }
            }
        }
        // 输出
        if (0 == max) {
            return;
        }
        for (int i = 0; i < length; i++) {
            if (max == lowerCaseArr[i]) {
                char c = (char) (i + 65);
                PrintUtil.log(c);
            }
            if (max == upperCaseArr[i]) {
                char c = (char) (i + 97);
                PrintUtil.log(c);
            }
        }
    }
}
