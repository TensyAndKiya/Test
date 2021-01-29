package com.clei.Y2019.M09.D23;

import com.clei.utils.FileUtil;
import com.clei.utils.PrintUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class CopyTxtContentTest {
    public static void main(String[] args) throws Exception{
        File file = new File("D:\\CLIdeaWorkspace\\git");
        // 删除指定目录下的.class文件
        FileUtil.fileOperation(file, f -> {
            String fileName = f.getName();
            if(fileName.endsWith(".yml")){
                findContent(f);
            }
        });
    }

    private static void findContent(File file) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
        String str;
        while(null != (str = br.readLine())){
            if(str.contains("cluster")) {
                PrintUtil.log(file.getAbsolutePath());
                PrintUtil.log(file.getName());
                PrintUtil.log("hasaki:::");
                break;
            }
        }
        br.close();
    }

    private static void transfer(File file) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
        StringBuilder sb = new StringBuilder();
        String str;
        while(null != (str = br.readLine())){
            sb.append(str + '\n');
        }
        br.close();
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"GB2312"));
        bw.write(sb.toString());
        bw.close();
    }
}
