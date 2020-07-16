package com.clei.utils;

import com.alibaba.fastjson.JSONObject;
import com.jdcloud.sdk.utils.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            if(!StringUtils.isEmpty(fileSuffix) && fileName.endsWith(fileSuffix)){
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

    public static void rewriteStr(String directory,String fileSuffix,String... strs) throws Exception{
        File file = new File(directory);

        List<Map<String,String>> list = new ArrayList<>();
        List<String> fileContent = new ArrayList<>();
        fileOperation(file, f -> {
            String fileName = f.getName();
            if(!file.isDirectory() && fileName.endsWith(fileSuffix)){


                Map<String,String> m = new HashMap<>();

                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
                String str;
                while(null != (str = br.readLine())){
                    String origin = str;
                    fileContent.add(origin);

                    boolean always = true;
                    for (int i = 0; i < strs.length; i++) {
                        if(!str.contains(strs[i])){
                            always = false;
                            break;
                        }else {

                            PrintUtil.print(str);

                            str = str.replace(strs[i],"");
                        }
                    }
                    // 全都有
                    if(always){

                        PrintUtil.println("wocao");

                        m.put("fileName",file.getAbsolutePath());
                        m.put("origin",origin);
                        // 暂时假设 长度 2
                        // AND timerule_name LIKE '%${timeRuleName}%'
                        // CONCAT('%',#{license},'%')
                        int index1 = origin.indexOf("'%${");
                        int index2 = origin.replace("'%${","").indexOf("}%") + 4;
                        String newStr = str.substring(0,index1) + "CONCAT('%',#{"
                                        + str.substring(index1 + 4,index2) + str.substring(index2 + 2);

                        m.put("newStr",newStr);
                        list.add(m);
                    }
                }
                br.close();


                PrintUtil.println("change : " + JSONObject.toJSONString(list));

                PrintUtil.println("old : " + fileContent);


                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),EncryptUtil.CHARSET_UTF8));
                for (String s : fileContent){
                    String writeStr = s;
                    for(Map<String,String> mm : list){
                        String origin = mm.get("origin");
                        if(writeStr.equals(origin)){
                            writeStr = mm.get("newStr");
                            break;
                        }
                    }
                    bw.write(writeStr);
                    bw.write("\n");
                }
            }
        });
    }

    /**
     * 二进制文件转为16 进制 样子
     * 2020-07-16
     */
    public static void binFileToHexString(String path) throws Exception {
        File file = new File(path);

        FileInputStream fis = new FileInputStream(file);

        byte[] bytes = new byte[16];

        int length = -1;

        while ((length = fis.read(bytes)) != -1){
            String str = EncryptUtil.byteArrToHexString(bytes,true);

            char[] array = str.toCharArray();

            for (int i = 0; i < array.length; i++) {

                if(i % 16 == 0){
                    System.out.println();
                }else if(i % 2 == 0){
                    System.out.print(' ');
                }

                System.out.print(array[i]);
            }
        }
    }

    public interface Operation{
        void operate(File file) throws Exception;
    }
}
