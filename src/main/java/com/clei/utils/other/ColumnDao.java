package com.clei.utils.other;

import java.util.List;
import java.util.Map;

public interface ColumnDao {
    List<Map<String,String>> getColumnInfo(Map<String, String> map);
}
