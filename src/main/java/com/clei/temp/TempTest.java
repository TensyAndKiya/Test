package com.clei.temp;

import com.clei.utils.DateUtil;
import com.clei.utils.FileUtil;
import com.clei.utils.PrintUtil;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 临时用到的一些测试
 *
 * @author KIyA
 */
public class TempTest {

    public static void main(String[] args) throws Exception {

        List<Node> list1 = new ArrayList<>();
        list1.add(new Node("张三"));
        list1.add(new Node("李四"));

        List<Node> list2 = new ArrayList<>(list1);

        PrintUtil.dateLine(list1 == list2);
        PrintUtil.dateLine(list1.get(0) == list2.get(0));

        Iterator<Node> it = list2.iterator();
        while (it.hasNext()) {
            it.next();
            it.remove();
        }

        PrintUtil.dateLine(list1.size());
        PrintUtil.dateLine(list2.size());

        long nowMillis = System.currentTimeMillis();
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime tomorrow = today.plusDays(1);

        PrintUtil.dateLine(DateUtil.toEpochSecond(today));
        PrintUtil.dateLine(DateUtil.toEpochSecond(tomorrow));

        PrintUtil.dateLine(DateUtil.toEpochMilli(today));
        PrintUtil.dateLine(nowMillis + 86400 * 1000);
        PrintUtil.dateLine(DateUtil.toEpochMilli(tomorrow));

        PrintUtil.dateLine(nowMillis + 86400 * 1000 - DateUtil.toEpochMilli(tomorrow));
        PrintUtil.dateLine(tomorrow.toInstant(ZoneOffset.ofHours(8)).toEpochMilli() / 1000);
        PrintUtil.dateLine(tomorrow.toInstant(ZoneOffset.ofHours(0)).toEpochMilli() / 1000);
        PrintUtil.dateLine(DateUtil.toEpochSecond(tomorrow) - DateUtil.toEpochSecond(today));

        PrintUtil.dateLine(ChronoUnit.MINUTES.between(tomorrow, today));
        PrintUtil.dateLine(tomorrow.until(today, ChronoUnit.MINUTES));


        LocalDateTime l1 = LocalDateTime.of(2020, 8, 31, 0, 0);
        PrintUtil.dateLine(DateUtil.toEpochMilli(l1));
        PrintUtil.dateLine(DateUtil.toEpochMilli(l1.plusDays(1)));
        PrintUtil.dateLine(DateUtil.format(l1.withHour(3)));

        test();

    }

    private static void test() throws Exception {

        // 看看改项目下用到了多少ES index
        Map<String,String> indexMap = new HashMap<>(16);

        String filePath = "D:\\WorkSpace\\HK\\tbd-transport\\";

        String keyword = "_index";

        String fileSuffix = ".java";

        FileUtil.fileOperation(filePath,f -> {

            String fileName = f.getName();

            if(fileName.endsWith(fileSuffix)){
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), StandardCharsets.UTF_8));
                String str;
                while(null != (str = br.readLine())){

                    int index = str.indexOf(keyword);

                    if(index > -1 && !str.contains("$") && !str.contains("{")){

                        int temp = index - 1;

                        while (temp > 0){
                            char c = str.charAt(temp);
                            if('"' == c){
                                break;
                            }
                            temp --;
                        }

                        if(temp != 0){
                            String indexName = str.substring(temp + 1,index + keyword.length());
                            if(!indexMap.containsKey(indexName)){
                                StringBuilder sb = new StringBuilder(f.getAbsolutePath());
                                sb.replace(0,filePath.length(),"");
                                sb.delete(sb.indexOf("\\"),sb.length());
                                sb.append("    ");
                                sb.append(f.getName());
                                indexMap.put(indexName,sb.toString());
                            }
                        }

                        break;
                    }
                }
                br.close();
            }
        });

        indexMap.forEach((k, v) -> PrintUtil.dateLine(k + "    " + v));
    }

    static class Node{
        private final String name;

        public Node(String name) {
            this.name = name;
        }

        public String getName(){
            return name;
        }

    }

}
