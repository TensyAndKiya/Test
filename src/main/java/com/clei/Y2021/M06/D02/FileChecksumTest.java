package com.clei.Y2021.M06.D02;

import com.clei.utils.MD5Util;
import com.clei.utils.PrintUtil;

import java.io.File;
import java.io.FileInputStream;

/**
 * 文件校验和测试
 *
 * @author KIyA
 * @date 2021-06-02
 */
public class FileChecksumTest {

    /**
     * jdk8u291_win64文件路径
     */
    private final static String JDK_8U291_PATH = "D:\\Download\\jdk-8u291-windows-x64.exe";

    /**
     * jdk8u291_win64文件md5校验和
     */
    private final static String JDK_8U291_WIN64_MD5 = "6581e22a2707c8e4ddac44627984563c";

    /**
     * jdk8u291_win64文件sha256校验和
     */
    private final static String JDK_8U291_WIN64_SHA256 = "9372a0abf33b38ce8afe31491af0e0b6fecb8b3705a2537709aaabd4ece7ee41";

    public static void main(String[] args) throws Exception {

        File file = new File(JDK_8U291_PATH);
        PrintUtil.log(JDK_8U291_WIN64_MD5.equals(MD5Util.md5(file)));
        FileInputStream fis = new FileInputStream(file);
        PrintUtil.log(JDK_8U291_WIN64_MD5.equals(MD5Util.md5(fis)));
    }
}
