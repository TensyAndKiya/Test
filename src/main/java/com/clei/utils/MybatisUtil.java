package com.clei.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.clei.Y2020.M09.D17.RoadObject;
import com.clei.Y2020.M09.D22.Checkpoint;
import com.clei.Y2020.M09.D22.CongestionForcast;
import com.clei.Y2020.M09.D22.CongestionHourForcat;
import com.clei.Y2020.M09.D22.CongestionTop;
import com.clei.Y2020.M09.D22.DateToDate;
import com.clei.Y2020.M09.D22.SectionInfo;
import com.clei.Y2020.M09.D22.SectionPoint;
import com.clei.Y2020.M09.D22.SectionRunState;
import com.clei.utils.other.ColumnDao;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.BeanUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 快速获取某表的 ResultMap信息，以及所有的字段名信息
 *
 * @author KIyA
 * @since 2019-10-17
 */
public class MybatisUtil {

    private final static String ENV = "test";
    private final static String DATABASE = "hdsp_itms";
    private final static String TABLE = "road_section_info";
    private final static char UNDERLINE = '_';
    private final static Map<String, String> javaTypeMap = new HashMap<>();
    private static boolean javaTypeMapInit = false;

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

        // ---------------Road Begin---------------

        // insertRoadInfo(env);

        // updateOneWayAdnLaneNumber(env);

        // insertRandomCheckpoint(env);

        // insertTempSectionRunState(env);

        // updateAllData(env, DateUtil.currentDateTime(), true, LocalDate.now(), new int[]{1,1,1,1,1,1,1,1});

        // updateRoadSectionRunState(env);

        // insertRoadSectionRunStateInfo(env);

        // insertCheckpoint(env);

        // ---------------Road End---------------

        // ---------------Vehicle Begin---------------

        // PrintUtil.printMemoryInfo();

        // insertVehicleState(env);

        printResultMapAndColumns(env);

