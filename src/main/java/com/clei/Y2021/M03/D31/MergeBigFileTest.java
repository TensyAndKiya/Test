package com.clei.Y2021.M03.D31;

import com.clei.utils.PrintUtil;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * 小内存合并大文件
 * <p>
 * 一个3T文件 id,name,age
 * 一个2T文件 id,score
 * 限制使用2G内存
 * 将两个文件合并成一个，根据id对应，两条数据合并成一条
 *
 * @author KIyA
 */
public class MergeBigFileTest {

    public static void main(String[] args) throws Exception {
        String fileName1 = "D:/temp/data/data1.txt";
        String fileName2 = "D:/temp/data/data2.txt";
        String tempFileDirectory1 = "D:/temp/data/small";
        String tempFileDirectory2 = "D:/temp/data/big";
        String targetFileName1 = "data1.txt";
        String targetFileName2 = "data2.txt";
        String targetFilePath = "D:/temp/data/result.txt";
        // createFile(fileName1, fileName2);
        int smallFileLineLimit = 1024 * 1024;
        long firstStart = System.currentTimeMillis();
        long start = firstStart;

        cutBigFile(fileName1, tempFileDirectory1, smallFileLineLimit);
        long stop = System.currentTimeMillis();
        PrintUtil.log("耗时：{}ms", stop - start);

        sortSmallFile(tempFileDirectory1, smallFileLineLimit);
        start = stop;
        stop = System.currentTimeMillis();
        PrintUtil.log("耗时：{}ms", stop - start);

        mergeSmallFile(tempFileDirectory1, tempFileDirectory2, targetFileName1);
        start = stop;
        stop = System.currentTimeMillis();
        PrintUtil.log("耗时：{}ms", stop - start);

        cutBigFile(fileName2, tempFileDirectory1, smallFileLineLimit);
        start = stop;
        stop = System.currentTimeMillis();
        PrintUtil.log("耗时：{}ms", stop - start);

        sortSmallFile(tempFileDirectory1, smallFileLineLimit);
        start = stop;
        stop = System.currentTimeMillis();
        PrintUtil.log("耗时：{}ms", stop - start);

        mergeSmallFile(tempFileDirectory1, tempFileDirectory2, targetFileName2);
        start = stop;
        stop = System.currentTimeMillis();
        PrintUtil.log("耗时：{}ms", stop - start);

        // 最终的合并操作
        mergeBigFile(tempFileDirectory2, targetFilePath, targetFileName1, targetFileName2);
        start = stop;
        stop = System.currentTimeMillis();
        PrintUtil.log("耗时：{}ms", stop - start);

        PrintUtil.log("总耗时：{}ms", stop - firstStart);
    }

    /**
     * 创建数据文件
     *
     * @param fileName1
     * @param fileName2
     * @throws Exception
     */
    private static void createFile(String fileName1, String fileName2) throws Exception {
        int maxLineLimit = 100_000_000;
        int bound = 3;
        int limit = 2;
        Random random = new Random();
        BufferedWriter bw1 = new BufferedWriter(new FileWriter(fileName1));
        BufferedWriter bw2 = new BufferedWriter(new FileWriter(fileName2));
        for (int i = 0; i < maxLineLimit; i++) {
            int num = random.nextInt(bound);
            if (num < limit) {
                String str = i + ",张三,18\n";
                bw1.write(str);
            }
            num = random.nextInt(bound);
            if (num < limit) {
                String str = i + ",88\n";
                bw2.write(str);
            }
        }
        bw1.flush();
        bw1.close();
        bw2.flush();
        bw2.close();
    }

