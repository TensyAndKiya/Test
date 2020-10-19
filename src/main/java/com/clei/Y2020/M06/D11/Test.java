package com.clei.Y2020.M06.D11;

import com.clei.utils.PrintUtil;
import org.openjdk.jol.info.ClassLayout;

/**
 * 对象占多少空间
 */
public class Test {
    public static void main(String[] args) {

        PrintUtil.dateLine(ClassLayout.parseClass(Long.class).toPrintable());
        PrintUtil.dateLine(ClassLayout.parseClass(Temp.class).toPrintable());
        PrintUtil.dateLine(ClassLayout.parseClass(Temp2.class).toPrintable());

        PrintUtil.dateLine(ClassLayout.parseInstance(new Long(0)).toPrintable());
        PrintUtil.dateLine(ClassLayout.parseInstance(new Temp()).toPrintable());
        PrintUtil.dateLine(ClassLayout.parseInstance(new Temp2()).toPrintable());

    }
}

class Temp{
    private Long l;
}

class Temp2{
    private Temp temp;
}
