package com.clei.Y2019.M04.D23;

import com.clei.utils.PrintUtil;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ExceptionCountTest {
    private static String LOG_File = "C:\\Users\\liudg\\Desktop\\logs\\2019-04-23 error\\all_error_0505.log";
    private final static String EXCEPTION = "Exception:";
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(LOG_File),"UTF-8"));
        Map<String,Integer> countMap = new HashMap<>();
        String line;
        while( (line = br.readLine()) != null ){
            if(line.contains(EXCEPTION) && !line.contains("Caused by:")){
                int index1 = line.indexOf(EXCEPTION);
                String subStr = line.substring(0,index1 + 9);
                int index2 = subStr.lastIndexOf(".");
                if(index2 != -1){
                    String str = subStr.substring(index2 + 1);
                    putMap(countMap,str);
                }
            }
        }
        br.close();
        PrintUtil.log("筛选完毕");
        List<ExceptionCount> list = mapToEC(countMap);
        Collections.sort(list);
        for(ExceptionCount ec : list){
            PrintUtil.log(ec);
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

    private static List<ExceptionCount> mapToEC(Map<String,Integer> map){
        List<ExceptionCount> list = new ArrayList<>();
        map.forEach( (k,v) -> {
            ExceptionCount ec = new ExceptionCount(k,v);
            list.add(ec);
        } );
        return list;
    }

    private static class ExceptionCount implements Comparable<ExceptionCount>{
        private String exception;
        private int count;
        public ExceptionCount(String exception, int count) {
            this.exception = exception;
            this.count = count;
        }

        @Override
        public int compareTo(ExceptionCount ec) {
            return Integer.compare(ec.getCount(),count);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ExceptionCount)) return false;
            ExceptionCount that = (ExceptionCount) o;
            return getCount() == that.getCount();
        }

        @Override
        public int hashCode() {
            return Objects.hash(getCount());
        }

        public String getException() {
            return exception;
        }
        public int getCount() {
            return count;
        }
        @Override
        public String toString() {
            return exception +  '\t' +count;
        }
    }
}
