package com.clei.Y2019.M04.D18;

import com.clei.utils.PrintUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

//用于一个表根据季度分表之后的查询。。分页是最骚的。。蓝瘦
public class SeasonTableTest {
    private static final String TABLE_NAME = "temp_orders";
    private static final String INITIAL_DATE = "2016 12 01 00 00 00";
    private static final Random rand = new Random();

    public static void main(String[] args) throws Exception{
        /*Scanner input = new Scanner(System.in,"UTF-8");
        PrintUtil.dateLine("时间格式 yyyy MM dd HH mm ss");
        while(true){
            PrintUtil.dateLine("请输入开始时间： xxxx xx xx xx xx xx(输入xx结束程序)");
            String str = input.nextLine();
            if(str.equals("xx")){
                break;
            }
            if(null == str || str.length() == 0){
                PrintUtil.dateLine("请输入正确内容：");
                continue;
            }
            Date startDate = SDF.parse(str);
            PrintUtil.dateLine("请输入结束时间： xxxx xx xx xx xx xx(输入xx结束程序)");
            str = input.nextLine();
            if(str.equals("xx")){
                break;
            }
            Date endDate = SDF.parse(str);
            //输出
            calcTable(startDate,endDate);
        }*/

        Date endDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(endDate.getTime()));
        for (int i = 0; i < 12; i++) {
            calendar.add(Calendar.MONTH, -1);
            PrintUtil.dateLine("第" + i + "次");
            List<TableObject> tables = calcTable(calendar.getTime(),endDate);
            int limit = 10;
            int page = 3;
            selectRecord(tables,page,limit);
        }
    }


    private static List<TableObject> calcTable(Date sDate,Date eDate) throws Exception{
        Date startDate = new Date(sDate.getTime());
        Date endDate = new Date(eDate.getTime());
        final SimpleDateFormat SDF = new SimpleDateFormat("yyyy MM dd HH mm ss");
        Date firstDate = SDF.parse(INITIAL_DATE);
        if(startDate.getTime() < firstDate.getTime()){
            startDate = firstDate;
        }
        Date curDate = new Date();
        if(endDate.getTime() > curDate.getTime()){
            endDate = curDate;
        }
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(endDate);
        //调整完开始时间和结束时间之后。。
        //添加temp_orders表
        endCalendar.add(Calendar.MONTH,-5);
        setMonthStart(endCalendar);
        //存储表情况的list
        List<TableObject> tables = new ArrayList<>();
        if(startDate.getTime() > endCalendar.getTimeInMillis()){
            tables.add(new TableObject(TABLE_NAME,startDate,endDate,getRandom()));
        }else{
            tables.add(new TableObject(TABLE_NAME,endCalendar.getTime(),endDate,getRandom()));
            endCalendar.add(Calendar.SECOND,-1);
        }
        //接下来添加之前的表
        while( startDate.getTime() <= endCalendar.getTimeInMillis() ){
            String tableName = TABLE_NAME + endCalendar.get(Calendar.YEAR) + getPart(endCalendar.get(Calendar.MONTH));
            endDate = endCalendar.getTime();
            //根据月份来判断该季度开始时间
            endCalendar.add(Calendar.MONTH,-(endCalendar.get(Calendar.MONTH)%3));
            setMonthStart(endCalendar);
            Date tempStartDate = endCalendar.getTime();
            if(startDate.getTime() > tempStartDate.getTime()){
                tables.add(new TableObject(tableName,startDate,endDate,getRandom()));
                break;
            }else{
                tables.add(new TableObject(tableName,tempStartDate,endDate,getRandom()));
                endCalendar.add(Calendar.SECOND,-1);
            }
        }
        return tables;

    }

    private static void setMonthStart(Calendar calendar){
        calendar.set(Calendar.DAY_OF_MONTH,1);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
    }

    private static String getPart(int month){
        switch (month){
            case 0:
            case 1:
            case 2:
                return "part1";
            case 3:
            case 4:
            case 5:
                return "part2";
            case 6:
            case 7:
            case 8:
                return "part3";
            case 9:
            case 10:
            case 11:
                return "part4";
        }
        return "";
    }

    private static void selectRecord(List<TableObject> tables,int page,int limit){
        //倒序一下。。让老的表在前面
        Collections.reverse(tables);
        for(TableObject to : tables){
            PrintUtil.dateLine(to);
        }
        int from = (page-1) * limit ;
        int sum = 0;
        //over 表示 是不是第一次 sum 大于 from
        boolean over = false;
        for(int i = 0; i < tables.size(); i ++){
            TableObject to = tables.get(i);
            sum += to.getCount();
            if( sum > from ){
                if(over){
                    int thisFrom = 0;
                    if(sum - from > limit -1){
                        //这一条表的记录就大于要查的了
                        limit = page * limit - (sum - to.getCount());
                        execSql(to.getName(),limit,thisFrom);
                        break;
                    }else{
                        execSql(to.getName(),limit,thisFrom);
                    }
                }else{
                    over = true;
                    int thisFrom = to.getCount() - ( sum - from );
                    execSql(to.getName(),limit,thisFrom);
                    if(sum - from > limit -1){
                        //这一条表的记录就大于要查的了
                        break;
                    }
                }
            }
        }
    }

    private static void execSql(String tableName,int limit,int offset){
        PrintUtil.dateLine("SELECT * FROM " + tableName + " LIMIT " + limit + " OFFSET " + offset);
    }

    private static int getRandom(){
        return rand.nextInt(20)+1;
    }

    static class TableObject{
        private String name;
        private Date start;
        private Date end;
        private int count;

        public TableObject(String name, Date start, Date end, int count) {
            this.name = name;
            this.start = start;
            this.end = end;
            this.count = count;
        }
        @Override
        public String toString() {
            final SimpleDateFormat SDF = new SimpleDateFormat("yyyy MM dd HH mm ss");
            return "TableObject{" +
                    "name='" + name + '\'' +
                    ", start=" + SDF.format(start) +
                    ", end=" + SDF.format(end) +
                    ", count=" + count +
                    '}';
        }
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Date getStart() {
            return start;
        }

        public void setStart(Date start) {
            this.start = start;
        }

        public Date getEnd() {
            return end;
        }

        public void setEnd(Date end) {
            this.end = end;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }

}


