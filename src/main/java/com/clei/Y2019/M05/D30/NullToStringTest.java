package com.clei.Y2019.M05.D30;

import com.clei.utils.PrintUtil;

import java.util.Date;

public class NullToStringTest {
    public static void main(String[] args) {
        Date date = null;
        PrintUtil.log(date + "");
    }
}
