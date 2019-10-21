package com.clei.Y2019.M04.D17;

import java.io.*;
import java.util.Scanner;

//用于快速筛选出百兆日志文件内的内容
public class LogContentTest{
    private final static String LOG_DIRECTORY = "C:\\Users\\liudg\\Desktop\\logs\\2019-04-23 error";
    public static void main(String[] args) throws Exception{
        Scanner input = new Scanner(System.in,"UTF-8");
        System.out.println("请输入匹配字符串：");
        //String outputFileName = input.next();
        String searchStr = input.next();
        File logFile = new File(LOG_DIRECTORY + File.separator + "all_error_0422.log");
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(logFile),"UTF-8"));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(LOG_DIRECTORY + File.separator + "temp.log"),"UTF-8"));
        boolean successPaid = false;
        String line = "";
        String preLine = "";
        while( (line = br.readLine()) != null ){
            if(line.contains(searchStr)){
                bw.write(preLine);
                bw.write(line);
                if(line.contains("successPaid")){
                    successPaid = true;
                }
                System.out.println(preLine);
                System.out.println(line);
            }
            preLine = line;
        }
        br.close();
        bw.close();
        if(successPaid){
            System.out.println("支付成功！");
        }else{
            System.out.println("支付失败！");
        }

    }
}
