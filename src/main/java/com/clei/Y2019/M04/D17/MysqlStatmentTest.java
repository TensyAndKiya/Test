package com.clei.Y2019.M04.D17;

import com.clei.utils.PrintUtil;

//一些不常用的sql语句，但是又很有用的。。
public class MysqlStatmentTest {
    public static void main(String[] args) {
        PrintUtil.dateLine("查询某个表的记录条数(数据量较大的情况下用count比较耗费性能)：");
        PrintUtil.dateLine("\t" + "SELECT table_rows FROM information_schema.`TABLES` WHERE table_schema = 'database_name' AND table_name = 'table_name' ;");
        PrintUtil.dateLine("但上述语句结果有时可能会比count结果小。。所以请在不要求很准确的情况下用");
    }
}
