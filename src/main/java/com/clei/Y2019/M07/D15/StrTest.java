package com.clei.Y2019.M07.D15;

import com.clei.utils.PrintUtil;

public class StrTest {
    public static void main(String[] args) {
        String str = "我是谁？";
        for (int i = 0; i < str.length(); i++) {
            PrintUtil.log(str.toCharArray()[i]);
        }
    }
}
