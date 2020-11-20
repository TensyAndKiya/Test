package com.clei.Y2019.M12.D19;

import com.clei.utils.PrintUtil;
import com.clei.utils.StringUtil;

import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        String[] aaa = {"aa","bb","cc"};
        PrintUtil.dateLine(Arrays.toString(aaa));

        for (int i = 0; i < aaa.length; i++) {
            aaa[i] = "";
            PrintUtil.dateLine(StringUtil.isEmpty(aaa[i]));
        }

        PrintUtil.dateLine(Arrays.toString(aaa));
    }
}
