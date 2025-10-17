-- 实战笔记添加富文本编辑器字段
-- 执行日期: 2025-10-13

ALTER TABLE `km_note`
ADD COLUMN `requirement_description` TEXT COMMENT '需求描述（富文本）' AFTER `project_type`,
ADD COLUMN `design_thinking` TEXT COMMENT '设计思路（富文本）' AFTER `requirement_description`,
ADD COLUMN `reference_design` TEXT COMMENT '参考设计（富文本）' AFTER `design_thinking`;



