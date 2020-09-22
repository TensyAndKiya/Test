package com.clei.utils.other;

import com.clei.Y2020.M09.D17.RoadObject;

import java.util.List;
import java.util.Map;

public interface ColumnDao {

    List<Map<String, String>> getColumnInfo(Map<String, String> map);

    /**
     * 批量插入roadInfo
     * @param list
     */
    void batchInsertRoadInfo(List<RoadObject> list);

    /**
     * 批量插入roadSectionInfo
     * @param list
     */
    void batchInsertRoadSectionInfo(List<RoadObject> list);

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
}
