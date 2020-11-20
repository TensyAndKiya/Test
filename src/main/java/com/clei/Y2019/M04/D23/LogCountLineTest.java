package com.clei.Y2019.M04.D23;

import com.clei.utils.PrintUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Scanner;

//用于快速筛选出百兆日志文件内的内容
public class LogCountLineTest {
    private static String LOG_FILE = "C:\\Users\\liudg\\Desktop\\logs\\2019-04-23 error\\temp.log";
    private static String TEMP_FILE = "C:\\Users\\liudg\\Desktop\\logs\\2019-04-23 error\\temp2.log";
    public static void main(String[] args) throws Exception{
        Scanner input = new Scanner(System.in,"UTF-8");
        PrintUtil.dateLine("请输入匹配字符串：");
        String searchStr = input.nextLine();
        File logFile = new File(LOG_FILE);
        File tempFile = new File(TEMP_FILE);
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(logFile),"UTF-8"));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempFile),"UTF-8"));
        String line;
        int count = 0;
        while( (line = br.readLine()) != null ){
            if(line.contains(searchStr)){
                bw.write(line);
                bw.newLine();
                count ++;
            }
        }
        br.close();
        PrintUtil.dateLine("searchStr : " + searchStr + "\t count : " + count);
    }
}
