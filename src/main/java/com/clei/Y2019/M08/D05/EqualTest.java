package com.clei.Y2019.M08.D05;

public class EqualTest {
    public static void main(String[] args) {
        System.out.println(equals(null,null));
    }

    public static boolean equals(final CharSequence cs1, final CharSequence cs2){
        if (cs1 == cs2) {
            System.out.println(1);
            return true;
        }
        if (cs1 == null || cs2 == null) {
            System.out.println(2);
            return false;
        }
        if (cs1 instanceof String && cs2 instanceof String) {
            System.out.println(3);
            return cs1.equals(cs2);
        }
        System.out.println("default");
        return false;
    }
}
