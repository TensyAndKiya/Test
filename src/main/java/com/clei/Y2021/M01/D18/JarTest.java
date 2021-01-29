package com.clei.Y2021.M01.D18;

import com.clei.utils.PrintUtil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 替换jar包中的某个class
 *
 * @author KIyA
 */
public class JarTest {

    public static void main(String[] args) throws Exception {
        String jarPath = "D:\\usr\\temp\\oldJar.jar";
        String targetPath = "D:\\usr\\temp\\jarFolder\\";
        List<String> replaceFiles = new ArrayList<>();
        replaceFiles.add("D:\\usr\\temp\\newJar\\com\\clei\\WordUtil.class");
        // 解压
        unJar(jarPath, targetPath, replaceFiles);
        // 重新打包
        String command = "jar -cvfm hasaki.jar " + targetPath + "META-INF\\MANIFEST.MF -C " + targetPath + " .";
        PrintUtil.log("command : {}", command);
        Process exec = Runtime.getRuntime().exec(command);
        printExec(exec.getInputStream());
        printExec(exec.getErrorStream());
    }

    /**
     * 将jar包解压到某个临时目录
     *
     * @param jarPath      jar源目录
     * @param targetPath   jar解压目录
     * @param replaceFiles 要替换的文件
     */
    private static void unJar(String jarPath, String targetPath, List<String> replaceFiles) throws Exception {
        // 目标目录
        File targetFile = new File(targetPath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        // 替换文件处理
        Map<String, String> replaceMap = new HashMap<>(replaceFiles.size());
        for (String f : replaceFiles) {
            String fileName = f.substring(f.lastIndexOf("\\") + 1);
            replaceMap.put(fileName, f);
        }
        // jar处理
        JarFile jar = new JarFile(jarPath);
        Enumeration<JarEntry> entries = jar.entries();
        byte[] buffer = new byte[1024];
        while (entries.hasMoreElements()) {
            JarEntry jarEntry = entries.nextElement();
            String jarEntryName = jarEntry.getName();
            String fileName = jarEntryName.substring(jarEntryName.lastIndexOf("/") + 1);
            String targetFilePath = targetPath + jarEntryName;
            targetFile = new File(targetFilePath);
            // 创建父目录
            if (jarEntry.isDirectory()) {
                targetFile.mkdirs();
            } else {
                targetFile.getParentFile().mkdirs();
                // 文件写入
                InputStream is;
                // 替换class
                if (replaceMap.containsKey(fileName)) {
                    PrintUtil.log("replaced file : {}", fileName);
                    is = new FileInputStream(replaceMap.get(fileName));
                } else {
                    is = jar.getInputStream(jarEntry);
                }
                BufferedInputStream bis = new BufferedInputStream(is);
                FileOutputStream fos = new FileOutputStream(targetFilePath);
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                int length;
                while (-1 != (length = bis.read(buffer))) {
                    bos.write(buffer, 0, length);
                }
                bis.close();
                bos.flush();
                bos.close();
            }
        }
        jar.close();
    }

    /**
     * 打印命令执行的输出
     *
     * @param is
     * @throws Exception
     */
    private static void printExec(InputStream is) throws Exception {
        InputStreamReader isr = new InputStreamReader(is, "GBK");
        BufferedReader br = new BufferedReader(isr);
        String line;
        while (null != (line = br.readLine())) {
            PrintUtil.println(line);
        }
        is.close();
    }
}
