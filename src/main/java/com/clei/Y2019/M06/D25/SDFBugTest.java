package com.clei.Y2019.M06.D25;

import com.clei.utils.PrintUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 重现多线程下SDF时间错乱的bug
 */
public class SDFBugTest {
    private final static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final static String DATE_STR = "1995-10-03 11:11:11";
    public static void main(String[] args){
        ConcurrentHashMap<String,Object> map = new ConcurrentHashMap<>();
        MyRunnable runnable = new MyRunnable(map);
        for(int i = 0; i < 100; i ++){
            new Thread(runnable).start();
        }
        Enumeration<String> enumeration = map.keys();
        while(enumeration.hasMoreElements()){
            String s = enumeration.nextElement();
            PrintUtil.dateLine(s);
        }
    }

    static class MyRunnable implements Runnable{
        ConcurrentHashMap<String,Object> map;
        public MyRunnable(ConcurrentHashMap<String,Object> map){
            this.map = map;
        }
        @Override
        public void run() {
            try {
                Date date = SDF.parse(DATE_STR);
                String str = SDF.format(date);
                //
                map.put(str,"");
            } catch (Exception e) {
                // 直接把错误吃掉！！！
            }

        }
    }

}
