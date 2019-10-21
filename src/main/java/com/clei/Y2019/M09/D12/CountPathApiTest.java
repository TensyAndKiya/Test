package com.clei.Y2019.M09.D12;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 用于统计指定文件夹中@Path接口的数量及api样子
 */
public class CountPathApiTest {

    private final static String CHARSET = "UTF-8";
    private final static String MARK_START = "@Path(\"";
    private final static int MARK_LENGTH = MARK_START.length();
    private final static String MARK_END = "\")";

    private static List<String> apiList = new ArrayList<>(60);

    public static void main(String[] args) throws Exception {
        File file = new File("D:\\CLIdeaWorkspace\\dev\\park\\park-webservice");
        count(file);
        int size = apiList.size();
        if(size > 0){
            for(String s : apiList){
                System.out.println(s);
            }
            System.out.println("\nSize : " + size);
        }
    }

    private static void count(File file) throws Exception {
        if(null != file && file.exists()){
            if(file.isDirectory()){
                File[] files = file.listFiles();
                for(File f : files){
                    count(f);
                }
            }else {
                // count java file api
                String name = file.getName();
                if(name.endsWith(".java")){
                    BufferedReader br = null;
                    try {
                        br = new BufferedReader(new InputStreamReader(new FileInputStream(file),CHARSET));
                        String line;
                        String api = "";
                        int i = 0;
                        while( null != (line = br.readLine()) ){
                            int index = line.indexOf(MARK_START);
                            if(index > -1){
                                String temp = line.substring(index + MARK_LENGTH,line.indexOf(MARK_END));
                                if(!temp.startsWith("/")){
                                    temp = "/" + temp;
                                }
                                if(temp.endsWith("/")){
                                    temp = temp.substring(0,temp.length() - 1);
                                }
                                if(i == 0){
                                    api = temp;
                                }else {
                                    temp = api + temp;
                                    // 添加到api列表里
                                    apiList.add(name + '\t' + temp);
                                }
                                i ++;
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally {
                        if(null != br){
                            br.close();
                        }
                    }
                }
            }
        }
    }
}
