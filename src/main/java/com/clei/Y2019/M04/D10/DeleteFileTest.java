package com.clei.Y2019.M04.D10;

import com.clei.utils.PrintUtil;

import java.io.File;
import java.util.Scanner;

//此包内用于工作项目的测试。
public class DeleteFileTest {
    public static void main(String[] args) {
        deleteTarget();
    }

    private static void deleteTarget() {
        PrintUtil.dateLine("输入文件(g || t || d)");
        Scanner input = new Scanner(System.in,"UTF-8");
        char c = input.next().charAt(0);
        String folder = "dev";
        switch (c) {
            case 'x':
                return;
            case 'g':
                folder = "git";
                break;
            case 't':
                folder = "tes";
                break;
            case 'd':
                break;
        }
        String parentFilePath = "D:\\CLIdeaWorkspace\\" + folder + "\\park";
        File file = new File(parentFilePath);
        File[] files = file.listFiles();
        if (null != files) {
            for (File tempFile : files) {
                if (tempFile.isDirectory() && tempFile.getName().contains("park")) {
                    File[] fs = tempFile.listFiles();
                    if (null != fs) {
                        for (File f : fs) {
                            if (f.getName().equals("target")) {
                                //直接点用f.delete()是不行的。。因为不是空文件件，，所以要递归删除
                                if (deleteFile(f)) {
                                    PrintUtil.date("删除成功！" + f.getAbsolutePath());
                                } else {
                                    PrintUtil.date("删除失败！" + f.getAbsolutePath());
                                }
                                PrintUtil.date(f.getAbsolutePath());
                                break;
                            }
                        }
                    }
                }
            }
        }
        boolean clear = true;
        if (null != files) {
            for (File tempFile : files) {
                if (tempFile.isDirectory() && tempFile.getName().contains("park")) {
                    File[] fs = tempFile.listFiles();
                    if (null != fs) {
                        for (File f : fs) {
                            if (f.getName().equals("target")) {
                                clear = false;
                                PrintUtil.dateLine(f.getAbsolutePath());
                                break;
                            }
                        }
                    }
                }
            }
        }
        if (clear) {
            PrintUtil.dateLine(folder + "\t删除完毕！！！");
        } else {
            PrintUtil.dateLine("NOT CLEAR!");
        }

    }

    private static boolean deleteFile(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (null != files) {
                for (File f : files) {
                    deleteFile(f);
                }
            }
        }
        return file.delete();
    }
}
