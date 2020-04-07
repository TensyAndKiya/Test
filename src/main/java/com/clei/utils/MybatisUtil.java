package com.clei.utils;

import com.clei.utils.other.ColumnDao;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * 快速获取某表的 ResultMap信息，以及所有的字段名信息
 *
 * @author KIyA
 * @since 2019-10-17
 */
public class MybatisUtil {
    private final static String ENV = "prod";
    private final static String DATABASE = "finance";
    private final static String TABLE = "finance_park_income";
    private final static char UNDERLINE = '_';
    private final static char AT = '@';

    // 102 agv == 3 || p2 floor ||

    public static void main(String[] args) throws Exception {
        String env = ENV;
        if(args.length > 0){
            if(args[0].equals("prod")){
                env = "prod";
            }
        }


        // doUpdate(env);

        /*List<Map<String,Object>> list = new ArrayList<>();
        for (int i = 1; i <= 102; i++) {
            Map<String,Object> map = new HashMap<>();
            String id = UUID.randomUUID().toString().replaceAll("-","");
            map.put("parkingspaceId",id);
            map.put("floorId","000002");
            map.put("areaId","200005");
            map.put("parkinglotId","9fb2a645362f58ea7fbf4978034028a3");
            map.put("parkingspaceCode","1F-AGV-" + getStr(i));
            map.put("parkingspaceType",3);
            map.put("parkingspaceStatus",0);
            map.put("createBy","00001");
            list.add(map);
        }

        SqlSession session = getSession(env);
        ColumnDao mapper = session.getMapper(ColumnDao.class);
        int result = mapper.insertSpace(list);
        session.commit();

        System.out.println("success " + result);*/

        // Map<String,String> param = new HashMap<>(2);
        /*param.put("list",DATABASE);
        param.put("table",TABLE);*/


        // 输出 table private
        List<Map<String,String>> result = (List<Map<String, String>>) doGet(env);
        printResultMapAndColumns(result);

        // doUpdateStatus(env);

         // doUpdate(env);

        // allTimeBack(env);

        /*System.out.println(0.5*3600);
        System.out.println(12*3600);*/

        //
        // insertCompanyParkinglot(env);

        // insertParkInvoiceInfo(env);

    }

    private static void insertCompanyParkinglot(String env) throws Exception{
        SqlSession session = getSession(env);
        ColumnDao mapper = session.getMapper(ColumnDao.class);

        List<String> parkIds = mapper.select1();

        List<Map<String,Object>> list = new ArrayList<>();
        for(String str : parkIds){
            Map<String,Object> param = new HashMap<>();
            param.put("companyId","2");
            param.put("parkId",str);
            list.add(param);
        }

        // session.commit();

        int result = mapper.insertCompanyParkinglot(list);

        PrintUtil.println("result : " + result,result);

    }

    private static void insertParkInvoiceInfo(String env) throws Exception{
        SqlSession session = getSession(env);
        ColumnDao mapper = session.getMapper(ColumnDao.class);

        List<String> parkIds = mapper.select1();

        List<Map<String,Object>> list = new ArrayList<>();
        for(String str : parkIds){
            Map<String,Object> param = new HashMap<>();
            param.put("invoiceId",UUID.randomUUID().toString().replaceAll("-", ""));
            param.put("parkId",str);
            param.put("goodsCode","3040502020200000000");
            param.put("codeVersion","33.0");
            param.put("taxRate",0.09);
            param.put("dsptbm","111JIY4C");
            param.put("secretId","2b92b210d95f48bda83eda5032d3b2e8");
            param.put("secretKey","0eece418a8984803878918a8a49d781b");
            param.put("drawer","张三三");
            param.put("reviewer","李思思");
            param.put("payee","王武武");
            list.add(param);
        }

        // session.commit();

        int result = mapper.insertCompanyParkinglot(list);

        PrintUtil.println("result : " + result,result);

    }

    private static void allTimeBack(String env) throws Exception{
        SqlSession session = getSession(env);
        ColumnDao mapper = session.getMapper(ColumnDao.class);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String yesterday = "2019-12-04 18:33:33";
        LocalDateTime yTime = LocalDateTime.parse(yesterday,dtf);
        LocalDateTime now = LocalDateTime.now();
        long seconds = ChronoUnit.SECONDS.between(yTime,now);
        System.out.println("秒 ： " + seconds);

        Map<String,Object> map = new HashMap<>();
        map.put("seconds",seconds);
        Integer result = mapper.updateTimeBack(map);

        System.out.println("result : " + result);
    }

    private static void doUpdateStatus(String env) throws Exception{
        SqlSession session = getSession(env);
        ColumnDao mapper = session.getMapper(ColumnDao.class);

        Map<String,Object> map = new HashMap<>();
        map.put("areaName","中华鲟");
        map.put("fromCode","B1-810");
        map.put("toCode","B1-870");
        map.put("limit",55);

        Integer origin = mapper.updateStatus1(map);
        System.out.println("effect : " + origin);

        Integer result  = mapper.updateStatus(map);
        System.out.println("result : " + result);

        session.commit();
    }

