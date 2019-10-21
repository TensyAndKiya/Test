package com.clei.Y2019.M04.D23;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class ThreadNameCount {
    private static String LOG_File = "C:\\Users\\liudg\\Desktop\\logs\\2019-04-23 error\\all_info_0422.log";
    private final static String ERROR = " ERROR ";
    private final static String INFO = " INFO ";
    private final static String DAY = "2019-04-22-";

    public static void main(String[] args) throws Exception {
        File logFile = new File(LOG_File);
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(logFile),"UTF-8"));
        Map<String,ThreadDiff> countMap = new HashMap<>();
        String line;
        while( (line = br.readLine()) != null ){
            if(line.startsWith(DAY) && line.contains(ERROR)){
                int index = line.indexOf(ERROR);
                String dateTime = line.substring(0,19);
                String threadName = line.substring(21,index - 1);
                createEntity(countMap,threadName,dateTime);
            }
        }
        br.close();
        System.out.println("筛选完毕");
        List<ThreadDiff> list = mapToTD(countMap);
        Collections.sort(list);
        list.forEach( td ->{
            System.out.println(td);
        } );
    }

    private static void createEntity(Map<String,ThreadDiff> map,String threadName,String dateTime) throws Exception{
        ThreadDiff td = map.get(threadName);
        final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
        Date date = SDF.parse(dateTime);
        if(null == td){
            map.put(threadName,new ThreadDiff(threadName,1,date,null,0));
        }else{
            td.setCount(td.getCount() + 1);
            td.setEnd(date);
            td.setDiff((td.getEnd().getTime() - td.getBegin().getTime())/1000);
        }
    }

    private static List<ThreadDiff> mapToTD(Map<String,ThreadDiff> map){
        List<ThreadDiff> list = new ArrayList<>();
        map.forEach( (k,v) -> {
            list.add(v);
        } );
        return list;
    }

    private static class ThreadDiff implements Comparable<ThreadDiff>{
        private String thread;
        private int count;
        private Date begin;
        private Date end;
        private long diff;

        public ThreadDiff() {
        }

        public ThreadDiff(String thread, int count, Date begin, Date end, long diff) {
            this.thread = thread;
            this.count = count;
            this.begin = begin;
            this.end = end;
            this.diff = diff;
        }

        @Override
        public int compareTo(ThreadDiff ec) {
            return Long.compare(ec.getDiff(),diff);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ThreadDiff)) return false;
            ThreadDiff that = (ThreadDiff) o;
            return getDiff() == that.getDiff();
        }

        @Override
        public int hashCode() {
            return Objects.hash(getDiff());
        }

        @Override
        public String toString() {
            final SimpleDateFormat SDF = new SimpleDateFormat("yyyy MM dd HH mm ss");
            return "ThreadDiff{" +
                    "thread='" + thread + '\'' +
                    ", diff=" + diff +
                    ", begin=" + SDF.format(begin) +
                    ", end=" + (null == end?"":SDF.format(end)) +
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

        public Date getBegin() {
            return begin;
        }

        public void setBegin(Date begin) {
            this.begin = begin;
        }

        public Date getEnd() {
            return end;
        }

        public void setEnd(Date end) {
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
