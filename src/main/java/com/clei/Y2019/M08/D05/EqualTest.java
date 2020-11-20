package com.clei.Y2019.M08.D05;

import com.clei.utils.PrintUtil;

public class EqualTest {
    public static void main(String[] args) {
        PrintUtil.dateLine(equals(null, null));
    }

    public static boolean equals(final CharSequence cs1, final CharSequence cs2){
        if (cs1 == cs2) {
            PrintUtil.dateLine(1);
            return true;
        }
        if (cs1 == null || cs2 == null) {
            PrintUtil.dateLine(2);
            return false;
        }
        if (cs1 instanceof String && cs2 instanceof String) {
            PrintUtil.dateLine(3);
            return cs1.equals(cs2);
        }
        PrintUtil.dateLine("default");
        return false;
    }
}
