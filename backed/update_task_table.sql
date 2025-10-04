-- 为 km_task 表添加 execution_records 字段
-- 如果数据库已存在该表，请执行此SQL来添加新字段

ALTER TABLE km_task ADD COLUMN execution_records TEXT COMMENT '执行记录JSON' AFTER new_progress;

