package com.clei.Y2021.M07.D20;

import com.clei.utils.FileUtil;
import com.clei.utils.PrintUtil;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 将一个普通java对象转成markdown表格
 *
 * @author KIyA
 * @date 2021-07-20
 */
public class JavaObjToTable {

    public static void main(String[] args) throws Exception {
        String filePath = "D:\\WorkSpace\\Person.java";
        javaObjToTable(filePath, false, 0, 0);
    }

    /**
     * javaObj源文件转markdown表格
     *
     * @param filePath        源文件路径
     * @param lineNumberLimit 读取行数控制 true是 false否
     * @param startLine       开始行 从1开始
     * @param endLine         结束行
     * @throws Exception
     */
    private static void javaObjToTable(String filePath, boolean lineNumberLimit, int startLine, int endLine) throws Exception {
        FileUtil.fileOperation(filePath, file -> {
            // 字段个数
            int order = 0;
            List<TableLine> list = new ArrayList<>();

            // 读取行数控制
            int lineNumber = 0;
            boolean lineStart = false;

            try (FileInputStream fis = new FileInputStream(file);
                 InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
                 BufferedReader br = new BufferedReader(isr)) {
                // 逐行读取
                String str;
                boolean classStart = false;
                boolean commentStart = false;
                StringBuilder commentSb = new StringBuilder();
                while (null != (str = br.readLine())) {
                    // 读取行控制
                    if (lineNumberLimit) {
                        lineNumber++;
                        if (!lineStart) {
                            if (lineNumber == startLine) {
                                lineStart = true;
                            } else {
                                continue;
                            }
                        } else if (lineNumber == endLine) {
                            break;
                        }
                    }

                    // 读取import之类的
                    if (!classStart) {
                        if (str.contains("public") && str.contains("class")) {
                            classStart = true;
                        }
                        continue;
                    }
                    // 读取class大括号里面的
                    // 读取字段描述信息
                    if (!commentStart) {
                        if (str.contains("/**")) {
                            commentStart = true;
                        }
                        continue;
                    }
                    int index = str.indexOf("* ");
                    if (-1 != index) {
                        String comment = str.substring(index + 2);
                        if (0 != commentSb.length()) {
                            commentSb.append("  ");
                        }
                        commentSb.append(comment);
                    }
                    // 读取字段
                    int index2 = str.indexOf("private ");
                    if (-1 != index2) {
                        String[] arr = str.substring(index2).replaceAll(";", "").split(" ");
                        // 输出
                        list.add(new TableLine(++order, arr[2], arr[1], null, commentSb.toString(), null, null));
                        // 清理
                        commentSb.setLength(0);
                        commentStart = false;
                    }
                }
            } catch (Exception e) {
                PrintUtil.log("读取文件错误 ", e);
            }

            System.out.println("| 序号 | 字段 | 字段类型 | 字段长度 | 备注 | 示例数据 | 示例说明 |");
            System.out.println("| --- | --- | --- | --- | --- | --- | --- |");
            list.forEach(System.out::println);
        });
    }

    /**
     * 表格行
     */
    private static class TableLine {

        /**
         * 序号
         */
        private Integer order;

        /**
         * 字段名
         */
        private String key;

        /**
         * 字段类型
         */
        private String type;

        /**
         * 字段长度
         */
        private Integer length;

        /**
         * 备注
         */
        private String comment;

        /**
         * 示例数据
         */
        private String example;

        /**
         * 示例说明
         */
        private String exampleComment;

        @Override
        public String toString() {
            return "| " + order + " | " + key + " | " + type + " | " + "" + " | " + comment + " | " + "" + " | " + "" + " |";
        }

        public TableLine(Integer order, String key, String type, Integer length, String comment, String example, String exampleComment) {
            this.order = order;
            this.key = key;
            this.type = type;
            this.length = length;
            this.comment = comment;
            this.example = example;
            this.exampleComment = exampleComment;
        }
    }
}
