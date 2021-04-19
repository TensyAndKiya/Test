package com.clei.Y2019.M04.D23;

import com.clei.utils.DateUtil;
import com.clei.utils.PrintUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 获取到一个日志文件里某天的错误排序
 *
 * @author KIyA
 */
public class ThreadNameCount {

    private final static String LOG_FILE = "C:\\Users\\liudg\\Desktop\\logs\\2019-04-23 error\\all_info_0422.log";
    private final static String ERROR = " ERROR ";
    private final static String DAY = "2019-04-22-";

    public static void main(String[] args) throws Exception {
        File logFile = new File(LOG_FILE);
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(logFile), StandardCharsets.UTF_8));
        Map<String, ThreadDiff> countMap = new HashMap<>();
        String line;
        while ((line = br.readLine()) != null) {
            if (line.startsWith(DAY) && line.contains(ERROR)) {
                int index = line.indexOf(ERROR);
                String dateTime = line.substring(0, 19);
                String threadName = line.substring(21, index - 1);
                createEntity(countMap, threadName, dateTime);
            }
        }
        br.close();
        PrintUtil.log("筛选完毕");
        List<ThreadDiff> list = new ArrayList<>(countMap.values());
        Collections.sort(list);
        list.forEach(PrintUtil::log);
    }

    private static void createEntity(Map<String, ThreadDiff> map, String threadName, String dateTime) {
        ThreadDiff td = map.get(threadName);
        LocalDateTime date = DateUtil.parse(dateTime, "yyyy-MM-dd-HH:mm:ss");
        if (null == td) {
            map.put(threadName, new ThreadDiff(threadName, 1, date, null, 0));
        } else {
            td.setCount(td.getCount() + 1);
            td.setEnd(date);
            td.setDiff(ChronoUnit.SECONDS.between(td.getBegin(), td.getEnd()));
        }
    }

    private static class ThreadDiff implements Comparable<ThreadDiff> {

        private String thread;
        private int count;
        private LocalDateTime begin;
        private LocalDateTime end;
        private long diff;

        public ThreadDiff(String thread, int count, LocalDateTime begin, LocalDateTime end, long diff) {
            this.thread = thread;
            this.count = count;
            this.begin = begin;
            this.end = end;
            this.diff = diff;
        }

        @Override
        public int compareTo(ThreadDiff ec) {
            return Long.compare(ec.getDiff(), diff);
        }

        @Override
        public String toString() {
            return "ThreadDiff{" +
                    "thread='" + thread + '\'' +
                    ", diff=" + diff +
                    ", begin=" + DateUtil.format(begin) +
                    ", end=" + (null == end ? "" : DateUtil.format(end)) +
                    ", count=" + count +
                    '}';
        }

        public String getThread() {
            return thread;
        }

        public void setThread(String thread) {
            this.thread = thread;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public LocalDateTime getBegin() {
            return begin;
        }

        public void setBegin(LocalDateTime begin) {
            this.begin = begin;
        }

        public LocalDateTime getEnd() {
            return end;
        }

        public void setEnd(LocalDateTime end) {
            this.end = end;
        }

        public long getDiff() {
            return diff;
        }

        public void setDiff(long diff) {
            this.diff = diff;
        }
    }
}
