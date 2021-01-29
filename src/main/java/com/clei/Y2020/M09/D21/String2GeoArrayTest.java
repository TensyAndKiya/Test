package com.clei.Y2020.M09.D21;

import com.clei.utils.PrintUtil;

/**
 * 将一组经纬度数组字符串转为[[18.3333,19.4444],[20.11223,19.31231]]格式
 *
 * @author KIyA
 */
public class String2GeoArrayTest {

    public static void main(String[] args) {

        String str = "106.580744,26.596785\n" +
                "106.593103,26.552262\n" +
                "106.646662,26.478225\n" +
                "106.742792,26.466854\n" +
                "106.784334,26.49236\n" +
                "106.841326,26.462551\n" +
                "106.9172,26.433349\n" +
                "106.938486,26.44334\n" +
                "106.938486,26.44334\n" +
                "106.974106,26.448373";

        String[] arr = str.split("\n");

        StringBuilder sb = new StringBuilder(str.length());

        sb.append('[');

        for (String s : arr) {
            sb.append('[');

            int index = s.indexOf(",");

            sb.append(s, 0, index);

            sb.append(",");

            sb.append(s.substring(index + 1));

            sb.append("],");
        }

        int length = sb.length();
        // 删除尾部逗号
        if (length > 1) {
            sb.deleteCharAt(length - 1);
        }
        sb.append(']');

        PrintUtil.log(sb.toString());

    }
}
