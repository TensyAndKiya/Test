# 创建索引
```
PUT /temp_index
{
    "mappings": {
        "properties": {
            "name": {
                "type": "keyword"
            },
            "age": {
                "type": "integer"
            },
            "gender": {
                "type": "keyword"
            }
        }
    }
}
```
# 删除索引
DELETE /index
# 关闭索引
/index/type/_close
# 打开索引
/index/type/_open
# 查看mapping
/index/type/_mapping
# 查看setting
/index/type/_settings
# 查询
/index/type/_search
# 查询记录数
/index/type/_count
# 更改字段类型
+ 使用想要的mapping，创建新索引
+ 通过reindex将原索引数据迁移到新索引
+ 删除原索引
+ 使用想要的mapping，创建原索引
+ 通过reindex将数据迁移到原索引
+ 删除新索引
# 清空索引数据
+ 删除索引
+ 重新创建索引
# 新增字段
```
PUT /index/_mapping/type
{
  "properties": {
      "FIELD": {
        "type": "TYPE"
      }
    }
}
```
# 查看别名
GET /index/_alias
# 新增别名
```
POST /_aliases
{
  "actions": [
    {
      "add": {
        "index": "INDEX",
        "alias": "ALIAS"
      }
    }
  ]
}
或
PUT /index/_alias/ALIAS
```
# 删除别名
```
POST /_aliases
{
  "actions": [
    {
      "remove": {
        "index": "INDEX",
        "alias": "ALIAS1"
      }
    }
  ]
}
或
DELETE /index/_alias/ALIAS
```
# 修改别名
```
POST /_aliases
{
  "actions": [
    {
      "remove": {
        "index": "INDEX",
        "alias": "ALIAS1"
      }
    },
    {
      "add": {
        "index": "INDEX",
        "alias": "ALIAS2"
      }
    }
  ]
}
```
# 根据条件删除数据
```
POST index/type/_delete_by_query
{
    "query": {
    }
}
```
