package com.clei.utils.other;

import com.clei.Y2021.M01.D22.Area;
import com.clei.entity.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * mybatis mapper
 *
 * @author KIyA
 */
public interface ColumnDao {

    /**
     * 获取表列信息
     *
     * @param map
     * @return
     */
    List<Map<String, String>> getColumnInfo(Map<String, String> map);

    /**
     * 批量插入roadInfo
     *
     * @param list
     */
    void batchInsertRoadInfo(List<RoadObject> list);

    /**
     * 批量插入roadSectionInfo
     *
     * @param list
     */
    void batchInsertRoadSectionInfo(List<RoadObject> list);

    /**
     * 批量插入roadSectionInfo
     *
     * @param tableName
     * @param dateTime
     */
    Integer batchInsertRoadSectionInfoByTable(@Param("tableName") String tableName, @Param("dateTime") String dateTime);

    /**
     * 批量插入roadSectionNode
     *
     * @param list
     */
    void batchInsertRoadSectionNode(List<RoadObject> list);

    /**
     * 获取id大于给定参数的section数据
     *
     * @param id
     * @return
     */
    List<Map<String, Object>> getSectionList(Integer id);

    /**
     * 批量插入roadSectionRunState
     *
     * @param sectionList
     */
    void batchInserRoadSectionRunState(List<Map<String, Object>> sectionList);

    /**
     * 批量插入tempRoadSectionRunState
     *
     * @param origin
     */
    void batchInsertTempSectionRunState(@Param("origin") List origin, @Param("tableName") String tableName);

    /**
     * 批量插入卡口
     *
     * @param origin
     */
    void batchInsertCheckpoint(List<Checkpoint> origin);

    /**
     * 批量插入路段卡口关系
     *
     * @param origin
     */
    void batchInsertSectionCheckpointRel(List<Checkpoint> origin);

    /**
     * 获取拥堵的的section数据
     *
     * @return
     */
    List<Map<String, Object>> getCongestionSectionList();

    /**
     * 批量插入warnCongestion
     *
     * @param sectionList
     */
    void batchInsertWarnCongestion(List<Map<String, Object>> sectionList);

    /**
     * 批量插入congestionForcast
     *
     * @param congestionHourForcatList
     */
    void batchInsertReportForcast(List<CongestionHourForcat> congestionHourForcatList);

    /**
     * 批量插入vehicleState
     *
     * @param list
     */
    <T> void batchInsertVehicleState(List<T> list);

    /**
     * 根据uuid获取到路段信息
     *
     * @param list
     */
    List<Map<String, Object>> getRoadSectionByUuidList(List<Map<String, String>> list);

    /**
     * 根据roadSectionId更新拥堵指数和速度
     *
     * @param m
     */
    void updateSectionRunStateById(Map m);

    List<SectionInfo> getSectionInfo();

    /**
     * 清空表数据
     *
     * @param tableName
     */
    void truncateTable(@Param("tableName") String tableName);

    /**
     * 更新实时拥堵指数总览
     */
    void updateCongestionIndex();

    /**
     * 插入实时道路运行速度
     */
    void insertRoadSpeed();

    /**
     * 插入实时道路拥堵里程
     */
    void insertCongestionMileage();

    /**
     * 批量插入实时拥堵前10
     *
     * @param congestionTopList
     */
    void batchInsertCongestionTop10(List<CongestionTop> congestionTopList);

    /**
     * 批量插入实时拥堵分析数据
     *
     * @param sectionRunStateList
     */
    void batchInsertReportHour(List sectionRunStateList);

    /**
     * 批量插入行政区划信息
     *
     * @param list 区域信息 list
     */
    void batchInsertArea(List<Area> list);

    /**
     * 获取双向的道路和路段
     *
     * @return
     */
    List<Map<String, Long>> getTwoWayRoadSection();

    /**
     * 查询实时拥堵前10
     *
     * @return
     */
    List<CongestionTop> getCongestionTopList();

    /**
     * 获取拥堵预警信息
     *
     * @return
     */
    List<Map<String, Object>> getWarnCongestionList();

    /**
     * 获取某天的小于某个时间段的全部路段运行数据
     *
     * @param tableName
     * @return
     */
    List<Map<String, Object>> getRoadSectionRunStateByTable(@Param("tableName") String tableName, @Param("dateTime") String dateTime);

    /**
     * 获取某天的全部路段运行数据
     *
     * @param tableName
     * @return
     */
    List<Map<String, Object>> getAllRoadSectionRunStateByTable(@Param("tableName") String tableName);

    /**
     * 获取当前一周预测的日期数据
     *
     * @return
     */
    List<Integer> getHourCongestionForcastDateDay();

    /**
     * 修改道路为单向
     *
     * @param roadSectionList
     */
    void updateRoadOneWay(List<Map<String, Long>> roadSectionList);

    /**
     * 修改路段为单向
     *
     * @param roadSectionList
     */
    void updateRoadSectionOneWay(List<Map<String, Long>> roadSectionList);

    /**
     * 修改道路车道数
     *
     * @param param
     */
    void updateRoadLaneNumber(Map<String, Integer> param);

    /**
     * 修改路段车道数
     *
     * @param param
     */
    void updateRoadSectionLaneNumber(Map<String, Integer> param);

    /**
     * 更新拥堵预测数据日期
     *
     * @param d
     */
    void updateReportForecastData(DateToDate d);

    /**
     * 获取所有区域数据
     *
     * @return
     */
    List<BaseArea> getAllArea();
}
