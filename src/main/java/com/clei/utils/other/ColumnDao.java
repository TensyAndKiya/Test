package com.clei.utils.other;

import java.util.List;
import java.util.Map;

public interface ColumnDao {
    List<Map<String,String>> getColumnInfo(Map<String, String> map);

    Integer insertSpace(List<Map<String,Object>> list);

    List<String> selectList();

    Integer updateTime(Map<String,Object> map);

    Integer updateStatus(Map<String,Object> map);

    Integer updateStatus1(Map<String, Object> map);

    Integer updateTimeBack(Map<String,Object> map);

    List<String> select1();

    Integer insertCompanyParkinglot(List<Map<String, Object>> list);

    /**
     * 查询符合要求的停车场id和name
     * @return
     */
    List<Map<String, Object>> getIdName();

    /**
     * 查询车场符合要求的停车记录数
     * @param parkId
     * @return
     */
    Integer getParkingRecordCount(String parkId);
}
