package com.clei.Y2019.M09.D23;

import com.clei.utils.FileUtil;

import java.io.*;

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
            if(str.contains("cluster")){
                System.out.println(file.getAbsolutePath());
                System.out.println(file.getName());
                System.out.println("hasaki:::");
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
