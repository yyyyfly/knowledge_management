-- 添加拓展笔记的understanding字段
-- 执行时间：2025-11-08

ALTER TABLE km_note ADD COLUMN understanding MEDIUMTEXT COMMENT '理解记录（拓展笔记专用）';

