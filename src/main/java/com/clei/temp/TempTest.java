package com.clei.temp;

import com.clei.utils.PrintUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 临时的小段代码的测试
 *
 * @author KIyA
 */
public class TempTest {

    public static void main(String[] args) {

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSzzz");
        PrintUtil.println("yyyy-MM-dd'T'HH:mm:ss.SSSzzz    " + sdf.format(date));
        sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        PrintUtil.println("yyyy-MM-dd'T'HH:mm:ss.SSSXXX    " + sdf.format(date));
    }
}
