-- 为 km_note 表添加类型特定字段

-- 框架笔记字段
ALTER TABLE km_note ADD COLUMN subject_type VARCHAR(500) COMMENT '学科类型（逗号分隔）' AFTER tags;
ALTER TABLE km_note ADD COLUMN knowledge_point VARCHAR(500) COMMENT '知识点（逗号分隔）' AFTER subject_type;

-- 求学笔记字段
ALTER TABLE km_note ADD COLUMN study_subject VARCHAR(500) COMMENT '求学学科（逗号分隔）' AFTER knowledge_point;

-- 背诵笔记字段
ALTER TABLE km_note ADD COLUMN project VARCHAR(200) COMMENT '学科项目' AFTER study_subject;
ALTER TABLE km_note ADD COLUMN original_text TEXT COMMENT '原文内容' AFTER project;
ALTER TABLE km_note ADD COLUMN explanation TEXT COMMENT '解释说明' AFTER original_text;
ALTER TABLE km_note ADD COLUMN cue TEXT COMMENT '记忆提示词' AFTER explanation;

-- 刷题笔记字段
ALTER TABLE km_note ADD COLUMN exercise_source VARCHAR(200) COMMENT '题目来源' AFTER cue;
ALTER TABLE km_note ADD COLUMN exercise_subject VARCHAR(200) COMMENT '刷题学科类型' AFTER exercise_source;
ALTER TABLE km_note ADD COLUMN exercise_knowledge VARCHAR(500) COMMENT '刷题知识点（逗号分隔）' AFTER exercise_subject;
ALTER TABLE km_note ADD COLUMN exercise_difficulty VARCHAR(20) COMMENT '难度等级:easy-简单,medium-中等,hard-困难' AFTER exercise_knowledge;

-- 实战笔记字段
ALTER TABLE km_note ADD COLUMN tech_tags VARCHAR(500) COMMENT '技术栈标签（逗号分隔）' AFTER exercise_difficulty;
ALTER TABLE km_note ADD COLUMN project_type VARCHAR(500) COMMENT '项目类型（逗号分隔）' AFTER tech_tags;

-- 碎片笔记字段
ALTER TABLE km_note ADD COLUMN fragment_category VARCHAR(500) COMMENT '碎片分类（逗号分隔）' AFTER project_type;
ALTER TABLE km_note ADD COLUMN fragment_theme VARCHAR(500) COMMENT '碎片主题（逗号分隔）' AFTER fragment_category;
ALTER TABLE km_note ADD COLUMN importance VARCHAR(20) COMMENT '重要程度:low-一般,medium-重要,high-非常重要' AFTER fragment_theme;

