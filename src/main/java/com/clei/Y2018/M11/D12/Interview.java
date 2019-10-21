package com.clei.Y2018.M11.D12;

public class Interview {
    public static void main(String[] args){
        Integer i1=55;
        int i2=55;
        Integer i3=Integer.valueOf(55);
        Integer i4=new Integer(55);
        System.out.println(i1==i2);
        System.out.println(i1==i3);
        System.out.println(i2==i3);
        System.out.println(i1==i4);
        System.out.println(i2==i4);
        System.out.println(i3==i4);

        Singleton instance1 = Singleton.getInstance();
        Singleton instance2 = Singleton.getInstance();
        Singleton instance3 = Singleton.getInstance();

        System.out.println(instance1 == instance2);
        System.out.println(instance1 == instance3);
    }
}

class Singleton{
    private static final Singleton instance = new Singleton();
    private Singleton(){}
    public static Singleton getInstance(){
        return instance;
    }
}
