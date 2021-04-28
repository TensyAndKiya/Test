package com.clei.Y2019.M04.D23;

import com.clei.utils.PrintUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * 截取日志内有指定内容的内容
 *
 * @author KIyA
 */
public class LogCountLineTest {

    /**
     * 日志文件路径
     */
    private final static String LOG_FILE = "C:\\Users\\liudg\\Desktop\\logs\\2019-04-23 error\\temp.log";

    /**
     * 输出文件路径
     */
    private final static String TEMP_FILE = "C:\\Users\\liudg\\Desktop\\logs\\2019-04-23 error\\temp2.log";

    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in, "UTF-8");
        PrintUtil.log("请输入匹配字符串：");
        String searchStr = input.nextLine();
        File logFile = new File(LOG_FILE);
        File tempFile = new File(TEMP_FILE);
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(logFile), StandardCharsets.UTF_8));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempFile), StandardCharsets.UTF_8));
        String line;
        int count = 0;
        while ((line = br.readLine()) != null) {
            if (line.contains(searchStr)) {
                bw.write(line);
                bw.newLine();
                count++;
            }
        }
        br.close();
        PrintUtil.log("searchStr : " + searchStr + "\t count : " + count);
    }
}
