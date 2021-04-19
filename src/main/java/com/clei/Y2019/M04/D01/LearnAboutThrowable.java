package com.clei.Y2019.M04.D01;

import com.clei.utils.PrintUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 找到某个类的所有子类
 *
 * @author KIyA
 */
public class LearnAboutThrowable {

    /**
     * 要查找的包
     */
    private final static String PACKAGE_NAME = "java.lang";

    /**
     * 要查找的类
     */
    private final static String CLASS_NAME = PACKAGE_NAME + ".Throwable";

    /**
     * 查询java.lang包下的某个类以及其下面的子类等。
     */
    public static void main(String[] args) {
        String classPath = System.getProperty("java.class.path");
        String[] jarPath = classPath.split(";");
        String rtPath = null;
        for (String path : jarPath) {
            if (path.endsWith("rt.jar") && path.charAt(path.lastIndexOf("rt.jar") - 1) == '\\') {
                rtPath = path;
                break;
            }
        }
        if (null != rtPath) {
            try (JarFile jarFile = new JarFile(new File(rtPath))) {
                List<String> classFiles = new ArrayList<>();
                Enumeration<JarEntry> entries = jarFile.entries();
                while (entries.hasMoreElements()) {
                    String name = entries.nextElement().getName();
                    if (name.startsWith(PACKAGE_NAME.replace(".", "/")) && name.endsWith(".class")) {
                        classFiles.add(name.substring(0, name.lastIndexOf(".class")).replaceAll("/", "."));
                    }
                }
                //
                Map<String, List<ClassNode>> map = new HashMap<>(classFiles.size());
                for (String name : classFiles) {
                    List<ClassNode> list = new ArrayList<>();
                    map.put(name, list);
                }
                classFiles.remove(CLASS_NAME);
                //
                fillSubClassList(CLASS_NAME, classFiles, map);
                // 遍历
                for (ClassNode classNode : map.get(CLASS_NAME)) {
                    PrintUtil.log(classNode.getClassName());
                }
            } catch (Exception e) {
                PrintUtil.log("查找出错", e);
            }


        } else {
            PrintUtil.log("你这Java环境有问题！！！rt.jar都没有！！！");
        }
    }

    private static boolean isChildClass(String className, String parentClassName) throws ClassNotFoundException {
        Class<?> clazz = Class.forName(className);
        Class<?> parentClass = Class.forName(parentClassName);
        // 判断一个类是否是另一个类的子孙类
        return parentClass.isAssignableFrom(clazz);
    }

    private static void fillSubClassList(String className, List<String> classFiles, Map<String, List<ClassNode>> map) throws ClassNotFoundException {
        List<ClassNode> subClassList = map.get(className);
        for (String name : classFiles) {
            if (!name.equals(className)) {
                if (isChildClass(name, className)) {
                    subClassList.add(new ClassNode(name, map.get(name)));
                    fillSubClassList(name, classFiles, map);
                }
            }
        }
    }
}

class ClassNode {

    private final String className;
    private final List<ClassNode> subClassList;

    public ClassNode(String className, List<ClassNode> subClassList) {
        this.className = className;
        this.subClassList = subClassList;
    }

    public String getClassName() {
        return className;
    }

    public List<ClassNode> getSubClassList() {
        return subClassList;
    }
}
