package com.clei.Y2019.M10.D12;

import com.clei.utils.FileUtil;

public class FindContentTest {
    public static void main(String[] args) throws Exception {
        String directory = "D:\\Workspace\\GIT\\ms";
        String fileSuffix = ".xml";
        String content = "park_parkingspace";
        FileUtil.findFileTxt(directory,"",content);
    }
}
