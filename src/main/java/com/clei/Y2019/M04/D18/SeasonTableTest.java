package com.clei.Y2019.M04.D18;

import com.clei.utils.DateUtil;
import com.clei.utils.PrintUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * 用于一个表根据季度分表之后的查询。。分页是最骚的。。蓝瘦
 *
 * @author KIyA
 */
public class SeasonTableTest {

    private static final String TABLE_NAME = "temp_orders";
    private static final String INITIAL_DATE = "2016 12 01 00 00 00";
    private static final Random rand = new Random();

    public static void main(String[] args) {
        LocalDateTime endDate = LocalDateTime.now();
        for (int i = 0; i < 12; i++) {
            LocalDateTime startDate = endDate.minusMonths(i + 1);
            PrintUtil.log("第" + i + "次");
            List<TableObject> tables = calcTable(startDate, endDate);
            int limit = 10;
            int page = 3;
            selectRecord(tables, page, limit);
        }
    }

    private static List<TableObject> calcTable(LocalDateTime startDate, LocalDateTime endDate) {
        LocalDateTime firstDate = DateUtil.parse(INITIAL_DATE, "yyyy MM dd HH mm ss");
        if (startDate.isBefore(firstDate)) {
            startDate = firstDate;
        }
        LocalDateTime curDate = LocalDateTime.now();
        if (endDate.isAfter(curDate)) {
            endDate = curDate;
        }
        // 调整完开始时间和结束时间之后。。
        // 添加temp_orders表
        // 当前月以及前5个月的数据都存到最新表内
        LocalDateTime tempDate = getMonthBegin(endDate.minusMonths(5));
        //存储表情况的list
        List<TableObject> tables = new ArrayList<>();
        if (startDate.isAfter(tempDate)) {
            tables.add(new TableObject(TABLE_NAME, startDate, endDate, getRandom()));
        } else {
            tables.add(new TableObject(TABLE_NAME, tempDate, endDate, getRandom()));
        }
        //接下来添加之前的表
        while (startDate.isBefore(tempDate)) {
            // 当月初变成上月末
            tempDate = tempDate.minusSeconds(1);
            String tableName = TABLE_NAME + tempDate.get(ChronoField.YEAR) + getPart(tempDate.get(ChronoField.MONTH_OF_YEAR));
            // 根据月份来判断该季度开始时间
            // 临时开始时间
            int monthValue = tempDate.getMonthValue();
            monthValue = monthValue - (monthValue - 1) % 3;
            LocalDateTime tempStartDate = LocalDateTime.of(LocalDate.of(tempDate.getYear(), monthValue, 1), LocalTime.MIN);
            if (startDate.isAfter(tempStartDate)) {
                tables.add(new TableObject(tableName, startDate, tempDate, getRandom()));
                break;
            } else {
                tables.add(new TableObject(tableName, tempStartDate, tempDate, getRandom()));
                tempDate = tempStartDate;
            }
        }
        return tables;
    }

    private static String getPart(int month) {
        switch (month) {
            case 1:
            case 2:
            case 3:
                return "part1";
            case 4:
            case 5:
            case 6:
                return "part2";
            case 7:
            case 8:
            case 9:
                return "part3";
            case 10:
            case 11:
            case 12:
                return "part4";
            default:
                return "";
        }
    }

    /**
     * 获得一个对应日期的当月的开始时刻时间
     *
     * @param localDateTime
     * @return
     */
    private static LocalDateTime getMonthBegin(LocalDateTime localDateTime) {
        return LocalDateTime.of(LocalDate.of(localDateTime.getYear(), localDateTime.getMonthValue(), 1), LocalTime.MIN);
    }

    private static void selectRecord(List<TableObject> tables, int page, int limit) {
        //倒序一下。。让老的表在前面
        Collections.reverse(tables);
        for (TableObject to : tables) {
            PrintUtil.log(to);
        }
        int from = (page - 1) * limit;
        int sum = 0;
        //over 表示 是不是第一次 sum 大于 from
        boolean over = false;
        for (int i = 0; i < tables.size(); i++) {
            TableObject to = tables.get(i);
            sum += to.getCount();
            if (sum > from) {
                if (over) {
                    int thisFrom = 0;
                    if (sum - from > limit - 1) {
                        //这一条表的记录就大于要查的了
                        limit = page * limit - (sum - to.getCount());
                        execSql(to.getName(), limit, thisFrom);
                        break;
                    } else {
                        execSql(to.getName(), limit, thisFrom);
                    }
                } else {
                    over = true;
                    int thisFrom = to.getCount() - (sum - from);
                    execSql(to.getName(), limit, thisFrom);
                    if (sum - from > limit - 1) {
                        //这一条表的记录就大于要查的了
                        break;
                    }
                }
            }
        }
    }

    private static void execSql(String tableName, int limit, int offset) {
        PrintUtil.log("SELECT * FROM " + tableName + " LIMIT " + limit + " OFFSET " + offset);
    }

    private static int getRandom() {
        return rand.nextInt(20) + 1;
    }

    static class TableObject {

        private String name;
        private LocalDateTime start;
        private LocalDateTime end;
        private int count;

        public TableObject(String name, LocalDateTime start, LocalDateTime end, int count) {
            this.name = name;
            this.start = start;
            this.end = end;
            this.count = count;
        }

        @Override
        public String toString() {
            return "TableObject{" +
                    "name='" + name + '\'' +
                    ", start=" + DateUtil.format(start) +
                    ", end=" + DateUtil.format(end) +
                    ", count=" + count +
                    '}';
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public LocalDateTime getStart() {
            return start;
        }

        public void setStart(LocalDateTime start) {
            this.start = start;
        }

        public LocalDateTime getEnd() {
            return end;
        }

        public void setEnd(LocalDateTime end) {
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


