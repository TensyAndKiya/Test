package com.clei.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 文件操作工具类
 *
 * @author KIyA
 */
public class FileUtil {

    /**
     * 连接超时时间
     */
    private final static int CONNECTION_TIME_OUT = 3000;

    /**
     * test main
     *
     * @param args
     */
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        // rename("D:\\Temp\\aaa", "bbb");
        rename("D:\\Work\\KIyA2", "KIyA3");
        long end = System.currentTimeMillis();
        PrintUtil.log("耗时：{}ms", end - start);
    }

    /**
     * 重载一下 用路径可以代替file
     *
     * @param path
     * @param operation
     * @throws Exception
     */
    public static void fileOperation(String path, Operation operation) throws Exception {
        if (StringUtil.isEmpty(path)) {
            return;
        }
        File file = new File(path);
        if (!file.exists()) {
            return;
        }
        fileOperation(file, operation);
    }

    public static void fileOperation(File file, Operation operation) throws Exception {
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                fileOperation(f, operation);
            }
        } else {
            operation.operate(file);
        }
    }

    /**
     * 只对第layer层的做处理
     *
     * @param file
     * @param layer     第几层
     * @param operation
     * @throws Exception
     */
    public static void fileOperation(File file, int layer, Operation operation) throws Exception {
        if (0 > layer) {
            throw new RuntimeException("竟然还有不是从0开始的东西？");
        }
        if (0 == layer) {
            operation.operate(file);
        } else {
            layer--;
            if (file.isDirectory()) {
                for (File f : file.listFiles()) {
                    fileOperation(f, layer, operation);
                }
            }
        }
    }

    /**
     * 找到含有content且后缀名为fileSuffix的文件
     *
     * @param directory
     * @param fileSuffix
     * @param content
     * @throws Exception
     */
    public static void findFileTxt(String directory, String fileSuffix, String content) throws Exception {
        File file = new File(directory);
        fileOperation(file, f -> {
            String fileName = f.getName();
            if (StringUtil.isNotEmpty(fileSuffix) && fileName.endsWith(fileSuffix)) {
                findContent(f, content);
            }
        });
    }

    /**
     * 找到含有content内容的文件
     *
     * @param file
     * @param content
     * @throws Exception
     */
    private static void findContent(File file, String content) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
        String str;
        while (null != (str = br.readLine())) {
            if (str.contains(content)) {
                PrintUtil.println(file.getAbsoluteFile());
                break;
            }
        }
        br.close();
    }

    /**
     * 二进制文件转为16 进制 样子
     * 2020-07-16
     */
    public static void binFileToHexString(String path) throws Exception {
        File file = new File(path);
        FileInputStream fis = new FileInputStream(file);
        byte[] bytes = new byte[16];
        while (fis.read(bytes) != -1) {
            String str = EncryptUtil.byteArrToHexString(bytes, true);
            char[] array = str.toCharArray();
            for (int i = 0; i < array.length; i++) {
                if (i % 16 == 0) {
                    PrintUtil.println();
                } else if (i % 2 == 0) {
                    PrintUtil.print(' ');
                }
                PrintUtil.print(array[i]);
            }
        }
    }

    /**
     * 获取某文件或文件夹大小
     *
     * @param path
     * @throws Exception
     */
    public static void getFileSize(String path) throws Exception {
        getFileSize(path, null);
    }

    /**
     * 获取某文件或文件夹大小
     *
     * @param path
     * @param unit
     * @throws Exception
     */
    public static void getFileSize(String path, String unit) throws Exception {
        getFileSize(path, 0, unit);
    }

    /**
     * 获取某文件或文件夹下面的第n层文件/文件夹大小
     *
     * @param path
     * @param unit
     * @throws Exception
     */
    public static void getFileSize(String path, int layer, String unit) throws Exception {
        File file = new File(path);
        List<FileSize> list = new ArrayList<>();
        fileOperation(file, layer, f -> {
            String name = f.getName();
            String fullName = f.getAbsolutePath();
            long size = getFileSizeNoUnit(f);
            list.add(new FileSize(name, fullName, size));
        });
        // 这个超方便的，不用自己写comparable了。。
        // list = list.stream().sorted(Comparator.comparingLong(FileSize::getSize)).collect(Collectors.toList());
        list.sort(Comparator.comparing(FileSize::getSize).reversed());
        for (FileSize fs : list) {
            PrintUtil.log(fs.toString(unit));
        }
    }

    /**
     * 获得某文件或文件夹的大小
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static String getFileSize(File file) {
        long length = getFileSizeNoUnit(file);
        return getUnitString(length, null);
    }

    /**
     * 获得某文件或文件夹的大小
     * 没有单位
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static long getFileSizeNoUnit(File file) {
        long length = 0;
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (null != files) {
                for (File f : file.listFiles()) {
                    length += getFileSizeNoUnit(f);
                }
            }
        } else {
            length = file.length();
        }
        return length;
    }

    /**
     * 获取网络上文件大小
     *
     * @param url
     * @throws Exception
     */
    public static String getHttpFileSize(String url) throws Exception {
        return getHttpFileSize(url, null);
    }

    /**
     * 获取网络上文件大小
     *
     * @param url  文件url
     * @param unit 文件大小单位
     * @throws Exception
     */
    public static String getHttpFileSize(String url, String unit) throws Exception {
        URL httpUrl = new URL(url);
        URLConnection connection = httpUrl.openConnection();
        // 避免长时间等待
        connection.setConnectTimeout(CONNECTION_TIME_OUT);
        long size = connection.getContentLengthLong();
        return getUnitString(size, unit);
    }

    /**
     * 将路径为path的文件/文件夹重命名为fileName
     * File.renameTo只适合单个文件或文件夹的操作
     *
     * @param path
     * @param fileName
     */
    public static void rename(String path, String fileName) {
        if (StringUtil.isEmpty(path) || StringUtil.isEmpty(fileName)) {
            throw new RuntimeException("源文件或新文件名不能为空");
        }
        File source = new File(path);
        if (!source.exists()) {
            throw new RuntimeException("源文件不存在");
        }
        File target = new File(source.getParent() + File.separator + fileName);
        source.renameTo(target);
    }

    /**
     * 将路径为path1的文件/文件夹移动到path2目录下
     *
     * @param path1
     * @param path2
     */
    public static void move(String path1, String path2) {
        if (StringUtil.isEmpty(path1) || StringUtil.isEmpty(path2)) {
            throw new RuntimeException("源文件或目标目录不能为空");
        }
        File source = new File(path1);
        if (!source.exists()) {
            throw new RuntimeException("源文件不存在");
        }
        // 若是文件且目标目录不存在则创建
        File target = new File(path2);
        if (!source.isDirectory() && !target.getParentFile().exists()) {
            target.getParentFile().mkdirs();
        }
        // 若是文件夹则也移动下面的文件
        move(source, path2);
    }

    /**
     * 将source文件/文件夹移动路径为path的文件
     *
     * @param source
     * @param path
     */
    private static void move(File source, String path) {
        // 若源文件是目录则也要移动下面的文件
        File target = new File(path);
        if (source.isDirectory()) {
            // 目录不存在则创建
            target.mkdirs();
            File[] files = source.listFiles();
            for (File f : files) {
                move(f, path + File.separator + f.getName());
            }
            // 删除原目录
            source.delete();
        } else {
            // 文件则移动
            source.renameTo(target);
        }
    }

    /**
     * 根据文件大小返回大小
     * 例1：1024,k -> 1.00K
     * 例2：1024,null -> 1024B
     *
     * @param length
     * @param unit
     * @return
     */
    private static String getUnitString(long length, String unit) {
        // 默认单位为B 字节
        if (StringUtil.isEmpty(unit)) {
            unit = "B";
        }
        // 除数
        int divisor;
        switch (unit) {
            case "G":
                divisor = 1024 * 1024 * 1024;
                break;
            case "M":
                divisor = 1024 * 1024;
                break;
            case "K":
                divisor = 1024;
                break;
            default:
                return length + "B";

        }
        // 保留两位小数
        BigDecimal result = new BigDecimal(length).divide(new BigDecimal(divisor), 2, RoundingMode.HALF_UP);
        return result + unit;
    }

    /**
     * Consumer<File>
     * 对文件的操作
     */
    public interface Operation {

        void operate(File file) throws Exception;
    }

    /**
     * 文件大小对象
     */
    private static class FileSize implements Comparable<FileSize> {

        // 文件名
        private String name;
        // 文件全名 包含路径
        private String fullName;
        // 文件大小
        private long size;

        public String toString(String unit) {
            return name + '\t' + getUnitString(size, unit) + '\t' + fullName;
        }

        public FileSize(String name, String fullName, long size) {
            this.name = name;
            this.fullName = fullName;
            this.size = size;
        }

        public long getSize() {
            return size;
        }

        @Override
        public int compareTo(FileSize o) {
            return new Long(this.size).compareTo(new Long(o.getSize()));
        }
    }
}
