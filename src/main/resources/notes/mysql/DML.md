# GROUP_CONCAT
```
查询字段组合的字符串(每个值的默认分隔符是逗号,)
SELECT GROUP_CONCAT('\'',id,'\'') FROM cat;
# mysql数据库默认的group_concat_max_len是1024
所以如果select出来的字符串长度超过1024会被切割
可以使用SET GLOBAL group_concat_max_len = 65536;
重启客户端使用SHOW VARIABLES LIKE 'group_concat_max_len';可以看到其值已变化
但mysql服务重启后又会失效
所以可以在my.ini里配置group_concat_max_len = val  # val为你要的最大长度
```

# 优化
```
EXPLAIN EXTENDED
SELECT * FROM temp_orders; # 这里放sql语句
SHOW WARNINGS ;
```

# 查询某数据库中所有表及其记录数(不准确)
```
SELECT table_name,table_rows FROM information_schema.tables
WHERE TABLE_SCHEMA = 'awreporting'
ORDER BY table_rows DESC;
```

# 查询某数据库中有某字段的表
```
SELECT table_name FROM information_schema.columns
WHERE table_schema = 'awreporting' AND column_name LIKE '%AUTH_TOKEN%';
```