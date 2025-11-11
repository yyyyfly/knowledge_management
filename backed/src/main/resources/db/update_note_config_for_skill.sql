-- ===================================================================
-- 更新笔记配置数据：框架笔记改为技能笔记
-- ===================================================================

-- 1. 更新配置类型：subjectType → skillType
UPDATE km_note_config 
SET config_type = 'skillType' 
WHERE note_type = 'framework' AND config_type = 'subjectType';

-- 2. 更新配置类型：knowledgePoint → skillPoint
UPDATE km_note_config 
SET config_type = 'skillPoint' 
WHERE note_type = 'framework' AND config_type = 'knowledgePoint';

-- 3. 更新配置名称以适配技能笔记场景
-- 将学科类型配置更新为技能类型
UPDATE km_note_config 
SET config_name = '编程技能' 
WHERE note_type = 'framework' AND config_type = 'skillType' AND config_name = '计算机科学';

UPDATE km_note_config 
SET config_name = '数学技能' 
WHERE note_type = 'framework' AND config_type = 'skillType' AND config_name = '数学';

UPDATE km_note_config 
SET config_name = '写作技能' 
WHERE note_type = 'framework' AND config_type = 'skillType' AND config_name = '文学';

UPDATE km_note_config 
SET config_name = '管理技能' 
WHERE note_type = 'framework' AND config_type = 'skillType' AND config_name = '管理学';

UPDATE km_note_config 
SET config_name = '商业技能' 
WHERE note_type = 'framework' AND config_type = 'skillType' AND config_name = '经济学';

-- 更新技能点配置（将知识点改为技能点）
UPDATE km_note_config 
SET config_name = 'Web前端' 
WHERE note_type = 'framework' AND config_type = 'skillPoint' AND config_name = '前端开发';

UPDATE km_note_config 
SET config_name = '后端开发' 
WHERE note_type = 'framework' AND config_type = 'skillPoint' AND config_name = '后端开发';

UPDATE km_note_config 
SET config_name = '算法与数据结构' 
WHERE note_type = 'framework' AND config_type = 'skillPoint' AND config_name IN ('算法设计', '数据结构');

UPDATE km_note_config 
SET config_name = '系统设计' 
WHERE note_type = 'framework' AND config_type = 'skillPoint' AND config_name = '系统架构';

UPDATE km_note_config 
SET config_name = 'DevOps运维' 
WHERE note_type = 'framework' AND config_type = 'skillPoint' AND config_name = '运维部署';

-- ===================================================================
-- 说明：
-- 1. 配置类型 subjectType → skillType
-- 2. 配置类型 knowledgePoint → skillPoint  
-- 3. 配置名称更新为更贴合技能学习的场景
-- ===================================================================

