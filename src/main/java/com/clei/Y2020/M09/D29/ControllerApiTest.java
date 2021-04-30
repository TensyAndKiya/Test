package com.clei.Y2020.M09.D29;

import com.alibaba.fastjson.JSONObject;
import com.clei.utils.FileUtil;
import com.clei.utils.PrintUtil;
import com.clei.utils.StringUtil;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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


    public static void main(String[] args) throws Exception {

        List<RequestApi> list = new ArrayList<>();

        FileUtil.Operation operation = f -> {

            String fileName = f.getName();

            if (fileName.endsWith(".java")) {

                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), StandardCharsets.UTF_8));

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

                while (null != (str = br.readLine())) {

                    if (StringUtil.isEmpty(str)) {
                        lineNumber++;
                        continue;
                    }

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
                            pathPrefix = getMappingUrl(str);
                        }
                    } else {
                        // 开始找接口方法url
                        if (-1 != controllerLineNumber) {
                            int containsIndex = StringUtil.containsIndex(str, REQUEST_API_ARR);
                            if (containsIndex > -1) {
                                // 放到集合里
                                String pathSuffix = getMappingUrl(str);
                                String url = pathPrefix + pathSuffix;
                                String method = REQUEST_API_ARR[containsIndex].substring(0, REQUEST_API_ARR[containsIndex].indexOf("Mapping")).toUpperCase();
                                RequestApi requestApi = new RequestApi();
                                requestApi.setUrl(url);
                                requestApi.setMethod(method);
                                requestApi.setJavaClass(className);
                                requestApi.setLineNumber(lineNumber + 1);
                                list.add(requestApi);

                            }
                        }
                    }

                    lineNumber++;
                }

                br.close();
            }
        };

        FileUtil.fileOperation("D:\\WorkSpace\\HK\\hdsp\\hdsp-module-itms-detection", operation);

        Map<String, List<RequestApi>> map = list.stream().collect(Collectors.groupingBy(RequestApi::getJavaClass));
        // 这里是遍历操作，可以有其它的查找操作【比如根据url找到文件和行号什么的】
        map.forEach((k, v) -> {
            PrintUtil.log(k);
            v.forEach(r -> PrintUtil.log("\t" + r));
        });
    }

    /**
     * 获取mapping注解内的url
     *
     * @param str
     * @return
     */
    private static String getMappingUrl(String str) {
        // 去空白符
        str = StringUtil.trimBlank(str);
        String url = null;
        // 括号内内容
        String content = str.substring(str.indexOf("(") + 1, str.lastIndexOf(")"));
        if (StringUtil.isNotEmpty(content)) {
            // 直接 ""包围
            if (content.startsWith("\"")) {
                url = content.substring(1, content.length() - 1);
                // 使用 value = 或 path = 等的
            } else if (content.contains("=")) {
                url = getValue(str, "value");
                if (null == url) {
                    url = getValue(str, "path");
                }
            }
        }

        // 格式修饰
        url = StringUtil.getUrl(url);

        return url;
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
            tempIndex = str.indexOf("=", index);

            // 找到了
            if (tempIndex == index + key.length()) {
                int index1 = str.indexOf("\"", tempIndex);
                if (index1 == tempIndex + 1) {
                    int index2 = str.indexOf("\"", index1 + 1);
                    String value = str.substring(index1 + 1, index2);
                    return value;
                }
            }
        }
        return null;
    }

    /**
     * 接口描述对象
     */
    public static class RequestApi {

        private String url;
        private String method;
        private String javaClass;
        private Integer lineNumber;

        @Override
        public String toString() {
            return JSONObject.toJSONString(this);
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public String getJavaClass() {
            return javaClass;
        }

        public void setJavaClass(String javaClass) {
            this.javaClass = javaClass;
        }

        public Integer getLineNumber() {
            return lineNumber;
        }

        public void setLineNumber(Integer lineNumber) {
            this.lineNumber = lineNumber;
        }
    }

}
