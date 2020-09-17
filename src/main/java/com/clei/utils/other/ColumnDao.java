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
     * @param list
     */
    void batchInsertRoadSectionNode(List<RoadObject> list);
}
