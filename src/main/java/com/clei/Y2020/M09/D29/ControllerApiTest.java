package com.clei.Y2020.M09.D29;

import com.clei.utils.FileUtil;
import com.clei.utils.StringUtil;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * controller api 统计查询
 *
 * @author KIyA
 */
public class ControllerApiTest {

    /**
     * 一个controller必有的注解
     */
    private final static String[] CONTROLLER_ANNOTATION_ARR = {"@Controller", "@RestController"};

    /**
     * 一个请求接口必有的注解
     */
    private final static String[] REQUEST_API_ARR = {"@RequestMapping", "@GetMapping", "@PostMapping", "@PutMapping", "DeleteMapping"};


    public static void main(String[] args) {

        FileUtil.Operation operation = f -> {

            String fileName = f.getName();

            if (fileName.endsWith(".java")) {

                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF-8"));

                // controller注解行号
                int controllerLineNumber = -1;

                // 类声明行号
                int classLineNumber = -1;

                // 行号
                int lineNumber = 0;

                // 路径前缀
                String pathPrefix = "";

                // 类名 去掉.java
                String className = fileName.substring(0, fileName.length() - 5);

                String str;

                while (StringUtil.isNotEmpty(str = br.readLine())) {

                    if (-1 == controllerLineNumber && StringUtil.contains(str, CONTROLLER_ANNOTATION_ARR)) {
                        controllerLineNumber = lineNumber;
                    }

                    // 这里要保证class与类名在同一行才行
                    if (-1 == classLineNumber) {
                        int classIndex = str.indexOf("class");
                        if (-1 < classIndex) {
                            int classNameIndex = str.indexOf(className);
                            if (classNameIndex > classIndex + 5) {
                                classLineNumber = lineNumber;
                            }
                        }

                        // 判断路径前缀
                        String annotation = REQUEST_API_ARR[0];
                        if (str.contains(annotation)) {

                        }

                    }


                    lineNumber++;
                }

                br.close();

            }


        };

    }

    /**
     * 获取mapping注解内的url
     *
     * @param str
     * @return
     */
    private static String getMappingUri(String str) {
        String url = null;
        // 括号内内容
        String content = str.substring(str.indexOf("(") + 1, str.lastIndexOf(")"));
        if (StringUtil.isNotEmpty(content)) {

            // 直接 ""包围
            if (content.startsWith("\"")) {
                url = content;
                // 使用 value = 或 path = 等的
            } else if (content.contains("=")) {

            }

        }

        return null;
    }

    /**
     * 从 value = "hasaki" 这样的字符串中取得hasaki value
     *
     * @param str
     * @param key
     * @return
     */
    private static String getValue(String str, String key) {
        int index = str.indexOf(key);
        if (-1 < index) {
            int tempIndex = -1;

            // 找到第一个key之后的等号
            while (true) {

                // 避免死循环
                int ttt = tempIndex;

                tempIndex = str.indexOf("=", tempIndex);

                // 结果与上次一样
                if (ttt == tempIndex) {
                    break;
                }

                // 找到了
                if (tempIndex > index) {
                    int index1 = str.indexOf("\"", tempIndex);
                    if (-1 != index) {
                        int index2 = str.indexOf("\"", index);
                    }
                }
            }
        }
        return null;
    }

}
