-- ===================================================================
-- 框架笔记改为技能笔记 - 数据库字段调整
-- 兼容 MySQL 5.7
-- ===================================================================

-- 1. 字段改名：subject_type → skill_type
ALTER TABLE km_note CHANGE COLUMN subject_type skill_type VARCHAR(500) COMMENT '技能类型（逗号分隔）';

-- 2. 字段改名：knowledge_point → skill_point
ALTER TABLE km_note CHANGE COLUMN knowledge_point skill_point VARCHAR(500) COMMENT '技能点（逗号分隔）';

-- 3. content 字段保持原样（技能笔记可以不填，但字段不需要修改）
-- ALTER TABLE km_note MODIFY COLUMN content TEXT NULL COMMENT '内容（部分笔记类型使用）';

-- 4. 新增三个富文本字段（技能笔记专用）
ALTER TABLE km_note ADD COLUMN expected_description TEXT COMMENT '预期描述（富文本）' AFTER content;
ALTER TABLE km_note ADD COLUMN thinking_summary TEXT COMMENT '思考总结（富文本）' AFTER expected_description;
ALTER TABLE km_note ADD COLUMN final_effect TEXT COMMENT '最终效果（富文本）' AFTER thinking_summary;

-- ===================================================================
-- 说明：
-- 1. subject_type → skill_type: 学科类型改为技能类型
-- 2. knowledge_point → skill_point: 知识点改为技能点
-- 3. content 字段保留但允许 NULL，技能笔记不使用
-- 4. 新增三个富文本字段完全替代 content 字段的作用
-- ===================================================================

