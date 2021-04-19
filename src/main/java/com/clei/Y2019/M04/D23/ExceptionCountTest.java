package com.clei.Y2019.M04.D23;

import com.clei.utils.PrintUtil;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 异常类型统计
 *
 * @author KIyA
 */
public class ExceptionCountTest {

    /**
     * 日志文件路径
     */
    private final static String LOG_FILE = "C:\\Users\\liudg\\Desktop\\logs\\2019-04-23 error\\all_error_0505.log";

    /**
     * 异常关键字
     */
    private final static String EXCEPTION = "Exception:";

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(LOG_FILE), StandardCharsets.UTF_8));
        Map<String, Integer> countMap = new HashMap<>();
        String line;
        while ((line = br.readLine()) != null) {
            if (line.contains(EXCEPTION) && !line.contains("Caused by:")) {
                int index1 = line.indexOf(EXCEPTION);
                String subStr = line.substring(0, index1 + 9);
                int index2 = subStr.lastIndexOf(".");
                if (index2 != -1) {
                    String str = subStr.substring(index2 + 1);
                    // 无则为一 有则加一
                    countMap.merge(str, 1, Integer::sum);
                }
            }
        }
        br.close();
        PrintUtil.log("筛选完毕");
        List<ExceptionCount> list = mapToExceptionCount(countMap);
        Collections.sort(list);
        for (ExceptionCount ec : list) {
            PrintUtil.log(ec);
        }
    }

    private static List<ExceptionCount> mapToExceptionCount(Map<String, Integer> map) {
        List<ExceptionCount> list = new ArrayList<>();
        map.forEach((k, v) -> {
            ExceptionCount ec = new ExceptionCount(k, v);
            list.add(ec);
        });
        return list;
    }

    private static class ExceptionCount implements Comparable<ExceptionCount> {

        private final String exception;
        private final int count;

        public ExceptionCount(String exception, int count) {
            this.exception = exception;
            this.count = count;
        }

        @Override
        public int compareTo(ExceptionCount ec) {
            return Integer.compare(ec.getCount(), count);
        }

        public String getException() {
            return exception;
        }

        public int getCount() {
            return count;
        }

        @Override
        public String toString() {
            return exception + '\t' + count;
        }
    }
}
