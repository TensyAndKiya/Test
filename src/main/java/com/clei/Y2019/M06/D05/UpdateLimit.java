package com.clei.Y2019.M06.D05;

import com.clei.utils.PrintUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class UpdateLimit {
    public static void main(String[] args) {
        int limit;
        if(args.length > 0){
            String limitStr = args[0];
            try{
                limit = Integer.parseInt(limitStr);
            }catch (Exception e){
                PrintUtil.log("请输入正确的数字(0--100)");
                return;
            }
            if(limit < 0 || limit > 100){
                PrintUtil.log("请输入正确的数字(0--100)");
                return;
            }
            Properties prop = getProperties();
            //设置值
            prop.setProperty("limit_threshold",limit + "");
            if(updateProp(prop,null)){
                PrintUtil.log("修改值为" + limit + "成功！");
            }else{
                PrintUtil.log("修改值为" + limit + "失败！请重试");
            }
        }
    }

    private static Properties getProperties(){
        InputStream inputStream = UpdateLimit.class.getClassLoader().getResourceAsStream("load.properties");
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            PrintUtil.log("加载配置文件异常!");
            e.printStackTrace();
            return null;
        }finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                PrintUtil.log("关闭输入流异常!");
                e.printStackTrace();
            }
        }
        return properties;
    }


    private static boolean updateProp(Properties prop, String comment){
        if(null == prop){
            return false;
        }
        final String PATH = UpdateLimit.class.getClassLoader().getResource("load.properties").getPath();
        File file = new File(PATH);
        if(!file.exists()){
            PrintUtil.log("写入负载配置文件失败，文件不存在！");
            return false;
        }
        OutputStream outputStream = null;
        try{
            //加锁
            outputStream = new FileOutputStream(file);
            prop.store(outputStream,comment);
        }catch (Exception e){
            PrintUtil.log("加载属性文件出错！");
            e.printStackTrace();
            return false;
        }finally {
            if(null != outputStream){
                try{
                    outputStream.close();
                }catch(Exception ee){
                    PrintUtil.log("关闭输出流出错！");
                    ee.printStackTrace();
                }
            }
        }
        return true;
    }
}
