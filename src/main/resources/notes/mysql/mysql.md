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
    
    declare age int;
    set age = month(sysdate()) - month(new.birthday);
    set new.age = abs(age);

    END;
$$

DELIMITER ;
```