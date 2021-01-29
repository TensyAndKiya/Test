package com.clei.Y2019.M09.D25;

import com.clei.utils.FileUtil;
import com.clei.utils.PrintUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class FindTxtTest {
    public static void main(String[] args) throws Exception{
        File file = new File("C:\\Users\\liudg\\Desktop\\农行相关\\TrustPayClient-Java-V3.1.6(1)\\TrustPayClient-Java-V3.1.6\\demo");
        // 删除指定目录下的.class文件
        FileUtil.fileOperation(file, f -> {
            String fileName = f.getName();
            if(fileName.endsWith(".html") || fileName.endsWith(".jsp")){
                transfer(f);
            }
        });
    }

    private static void transfer(File file) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
        String str;
        while(null != (str = br.readLine())){
            if(str.contains("PaymentResult")){
                PrintUtil.log(file.getName());
                break;
            }
        }
        br.close();
    }
}