        // ---------------Vehicle End---------------
    }


    /**
     * @param env 环境
     *            将大数据那边给的一份道路信息csv文件 插入到mysql数据库里
     */
    private static void insertRoadInfo(String env) throws Exception {

        Map<String, String> directionMap = new HashMap<>(8);
        directionMap.put("1", "南向北");
        directionMap.put("2", "西南向东北");
        directionMap.put("3", "西向东");
        directionMap.put("4", "西北向东南");
        directionMap.put("5", "北向南");
        directionMap.put("6", "东北向西南");
        directionMap.put("7", "东向西");
        directionMap.put("8", "东南向西北");

        // 1. 将数据装入 list
        String filePath = "D:\\Download\\DingDing\\城市道路基础信息.csv";

        LinkedHashMap<String, List<RoadObject>> map = new LinkedHashMap<>();

        List<RoadObject> sectionList = new ArrayList<>();

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));
        String str;

        while (null != (str = br.readLine())) {

            String[] array = str.split("\u0001");
            int length = array.length;

/*            PrintUtil.dateLine(array.length);
            PrintUtil.dateLine(Arrays.toString(array));*/

            String roadName = array[3];

            RoadObject obj = new RoadObject();
            obj.setRoadCode(array[0]);
            obj.setRoadName(roadName);
            obj.setStartLocation(array[4]);
            obj.setEndLocation(array[5]);
            obj.setLength(new BigDecimal(array[length - 3]));
            // 数字方向转汉字
            obj.setDirection(directionMap.get(array[length - 4]));
            // centerPoint:[106.630610,26.684490]
            String centerPoint = array[length - 2];
            int commaIndex = centerPoint.indexOf(",");

            obj.setCenterLon(new BigDecimal(centerPoint.substring(1, commaIndex)));
            obj.setCenterLat(new BigDecimal(centerPoint.substring(commaIndex + 1, centerPoint.length() - 1)));
            obj.setGeo(array[length - 1]);

            // 道路类型
            String type = array[2];
            Integer roadType = 1;

            char c = roadName.charAt(0);
            if (c == 'G' || c == 'g' || c == 'S' || c == 's') {
                roadType = 3;
            } else {
                if (type.equals("1") || type.equals("2")) {
                    roadType = 2;
                }
            }
            obj.setRoadType(roadType);

            // 放入map
            List<RoadObject> tempSectionList = map.get(obj.getRoadName());
            if (tempSectionList == null) {
                tempSectionList = new ArrayList<>(1);
            }
            tempSectionList.add(obj);
            map.put(obj.getRoadName(), tempSectionList);

            // 放入list
            sectionList.add(obj);
        }

        // 排序
        sectionList = sectionList.stream().sorted(Comparator.comparing(RoadObject::getRoadCode)).collect(Collectors.toList());

        List<RoadObject> roadList = new ArrayList<>();

        for (List<RoadObject> l : map.values()) {
            RoadObject ro = new RoadObject();
            BeanUtils.copyProperties(l.get(0), ro);
            roadList.add(ro);
            if (1 < l.size()) {
                // 设置道路长度为路段里最长的那个长度
                for (RoadObject r : l) {
                    ro.setLength(ro.getLength().compareTo(r.getLength()) == 1 ? ro.getLength() : r.getLength());
                }
            }
        }

        // 排序
        roadList = roadList.stream().sorted(Comparator.comparing(RoadObject::getRoadCode)).collect(Collectors.toList());

        // 插入道路信息
        SqlSession session = getSession(env);

        ColumnDao mapper = session.getMapper(ColumnDao.class);

        // 清空原有数据
        String truncateTableName = "road_info";
        mapper.truncateTable(truncateTableName);
        truncateTableName = "road_section_info";
        mapper.truncateTable(truncateTableName);
        truncateTableName = "road_section_node_coordinate";
        mapper.truncateTable(truncateTableName);

        // 插入到表并获取到roadId
        mapper.batchInsertRoadInfo(roadList);
        PrintUtil.dateLine("批量插入道路数据成功 size : " + roadList.size());

        // 给所有路段设置roadId
        for (RoadObject obj : roadList) {
            String name = obj.getRoadName();
            Long roadId = obj.getRoadId();

            List<RoadObject> tempSectionList = map.get(name);

            for (RoadObject ro : tempSectionList) {
                ro.setRoadId(roadId);
            }
        }

        // 插入到表并获取到roadSectionId
        mapper.batchInsertRoadSectionInfo(sectionList);
        PrintUtil.dateLine("批量插入路段数据成功 size : " + sectionList.size());

        // 插入到表
        mapper.batchInsertRoadSectionNode(sectionList);
        PrintUtil.dateLine("批量插入路段节点数据成功 size : " + sectionList.size());

        br.close();

        session.close();
    }

    /**
     * 修改道路的单双向和车道数
     *
     * @param env
     * @throws Exception
     */
    private static void updateOneWayAdnLaneNumber(String env) throws Exception {

        SqlSession session = getSession(env);
        ColumnDao mapper = session.getMapper(ColumnDao.class);

        // 1. 获取到单向的道路和路段
        List<Map<String, Long>> roadSectionList = mapper.getTwoWayRoadSection();

        // 2. 修改道路和路段的单向和车道
        mapper.updateRoadOneWay(roadSectionList);
        mapper.updateRoadSectionOneWay(roadSectionList);
        // 高/快速路 & 普通国省干线 -> 单向4车道
        Map<String, Integer> param = new HashMap<>(4);
        param.put("roadType", 2);
        param.put("oneWay", 1);
        param.put("laneNumber", 4);
        mapper.updateRoadLaneNumber(param);
        mapper.updateRoadSectionLaneNumber(param);
        param.put("roadType", 3);
        mapper.updateRoadLaneNumber(param);
        mapper.updateRoadSectionLaneNumber(param);
        // 高/快速路 & 普通国省干线 -> 双向8车道
        param.put("oneWay", 0);
        param.put("laneNumber", 8);
        mapper.updateRoadLaneNumber(param);
        mapper.updateRoadSectionLaneNumber(param);
        param.put("roadType", 2);
        mapper.updateRoadLaneNumber(param);
        mapper.updateRoadSectionLaneNumber(param);
        // 城市道路 -> 双向六车道
        param.put("roadType", 1);
        param.put("oneWay", 0);
        param.put("laneNumber", 6);
        mapper.updateRoadLaneNumber(param);
        mapper.updateRoadSectionLaneNumber(param);

        session.close();

        PrintUtil.dateLine("更新道路路段单双向及车道数成功");
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

        session.close();
    }

    /**
     * 有个文件夹里是某天的全路段的拥堵数据
     * 里面多个文件，内容格式一致
     * 根据日期存到表里
     *
     * @param env
     * @throws Exception
     */
    private static void insertTempSectionRunState(String env) throws Exception {
        SqlSession session = getSession(env);

        ColumnDao mapper = session.getMapper(ColumnDao.class);

        String[] tableDateArr = {"2020-09-02", "2020-09-03", "2020-09-04", "2020-09-05", "2020-09-06", "2020-09-07", "2020-09-08"};

        for (String tableDate : tableDateArr) {

            // sectionId 与 uuid 对应list
            List<SectionInfo> sectionInfoList = mapper.getSectionInfo();

            // uuid - sectionId map
            Map<String, Long> uuidMap = new HashMap<>(sectionInfoList.size());
            // roadSectionId dateTime set
            Set<String> set = new HashSet<>();
            for (SectionInfo s : sectionInfoList) {
                uuidMap.put(s.getUuid(), s.getRoadSectionId());
            }

            // 全部数据 看了一个日期的文件，数据量大概在200w左右
            List<SectionRunState> origin = new ArrayList<>(2000000);

            // 从文件读取数据
            String filePath = "D:\\Download\\DingDing\\cityRoadIndex\\" + tableDate;
            File file = new File(filePath);
            File[] files = file.listFiles();

            int i = 0;
            int j = 0;
            int k = 0;
            for (File f : files) {
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF-8"));
                String str;
                while (null != (str = br.readLine())) {
                    i++;
                    String[] arr = str.split("\u0001");

                    String uuid = arr[2];
                    // 找不到路段id则丢弃
                    Long roadSectionId = uuidMap.get(uuid);
                    if (roadSectionId == null) {
                        j++;
                        continue;
                    }

                    String dateTime = arr[arr.length - 3];
                    // 路段 时间已存在 丢弃
                    String uniqueKey = roadSectionId + dateTime;
                    if (set.contains(uniqueKey)) {
                        k++;
                        continue;
                    }

                    set.add(uniqueKey);

                    SectionRunState obj = new SectionRunState();
                    obj.setUuid(uuid);
                    obj.setCongestionIndex(new BigDecimal(arr[4]));
                    obj.setSpeed(new BigDecimal(arr[5]));
                    obj.setDateTime(dateTime);
                    obj.setRoadSectionId(roadSectionId);

                    // 添加到list
                    origin.add(obj);
                }
                br.close();
            }

            PrintUtil.dateLine("数据日期： " + tableDate);
            PrintUtil.dateLine("实际数据条数： " + i);
            PrintUtil.dateLine("找不到路段id： " + j);
            PrintUtil.dateLine("路段时间重复： " + k);
            PrintUtil.dateLine("可用数据条数： " + origin.size());

            String tableName = "road_section_run_state_" + tableDate.replaceAll("-", "");
            ;

            // 切割并插入
            cutAndInsert(origin, 0, 1, tableName, mapper);

            // mapper.batchInsertTempSectionRunState(origin,tableName);

        }
        session.close();
    }

    /**
     * 一个比较糙的卡口数据文件
     * 插入到表中
     *
     * @param env
     * @throws Exception
     */
    private static void insertCheckpoint(String env) throws Exception {
        Map<String, String> directionMap = new HashMap<>(8);
        directionMap.put("1", "南向北");
        directionMap.put("2", "西南向东北");
        directionMap.put("3", "西向东");
        directionMap.put("4", "西北向东南");
        directionMap.put("5", "北向南");
        directionMap.put("6", "东北向西南");
        directionMap.put("7", "东向西");
        directionMap.put("8", "东南向西北");

        SqlSession session = getSession(env);

        ColumnDao mapper = session.getMapper(ColumnDao.class);

        // sectionId 与 sectionName
        List<SectionInfo> sectionInfoList = mapper.getSectionInfo();

        Map<String, List<SectionInfo>> sectionMap = new HashMap<>(sectionInfoList.size());

        for (SectionInfo s : sectionInfoList) {

            // 一个路段名 多个 路段用
            String roadSectionName = s.getRoadSectionName();
            List<SectionInfo> tempList = sectionMap.get(roadSectionName);
            if (null == tempList) {
                tempList = new ArrayList<>(1);
            }
            tempList.add(s);
            sectionMap.put(roadSectionName, tempList);
        }

        // 全部数据
        List<Checkpoint> origin = new ArrayList<>(1600);

        // 从文件读取数据
        String filePath = "D:\\Download\\DingDing\\咔咔咔咔咔咔卡口.csv";
        File file = new File(filePath);

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
        String str;
        while (null != (str = br.readLine())) {

            String[] arr = str.split(",");

            String longitude = arr[4];
            String latitude = arr[5];

            // 经度或纬度或方向为空则丢弃
            if (StringUtil.isEmpty(longitude) || StringUtil.isEmpty(latitude) || arr.length != 10 || StringUtil.isEmpty(arr[7])) {
                continue;
            }

            String roadName = arr[9];
            List<SectionInfo> tempList = sectionMap.get(roadName);

            if (null == tempList) {
                continue;
            }

            BigDecimal lon, lat;

            try {
                lon = new BigDecimal(longitude);
                lat = new BigDecimal(latitude);
            } catch (Exception e) {
                continue;
            }

            Long roadSectionId = getRoadSectionIdByLonLat(tempList, lon, lat);

            if (null != roadSectionId && arr.length > 7) {
                Checkpoint obj = new Checkpoint();
                obj.setRoadSectionId(roadSectionId);
                obj.setCheckpointName(arr[3]);
                obj.setLon(lon);
                obj.setLat(lat);
                obj.setStatus("1".equals(arr[6]) ? 1 : 0);

                obj.setDirection(directionMap.get(arr[7]));

                // 添加到list
                origin.add(obj);
            }
        }
        br.close();

        PrintUtil.dateLine("数据条数： " + origin.size());

        // 批量插入
        mapper.batchInsertCheckpoint(origin);
        mapper.batchInsertSectionCheckpointRel(origin);

        session.close();
    }

    /**
     * 根据路段点数随机插入n个卡口
     *
     * @param env
     * @throws Exception
     */
    private static void insertRandomCheckpoint(String env) throws Exception {

        String[] numberArr = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九",};

        // 随机100个左右
        int checkpointCount = 100;

        List<Checkpoint> checkpointList = new ArrayList<>(checkpointCount);

        SqlSession session = getSession(env);

        ColumnDao mapper = session.getMapper(ColumnDao.class);

        // sectionId 与 sectionName
        List<SectionInfo> sectionInfoList = mapper.getSectionInfo();

        int pointCount = 0;

        for (SectionInfo s : sectionInfoList) {
            String geo = s.getGeo();

            List<String> geoList = JSONObject.parseArray(geo, String.class);

            s.setGeoList(geoList);

            pointCount += geoList.size();

        }

        Random checkpointRandom = new Random();
        Random statusRandom = new Random();

        // 路段名 卡口数 count
        Map<String, Integer> sectionCheckpointCountMap = new HashMap<>(64);

        for (SectionInfo s : sectionInfoList) {

            List<String> geoList = s.getGeoList();

            String roadSectionName = s.getRoadSectionName();

            for (int i = 0; i < geoList.size(); i++) {

                String str = geoList.get(i);

                int cr = checkpointRandom.nextInt(pointCount);
                // 满足概率 checkpointCount/pointCount

                if (cr < checkpointCount) {

                    // 经纬度
                    int index = str.indexOf(",");
                    String longitude = str.substring(1, index);
                    String latitude = str.substring(index + 1, str.length() - 1);

                    // 卡口状态 2/3 几率好 1/3 几率坏
                    Integer status = statusRandom.nextInt(3) > 0 ? 1 : 0;

                    // 根据路段名取卡口名
                    Integer sectionCheckpointCount = sectionCheckpointCountMap.get(roadSectionName);
                    if (null == sectionCheckpointCount) {
                        sectionCheckpointCount = 1;
                    } else {
                        sectionCheckpointCount++;
                    }
                    sectionCheckpointCountMap.put(roadSectionName, sectionCheckpointCount);
                    String checkpointName = roadSectionName + numberArr[sectionCheckpointCount];

                    Checkpoint checkpoint = new Checkpoint();
                    checkpoint.setRoadSectionId(s.getRoadSectionId());
                    checkpoint.setCheckpointName(checkpointName);
                    checkpoint.setDirection(s.getDirection());
                    checkpoint.setLon(new BigDecimal(longitude));
                    checkpoint.setLat(new BigDecimal(latitude));
                    checkpoint.setStatus(status);

                    checkpointList.add(checkpoint);

                    // 剩余卡口数减少
                    checkpointCount--;

                    // 字母开头的道路减少其卡口数量
                    char c = roadSectionName.charAt(0);
                    if (c == 'C' || c == 'G' || c == 'S' || c == 'X' || c == 'Y') {
                        pointCount = pointCount - (geoList.size() - i);
                        break;
                    }

                }
                // 剩余点数减少
                pointCount--;
            }
        }

        PrintUtil.dateLine("real checkpoint size : " + checkpointList.size());

        // 清空原有数据
        String truncateTableName = "road_checkpoint";
        mapper.truncateTable(truncateTableName);
        truncateTableName = "road_section_checkpoint_rel";
        mapper.truncateTable(truncateTableName);

        // 批量插入
        mapper.batchInsertCheckpoint(checkpointList);
        mapper.batchInsertSectionCheckpointRel(checkpointList);

        PrintUtil.dateLine("随机批量插入卡口成功");

        session.close();
    }

    /**
     * 更新路网实时数据
     *
     * @param env              数据库环境
     * @param dateTime         要取的数据时间
     * @param useSameDayOfWeek 是否使用同一个周几的数据
     * @param toDate           设置为哪一天的数据
     * @param methods          要执行的方法
     */
    private static void updateAllData(String env, String dateTime, boolean useSameDayOfWeek, LocalDate toDate, int[] methods) throws Exception {

        // 啥都不执行
        if (null == methods || 0 == methods.length) {
            return;
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String[] tableDateArr = {"", "2020-09-07", "2020-09-08", "2020-09-02", "2020-09-03", "2020-09-04", "2020-09-05", "2020-09-06"};

        // 使用同一个dayOfWeek的数据
        if (useSameDayOfWeek) {
            int dayOfWeek = LocalDateTime.parse(dateTime, dtf).getDayOfWeek().getValue();
            dateTime = tableDateArr[dayOfWeek] + dateTime.substring(10);
        }

        // 去除秒数
        dateTime = dateTime.substring(0, 17) + "00";
        String date = dateTime.substring(0, 10);
        String tableName = "road_section_run_state_" + date.replaceAll("-", "");

        LocalDateTime dt = LocalDateTime.parse(dateTime, dtf);

        SqlSession session = getSession(env);
        ColumnDao mapper = session.getMapper(ColumnDao.class);

        // 方法计数器
        int methodCounter = 0;

        // 第一步 更新section_run_state_all
        if (1 == methods[methodCounter++]) {

            // 1. 清空数据
            String truncateTableName = "road_real_time_section_run_state_all";
            mapper.truncateTable(truncateTableName);
            // 2. 插入数据
            Integer result = mapper.batchInsertRoadSectionInfoByTable(tableName, dateTime);
            // 若没有数据则加一分钟重新插入
            if (result < 1) {
                updateAllData(env, dt.plusMinutes(1).format(dtf), useSameDayOfWeek, toDate, methods);
                PrintUtil.dateLine(dateTime + " 道路运行状态数据插入失败");
                // 做完了，返回
                return;
            } else {
                PrintUtil.dateLine(dateTime + " 道路运行状态数据插入成功");
            }
        }

        // 第二步 更新congestion_index
        if (1 == methods[methodCounter++]) {

            mapper.updateCongestionIndex();
            PrintUtil.dateLine("更新congestionIndex成功");
        }

        // 第三步 更新speed
        if (1 == methods[methodCounter++]) {

            // 1. 清空数据
            String truncateTableName = "road_real_time_road_speed";
            mapper.truncateTable(truncateTableName);
            // 2. 插入数据
            mapper.insertRoadSpeed();
            PrintUtil.dateLine("更新speed成功");
        }

        // 第四步 更新mileage
        if (1 == methods[methodCounter++]) {

            // 1. 清空数据
            String truncateTableName = "road_real_time_congestion_mileage";
            mapper.truncateTable(truncateTableName);
            // 2. 插入数据
            mapper.insertCongestionMileage();
            PrintUtil.dateLine("更新congestionMileage成功");
        }

        // 第五步 更新congestionTop
        if (1 == methods[methodCounter++]) {

            // 1. 清空数据
            String truncateTableName = "road_real_time_congestion_top";
            mapper.truncateTable(truncateTableName);
            // 2. 获取实时拥堵前10
            List<CongestionTop> congestionTopList = mapper.getCongestionTopList();
            // 3. 更改数据并插入
            String[] congestionArr = {"", "畅通", "基本畅通", "轻度拥堵", "中度拥堵", "重度拥堵"};
            // String[] directionArr = {"","南向北","西南向东北","西向东","西北向东南","北向南","东北向西南","东向西","东南向西北"};

            int i = 0;
            for (CongestionTop c : congestionTopList) {
                String startLocation = c.getStartLocation();
                String endLocation = c.getEndLocation();

                // 拥堵描述
                StringBuilder sb = new StringBuilder();
                if (StringUtil.isNotEmpty(startLocation)) {
                    sb.append("从");
                    sb.append(startLocation);
                    if (StringUtil.isNotEmpty(endLocation)) {
                        sb.append("到");
                        sb.append(endLocation);
                    } else {
                        sb.append("起");
                    }
                } else {
                    if (StringUtil.isNotEmpty(endLocation)) {
                        sb.append("到");
                        sb.append(endLocation);
                        sb.append("止");
                    }
                }
                sb.append(' ');
                sb.append(c.getDirection());
                sb.append(' ');
                sb.append(congestionArr[c.getCongestionType()]);
                sb.append(' ');

                String mileage = 1 == BigDecimal.ONE.compareTo(c.getLength()) ? c.getLength().multiply(new BigDecimal(1000)).intValue() + "m" : c.getLength().toString() + "km";
                sb.append(mileage);
                c.setCongestionDescription(sb.toString());
                // 排名
                c.setRank(++i);
            }
            mapper.batchInsertCongestionTop10(congestionTopList);
            PrintUtil.dateLine("更新congestionTop成功");
        }

        // 第六步 更新warnCongestion
        if (1 == methods[methodCounter++]) {

            // 1. 获取原来的拥堵预警信息
            List<Map<String, Object>> congestionList = mapper.getWarnCongestionList();

            // 拥堵路段id - startTime map
            Map<String, String> congestionMap = new HashMap<>();

            for (Map<String, Object> m : congestionList) {
                String roadSectionId = String.valueOf(m.get("roadSectionId"));
                Object startTime = m.get("startTime");
                if (null != startTime && 0 != startTime.toString().length()) {
                    congestionMap.put(roadSectionId, startTime.toString());
                }
            }

            // 2. 获取符合条件的交通拥堵/瘫痪路段信息
            List<Map<String, Object>> sectionList = mapper.getCongestionSectionList();

            String[] numberArr = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九",};

            for (Map<String, Object> m : sectionList) {

                String roadSectionName = String.valueOf(m.get("roadSectionName"));

                String congestionLevel = String.valueOf(m.get("congestionLevel"));

                m.put("name", "1".equals(congestionLevel) ? "交通拥堵" : "交通瘫痪");
                m.put("address", roadSectionName);

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

                Boolean oneWay = Boolean.valueOf(String.valueOf(m.get("oneWay")));
                Integer laneNumber = Integer.parseInt(String.valueOf(m.get("laneNumber")));
                String laneConfig = (oneWay ? "单向" : "双向") + numberArr[laneNumber] + "车道";

                m.put("laneConfig", laneConfig);

                String roadSectionId = String.valueOf(m.get("roadSectionId"));
                String startTime = congestionMap.get(roadSectionId);
                if (StringUtil.isNotEmpty(startTime)) {
                    m.put("startTime", startTime);
                    m.put("congestionTime", LocalDateTime.parse(startTime, dtf).until(LocalDateTime.now(), ChronoUnit.SECONDS));
                } else {
                    m.put("startTime", LocalDateTime.now().format(dtf));
                    m.put("congestionTime", 0);
                }
            }

            // 3. 清空原有数据
            String truncateTableName = "road_warn_congestion";
            mapper.truncateTable(truncateTableName);

            // 4. 插入数据
            mapper.batchInsertWarnCongestion(sectionList);
            PrintUtil.dateLine("更新warnCongestion成功");
        }

        // 第七步 更新report_hour_road_run_state
        if (1 == methods[methodCounter++]) {

            // 1. 获取源数据
            List<Map<String, Object>> sectionRunStateList = mapper.getRoadSectionRunStateByTable(tableName, dt.plusMinutes(1).format(dtf));
            PrintUtil.dateLine("查询到sectionRunState 数据 size : " + sectionRunStateList.size());
            // 修改数据格式
            String dateDay = toDate.format(dateFormatter);
            for (Map<String, Object> m : sectionRunStateList) {
                String dataTime = String.valueOf(m.get("dataTime"));
                int hour = Integer.parseInt(dataTime.substring(11, 13));
                int minute = Integer.parseInt(dataTime.substring(14, 16));
                m.put("date", Integer.parseInt(dateDay.replaceAll("-", "")));
                m.put("hour", hour);
                m.put("minute", minute);
            }

            // 2. 清空原有数据
            String truncateTableName = "road_report_hour_road_run_state";
            mapper.truncateTable(truncateTableName);

            // 3. 插入数据
            cutAndInsert(sectionRunStateList, 0, 2, "", mapper);
            PrintUtil.dateLine("更新report_hour_road_run_state成功");
        }

        // 第八步 更新report_hour_congestion_forcast
        if (1 == methods[methodCounter++]) {

            int dayOfWeek = toDate.getDayOfWeek().getValue() + 1;

            // 1. 判断当前数据的日期
            List<Integer> dateDayList = mapper.getHourCongestionForcastDateDay();
            // 要求是连续的日期数，这里不好判断，省略了
            if (null != dateDayList && 7 == dateDayList.size()) {

                List<LocalDate> tempDateDayList = new ArrayList<>(7);
                for (int j = 0; j < 7; j++) {
                    LocalDate tempDateDay = toDate.plusDays(1 + j);
                    tempDateDayList.add(tempDateDay);
                }

                Integer firstDate = dateDayList.get(0);
                long diff = LocalDate.of(firstDate / 10000, firstDate / 100 % 100, firstDate % 100).until(tempDateDayList.get(0), ChronoUnit.DAYS);

                // 设置源日期与目标日期对应关系，这里的规则保证了源于目标的日期的dayOfWeek是相等的
                List<DateToDate> dateToDateList = new ArrayList<>(7);
                for (int j = 0; j < 7; j++) {

                    int sourceIndex = (int) (j + diff);
                    sourceIndex = sourceIndex % 7;
                    sourceIndex = sourceIndex < 0 ? sourceIndex + 7 : sourceIndex;

                    Integer ii = dateDayList.get(sourceIndex);
                    Integer jj = Integer.parseInt(tempDateDayList.get(j).format(dateFormatter).replaceAll("-", ""));
                    if (ii.equals(jj)) {
                        continue;
                    }
                    dateToDateList.add(new DateToDate(ii, jj));
                }

                if (dateToDateList.size() > 0) {
                    for (DateToDate d : dateToDateList) {
                        mapper.updateReportForcastDate(d);
                        PrintUtil.dateLine("更新" + d.getSource() + " -> " + d.getTarget() + "预测拥堵数据成功");
                    }
                } else {
                    PrintUtil.dateLine("无需更新预测拥堵数据");
                }

            } else {

                // 2. 清空原有数据
                String truncateTableName = "road_report_hour_congestion_forcast";
                mapper.truncateTable(truncateTableName);

                // 3. 获取源数据并插入
                // 未来一周
                for (int j = 0; j < 7; j++) {

                    int index = dayOfWeek + j;
                    index = index > 7 ? index - 7 : index;

                    String tbName = "road_section_run_state_" + tableDateArr[index].replaceAll("-", "");

                    List<Map<String, Object>> allRunStateList = mapper.getAllRoadSectionRunStateByTable(tbName);

                    String dateStr = toDate.plusDays(1 + j).format(dateFormatter);

                    Integer tempDay = Integer.parseInt(dateStr.replaceAll("-", ""));

                    Map<String, CongestionForcast> sectionCongestionForcastMap = new HashMap<>();

                    for (Map<String, Object> m : allRunStateList) {

                        String roadSectionId = String.valueOf(m.get("roadSectionId"));

                        CongestionForcast congestionForcast = sectionCongestionForcastMap.get(roadSectionId);

                        if (null == congestionForcast) {
                            congestionForcast = new CongestionForcast();
                            BigDecimal[] hourCongestionIndexSum = congestionForcast.getHourCongestionIndexSum();
                            int[] hourRecordCount = congestionForcast.getHourRecordCount();
                            // 数组初始化
                            for (int k = 0; k < 24; k++) {
                                hourCongestionIndexSum[k] = BigDecimal.ZERO;
                                hourRecordCount[k] = 0;
                            }

                            // 放入map
                            sectionCongestionForcastMap.put(roadSectionId, congestionForcast);

                            // 设置值
                            congestionForcast.setDate(tempDay);
                            congestionForcast.setRoadSectionId(Long.parseLong(roadSectionId));
                            congestionForcast.setRoadId(Long.parseLong(String.valueOf(m.get("roadId"))));
                        }
                        BigDecimal[] hourCongestionIndexSum = congestionForcast.getHourCongestionIndexSum();
                        int[] hourRecordCount = congestionForcast.getHourRecordCount();

                        String dateTimeStr = String.valueOf(m.get("dataTime"));

                        int tempDayHour = Integer.parseInt(dateTimeStr.substring(11, 13));

                        BigDecimal congestionIndex = new BigDecimal(String.valueOf(m.get("congestionIndex")));

                        hourRecordCount[tempDayHour] = hourRecordCount[tempDayHour] + 1;
                        hourCongestionIndexSum[tempDayHour] = hourCongestionIndexSum[tempDayHour].add(congestionIndex);
                    }

                    List<CongestionHourForcat> congestionHourForcatList = new ArrayList<>(sectionCongestionForcastMap.keySet().size() * 24);

                    sectionCongestionForcastMap.forEach((k, v) -> {
                        BigDecimal[] hourCongestionIndexSum = v.getHourCongestionIndexSum();
                        int[] hourRecordCount = v.getHourRecordCount();

                        for (int l = 0; l < 24; l++) {
                            CongestionHourForcat c = new CongestionHourForcat();
                            c.setRoadId(v.getRoadId());
                            c.setRoadSectionId(v.getRoadSectionId());
                            c.setDate(v.getDate());
                            c.setHour(l);

                            int hourCount = hourRecordCount[l];
                            BigDecimal congestionIndex = 0 == hourCount ? BigDecimal.ZERO : hourCongestionIndexSum[l].divide(new BigDecimal(hourRecordCount[l]), 10, RoundingMode.HALF_UP);
                            c.setCongestionIndex(congestionIndex);
                            c.setCongestionType(getCongestionType(congestionIndex));

                            congestionHourForcatList.add(c);
                        }
                    });

                    Comparator<CongestionHourForcat> comparator = (c1, c2) -> {
                        int num = c1.getRoadId().compareTo(c2.getRoadId());
                        if (num == 0) {
                            num = c1.getRoadSectionId().compareTo(c2.getRoadSectionId());
                            if (num == 0) {
                                num = c1.getDate().compareTo(c2.getDate());
                                if (num == 0) {
                                    num = c1.getHour().compareTo(c2.getHour());
                                }
                            }
                        }
                        return num;
                    };

                    // 批量插入
                    if (congestionHourForcatList.size() > 0) {
                        // 排下序
                        Collections.sort(congestionHourForcatList, comparator);
                        mapper.batchInsertReportForcast(congestionHourForcatList);
                        PrintUtil.dateLine("批量插入" + dateStr + "日预测数据成功");
                    } else {
                        PrintUtil.dateLine("批量插入" + dateStr + "日预测数据失败");
                    }
                }
            }
        }

        session.close();
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

        session.close();
    }

    /**
     * 随机插入n条车辆状态
     *
     * @param env 环境
     */
    private static void insertVehicleState(String env) throws Exception {
        // 整这么多条
        int recordCount = 990000;

        SqlSession session = getSession(env);

        ColumnDao mapper = session.getMapper(ColumnDao.class);

        // 查询到所有路段点是为了把车的位置放到路上
        List<SectionPoint> sectionPointList = getAllSectionPoint(mapper);

        int pointSize = sectionPointList.size();

        PrintUtil.dateLine("路段点个数： " + pointSize);

        // 路段点与车的比例 1:n
        int n = 500;

        int tempCount = pointSize / n;

        // 最多5个点分配一个车 避免车辆太过密集
        recordCount = recordCount > tempCount ? tempCount : recordCount;

        // 选中点记录 map
        Map<String, Integer> selectedMap = new HashMap<>(recordCount);

        List<Map<String, Object>> list = new ArrayList<>(recordCount);

        Random random = new Random();

        long millis = System.currentTimeMillis();
        int dayMills = 1000 * 60 * 60 * 24;

        int i = 2;

        PrintUtil.dateLine("批量插入车辆状态数据准备");

        // 记录数判断
        boolean endLoop = false;

        while (!endLoop) {
            // 车辆类型 !endLoop保证 条数达到了立即跳出
            for (int j = 1; j < 8 && !endLoop; j++) {
                // 车牌颜色 !endLoop保证 条数达到了立即跳出
                for (int k = 1; k < 6 && !endLoop; k++, i++, endLoop = i == recordCount + 2) {

                    Map<String, Object> m = new HashMap<>();

                    String vehicleNo = getVehicleNo("贵A", i, 6);

                    m.put("vehicleNo", vehicleNo);
                    m.put("vehicleType", j);
                    m.put("plateColor", k);

                    // 危险等级 2/3 安全 2/9 中危 1/9 高危
                    int dangerLevel = random.nextInt(100);
                    dangerLevel = dangerLevel < 67 ? 1 : dangerLevel < 89 ? 2 : 3;

                    m.put("dangerLevel", dangerLevel);
                    m.put("onlineStatus", 1);
                    m.put("routeType", 0);
                    m.put("affiliatedCompany", "hikcreate");
                    m.put("gmtMark", DateUtil.formatMillis(millis + random.nextInt(dayMills)));

                    // 使用重复随机选而不是选中点后remove
                    // 是因为点太多了，频繁获取，remove，花销太大，而这个车与点的比例最多1：n，重复几率也不大
                    // 刚才试了下通过linkedList remove的方式
                    // 代码重构成这样之后数据还没准备完毕
                    // 而采用这个方式 一下就准备完了
                    while (true) {
                        int pointIndex = random.nextInt(sectionPointList.size());
                        SectionPoint sp = sectionPointList.get(pointIndex);

                        String key = sp.getLon().toString() + sp.getLat().toString();
                        Integer result = selectedMap.get(key);

                        if (null == result) {
                            m.put("lon", sp.getLon());
                            m.put("lat", sp.getLat());
                            m.put("markAddress", sp.getRoadSectionName());
                            selectedMap.put(key, 1);
                            break;
                        }

                    }

                    list.add(m);
                }
            }
        }

        PrintUtil.dateLine("批量插入车辆状态开始");

        PrintUtil.dateLine("总数据条数： " + list.size());

        // 批量插入
        // mapper.batchInsertVehicleState(list);

        cutAndInsert(list, 0, 3, "", mapper);

        session.close();

        PrintUtil.dateLine("批量插入车辆状态成功");
    }

    /**
     * 获取所有的路段经纬度点集合
     *
     * @param mapper
     * @return
     */
    private static List<SectionPoint> getAllSectionPoint(ColumnDao mapper) {

        // 全部路段数据
        List<SectionInfo> sectionInfoList = mapper.getSectionInfo();

        // 全部路段点数据 使用linkedList是为了方便remove
        List<SectionPoint> sectionPointList = new ArrayList<>();

        for (SectionInfo s : sectionInfoList) {
            String geo = s.getGeo();
            String roadSectionName = s.getRoadSectionName();

            List<String> geoList = JSONObject.parseArray(geo, String.class);

            for (String str : geoList) {

                int index = str.indexOf(",");

                String longitude = str.substring(1, index);

                String latitude = str.substring(index + 1, str.length() - 1);

                SectionPoint sectionPoint = new SectionPoint();
                sectionPoint.setRoadSectionName(roadSectionName);
                sectionPoint.setLon(new BigDecimal(longitude));
                sectionPoint.setLat(new BigDecimal(latitude));
                sectionPointList.add(sectionPoint);
            }
        }

        return sectionPointList;
    }

    /**
     * 根据一个点找到最近的路段id
     *
     * @param sectionInfoList
     * @param lon
     * @param lat
     * @return
     */
    private static Long getRoadSectionIdByLonLat(List<SectionInfo> sectionInfoList, BigDecimal lon, BigDecimal lat) {

        // 路段点集合
        List<SectionPoint> sectionPointList = new ArrayList<>(2000);

        for (SectionInfo s : sectionInfoList) {
            String geo = s.getGeo();

            List<String> geoList = JSONObject.parseArray(geo, String.class);

            Long roadSectionId = s.getRoadSectionId();

            for (String str : geoList) {

                int index = str.indexOf(",");

                String longitude = str.substring(1, index);

                String latitude = str.substring(index + 1, str.length() - 1);

                SectionPoint sectionPoint = new SectionPoint();
                sectionPoint.setRoadSectionId(roadSectionId);
                sectionPoint.setLon(new BigDecimal(longitude));
                sectionPoint.setLat(new BigDecimal(latitude));
                sectionPointList.add(sectionPoint);
            }
        }

        double min = 0.01;
        Long roadSectionId = null;

        double EARTH_RADIUS = 6378.137;
        BigDecimal pi = new BigDecimal(Math.PI);
        double temp = pi.divide(new BigDecimal(180), 10, RoundingMode.HALF_UP).doubleValue();

        for (SectionPoint p : sectionPointList) {
            BigDecimal lon1 = p.getLon();
            BigDecimal lat1 = p.getLat();

            double radLat1 = lat1.doubleValue() * temp;
            double radLat2 = lat.doubleValue() * temp;
            double a = radLat1 - radLat2;
            double b = lon1.doubleValue() * temp - lon.doubleValue() * temp;
            double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) +
                    Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
            s = s * EARTH_RADIUS;

            if (s < min) {
                roadSectionId = p.getRoadSectionId();
                min = s;
            }
        }

        return roadSectionId;
    }

    /**
     * 切割批量导入
     *
     * @param list      数据
     * @param n         切割每份多少条
     * @param type      插入类型
     * @param tableName 表名
     * @param mapper    mapper接口
     */
    private static void cutAndInsert(List list, int n, int type, String tableName, ColumnDao mapper) {

        // 默认每次10w条
        int number = 0 == n ? 100000 : n;
        int size = list.size();
        int start = 0;

        while (true) {

            int end = start + number;
            end = end > size ? size : end;
            List l = new ArrayList<>(list.subList(start, end));
            // 批量插入
            switch (type) {
                case 1:
                    mapper.batchInsertTempSectionRunState(l, tableName);
                    break;
                case 2:
                    mapper.batchInsertReportHour(l);
                    break;
                case 3:
                    mapper.batchInsertVehicleState(l);
                    break;
                default:
                    PrintUtil.dateLine("未知插入类型");
                    return;
            }

            PrintUtil.dateLine("已经插入数据条数： " + end);

            if (end == size) {
                break;
            }
            start = end;

        }
    }

    /**
     * 通过congestionIndex 返回 congestionType
     *
     * @param congestionIndex
     * @return
     */
    private static Integer getCongestionType(BigDecimal congestionIndex) {

        int congestionType = congestionIndex.subtract(BigDecimal.ONE).divide(new BigDecimal(0.3), 18, RoundingMode.CEILING).intValue();

        congestionType = congestionType < 1 ? 1 : congestionType > 5 ? 5 : congestionType;

        return congestionType;

    }

    /**
     * 获取一个车牌
     *
     * @param prefix 例 川A
     * @param number 例 18
     * @param size   例 5 除了prefix之外的字符串size
     * @return
     */
    private static String getVehicleNo(String prefix, int number, int size) {

        int length = prefix.length() + size;
        StringBuilder sb = new StringBuilder(length);
        sb.append(prefix);

        int diff = size - String.valueOf(number).length();

        if (diff < 0) {
            throw new RuntimeException("number out of bounds");
        }

        while (diff > 0) {
            // 缺的补0
            sb.append('0');
            diff--;
        }

        sb.append(number);

        return sb.toString();
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
     * @throws Exception
     */
    private static List<Map<String, String>> getColumnInfo(String env) throws Exception {
        SqlSession session = getSession(env);
        ColumnDao mapper = session.getMapper(ColumnDao.class);
        Map<String, String> param = new HashMap<>(2);
        param.put("database", DATABASE);
        param.put("table", TABLE);
        List<Map<String, String>> result = mapper.getColumnInfo(param);
        session.close();
        return result;
    }

    /**
     * 根据column信息打印 mybatis可能用到的代码
     *
     * @param env
     */
    private static void printResultMapAndColumns(String env) throws Exception {

        List<Map<String, String>> list = getColumnInfo(env);

        PrintUtil.dateLine("list : " + list);

        StringBuilder resultMap = new StringBuilder("<resultMap id=\"baseMap\" type=\"\">");
        StringBuilder columnSql = new StringBuilder("<sql id=\"baseColumn\">\n");
        StringBuilder properties = new StringBuilder();
        StringBuilder selectAsSql = new StringBuilder();
        StringBuilder insertColumnSql = new StringBuilder();
        StringBuilder insertPropertySql = new StringBuilder();
        StringBuilder updateSql = new StringBuilder();

        String cn = "column_name";
        String ct = "column_type";

        if (null != list && !list.isEmpty()) {
            Map<String, String> m = list.get(0);
            Set<String> set = m.keySet();

            for (String s : set) {
                if (s.equalsIgnoreCase(cn)) {
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
            resultMap.append("\n");
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
            selectAsSql.append(",\n");

            // updateSql
            updateSql.append(column);
            updateSql.append("=#{");
            updateSql.append(property);
            updateSql.append("},\n");

        });
        resultMap.append("\n</resultMap>");
        columnSql.deleteCharAt(columnSql.length() - 1);
        columnSql.append("\n</sql>");

        PrintUtil.dateLine(resultMap.toString());
        PrintUtil.dateLine(columnSql.toString());
        PrintUtil.dateLine(properties.toString());
        PrintUtil.dateLine(selectAsSql.toString());
        PrintUtil.dateLine(insertColumnSql.toString());
        PrintUtil.dateLine(insertPropertySql.toString());
        PrintUtil.dateLine(updateSql.toString());
    }

    private static String getProperty(String column) {

        char[] arr = column.toCharArray();

        StringBuilder sb = new StringBuilder(arr.length);

        boolean underlinePrefix = false;

        for (char c : arr) {
            // 下划线
            if (UNDERLINE == c) {
                underlinePrefix = true;
                continue;
            } else {
                // 小写字母
                if ('a' <= c && 'z' >= c) {
                    // 前面是下划线且不是第一个字母
                    if (underlinePrefix && sb.length() > 0) {
                        // 转大写
                        c = Character.toUpperCase(c);
                    }
                    sb.append(c);
                    // 还原
                    underlinePrefix = false;
                } else {
                    // 其它字符

                    sb.append(c);
                    // 还原可能的下划线前缀
                    underlinePrefix = false;
                }
            }
        }

        return sb.toString();
    }

    private static String getJavaType(String type) {
        // 全转为小写
        type = type.toLowerCase();
        // 去掉后面的括号
        int index = type.indexOf('(');
        if (-1 != index) {
            type = type.substring(0, index);
        }
        // map是否初始化
        if (!javaTypeMapInit) {
            initJavaTypeMap();
        }
        // 结果
        String javaType = javaTypeMap.get(type);
        if (null == javaType) {
            javaType = javaTypeMap.get("default");
        }

        return javaType;
    }

    private static void initJavaTypeMap() {
        // 整数
        javaTypeMap.put("tinyint", "java.lang.Boolean");
        javaTypeMap.put("smallint", "java.lang.Short");
        javaTypeMap.put("int", "java.lang.Integer");
        javaTypeMap.put("bigint", "java.lang.Long");
        // 字符串
        javaTypeMap.put("text", "java.lang.String");
        javaTypeMap.put("char", "java.lang.String");
        javaTypeMap.put("varchar", "java.lang.String");
        javaTypeMap.put("date", "java.lang.String");
        // 小数
        javaTypeMap.put("float", "java.lang.Float");
        javaTypeMap.put("double", "java.lang.Double");
        javaTypeMap.put("decimal", "java.math.BigDecimal");
        // 日期
        javaTypeMap.put("datetime", "java.util.Date");
        javaTypeMap.put("timestamp", "java.util.Date");
        // 默认类型
        javaTypeMap.put("default", "java.lang.String");
        // 初始化完毕
        javaTypeMapInit = true;
    }

    /**
     * 测试mybatis的方法
     *
     * @param env 环境参数
     */
    private static void test(String env) throws Exception {

        /*SqlSession session = getSession(env);

        ColumnDao mapper = session.getMapper(ColumnDao.class);

        session.close();*/
    }
}