    /**
     * 把大文件切割成小文件
     *
     * @param fileName
     * @param tempFileDirectory
     * @param smallFileLineLimit
     */
    private static void cutBigFile(String fileName, String tempFileDirectory, int smallFileLineLimit) throws Exception {
        File sourceFile = new File(fileName);
        File tempFile = new File(tempFileDirectory);
        if (!tempFile.exists()) {
            tempFile.mkdirs();
        }
        PrintUtil.log("cut fileName : {}", fileName);
        // 删除已有文件
        for (File f : tempFile.listFiles()) {
            f.delete();
        }
        String smallFilePrefix = tempFileDirectory + File.separator + "data";
        BufferedReader br = new BufferedReader(new FileReader(sourceFile));
        String str;
        int fileCount = 0;
        boolean createNext = true;
        BufferedWriter bw = null;
        int lineCount = 0;
        while (null != (str = br.readLine())) {
            if (createNext) {
                createNext = false;
                String fName = smallFilePrefix + fileCount + ".txt";
                fileCount++;
                bw = new BufferedWriter(new FileWriter(new File(fName)));
            }

            bw.write(str + '\n');

            lineCount++;
            if (lineCount == smallFileLineLimit) {
                createNext = true;
                bw.flush();
                bw.close();
                lineCount = 0;
            }

            // file.length 多次调用太耗费时间
            /*if(writeFile.length() > smallFileSize) {
                createNext = true;
                bw.flush();
                bw.close();
            }*/
        }

        if (null != bw) {
            bw.flush();
            bw.close();
        }

        br.close();
    }

    /**
     * 对小文件排序
     *
     * @param tempFileDirectory
     * @param smallFileLineLimit
     */
    private static void sortSmallFile(String tempFileDirectory, int smallFileLineLimit) throws Exception {
        File file = new File(tempFileDirectory);
        File[] files = file.listFiles();
        for (File f : files) {
            BufferedReader br = new BufferedReader(new FileReader(f));
            List<IdStr> list = new ArrayList<>(smallFileLineLimit);
            String str;
            while (null != (str = br.readLine())) {
                int index = str.indexOf(',');
                int id = Integer.parseInt(str.substring(0, index));
                IdStr obj = new IdStr(id, str.substring(index));
                list.add(obj);
            }
            // 排序
            Collections.sort(list);
            br.close();
            // 重新写入
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            for (IdStr obj : list) {
                bw.write(obj.getId() + obj.getStr() + '\n');
            }
            bw.flush();
            bw.close();
            PrintUtil.log(f.getName() + " finished!");
        }
    }

    /**
     * 合并排序好的小文件为一个排序好的大文件
     *
     * @param sourceFileDirectory
     * @param targetFileDirectory
     * @param targetFileName
     */
    private static void mergeSmallFile(String sourceFileDirectory, String targetFileDirectory, String targetFileName) throws Exception {
        File targetFile = new File(targetFileDirectory);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        targetFile = new File(targetFileDirectory + File.separator + targetFileName);

        File file = new File(sourceFileDirectory);
        File[] files = file.listFiles();
        int length = files.length;
        List<MergeObj> list = new ArrayList<>(length);

        for (File f : files) {
            BufferedReader br = new BufferedReader(new FileReader(f));
            String str;
            if (null != (str = br.readLine())) {
                int index = str.indexOf(',');
                int id = Integer.parseInt(str.substring(0, index));
                MergeObj obj = new MergeObj(id, str.substring(index), br, f);
                list.add(obj);
            }
        }
        // 排序
        Collections.sort(list);
        for (int i = 0; i < length; i++) {
            list.get(i).setIndex(i);
        }
        // 开始合并
        BufferedWriter bw = new BufferedWriter(new FileWriter(targetFile));
        while (true) {
            MergeObj obj = list.get(0);
            if (null != obj) {
                bw.write(obj.getId() + obj.getStr() + '\n');

                String str = obj.getBr().readLine();
                if (null != str) {
                    int index = str.indexOf(',');
                    int id = Integer.parseInt(str.substring(0, index));
                    obj.setId(id);
                    obj.setStr(str.substring(index));
                    // 重新排序 往后挪
                    for (int i = obj.getIndex(); i < length - 1; i++) {
                        MergeObj tempObj = list.get(i + 1);
                        if (null != tempObj) {
                            if (obj.compareTo(tempObj) > 0) {
                                obj.setIndex(i + 1);
                                list.set(i + 1, obj);
                                tempObj.setIndex(i);
                                list.set(i, tempObj);
                            } else {
                                break;
                            }
                        }
                    }
                } else {
                    list.set(obj.getIndex(), null);
                    // 关闭资源 并删除文件
                    obj.getBr().close();
                    file.deleteOnExit();
                    // 重新排序 往后挪
                    for (int i = obj.getIndex(); i < length - 1; i++) {
                        MergeObj tempObj = list.get(i + 1);
                        if (null != tempObj) {
                            tempObj.setIndex(i);
                            list.set(i, tempObj);
                        } else {
                            list.set(i, null);
                            break;
                        }
                    }
                    // 第一次 把最后一个元素设置为null
                    list.set(length - 1, null);
                }
            } else {
                break;
            }
        }
        bw.flush();
        bw.close();
    }

