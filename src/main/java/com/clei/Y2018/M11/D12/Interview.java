package com.clei.Y2018.M11.D12;

import com.clei.utils.PrintUtil;

public class Interview {
    public static void main(String[] args){
        Integer i1 = 55;
        int i2 = 55;
        Integer i3 = Integer.valueOf(55);
        Integer i4 = new Integer(55);
        PrintUtil.dateLine(i1 == i2);
        PrintUtil.dateLine(i1 == i3);
        PrintUtil.dateLine(i2 == i3);
        PrintUtil.dateLine(i1 == i4);
        PrintUtil.dateLine(i2 == i4);
        PrintUtil.dateLine(i3 == i4);

        Singleton instance1 = Singleton.getInstance();
        Singleton instance2 = Singleton.getInstance();
        Singleton instance3 = Singleton.getInstance();

        PrintUtil.dateLine(instance1 == instance2);
        PrintUtil.dateLine(instance1 == instance3);
    }
}

class Singleton{
    private static final Singleton instance = new Singleton();
    private Singleton(){}
    public static Singleton getInstance(){
        return instance;
    }
}
