-- 检查时光轨迹表是否存在

USE knowledge_management;

-- 1. 查看所有km_开头的表
SHOW TABLES LIKE 'km_%';

-- 2. 查看具体的时光轨迹表
SELECT 
    TABLE_NAME AS '表名',
    TABLE_COMMENT AS '表注释',
    CREATE_TIME AS '创建时间',
    TABLE_ROWS AS '行数'
FROM information_schema.TABLES 
WHERE TABLE_SCHEMA = 'knowledge_management' 
  AND TABLE_NAME IN ('km_topic', 'km_timeline_event');

-- 3. 如果表存在，查看表结构
DESC km_topic;
DESC km_timeline_event;

