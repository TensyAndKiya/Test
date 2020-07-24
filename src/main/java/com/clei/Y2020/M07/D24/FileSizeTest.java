package com.clei.Y2020.M07.D24;

import com.clei.utils.FileUtil;

/**
 * @author KIyA
 * @backStory 发现C盘某个文件夹里有太多其它程序的东西了
 * 排个序看看哪些个占用的最多，清理一下
 */
public class FileSizeTest {

    public static void main(String[] args) throws Exception {

        FileUtil.getFileSize("C:\\Users\\yueya\\AppData\\Local","B");

    }

}
