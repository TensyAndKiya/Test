package com.clei.Y2020.M06.D04;

import com.mysql.jdbc.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * 自定义的类加载器
 *
 * @author KIyA
 */
public class MyClassLoader extends ClassLoader{

    private String classPath;

    public MyClassLoader(String classPath){
        this.classPath = classPath;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

        if(StringUtils.isNullOrEmpty(classPath) || StringUtils.isNullOrEmpty(name)){
            throw new ClassNotFoundException("真滴找不到！");
        }

        File file = new File(classPath,name + ".class");

        try(FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            int len = 0;

            while ((len = fis.read()) != -1){
                baos.write(len);
            }

            byte[] data = baos.toByteArray();

            return defineClass(name,data,0,data.length);

        }catch (Exception e){

            e.printStackTrace();
            return super.findClass(name);
        }
    }
}
