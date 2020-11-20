package com.clei.Y2019.M06.D05;

import com.clei.utils.PrintUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class PropertiesTest {
    private final static ReentrantReadWriteLock.WriteLock writeLock = new ReentrantReadWriteLock().writeLock();
    private final static int DEFAULT_PERCENT = 0;
    private static String PATH = PropertiesTest.class.getClassLoader().getResource("load.properties").getPath();
    public static void main(String[] args) {
        Properties prop = getPropByFile();
        String parks = prop.getProperty("limit_parks");
        String[] park = parks.split(",");
        PrintUtil.dateLine(park.length);
        for(String s : park){
            PrintUtil.dateLine(s);
        }
    }

    private static Properties getPropByFile(){
        Properties prop = new Properties();
        File file = new File(PATH);
        if(!file.exists()){
            PrintUtil.dateLine("读取负载配置文件失败，文件不存在！");
            return null;
        }
        InputStream inputStream = null;
        try{
            inputStream = new FileInputStream(file);
            prop.load(inputStream);
        }catch (Exception e){
            PrintUtil.dateLine("加载属性文件出错！");
            e.printStackTrace();
        }finally {
            if(null != inputStream){
                try{
                    inputStream.close();
                }catch(Exception ee){
                    PrintUtil.dateLine("关闭输入流出错！");
                    ee.printStackTrace();
                }
            }
        }
        return prop;
    }

    private static Set<String> getKeysByProp(Properties prop){
        Set<String> keys = prop.stringPropertyNames();
        if(null != keys && keys.size() > 0){
            return keys;
        }else{
            return null;
        }
    }

    private static int getIntPercent(Properties prop) {
        String percentStr =  prop.getProperty("percent");
        //出错则默认0值
        int percent = DEFAULT_PERCENT;
        //非null 非空串
        if(null != percentStr && percentStr.length() > 0){
            try{
                percent = Integer.parseInt(percentStr);
            }catch (Exception e){
                PrintUtil.dateLine("获取阀值出错！！");
            }
        }
        return percent;
    }

    private static String getPercent(Properties prop) {
        String percentStr =  prop.getProperty("percent");
        //非null 非空串
        if(null != percentStr && percentStr.length() > 0){
            return percentStr;
        }
        return DEFAULT_PERCENT + "";
    }

    private static Set<String> getParks(Properties prop){
        Set<String> keys = getKeysByProp(prop);
        if(null != keys){
            Set<String> parks = new HashSet<>(keys.size());
            for(String key : keys){
                if(key.startsWith("park")){
                    String parkId = prop.getProperty(key);
                    //保证id是对的
                    if(null != parkId && parkId.length() == 32){
                        parks.add(parkId);
                    }
                }
            }
            if(parks.size() == 0){
                return null;
            }
            return parks;
        }
        return null;
    }

    private static List<String> getParksList(Properties prop){
        Set<String> parks = getParks(prop);
        if(null != parks){
            List<String> parksArray = new ArrayList<>(parks);
            return parksArray;
        }
        return null;
    }

    private static String[] getParksArray(Properties prop){
        Set<String> parks = getParks(prop);
        if(null != parks){
            String[] parksArray = new String[parks.size()];
            parks.toArray(parksArray);
            return parksArray;
        }
        return null;
    }

    private static String getVal(Properties prop,String key){
        return prop.getProperty(key);
    }

    private static boolean setPercent(String percent){
        if(null != percent && percent.length() > 0){
            try {
                int per = Integer.parseInt(percent);
                if(per < 0 || per > 100){
                    return false;
                }
                Properties prop = getPropByFile();
                prop.setProperty("percent",per + "");
                return updateProp(prop,null);
            } catch (Exception e){
                PrintUtil.dateLine("更新属性文件出错！");
                return false;
            }
        }else{
            return false;
        }
    }

    private static boolean updateProp(Properties prop,String comment){
        if(null == prop){
            return false;
        }
        File file = new File(PATH);
        if(!file.exists()){
            PrintUtil.dateLine("写入负载配置文件失败，文件不存在！");
            return false;
        }
        OutputStream outputStream = null;
        //加锁
        writeLock.lock();
        try{
            outputStream = new FileOutputStream(file);
            prop.store(outputStream,comment);
        }catch (Exception e){
            PrintUtil.dateLine("加载属性文件出错！");
            e.printStackTrace();
            return false;
        }finally {
            if(null != outputStream){
                try{
                    outputStream.close();
                }catch(Exception ee){
                    PrintUtil.dateLine("关闭输出流出错！");
                    ee.printStackTrace();
                }
            }
            writeLock.unlock();
        }
        return true;
    }
}
