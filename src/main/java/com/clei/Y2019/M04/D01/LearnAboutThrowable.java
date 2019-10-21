package com.clei.Y2019.M04.D01;

import java.io.File;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class LearnAboutThrowable {

    //查询java.lang包下的某个类以及其下面的子类等。
    public static void main(String[] args) throws Exception{

        String classPath = System.getProperty("java.class.path");
        String[] jarPath = classPath.split(";");
        String rtPath = null;
        for(String path : jarPath){
            if(path.endsWith("rt.jar") && path.charAt(path.lastIndexOf("rt.jar")-1) == '\\'){
                rtPath = path;
                break;
            }
        }
        if(null != rtPath){
            JarFile jarFile = null;
            try{
                jarFile = new JarFile(new File(rtPath));
                if(null != jarFile){
                    List<String> classFiles = new ArrayList<>();
                    Enumeration<JarEntry> entries = jarFile.entries();
                    while(entries.hasMoreElements()){
                        String name = entries.nextElement().getName();
                        if(!name.startsWith("java/lang") || !name.endsWith(".class")){
                            continue;
                        }
                        classFiles.add(name.substring(0,name.lastIndexOf(".class")).replaceAll("/","."));
                    }
                    //
                    Map<String,List<ClassNode>> map = new HashMap<>();
                    for(String name : classFiles){
                        List<ClassNode> list = new ArrayList<>();
                        map.put(name, list);
                    }
                    classFiles.remove("java.lang.Throwable");
                    //
                    fillSubClassList("java.lang.Throwable", classFiles, map);
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if(null != jarFile){
                    jarFile.close();
                }
            }


        }else{
            System.out.println("你这Java环境有问题！！！rt.jar都没有！！！");
        }
    }

    private static boolean isChildClass(String className, String parentClassName) throws ClassNotFoundException {
        Class clazz = Class.forName(className);
        Class parentClass = Class.forName(parentClassName);
        return clazz.isAssignableFrom(parentClass);
    }

    private static void fillSubClassList(String className, List<String> classFiles, Map<String,List<ClassNode>> map) throws ClassNotFoundException {
        List<ClassNode> subClassList = map.get(className);
        for(String name : classFiles){
            if(isChildClass(name,className)){
                System.out.println(className);
                subClassList.add(new ClassNode(name, map.get(name)));
                fillSubClassList(name, classFiles, map);
            }
        }
    }
}

class ClassNode{
    private String className;
    private List<ClassNode> subClassList;

    public ClassNode(String className, List<ClassNode> subClassList) {
        this.className = className;
        this.subClassList = subClassList;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<ClassNode> getSubClassList() {
        return subClassList;
    }

    public void setSubClassList(List<ClassNode> subClassList) {
        this.subClassList = subClassList;
    }
}
