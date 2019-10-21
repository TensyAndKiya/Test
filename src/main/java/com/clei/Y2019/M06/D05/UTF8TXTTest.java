package com.clei.Y2019.M06.D05;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class UTF8TXTTest {
    public static void main(String[] args) throws Exception{
        //这样子搞出来的文本就是使用UTF8编码的
        String path = "D:\\DEV";
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path + File.separator + "test.txt"),"UTF-8"));
        bw.write("我只是测试一下！！！");
        bw.flush();
        bw.close();
    }
}
