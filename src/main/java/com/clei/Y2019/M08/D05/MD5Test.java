package com.clei.Y2019.M08.D05;

import com.clei.utils.MD5Util;
import com.clei.utils.PrintUtil;

public class MD5Test {
    public static void main(String[] args) {
        String mobile = "18655101205";
        String codeKey = "www.eeparking.com";

        String str = MD5Util.md5(mobile + codeKey);
        String str2 = "18672f066e94c871df6774ec8d4ac6e6";
        PrintUtil.dateLine(str.equals(str2));
    }
}
