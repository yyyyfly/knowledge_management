-- 升级笔记表字段类型：从 TEXT 升级到 MEDIUMTEXT
-- 目的：支持富文本编辑器中的图片（Base64格式）
-- TEXT 最大 65KB，MEDIUMTEXT 最大 16MB
-- 执行日期: 2025-10-21



-- 基础字段
ALTER TABLE km_note MODIFY COLUMN content MEDIUMTEXT COMMENT '内容';

-- 求学笔记字段（6个富文本编辑器）
ALTER TABLE km_note MODIFY COLUMN core_concept MEDIUMTEXT COMMENT '核心概念（富文本）';
ALTER TABLE km_note MODIFY COLUMN mechanism MEDIUMTEXT COMMENT '机制原理（富文本）';
ALTER TABLE km_note MODIFY COLUMN application_case MEDIUMTEXT COMMENT '应用案例（富文本）';
ALTER TABLE km_note MODIFY COLUMN extension MEDIUMTEXT COMMENT '延伸/对比（富文本）';
ALTER TABLE km_note MODIFY COLUMN common_mistake MEDIUMTEXT COMMENT '常见误区（富文本）';
ALTER TABLE km_note MODIFY COLUMN reflection MEDIUMTEXT COMMENT '思考理解（富文本）';

-- 背诵笔记字段（3个富文本编辑器）
ALTER TABLE km_note MODIFY COLUMN original_text MEDIUMTEXT COMMENT '原文内容';
ALTER TABLE km_note MODIFY COLUMN explanation MEDIUMTEXT COMMENT '解释说明';
ALTER TABLE km_note MODIFY COLUMN cue MEDIUMTEXT COMMENT '记忆提示词';

-- 刷题笔记字段（3个富文本编辑器）
ALTER TABLE km_note MODIFY COLUMN question_description MEDIUMTEXT COMMENT '题目描述（富文本）';
ALTER TABLE km_note MODIFY COLUMN analysis MEDIUMTEXT COMMENT '分析理解（富文本）';
ALTER TABLE km_note MODIFY COLUMN reference_answer MEDIUMTEXT COMMENT '参考答案（富文本）';

-- 实战笔记字段（3个富文本编辑器）
ALTER TABLE km_note MODIFY COLUMN requirement_description MEDIUMTEXT COMMENT '需求描述（富文本）';
ALTER TABLE km_note MODIFY COLUMN design_thinking MEDIUMTEXT COMMENT '设计思路（富文本）';
ALTER TABLE km_note MODIFY COLUMN reference_design MEDIUMTEXT COMMENT '参考设计（富文本）';

-- 验证修改结果
SELECT 
    COLUMN_NAME, 
    COLUMN_TYPE, 
    COLUMN_COMMENT
FROM 
    INFORMATION_SCHEMA.COLUMNS
WHERE 
    TABLE_SCHEMA = 'knowledge_management' 
    AND TABLE_NAME = 'km_note'
    AND COLUMN_TYPE LIKE '%text%'
ORDER BY 
    ORDINAL_POSITION;















