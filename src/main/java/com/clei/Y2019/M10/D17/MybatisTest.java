package com.clei.Y2019.M10.D17;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 快速获取某表的 ResultMap信息，以及所有的字段名信息
 *
 * @author KIyA
 * @since 2019-10-17
 */
public class MybatisTest {
    private final static String ENV = "prod";
    private final static String DATABASE = "park";
    private final static String TABLE = "park_parkinglot";
    private final static char UNDERLINE = '_';
    private final static char AT = '@';

    public static void main(String[] args) throws IOException {
        String env = ENV;
        if(args.length > 0){
            if(args[0].equals("prod")){
                env = "prod";
            }
        }
        List<Map<String,String>> result = (List<Map<String, String>>) doGet(env);
        printResultMapAndColumns(result);
    }

    private static SqlSession getSession(String env) throws IOException {
        Reader reader = Resources.getResourceAsReader("mybatisConf/mybatisConf.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader,env);
        SqlSession session = sessionFactory.openSession();
        return session;
    }

    private static Object doGet(String env) throws IOException {
        SqlSession session = getSession(env);
        ColumnDao mapper = session.getMapper(ColumnDao.class);
        Map<String,String> param = new HashMap<>();
        param.put("database",DATABASE);
        param.put("table",TABLE);
        List<Map<String,String>> reuslt = mapper.getColumnInfo(param);
        return reuslt;
    }

    private static void printResultMapAndColumns(List<Map<String, String>> list){
        StringBuilder resultMap = new StringBuilder("<resultMap id=\"baseMap\" type=\"\">");
        StringBuilder columnSql = new StringBuilder("<sql id=\"baseColumn\">\n\t");
        System.out.println(list.size());
        list.forEach(e -> {
            String column = e.get("column_name");
            String type = e.get("column_type");
            resultMap.append("\n\t");
            resultMap.append("<result property=\"");
            resultMap.append(getProperty(column));
            resultMap.append("\" column=\"");
            resultMap.append(column);
            resultMap.append("\" javaType=\"");
            resultMap.append(getJavaType(type));
            resultMap.append("\"></result>");

            // columnSql
            columnSql.append(column);
            columnSql.append(',');
        });
        resultMap.append("\n</resultMap>");
        columnSql.deleteCharAt(columnSql.length() - 1);
        columnSql.append("\n</sql>");

        System.out.println(resultMap.toString());
        System.out.println(columnSql.toString());
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
            return "java.lang.Boolean";
        }else if(type.startsWith("datetime")){
            return "java.util.Date";
        }else if(type.contains("char")){
            return "java.lang.String";
        }
        return "java.lang.String";
    }
}
