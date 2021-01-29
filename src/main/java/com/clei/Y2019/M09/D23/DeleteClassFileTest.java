package com.clei.Y2019.M09.D23;

import com.clei.utils.FileUtil;
import com.clei.utils.PrintUtil;

import java.io.File;

public class DeleteClassFileTest {
    public static void main(String[] args) throws Exception {
        File file = new File("C:\\Users\\liudg\\Desktop\\TrustPayClient-V3.1.6");
        // 删除指定目录下的.class文件
        FileUtil.fileOperation(file, f -> {
            if(f.getName().endsWith(".class")){
                PrintUtil.log(f.getName() + "  " + f.delete());
            }
        });
    }
}
