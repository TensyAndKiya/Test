package com.clei.Y2019.M04.D01;

import com.clei.utils.PrintUtil;

import java.io.File;

/**
 * 文件目录打印
 *
 * @author KIyA
 */
public class FilePathTest {

    /**
     * 目录名
     */
    private final static String DIRECTORY = "";

    /**
     * 对齐字符串
     */
    private final static String TAB_STR = "    ";

    public static void main(String[] args) {
        File file = new File(DIRECTORY);
        printFilePath(file);
    }

    private static void printFilePath(File file) {
        printFilePath(file, 0);
    }

    private static void printFilePath(File file, int level) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < level; i++) {
            str.append("    ");
        }
        PrintUtil.log(str + file.getAbsolutePath());
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (null != files) {
                for (File f : files) {
                    printFilePath(f, level + 1);
                }
            }
        }
    }
}
