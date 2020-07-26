package com.clei.utils;

import com.alibaba.fastjson.JSONObject;
import com.jdcloud.sdk.utils.StringUtils;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

public class FileUtil {

    /**
     * 重载一下 用路径可以代替file
     * @param path
     * @param operation
     * @throws Exception
     */
    public static void fileOperation(String path,Operation operation) throws Exception {
        File file = new File(path);

        fileOperation(file,operation);
    }

    public static void fileOperation(File file,Operation operation) throws Exception {
        if(file.isDirectory()){
            for(File f :file.listFiles()){
                fileOperation(f,operation);
            }
        }else {
            operation.operate(file);
        }
    }

    /**
     * 只对第layer层的做处理
     * @param file
     * @param layer 第几层
     * @param operation
     * @throws Exception
     */
    public static void fileOperation(File file, int layer, Operation operation) throws Exception {

        if(file.isDirectory() && layer > 0){

            layer --;

            for(File f :file.listFiles()){
                fileOperation(f,layer,operation);
            }
        }else {
            operation.operate(file);
        }
    }

    public static void findFileTxt(String directory,String fileSuffix,String content) throws Exception {
        File file = new File(directory);
        // 删除指定目录下的.class文件
        fileOperation(file, f -> {
            String fileName = f.getName();
            if(!StringUtils.isEmpty(fileSuffix) && fileName.endsWith(fileSuffix)){
                findContent(f,content);
            }
        });
    }

    private static void findContent(File file,String content) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
        String str;
        while(null != (str = br.readLine())){
            if(str.contains(content)){
                PrintUtil.println(file.getAbsoluteFile());
                break;
            }
        }
        br.close();
    }

    public static void rewriteStr(String directory,String fileSuffix,String... strs) throws Exception{
        File file = new File(directory);

        List<Map<String,String>> list = new ArrayList<>();
        List<String> fileContent = new ArrayList<>();
        fileOperation(file, f -> {
            String fileName = f.getName();
            if(!file.isDirectory() && fileName.endsWith(fileSuffix)){


                Map<String,String> m = new HashMap<>();

                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
                String str;
                while(null != (str = br.readLine())){
                    String origin = str;
                    fileContent.add(origin);

                    boolean always = true;
                    for (int i = 0; i < strs.length; i++) {
                        if(!str.contains(strs[i])){
                            always = false;
                            break;
                        }else {

                            PrintUtil.print(str);

                            str = str.replace(strs[i],"");
                        }
                    }
                    // 全都有
                    if(always){

                        PrintUtil.println("wocao");

                        m.put("fileName",file.getAbsolutePath());
                        m.put("origin",origin);
                        // 暂时假设 长度 2
                        // AND timerule_name LIKE '%${timeRuleName}%'
                        // CONCAT('%',#{license},'%')
                        int index1 = origin.indexOf("'%${");
                        int index2 = origin.replace("'%${","").indexOf("}%") + 4;
                        String newStr = str.substring(0,index1) + "CONCAT('%',#{"
                                        + str.substring(index1 + 4,index2) + str.substring(index2 + 2);

                        m.put("newStr",newStr);
                        list.add(m);
                    }
                }
                br.close();


                PrintUtil.println("change : " + JSONObject.toJSONString(list));

                PrintUtil.println("old : " + fileContent);


                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),EncryptUtil.CHARSET_UTF8));
                for (String s : fileContent){
                    String writeStr = s;
                    for(Map<String,String> mm : list){
                        String origin = mm.get("origin");
                        if(writeStr.equals(origin)){
                            writeStr = mm.get("newStr");
                            break;
                        }
                    }
                    bw.write(writeStr);
                    bw.write("\n");
                }
            }
        });
    }

    /**
     * 二进制文件转为16 进制 样子
     * 2020-07-16
     */
    public static void binFileToHexString(String path) throws Exception {
        File file = new File(path);

        FileInputStream fis = new FileInputStream(file);

        byte[] bytes = new byte[16];

        int length = -1;

        while ((length = fis.read(bytes)) != -1){
            String str = EncryptUtil.byteArrToHexString(bytes,true);

            char[] array = str.toCharArray();

            for (int i = 0; i < array.length; i++) {

                if(i % 16 == 0){
                    System.out.println();
                }else if(i % 2 == 0){
                    System.out.print(' ');
                }

                System.out.print(array[i]);
            }
        }
    }

    /**
     * 获取某文件或文件夹下面的文件大小
     * @param path
     * @param unit
     * @throws Exception
     */
    public static void getFileSize(String path,String unit) throws Exception{
        File file = new File(path);

        List<FileSize> list = new ArrayList<>();

        fileOperation(file,1,f -> {

            String name = f.getName();

            String fullName = f.getAbsolutePath();

            BigDecimal size = getFileSize(f);

            // 根据单位设置大小
            switch (unit){
                case "G":
                    size = size.divide(new BigDecimal(1024 * 1024 * 1024));
                    break;
                case "M":
                    size = size.divide(new BigDecimal(1024 * 1024));
                    break;
                case "K":
                default:
                    size = size.divide(new BigDecimal(1024));

            }

            list.add(new FileSize(name,fullName,size.longValue()));
        });

        // 这个超方便的，不用自己写comparable了。。顺序倒序 改一下 s2 s1的前后就行了
        list.sort(Comparator.comparing(FileSize::getSize, (s1,s2) -> (int)(s2 - s1)));

        // 避免带为错误单位
        String tempUnit = unit;
        if(!"G".equals(unit) && !"M".equals(unit)){
            tempUnit = "K";
        }

        for (FileSize fs : list){
            System.out.println(fs.toString(tempUnit));
        }
    }

    /**
     * 获得某文件或文件夹的大小
     * @param file
     * @return
     * @throws Exception
     */
    public static BigDecimal getFileSize(File file) throws Exception{

        BigDecimal length = BigDecimal.ZERO;

        if(file.isDirectory()){

            File[] files = file.listFiles();

            if(null != files){

                for(File f :file.listFiles()){
                    length = length.add(getFileSize(f));
                }

            }

        }else {
            length = new BigDecimal(file.length());
        }

        // 除以1000 单位变成k
        return length;
    }

    public interface Operation{
        void operate(File file) throws Exception;
    }

    /**
     * 文件大小对象
     */
    private static class FileSize implements Comparable{
        // 文件名
        private String name;
        // 文件全名 包含路径
        private String fullName;
        // 文件大小
        private long size;

        public String toString(String unit) {
            return name + '\t' + size + unit + '\t' + fullName;
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
        public int compareTo(Object o) {
            if(o instanceof FileSize){

                long diff = this.size - ((FileSize) o).size;

                if(diff > 0){
                    return 1;
                }else if(diff < 0){
                    return -1;
                }else {
                    return 0;
                }

            }else {
                return 0;
            }
        }
    }
}
