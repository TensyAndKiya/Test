package com.clei.Y2019.M04.D10;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.*;

//将dev下面的修改同步到tes下
public class SyncDevUpdate2TesTest {

    //private final static String[] CONFIG_FILES = {};
    private final static String[] CONFIG_FILES = {"AlipayConfig.java","AlipayWXConfig.java","CdPayConfig.java","application.properties","main.jsp","mainV2.jsp","monitor.jsp","sub_monitor.jsp","inOut.jsp"};

    public static void main(String[] args){
        System.out.println("Sync A to B");
        System.out.println("输入A B");
        Scanner input = new Scanner(System.in,"UTF-8");
        String from = input.next();
        String to = input.next();
        String fromPath = "D:\\CLIdeaWorkspace\\" + from + "\\park\\";
        String toPath = "D:\\CLIdeaWorkspace\\" + to + "\\park\\";
        Map<String,String> updateFiles = new HashMap<>();
        System.out.println("输入需要同步的文件");
        String str = input.nextLine();
        while ( !str.contains("no changes added to commit") ){
            if(str.length() != 0 && str.contains("park-")){
                str = str.substring(str.indexOf("park-"));
                str= str.replaceAll("/","\\\\");
                updateFiles.put(fromPath + str,toPath + str);
            }
            str = input.nextLine();
        }
        Iterator<Map.Entry<String,String>> it = updateFiles.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry<String,String> entry = it.next();
            for(String s : CONFIG_FILES){
                if(entry.getKey().substring(entry.getKey().lastIndexOf("\\") + 1).equals(s)){
                    it.remove();
                    break;
                }
            }
        }
        updateFiles.forEach( (k,v) ->{
            try {
                overrideFile(k,v);
                System.out.println(k + "\t成功！");
            } catch (Exception e) {
                e.printStackTrace();
                return ;
            }
        } );
        System.out.println("同步修改文件成功！！！");
    }

    private static void overrideFile(String path,String toPath) throws Exception{
        File file = new File(path);
        if(!file.exists()){
            System.out.println("文件 " + path + "不存在！");
            return ;
        }
        File newFile = new File(toPath);
        if(!newFile.exists()){
            System.out.println("文件 " + toPath + "不存在！");
            return ;
        }
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try{
            fis = new FileInputStream(file);
            fos = new FileOutputStream(newFile);

            byte[] buffer = new byte[1024];
            int length = 0;

            while ( (length = fis.read(buffer)) != -1 ){
                fos.write(buffer,0,length);
            }
        }catch (Exception e){
            System.out.println("求你了大佬，不要报错！！！！");
            e.printStackTrace();
        }finally {
            if(null != fis){
                fis.close();
            }
            if(null != fos){
                fos.close();
            }
        }
    }
}
