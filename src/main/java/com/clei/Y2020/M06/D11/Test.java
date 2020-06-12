package com.clei.Y2020.M06.D11;

import org.openjdk.jol.info.ClassLayout;

/**
 * 对象占多少空间
 */
public class Test {
    public static void main(String[] args) {

        System.out.println(ClassLayout.parseClass(Long.class).toPrintable());
        System.out.println(ClassLayout.parseClass(Temp.class).toPrintable());
        System.out.println(ClassLayout.parseClass(Temp2.class).toPrintable());

        System.out.println(ClassLayout.parseInstance(new Long(0)).toPrintable());
        System.out.println(ClassLayout.parseInstance(new Temp()).toPrintable());
        System.out.println(ClassLayout.parseInstance(new Temp2()).toPrintable());

    }
}

class Temp{
    private Long l;
}

class Temp2{
    private Temp temp;
}
