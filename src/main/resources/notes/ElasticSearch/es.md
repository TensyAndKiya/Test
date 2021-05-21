# 查询
/index/type/_search
# 查询记录数
/index/type/_count
# 查看mapping
/index/type/_mapping
# 删除索引
DELETE /index

# 清空索引数据
+ 删除索引
+ 重新建立索引

# 创建索引
```
PUT /temp_index
{
  "mappings": {
    "properties": {
      "name" : {
            "type" : "keyword"
          },
          "age" : {
            "type" : "integer"
          },
          "gender" : {
            "type" : "keyword"
          }
    }
  }
}
```

# 更改字段类型
+ 创建新索引，并设置正确的字段类型
+ 通过reindex将原索引数据迁移到新索引
+ 删除原索引
+ 重新建立原索引
+ 通过reindex将数据迁移到原索引
+ 删除新索引