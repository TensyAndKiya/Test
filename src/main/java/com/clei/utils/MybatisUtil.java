package com.clei.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.clei.Y2020.M09.D17.RoadObject;
import com.clei.utils.other.ColumnDao;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * 快速获取某表的 ResultMap信息，以及所有的字段名信息
 *
 * @author KIyA
 * @since 2019-10-17
 */
public class MybatisUtil {
    private final static String ENV = "test";
    private final static String DATABASE = "security";
    private final static String TABLE = "user";
    private final static char UNDERLINE = '_';
    private final static char AT = '@';

    // 102 agv == 3 || p2 floor ||

    public static void main(String[] args) throws Exception {
        String env = ENV;
        if (args.length > 0) {
            if (args[0].equals("prod")) {
                env = "prod";
            } else {
                env = "test";
            }
        }

        // insertRoadInfo(env);

        // insertRoadSectionRunStateInfo(env);

        // insertWarnCongestion(env);

        updateRoadSectionRunState(env);
    }


    /**
     * @param env 环境
     *            将大数据那边给的一份道路信息csv文件 插入到mysql数据库里
     */
    private static void insertRoadInfo(String env) throws Exception{

        // 1. 将数据装入 list
        String filePath = "D:\\Download\\DingDing\\0.csv";

        Map<String,List<RoadObject>> map = new HashMap<>();

        List<RoadObject> list = new ArrayList<>();

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath),"UTF-8"));
        String str;

        while(null != (str = br.readLine())){

            String[] array = str.split("\\s");
            int length = array.length;

            RoadObject obj = new RoadObject();
            obj.setRoadCode(array[0]);
            obj.setRoadName(array[3]);
            obj.setLength(new BigDecimal(array[length - 3]));
            obj.setDirection(array[length - 4]);
            // centerPoint:[106.630610,26.684490]
            String centerPoint = array[length - 2];
            int commaIndex = centerPoint.indexOf(",");

            obj.setCenterLon(new BigDecimal(centerPoint.substring(1,commaIndex)));
            obj.setCenterLat(new BigDecimal(centerPoint.substring(commaIndex + 1,centerPoint.length() - 1)));
            obj.setGeo(array[length - 1]);

            // 放入list
            list.add(obj);
            // 放入map
            List<RoadObject> sectionList = map.get(obj.getRoadName());
            if(sectionList == null){
                sectionList = new ArrayList<>(1);
            }
            sectionList.add(obj);
            map.put(obj.getRoadName(),sectionList);
        }

        // 设置roadList
        Collection<List<RoadObject>> values = map.values();
        List<RoadObject> roadList = new ArrayList<>(values.size());
        for (List<RoadObject> l : values){
            // 只选第一个
            roadList.add(l.get(0));
        }

        // 插入道路信息
        SqlSession session = getSession(env);

        ColumnDao mapper = session.getMapper(ColumnDao.class);

        // 插入到表并获取到roadId
        mapper.batchInsertRoadInfo(roadList);

        // 给所有路段设置roadId
        for(RoadObject obj : list){
            String name = obj.getRoadName();

            obj.setRoadId(map.get(name).get(0).getRoadId());
        }

        // 插入到表并获取到roadSectionId
        mapper.batchInsertRoadSectionInfo(list);

        // 插入到表
        mapper.batchInsertRoadSectionNode(list);

        br.close();
    }


    /**
     * 根据数据库道路路段信息 随机设置 运行状态
     *
     * @param env
     * @throws Exception
     */
    private static void insertRoadSectionRunStateInfo(String env) throws Exception {
        SqlSession session = getSession(env);

        ColumnDao mapper = session.getMapper(ColumnDao.class);

        // 获取已有的道路路段信息
        List<Map<String, Object>> sectionList = mapper.getSectionList(240001);

        Random random = new Random();

        for (Map<String, Object> m : sectionList) {
            double d1 = random.nextDouble();
            int i1 = random.nextInt(4);
            BigDecimal b1 = new BigDecimal(d1 + i1);
            b1 = b1.setScale(2, RoundingMode.HALF_UP);

            double d2 = random.nextDouble();
            int i2 = random.nextInt(200);
            BigDecimal b2 = new BigDecimal(d2 + i2);
            b2 = b2.setScale(2, RoundingMode.HALF_UP);

            BigDecimal b3 = new BigDecimal(String.valueOf(m.get("length")));
            b3 = b3.divide(new BigDecimal(2)).setScale(2, RoundingMode.HALF_UP);

            m.put("congestionIndex", b1);
            m.put("speed", b2);
            m.put("congestionMileage", b3);
            m.put("congestionType", random.nextInt(5 + 1));
        }

        mapper.batchInserRoadSectionRunState(sectionList);
    }

    /**
     * 根据数据库道路路段信息 插入 拥堵预警信息
     *
     * @param env
     * @throws Exception
     */
    private static void insertWarnCongestion(String env) throws Exception {
        SqlSession session = getSession(env);

        ColumnDao mapper = session.getMapper(ColumnDao.class);

        // 获取已有的道路路段信息
        List<Map<String, Object>> sectionList = mapper.getCongestionSectionList();

        Random random = new Random();

        for (Map<String, Object> m : sectionList) {

            String roadSectionName = String.valueOf(m.get("roadSectionName"));

            m.put("name", roadSectionName);
            m.put("address", roadSectionName);
            m.put("laneConfig", "双向四车道");
            m.put("congestionTime", random.nextInt(200));

            String geo = String.valueOf(m.remove("geo"));

            JSONArray array = JSONObject.parseArray(geo);
            int length = array.size();

            String start = array.get(0).toString();
            String end = array.get(length - 1).toString();

            int commaIndex = start.indexOf(",");
            BigDecimal lonStart = new BigDecimal(start.substring(1, commaIndex));
            BigDecimal latStart = new BigDecimal(start.substring(commaIndex + 1, start.length() - 1));

            commaIndex = end.indexOf(",");
            BigDecimal lonEnd = new BigDecimal(end.substring(1, commaIndex));
            BigDecimal latEnd = new BigDecimal(end.substring(commaIndex + 1, end.length() - 1));

            m.put("lonStart", lonStart);
            m.put("latStart", latStart);
            m.put("lonEnd", lonEnd);
            m.put("latEnd", latEnd);

            String congestionIndex = String.valueOf(m.get("congestionIndex"));

            BigDecimal b = new BigDecimal(congestionIndex);
            int congestionLevel = 1;
            if (b.compareTo(new BigDecimal(6)) == 1) {
                congestionLevel = 2;
            }
            m.put("congestionLevel", congestionLevel);

        }

        mapper.batchInsertWarnCongestion(sectionList);
    }

    /**
     * @param env 环境
     *            将大数据那边给的一份道路信息csv文件 更新到road_section_run_state
     */
    private static void updateRoadSectionRunState(String env) throws Exception {

        // 1. 将数据装入 list
        String filePath = "D:\\Download\\DingDing\\城市道路指数.csv";

        List<Map<String, String>> list = new ArrayList<>();

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));
        String str;

        Map<String, String> uuidMap = new HashMap<>();

        while (null != (str = br.readLine())) {

            String[] array = str.split(",");

            Map<String, String> map = new HashMap<>();

            String uuid = array[2];
            String congestionIndex = array[4];
            String speed = array[5];

            map.put("uuid", uuid);
            map.put("congestionIndex", congestionIndex);
            map.put("speed", speed);

            list.add(map);

            uuidMap.put(uuid, congestionIndex + "," + speed);
        }

        SqlSession session = getSession(env);

        ColumnDao mapper = session.getMapper(ColumnDao.class);

        // 根据uuid获取到路段信息
        List<Map<String, Object>> sectionList = mapper.getRoadSectionByUuidList(list);

        for (Map m : sectionList) {
            String uuid = m.get("uuid").toString();

            String tempStr = uuidMap.get(uuid);

            String[] arr = tempStr.split(",");

            BigDecimal congestionIndex = new BigDecimal(arr[0]);

            BigDecimal speed = new BigDecimal(arr[1]);

            m.put("congestionIndex", congestionIndex);

            m.put("speed", speed);

            mapper.updateSectionRunStateById(m);
        }
    }

    /**
     * 获取sql session
     *
     * @param env
     * @return
     * @throws IOException
     */
    public static SqlSession getSession(String env) throws IOException {
        Reader reader = Resources.getResourceAsReader("mybatisConf/mybatisConf.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader, env);
        // 自动提交 true
        SqlSession session = sessionFactory.openSession(true);
        return session;
    }

    /**
     * 查询某数据库某表的column信息
     *
     * @param env
     * @return
     * @throws IOException
     */
    private static Object getColumnInfo(String env) throws IOException {
        SqlSession session = getSession(env);
        ColumnDao mapper = session.getMapper(ColumnDao.class);
        Map<String, String> param = new HashMap<>(2);
        param.put("database", DATABASE);
        param.put("table", TABLE);
        List<Map<String, String>> reuslt = mapper.getColumnInfo(param);
        return reuslt;
    }

    /**
     * 根据column信息打印 mybatis可能用到的代码
     *
     * @param list
     */
    private static void printResultMapAndColumns(List<Map<String, String>> list) {
        StringBuilder resultMap = new StringBuilder("<resultMap id=\"baseMap\" type=\"\">");
        StringBuilder columnSql = new StringBuilder("<sql id=\"baseColumn\">\n\t");
        StringBuilder properties = new StringBuilder("");
        StringBuilder selectAsSql = new StringBuilder("");
        StringBuilder insertColumnSql = new StringBuilder("");
        StringBuilder insertPropertySql = new StringBuilder("");
        StringBuilder updateSql = new StringBuilder("");

        System.out.println("list : " + list);

        String cn = "column_name";
        String ct = "column_type";

        if(null != list && !list.isEmpty()){
            Map<String,String> m = list.get(0);
            Set<String> set = m.keySet();

            for(String s : set){
                if(s.equalsIgnoreCase(cn)){
                    cn = s;
                }else {
                    ct = s;
                }
            }
        }

        final String columnName = cn;
        final String columnType = ct;

        list.forEach(e -> {


            String column = e.get(columnName);
            String type = e.get(columnType);
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
            return "java.lang.Boolean";
        }else if(type.startsWith("datetime") || type.startsWith("date")){
            return "java.util.Date";
        }else if(type.contains("char")){
            return "java.lang.String";
        }
        return "java.lang.String";
    }
}

