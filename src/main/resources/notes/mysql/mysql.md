# 存储过程
```
DELIMITER $$
USE `test`$$
DROP PROCEDURE IF EXISTS `PROC_INSERT_DOG_EVERY_MINUTE`$$

CREATE DEFINER=`kiya`@`%` PROCEDURE `PROC_INSERT_DOG_EVERY_MINUTE`(in birthYear int)
BEGIN
	
	declare birthMonth int;
	DECLARE birthTime VARCHAR(32);
	DECLARE birthday DATETIME;
	
	set birthMonth = ceil(RAND() * 12);
	SET birthTime = TIME(NOW());
	SET birthday = str_to_date(concat(birthYear,'-',birthMonth,'-01',' ',birthTime),'%Y-%m-%d %H:%i:%s');
	set birthday = date_add(birthday,interval 1 month);
	SET birthday = DATE_ADD(birthday,INTERVAL -1 day);
	
	insert into dog(name,color,age,birthday) values('test','黑色',0,birthday);
	
	END$$

DELIMITER ;
```
# 函数
```
DELIMITER $$
USE `test`$$
DROP FUNCTION IF EXISTS `test`$$

CREATE DEFINER=`kiya`@`%` FUNCTION `test`(birthYear int) RETURNS datetime
    NO SQL
BEGIN

	declare birthStr varchar(32);
	declare birthday datetime;
	
	set birthStr = CONCAT(birthYear,'-',CEIL(RAND() * 12),'-01',' ',CONCAT('',TIME(NOW())));
	set birthday = str_to_date(birthStr,'%Y-%m-%d %H:%i:%s');
	
	return birthday;
	
    END$$

DELIMITER ;
```
# 事件
```
DELIMITER $$
CREATE EVENT `test`.`event_dog`

ON SCHEDULE
every 1 minute

DO
	BEGIN
	    call PROC_INSERT_DOG_EVERY_MINUTE(year(sysdate()));
	END$$

DELIMITER ;
```
# 触发器
```
DELIMITER $$
USE `test`$$
DROP TRIGGER IF EXISTS `trigger_dog_after_insert`$$

CREATE
    DEFINER = 'kiya'@'%'
    TRIGGER `trigger_dog_after_insert` BEFORE INSERT ON `dog` 
    FOR EACH ROW BEGIN
    
    DECLARE age INT;
    SET age = MONTH(SYSDATE()) - MONTH(new.birthday);
    IF(age < 0) THEN
    signal SQLSTATE '45000' SET message_text = '年龄不能为负数';
    END IF;	
    SET new.age = ABS(age);

    END;
$$

DELIMITER ;
```

# 创建partition并使用sql删除
```
CREATE TABLE `p` (
  `id` INT NOT NULL,
  `p_name` VARCHAR(16) DEFAULT NULL,
  `create_date` DATETIME DEFAULT NULL
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci

PARTITION BY RANGE(TO_DAYS(create_date))
(
PARTITION p1 VALUES LESS THAN (TO_DAYS('2021-04-01 00:00:00')),
PARTITION p2 VALUES LESS THAN (TO_DAYS('2021-07-01 00:00:00')),
PARTITION p3 VALUES LESS THAN (TO_DAYS('2021-10-01 00:00:00')),
PARTITION p4 VALUES LESS THAN (TO_DAYS('2022-01-01 00:00:00'))
);

DELIMITER $$
USE `test`$$
DROP PROCEDURE IF EXISTS `PROC_DROP_OLD_PARTITIONS`$$
# 以下用到了 cursor while statement partition等知识点
CREATE DEFINER=`kiya`@`%` PROCEDURE `PROC_DROP_OLD_PARTITIONS`(in limit_time datetime)
BEGIN
	declare pName varchar(16);
	declare done int default 0;
	declare cs cursor for SELECT partition_name FROM `information_schema`.`PARTITIONS` WHERE table_name = 'p' AND create_time < limit_time and partition_name != 'p4';
	
	# 游标执行完后设置done为1
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done=1; 
	
	open cs ;
	
	while_label: begin
	while done != 1 do
	
        # 有多个字段的话，个数与类型要一致
		fetch cs into pName;
		
        # 避免游标最后多执行一次
		if done = 1 then 
		leave while_label;
		end if;
		
		set @exeSql = concat('ALTER TABLE p DROP PARTITION ',pName);
		prepare stmt from @exeSql;
		# 记录下拼接的sql
		INSERT INTO dog(id,NAME) VALUES(NULL,@exeSql);
		execute stmt;
		deallocate prepare stmt;
	
	end while;
	end while_label;
	
	close cs ;

	END$$

DELIMITER ;
```