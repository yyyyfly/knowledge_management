-- ======================================================
-- 将 framework 彻底重命名为 skill 的数据库迁移脚本
-- 执行时间：2024-11-11
-- 说明：将所有 note_type='framework' 改为 'skill'
-- ======================================================

-- 1. 更新 km_note 表中的笔记类型
UPDATE km_note 
SET type = 'skill' 
WHERE type = 'framework';

-- 2. 更新 km_note_config 表中的配置类型
UPDATE km_note_config 
SET note_type = 'skill' 
WHERE note_type = 'framework';

-- 3. 验证更新结果
SELECT COUNT(*) as skill_notes_count FROM km_note WHERE type = 'skill';
SELECT COUNT(*) as framework_notes_count FROM km_note WHERE type = 'framework';

SELECT COUNT(*) as skill_config_count FROM km_note_config WHERE note_type = 'skill';
SELECT COUNT(*) as framework_config_count FROM km_note_config WHERE note_type = 'framework';

