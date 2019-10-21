package com.clei.Y2019.M04.D04;

public class BlockingQueueTest {
    public static void main(String[] args){
        String path = BlockingQueueTest.class.getProtectionDomain().getCodeSource().getLocation().getFile();
        String[] str = path.split("/");
        StringBuilder configPath = new StringBuilder("");
        for (int i = 0; i < str.length-1; i++) {
            if( null != str[i] && str[i].length() != 0 ){
                configPath.append("/"+str[i]);
            }
        }
        System.out.println(path);
        System.out.println(configPath.toString()+"/conf/");
    }
}
