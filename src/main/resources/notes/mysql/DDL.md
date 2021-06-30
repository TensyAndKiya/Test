# 新增列
ALTER TABLE dog ADD COLUMN age INT(11) DEFAULT 0 COMMENT '年龄';
# 删除列
ALTER TABLE dog DROP COLUMN gender;
# 修改列名及类型等
ALTER TABLE dog CHANGE COLUMN age gender TINYINT(1) DEFAULT 1 COMMENT '0雌1雄';
# 设置自增值
ALTER TABLE dog AUTO_INCREMENT = 8;
# 修改字符集和排序规则
ALTER TABLE test.book CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;