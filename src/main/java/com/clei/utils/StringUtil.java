package com.clei.utils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 *
 * @author KIyA
 */
public class StringUtil {

    public static String createOrderNo() {
        String timestamp = DateUtil.format(LocalDateTime.now(), "yyyyMMddHHmmss");
        Integer max = 999999;
        int randomInt = new Random().nextInt(999999);
        StringBuilder sb = new StringBuilder();
        sb.append(timestamp);
        int length = String.valueOf(max).length() - String.valueOf(randomInt).length();
        for (int i = 0; i < length; i++) {
            sb.append('0');
        }
        sb.append(randomInt);
        return sb.toString();
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isEmpty(String str) {
        return null == str || 0 == str.length();
    }

    /**
     * 去掉空白
     *
     * @param str
     * @return
     */
    public static String trimBlank(String str) {
        if (isEmpty(str)) {
            return str;
        }

        char[] arr = str.toCharArray();

        StringBuilder sb = new StringBuilder();

        for (char c : arr) {
            if (' ' == c || '\t' == c || '\r' == c || '\n' == c) {
                continue;
            }
            sb.append(c);
        }
        return sb.toString();
    }

    /**
     * 获得一个正确格式的url【后缀】
     *
     * @param url
     * @return
     */
    public static String getUrl(String url) {
        if (isNotEmpty(url)) {
            char slash = '/';
            char[] arr = url.toCharArray();
            StringBuilder sb = new StringBuilder(url.length());
            sb.append(slash);
            for (char c : arr) {
                if (slash == c && slash == sb.charAt(sb.length() - 1)) {
                    continue;
                }
                sb.append(c);
            }
            if (slash == sb.charAt(sb.length() - 1)) {
                sb.deleteCharAt(sb.length() - 1);
            }
            url = sb.toString();
        }
        return url;
    }

    /**
     * str 是否包含 strArr中的某个字符串
     *
     * @param str
     * @param strArr
     * @return
     */
    public static boolean contains(String str, String[] strArr) {
        return containsIndex(str, strArr) > -1;
    }

    /**
     * str 是否包含 strArr中的某个字符串
     *
     * @param str
     * @param strArr
     * @return 第一个包含字符串的索引
     */
    public static int containsIndex(String str, String[] strArr) {

        if (isEmpty(str) || null == strArr || 0 == strArr.length) {
            throw new RuntimeException("参数为空");
        }

        for (int i = 0; i < strArr.length; i++) {
            if (isNotEmpty(strArr[i])) {

                boolean result = str.contains(strArr[i]);

                if (result) {
                    return i;
                }
            }
        }

        return -1;
    }

    /**
     * 是数字
     *
     * @param str
     * @return
     */
    public static boolean isDigit(String str) {
        String pattern = "\\d*";

        return Pattern.matches(pattern, str);
    }

    /**
     * 是数字或字符
     *
     * @param str
     * @return
     */
    public static boolean isDigitOrChar(String str) {
        String pattern = "[0-9A-Za-z]*";

        return Pattern.matches(pattern, str);
    }

    public static String[] arrayRemoveDuplicate() {
        String[] array = new String[0];
        array = new HashSet<String>(Arrays.asList(array)).toArray(array);
        return array;
    }

    /**
     * 反转字符串
     *
     * @param str
     * @return
     */
    public static String reverse(String str) {
        // 空直接返回
        if (isEmpty(str)) {
            return str;
        }
        // 转成数组再翻转
        char[] arr = str.toCharArray();
        ArrayUtil.reverse(arr);
        return new String(arr);
    }
}
