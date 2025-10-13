-- 刷题笔记添加富文本编辑器字段
-- 执行日期: 2025-10-13

ALTER TABLE `km_note`
ADD COLUMN `question_description` TEXT COMMENT '题目描述（富文本）' AFTER `exercise_difficulty`,
ADD COLUMN `analysis` TEXT COMMENT '分析理解（富文本）' AFTER `question_description`,
ADD COLUMN `reference_answer` TEXT COMMENT '参考答案（富文本）' AFTER `analysis`;