    private static void doUpdate(String env) throws Exception{
        SqlSession session = getSession(env);
        ColumnDao mapper = session.getMapper(ColumnDao.class);
        List<String> strs = mapper.selectList();
        Random random = new Random();
        int[] array = random.ints(strs.size(),1800,43200).toArray();

        System.out.println(strs.size());

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        for (int i = 0; i < strs.size(); i++) {
            Map<String,Object> map = new HashMap<>();
            map.put("parkingspaceId",strs.get(i));
            map.put("entranceTime",dtf.format(now.minusSeconds(array[i])));
            mapper.updateTime(map);
            System.out.println(i);
        }

        session.commit();
    }

    private static String getStr(int i){
        if (i < 10){
            return "00" + i;
        }else if(i< 100){
            return "0" + i;
        }else {
            return "" + i;
        }
    }

    private static SqlSession getSession(String env) throws IOException {
        Reader reader = Resources.getResourceAsReader("mybatisConf/mybatisConf.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader,env);
        // 自动提交 true
        SqlSession session = sessionFactory.openSession(true);
        return session;
    }

    private static Object doGet(String env) throws IOException {
        SqlSession session = getSession(env);
        ColumnDao mapper = session.getMapper(ColumnDao.class);
        Map<String,String> param = new HashMap<>(2);
        param.put("database",DATABASE);
        param.put("table",TABLE);
        List<Map<String,String>> reuslt = mapper.getColumnInfo(param);
        return reuslt;
    }

    private static void printResultMapAndColumns(List<Map<String, String>> list){
        StringBuilder resultMap = new StringBuilder("<resultMap id=\"baseMap\" type=\"\">");
        StringBuilder columnSql = new StringBuilder("<sql id=\"baseColumn\">\n\t");
        StringBuilder properties = new StringBuilder("");
        StringBuilder selectAsSql = new StringBuilder("");
        StringBuilder insertColumnSql = new StringBuilder("");
        StringBuilder insertPropertySql = new StringBuilder("");
        StringBuilder updateSql = new StringBuilder("");
        System.out.println(list.size());
        list.forEach(e -> {
            String column = e.get("column_name");
            String type = e.get("column_type");
            resultMap.append("\n\t");
            resultMap.append("<result property=\"");
            String property = getProperty(column);
            resultMap.append(property);
            resultMap.append("\" column=\"");
            resultMap.append(column);
            resultMap.append("\" javaType=\"");
            String javaType = getJavaType(type);
            resultMap.append(javaType);
            resultMap.append("\"></result>");

            // columnSql
            columnSql.append(column);
            columnSql.append(',');

            // insertColumnSql
            insertColumnSql.append(column);
            insertColumnSql.append(",\n");

            // insertPropertySql
            insertPropertySql.append("#{");
            insertPropertySql.append(property);
            insertPropertySql.append("},\n");

            // properties
            properties.append("private ");
            properties.append(javaType.substring(javaType.lastIndexOf('.') + 1));
            properties.append(' ');
            properties.append(property);
            properties.append(";\n");

            // selectAsSql
            selectAsSql.append(column);
            selectAsSql.append(" AS ");
            selectAsSql.append(property);
            selectAsSql.append(",\n\t");

            // updateSql
            updateSql.append(column);
            updateSql.append("=#{");
            updateSql.append(property);
            updateSql.append("},\n");

        });
        resultMap.append("\n</resultMap>");
        columnSql.deleteCharAt(columnSql.length() - 1);
        columnSql.append("\n</sql>");

        System.out.println(resultMap.toString());
        System.out.println(columnSql.toString());
        System.out.println(properties.toString());
        System.out.println(selectAsSql.toString());
        System.out.println(insertColumnSql.toString());
        System.out.println(insertPropertySql.toString());
        System.out.println(updateSql.toString());
    }

    private static String getProperty(String column){
        int index = -1;
        while (UNDERLINE == column.charAt(0)){
            column = AT + column.substring(1);
        }
        while (UNDERLINE == column.charAt(column.length() - 1)){
            column = column.substring(0,column.length() -1) + AT;
        }
        while ((index = column.indexOf(UNDERLINE)) > 0){
            char c = column.charAt(index + 1);
            if(Character.isLowerCase(c)){
                // 转为大写
                c -= 32;
            }
            column = column.substring(0,index) + c + column.substring(index + 2);
        }
        return column.replaceAll("" + AT,"");
    }

    private static String getJavaType(String type){
        type = type.toLowerCase();
        if(type.startsWith("int")){
            return "java.lang.Integer";
        }else if(type.startsWith("float")){
            return "java.lang.Float";
        }else if(type.startsWith("double") || type.startsWith("decimal")){
            return "java.lang.Double";
        }else if(type.startsWith("tinyint")){
            return "java.lang.Short";
        }else if(type.startsWith("datetime") || type.startsWith("date")){
            return "java.util.Date";
        }else if(type.contains("char")){
            return "java.lang.String";
        }
        return "java.lang.String";
    }
}

