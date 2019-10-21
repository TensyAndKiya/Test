package com.clei.utils;

import com.jdcloud.sdk.utils.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class FileUtil {

    public static void fileOperation(File file,Operation operation) throws Exception {
        if(file.isDirectory()){
            for(File f :file.listFiles()){
                fileOperation(f,operation);
            }
        }else {
            operation.operate(file);
        }
    }

    public static void findFileTxt(String directory,String fileSuffix,String content) throws Exception {
        File file = new File(directory);
        // 删除指定目录下的.class文件
        fileOperation(file, f -> {
            String fileName = f.getName();
            if(StringUtils.isEmpty(fileSuffix) || fileName.endsWith(fileSuffix)){
                findContent(f,content);
            }
        });
    }

    private static void findContent(File file,String content) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
        String str;
        while(null != (str = br.readLine())){
            if(str.contains(content)){
                PrintUtil.println(file.getAbsoluteFile());
                break;
            }
        }
        br.close();
    }

    public interface Operation{
        void operate(File file) throws Exception;
    }
}
