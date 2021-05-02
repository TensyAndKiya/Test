package com.clei.Y2020.M07.D26;

import com.clei.utils.FileUtil;
import com.clei.utils.PrintUtil;

import java.io.File;

/**
 * @author KIyA
 * 下载了26集鬼灭之刃
 * 其文件名的鬼灭竟然是以GM代替的
 * 我要改过来
 */
public class UpdateFileNameTest {
    public static void main(String[] args) throws Exception {

        FileUtil.fileOperation("E:\\Media\\鬼灭之刃",f -> {

            String originName = f.getName();

            // 只有名字以GM开头的才去更改
            if(originName.startsWith("GM")){

                PrintUtil.println("originName : {}",originName);

                String parentPath = f.getParentFile().getAbsolutePath();

                String newName = originName.replace("GM","鬼灭");

                File newFile = new File(parentPath + File.separator + newName);

                f.renameTo(newFile);

                PrintUtil.println("newName : {}",newName);
            }
        });
    }
}
