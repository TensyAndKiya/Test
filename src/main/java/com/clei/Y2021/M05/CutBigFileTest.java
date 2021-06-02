package com.clei.Y2021.M05;

import com.clei.utils.FileUtil;

/**
 * git不准上传超过100M的文件
 * 懒得试其它命令了，切成小文件就行了
 *
 * @author KIyA
 */
public class CutBigFileTest {

    /**
     * 源文件路径
     */
    private final static String INPUT_FILE = "D:\\Temp\\win.tar.gz";

    /**
     * 输出文件夹路径
     */
    private final static String OUTPUT_FILE_PATH = "D:\\Temp\\cut";

    /**
     * 输出文件名称后缀
     */
    private final static String OUTPUT_FILE_NAME = "out";

    /**
     * 输出文件最大值限制 单位M
     */
    private final static int LIMIT_SIZE = 88;

    public static void main(String[] args) throws Exception {
        FileUtil.cut(INPUT_FILE, OUTPUT_FILE_PATH, OUTPUT_FILE_NAME, LIMIT_SIZE);
        // FileUtil.merge(OUTPUT_FILE_PATH,INPUT_FILE + ".bak");
    }
}
