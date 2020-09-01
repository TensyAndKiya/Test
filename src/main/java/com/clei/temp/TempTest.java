package com.clei.temp;

import com.clei.utils.DateUtil;
import com.clei.utils.FileUtil;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author KIyA
 * @backStory 临时用到的一些测试
 */
public class TempTest {

    public static void main(String[] args) throws Exception {

        List<Node> list1 = new ArrayList<>();
        list1.add(new Node("张三"));
        list1.add(new Node("李四"));

        List list2 = list1.stream().collect(Collectors.toList());

        System.out.println(list1 == list2);
        System.out.println(list1.get(0) == list2.get(0));

        Iterator<Node> it = list2.iterator();
        while (it.hasNext()){
            it.next();
            it.remove();
        }

        System.out.println(list1.size());
        System.out.println(list2.size());

        long nowMillis = System.currentTimeMillis();
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime tomorrow = today.plusDays(1);

        System.out.println(DateUtil.toEpochSecond(today));
        System.out.println(DateUtil.toEpochSecond(tomorrow));

        System.out.println(DateUtil.toEpochMilli(today));
        System.out.println(nowMillis + 86400 * 1000);
        System.out.println(DateUtil.toEpochMilli(tomorrow));

        System.out.println(nowMillis + 86400 * 1000 - DateUtil.toEpochMilli(tomorrow));
        System.out.println(tomorrow.toInstant(ZoneOffset.ofHours(8)).toEpochMilli() / 1000);
        System.out.println(tomorrow.toInstant(ZoneOffset.ofHours(0)).toEpochMilli() / 1000);
        System.out.println(DateUtil.toEpochSecond(tomorrow) - DateUtil.toEpochSecond(today));

        System.out.println(ChronoUnit.MINUTES.between(tomorrow,today));
        System.out.println(tomorrow.until(today,ChronoUnit.MINUTES));


        LocalDateTime l1 = LocalDateTime.of(2020,8,31,0,0);
        System.out.println(DateUtil.toEpochMilli(l1));
        System.out.println(DateUtil.toEpochMilli(l1.plusDays(1)));
        System.out.println(DateUtil.format(l1.withHour(3)));

        // 看看改项目下用到了多少ES index
        Map<String,String> indexMap = new HashMap<>(16);

        String filePath = "D:\\WorkSpace\\HK\\tbd-transport\\";

        String keyword = "_index";

        FileUtil.fileOperation(filePath,f -> {

            String fileName = f.getName();

            if(fileName.endsWith(".java")){
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f),"UTF-8"));
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
                                fileName = f.getAbsolutePath();
                                fileName = fileName.replace(filePath,"");
                                fileName = fileName.substring(0,fileName.indexOf("\\")) + "    " + f.getName();
                                indexMap.put(indexName,fileName);
                            }
                        }

                        break;
                    }
                }
                br.close();
            }
        });

        indexMap.forEach((k,v) -> {
            System.out.println(k + "    " + v);
        });

    }

    static class Node{
        private String name;

        public Node(String name) {
            this.name = name;
        }

        public String getName(){
            return name;
        }

    }

}