    /**
     * 合并排序好的小文件为一个排序好的大文件
     *
     * @param sourceFileDirectory
     * @param targetFilePath
     * @param targetFileName1
     * @param targetFileName2
     */
    private static void mergeBigFile(String sourceFileDirectory, String targetFilePath, String targetFileName1, String targetFileName2) throws Exception {
        BufferedReader br1 = new BufferedReader(new FileReader(sourceFileDirectory + File.separator + targetFileName1));
        BufferedReader br2 = new BufferedReader(new FileReader(sourceFileDirectory + File.separator + targetFileName2));
        BufferedReader br = null;
        BufferedWriter bw = new BufferedWriter(new FileWriter(targetFilePath));

        String str1 = br1.readLine();
        String str2 = br2.readLine();

        IdStr obj1 = getIdStr(str1);
        IdStr obj2 = getIdStr(str2);

        int compare = obj1.compareTo(obj2);

        while (true) {
            if (compare > 0) {
                bw.write(str2 + '\n');

                str2 = br2.readLine();
                if (null == str2) {
                    br = br1;
                } else {
                    obj2 = getIdStr(str2);
                }
            } else if (compare == 0) {
                String str = str1 + obj2.getStr();
                bw.write(str + '\n');

                str1 = br1.readLine();
                str2 = br2.readLine();
                if (null == str1 && null == str2) {
                    break;
                }
                if (null == str2) {
                    br = br1;
                } else {
                    obj2 = getIdStr(str2);
                }
                if (null == str1) {
                    br = br2;
                } else {
                    obj1 = getIdStr(str1);
                }
            } else {
                bw.write(str1 + '\n');

                str1 = br1.readLine();
                if (null == str1) {
                    br = br2;
                } else {
                    obj1 = getIdStr(str1);
                }
            }

            if (null != br) {
                String str;
                while (null != (str = br.readLine())) {
                    bw.write(str + '\n');
                }
                break;
            }

            compare = obj1.compareTo(obj2);
        }

        bw.flush();
        bw.close();
        br1.close();
        br2.close();
    }

    /**
     * str -> IdStr
     *
     * @param str
     * @return
     */
    private static IdStr getIdStr(String str) {
        int index = str.indexOf(',');
        int id = Integer.parseInt(str.substring(0, index));
        return new IdStr(id, str.substring(index));
    }

    /**
     * id str Object
     * 用于根据id排序
     */
    private static class IdStr implements Comparable<IdStr> {

        private int id;
        private String Str;

        public IdStr(int id, String str) {
            this.id = id;
            this.Str = str;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getStr() {
            return Str;
        }

        public void setStr(String str) {
            Str = str;
        }

        @Override
        public int compareTo(@NotNull IdStr o) {
            if (this.getId() > o.getId()) {
                return 1;
            } else if (this.getId() == o.getId()) {
                return 0;
            }
            return -1;
        }
    }

    /**
     * merge Object
     * 用于合并比较
     */
    private static class MergeObj implements Comparable<MergeObj> {

        private int id;
        private String Str;
        private BufferedReader br;
        private File file;
        private int index;

        public MergeObj(int id, String str, BufferedReader br, File file) {
            this.id = id;
            this.Str = str;
            this.br = br;
            this.file = file;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getStr() {
            return Str;
        }

        public void setStr(String str) {
            Str = str;
        }

        public BufferedReader getBr() {
            return br;
        }

        public void setBr(BufferedReader br) {
            this.br = br;
        }

        public File getFile() {
            return file;
        }

        public void setFile(File file) {
            this.file = file;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        @Override
        public int compareTo(@NotNull MergeObj o) {
            if (this.getId() > o.getId()) {
                return 1;
            } else if (this.getId() == o.getId()) {
                return 0;
            }
            return -1;
        }
    }
}
