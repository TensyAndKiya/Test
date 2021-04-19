package com.clei.Y2019.M04.D17;

import com.clei.utils.PrintUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Scanner;

/**
 * 快速筛选出百兆日志文件内的内容
 *
 * @author KIyA
 */
public class LogContentTest {

    /**
     * 日志文件路径
     */
    private final static String LOG_DIRECTORY = "C:\\Users\\liudg\\Desktop\\logs\\2019-04-23 error";

    /**
     * 关键字
     */
    private final static String KEYWORD = "successPaid";

    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in, "UTF-8");
        PrintUtil.log("请输入匹配字符串：");
        //String outputFileName = input.next();
        String searchStr = input.next();
        File logFile = new File(LOG_DIRECTORY + File.separator + "all_error_0422.log");
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(logFile), "UTF-8"));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(LOG_DIRECTORY + File.separator + "temp.log"), "UTF-8"));
        boolean successPaid = false;
        String line;
        String preLine = "";
        while ((line = br.readLine()) != null) {
            if (line.contains(searchStr)) {
                bw.write(preLine);
                bw.write(line);
                if (line.contains(KEYWORD)) {
                    successPaid = true;
                }
                PrintUtil.log(preLine);
                PrintUtil.log(line);
            }
            preLine = line;
        }
        br.close();
        bw.close();
        if (successPaid) {
            PrintUtil.log("支付成功！");
        } else {
            PrintUtil.log("支付失败！");
        }

    }
}
