package com.clei.Y2020.M09.D01;

import com.clei.utils.FileUtil;
import com.clei.utils.PrintUtil;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 某项目文件夹下用到index统计
 *
 * @author KIyA
 */
public class ESIndexCountTest {

    public static void main(String[] args) throws Exception {
        test();
    }

    private static void test() throws Exception {

        // 看看改项目下用到了多少ES index
        Map<String, String> indexMap = new HashMap<>(16);

        String filePath = "D:\\WorkSpace\\HK\\tbd-transport\\";

        String keyword = "_index";

        String fileSuffix = ".java";

        FileUtil.fileOperation(filePath, f -> {

            String fileName = f.getName();

            if (fileName.endsWith(fileSuffix)) {
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), StandardCharsets.UTF_8));
                String str;
                while (null != (str = br.readLine())) {

                    int index = str.indexOf(keyword);

                    if (index > -1 && !str.contains("$") && !str.contains("{")) {

                        int temp = index - 1;

                        while (temp > 0) {
                            char c = str.charAt(temp);
                            if ('"' == c) {
                                break;
                            }
                            temp--;
                        }

                        if (temp != 0) {
                            String indexName = str.substring(temp + 1, index + keyword.length());
                            if (!indexMap.containsKey(indexName)) {
                                StringBuilder sb = new StringBuilder(f.getAbsolutePath());
                                sb.replace(0, filePath.length(), "");
                                sb.delete(sb.indexOf("\\"), sb.length());
                                sb.append("    ");
                                sb.append(f.getName());
                                indexMap.put(indexName, sb.toString());
                            }
                        }

                        break;
                    }
                }
                br.close();
            }
        });

        indexMap.forEach((k, v) -> PrintUtil.log(k + "    " + v));
    }
}
