package com.clei.Y2019.M04.D01;

import com.clei.utils.PrintUtil;

import java.io.File;

public class FilePathTest {
    public static void main( String[] args ) {
        String filePath = "/usr/local/tomcat/webapps/photo/park_log";
        File file = new File(filePath);
        boolean result = false;
        if(!file.exists()){
            result = file.mkdirs();
        }
        if(result){
            printFilePath(file);
        }
    }

    private static void printFilePath(File file){
        printFilePath(file, 0);
    }

    private static void printFilePath(File file,int level){
        StringBuilder tableStr = new StringBuilder();
        for (int i = 0; i < level; i++) {
            tableStr.append('\t');
        }
        PrintUtil.log(tableStr + file.getAbsolutePath());
        if(file.isDirectory()){
            File[] files = file.listFiles();
            if(null != files){
                for(File f : files){
                    printFilePath(f,level+1);
                }
            }
        }
    }
}
