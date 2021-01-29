package com.clei.Y2020.M06.D11;

import com.clei.utils.PrintUtil;
import org.openjdk.jol.info.ClassLayout;

/**
 * 对象占多少空间
 */
public class Test {

    public static void main(String[] args) {

        PrintUtil.log(ClassLayout.parseClass(Long.class).toPrintable());
        PrintUtil.log(ClassLayout.parseClass(Boolean.class).toPrintable());
        PrintUtil.log(ClassLayout.parseClass(Temp.class).toPrintable());
        PrintUtil.log(ClassLayout.parseClass(Temp2.class).toPrintable());

        PrintUtil.log(ClassLayout.parseInstance(new Long(1234)).toPrintable());
        PrintUtil.log(ClassLayout.parseInstance(Boolean.FALSE).toPrintable());
        PrintUtil.log(ClassLayout.parseInstance(new Temp()).toPrintable());
        PrintUtil.log(ClassLayout.parseInstance(new Temp2()).toPrintable());
    }
}

class Temp {

    private Long l = Long.valueOf(1);
}

class Temp2 {

    private Temp temp = new Temp();
}
