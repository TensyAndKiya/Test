package com.clei.Y2019.M08.D05;

import com.clei.utils.PrintUtil;

public class EqualTest {
    public static void main(String[] args) {
        PrintUtil.log(equals(null, null));
    }

    public static boolean equals(final CharSequence cs1, final CharSequence cs2){
        if (cs1 == cs2) {
            PrintUtil.log(1);
            return true;
        }
        if (cs1 == null || cs2 == null) {
            PrintUtil.log(2);
            return false;
        }
        if (cs1 instanceof String && cs2 instanceof String) {
            PrintUtil.log(3);
            return cs1.equals(cs2);
        }
        PrintUtil.log("default");
        return false;
    }
}
