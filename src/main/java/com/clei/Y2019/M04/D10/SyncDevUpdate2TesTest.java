package com.clei.Y2019.M04.D10;

import com.clei.utils.PrintUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

/**
 * 将dev下面的修改同步到tes下
 *
 * @author KIyA
 */
public class SyncDevUpdate2TesTest {

    /**
     * 配置文件 不修改
     */
    private final static String[] CONFIG_FILES = {"AlipayConfig.java", "AlipayWXConfig.java", "CdPayConfig.java", "application.properties", "main.jsp", "mainV2.jsp", "monitor.jsp", "sub_monitor.jsp", "inOut.jsp"};

    public static void main(String[] args) {
        PrintUtil.log("Sync A to B");
        PrintUtil.log("输入A B");
        Scanner input = new Scanner(System.in, "UTF-8");
        String from = input.next();
        String to = input.next();
        String fromPath = "D:\\CLIdeaWorkspace\\" + from + "\\park\\";
        String toPath = "D:\\CLIdeaWorkspace\\" + to + "\\park\\";
        Map<String, String> updateFiles = new HashMap<>();
        PrintUtil.log("输入需要同步的文件");
        String str = input.nextLine();
        while (!str.contains("no changes added to commit")) {
            if (str.length() != 0 && str.contains("park-")) {
                str = str.substring(str.indexOf("park-"));
                str = str.replaceAll("/", "\\\\");
                updateFiles.put(fromPath + str, toPath + str);
            }
            str = input.nextLine();
        }
        Iterator<Map.Entry<String, String>> it = updateFiles.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            for (String s : CONFIG_FILES) {
                if (entry.getKey().substring(entry.getKey().lastIndexOf("\\") + 1).equals(s)) {
                    it.remove();
                    break;
                }
            }
        }
        updateFiles.forEach((k, v) -> {
            try {
                overrideFile(k, v);
                PrintUtil.log(k + "\t成功！");
            } catch (Exception e) {
                PrintUtil.log("同步修改文件失败！！！", e);
            }
        });
        PrintUtil.log("同步修改文件成功！！！");
    }

    private static void overrideFile(String path, String toPath) throws Exception {
        File file = new File(path);
        if (!file.exists()) {
            PrintUtil.log("文件 " + path + "不存在！");
            return;
        }
        File newFile = new File(toPath);
        if (!newFile.exists()) {
            PrintUtil.log("文件 " + toPath + "不存在！");
            return;
        }
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(file);
            fos = new FileOutputStream(newFile);

            byte[] buffer = new byte[1024];
            int length;

            while ((length = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, length);
            }
        } catch (Exception e) {
            PrintUtil.log("求你了大佬，不要报错！！！！", e);
        } finally {
            if (null != fis) {
                fis.close();
            }
            if (null != fos) {
                fos.close();
            }
        }
    }
}
