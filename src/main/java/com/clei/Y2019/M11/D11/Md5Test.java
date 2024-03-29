package com.clei.Y2019.M11.D11;

import com.clei.utils.MD5Util;
import com.clei.utils.PrintUtil;
import org.apache.commons.codec.digest.DigestUtils;

import java.security.MessageDigest;

/**
 * md5测试类
 *
 * @author KIyA
 */
public class Md5Test {

    public static void main(String[] args) {
        String str1 = "123456pda";
        PrintUtil.log(md5(str1));
        PrintUtil.log(DigestUtils.md5Hex(str1));
        PrintUtil.log(MD5Util.md55(str1));
        PrintUtil.log(MD5Util.md5(str1));

        String str2 = "带中文的字符串";
        PrintUtil.log(md5(str2));
        PrintUtil.log(DigestUtils.md5Hex(str2));
        PrintUtil.log(MD5Util.md55(str2));
        PrintUtil.log(MD5Util.md5(str2));
    }


    private static String md5(String pwd) {
        //用于加密的字符
        char[] md5String = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            //使用平台的默认字符集将此 String 编码为 byte序列，并将结果存储到一个新的 byte数组中
            byte[] btInput = pwd.getBytes();

            //信息摘要是安全的单向哈希函数，它接收任意大小的数据，并输出固定长度的哈希值。
            MessageDigest mdInst = MessageDigest.getInstance("MD5");

            //MessageDigest对象通过使用 update方法处理数据， 使用指定的byte数组更新摘要
            mdInst.update(btInput);

            // 摘要更新之后，通过调用digest（）执行哈希计算，获得密文
            byte[] md = mdInst.digest();

            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char[] arr = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                arr[k++] = md5String[byte0 >>> 4 & 0xf];
                arr[k++] = md5String[byte0 & 0xf];
            }
            //返回经过加密后的字符串
            return new String(arr);
        } catch (Exception e) {
            PrintUtil.log("md5 出错", e);
        }
        return null;
    }
}
