package com.clei.Y2020.M07.D16;

import com.clei.utils.FileUtil;
import com.clei.utils.PrintUtil;

/**
 * 二进制文件转为16 进制 样子
 * @backStory notepad++ 使用HexEditor插件
 * 然后说用不了32位的，我又不想用32位的notepad++
 * 所以自己写了个试试
 * @author KIyA
 * @date 2020-07-16
 */
public class BinFileToHexStringTest {

    public static void main(String[] args) throws Exception {

        PrintUtil.dateLine("开始");
        FileUtil.binFileToHexString("F:\\Game\\GAME\\法利恩戰記（Furion Chronicles）\\Save\\save02.sav");
        PrintUtil.dateLine("结束");

        System.out.println(Integer.toHexString(20));

    }

}
