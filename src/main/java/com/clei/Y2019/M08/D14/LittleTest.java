package com.clei.Y2019.M08.D14;

public class LittleTest {
    private static String getRightName(String name,int length) throws Exception {
        if(name.getBytes("GBK").length < length + 1){
            return name;
        }
        return getRightName(name.substring(1),length);
    }

    public static void main(String[] args) throws Exception {
        System.out.println(getRightName("我是你滴大爷爷呀唉",8));
    }
}
