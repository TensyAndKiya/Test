package com.clei.utils;

/**
 * 数字处理工具
 *
 * @author KIyA
 * @date 2020-06-15
 */
public class NumUtil {

    private final static char[] lCaseArr;

    private final static char[] uCaseArr;

    // 初始化
    static {
        lCaseArr = new char[36];
        uCaseArr = new char[36];

        // 10个数字字符
        for (int i = 0; i < 10; i++) {
            lCaseArr[i] = (char) ('0' + i);
            uCaseArr[i] = (char) ('0' + i);
        }

        // 26个字母字符 分大小写
        for (int i = 0; i < 26; i++) {
            lCaseArr[i + 10] = (char) ('a' + i);
            uCaseArr[i + 10] = (char) ('A' + i);
        }
    }

    /**
     * 数字进制转换
     * @param str
     * @param from
     * @param to
     * @return
     */
    public static String transRadix(String str, int from, int to){
        // 校验数据合法性
        if(from < 2 || from > 36 || to < 2 || to > 36){
            throw new RuntimeException("你是沙雕吗？");
        }
        if(!StringUtil.isDigitOrChar(str)){
            throw new RuntimeException("你是真滴沙雕！");
        }

        // 获取10进制的num
        int num = Integer.parseInt(str,from);

        // 转成to进制的String
        return Integer.toString(num,to);
    }

    public static void main(String[] args) {

        System.out.println(transRadix("x",333,10));
    }
}
