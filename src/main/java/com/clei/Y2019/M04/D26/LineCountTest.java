package com.clei.Y2019.M04.D26;

import java.io.*;
import java.util.*;

public class LineCountTest {
    private static String LOG_File = "C:\\Users\\liudg\\Desktop\\logs\\2019-04-23 error\\all_info_0426.log";
    public static void main(String[] args) throws Exception {
        File logFile = new File(LOG_File);
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(logFile),"UTF-8"));
        Map<String,Integer> countMap = new HashMap<>();
        String line;
        while( (line = br.readLine()) != null ){
            if(line.contains(" - ErrorScene^_^")){
                putMap(countMap,line);
            }
        }
        br.close();
        List<LineCount> list = mapToEC(countMap);
        Collections.sort(list);
        for (int i =0;i < 100 && i < list.size();i ++){
            System.out.println(list.get(i));
        }
    }

    private static void putMap(Map<String,Integer> map,String key){
        Integer val = map.get(key);
        if(null == val){
            map.put(key,1);
        }else{
            map.put(key,val + 1);
        }
    }

    private static List<LineCount> mapToEC(Map<String,Integer> map){
        List<LineCount> list = new ArrayList<>();
        map.forEach( (k,v) -> {
            LineCount ec = new LineCount(k,v);
            list.add(ec);
        } );
        return list;
    }

    private static class LineCount implements Comparable<LineCount>{
        private String line;
        private int count;
        public LineCount(String line, int count) {
            this.line = line;
            this.count = count;
        }

        @Override
        public int compareTo(LineCount lc) {
            return Integer.compare(lc.getCount(),count);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof LineCount)) return false;
            LineCount lineCount = (LineCount) o;
            return getCount() == lineCount.getCount();
        }

        @Override
        public int hashCode() {
            return Objects.hash(getCount());
        }

        public String getLine() {
            return line;
        }
        public int getCount() {
            return count;
        }
        @Override
        public String toString() {
            return line +  '\t' +count;
        }
    }
}
