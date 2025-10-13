-- 求学笔记添加富文本编辑器字段
-- 执行日期: 2025-10-13

ALTER TABLE `km_note`
ADD COLUMN `core_concept` TEXT COMMENT '核心概念（富文本）' AFTER `study_subject`,
ADD COLUMN `mechanism` TEXT COMMENT '机制原理（富文本）' AFTER `core_concept`,
ADD COLUMN `application_case` TEXT COMMENT '应用案例（富文本）' AFTER `mechanism`,
ADD COLUMN `extension` TEXT COMMENT '延伸/对比（富文本）' AFTER `application_case`,
ADD COLUMN `common_mistake` TEXT COMMENT '常见误区（富文本）' AFTER `extension`,
ADD COLUMN `reflection` TEXT COMMENT '思考理解（富文本）' AFTER `common_mistake`;

