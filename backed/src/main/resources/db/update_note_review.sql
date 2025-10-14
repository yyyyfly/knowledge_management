-- 为note表添加巩固相关字段
-- 执行日期: 2025-10-13

ALTER TABLE note ADD COLUMN review_count INT DEFAULT 0 COMMENT '巩固次数';
ALTER TABLE note ADD COLUMN last_review_time DATETIME COMMENT '最后巩固时间';

