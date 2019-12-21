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
}
