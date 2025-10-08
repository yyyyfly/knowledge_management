-- ===================================================================
-- 笔记配置类型字段更新SQL
-- ===================================================================
-- 用途：更新km_note_config表中的config_type字段，使其与前端表单字段名称一致
-- 执行时机：在已有数据的数据库上执行，用于数据迁移
-- 注意事项：执行前请备份数据库！
-- ===================================================================

USE `kmdb`;

-- ===================================================================
-- 0. 修改config_type字段长度（如果需要）
-- ===================================================================
-- knowledgePoint有14个字符，确保字段长度足够
ALTER TABLE `km_note_config` 
MODIFY COLUMN `config_type` VARCHAR(30) NOT NULL COMMENT '配置类型：根据笔记类型不同而不同，如category/theme/subject/knowledgePoint/source/domain/techStack/subjectType等';

-- ===================================================================
-- 1. 更新框架笔记配置类型
-- ===================================================================
-- 将 category 改为 subjectType（学科类型）
UPDATE `km_note_config` 
SET `config_type` = 'subjectType',
    `rec_revisor` = 'admin',
    `rec_revise_time` = NOW()
WHERE `note_type` = 'framework' AND `config_type` = 'category';

-- 将 subject 改为 knowledgePoint（知识点类型）
UPDATE `km_note_config` 
SET `config_type` = 'knowledgePoint',
    `rec_revisor` = 'admin',
    `rec_revise_time` = NOW()
WHERE `note_type` = 'framework' AND `config_type` = 'subject';

-- ===================================================================
-- 2. 更新求学笔记配置类型
-- ===================================================================
-- 将 course 改为 knowledgePoint（知识点）
UPDATE `km_note_config` 
SET `config_type` = 'knowledgePoint',
    `rec_revisor` = 'admin',
    `rec_revise_time` = NOW()
WHERE `note_type` = 'study' AND `config_type` = 'course';

-- subject保持不变

-- ===================================================================
-- 3. 更新背诵笔记配置类型
-- ===================================================================
-- 将 category 改为 knowledgePoint（知识点）
UPDATE `km_note_config` 
SET `config_type` = 'knowledgePoint',
    `rec_revisor` = 'admin',
    `rec_revise_time` = NOW()
WHERE `note_type` = 'memorization' AND `config_type` = 'category';

-- subject保持不变

-- ===================================================================
-- 4. 刷题笔记、实战笔记、碎片笔记配置类型无需更新
-- ===================================================================
-- exercise: source, subject（顺序正确，无需修改）
-- practical: domain, techStack（无需修改）
-- fragment: category, theme（无需修改）

-- ===================================================================
-- 验证更新结果
-- ===================================================================
SELECT 
  note_type AS '笔记类型',
  config_type AS '配置类型',
  COUNT(*) AS '配置项数量'
FROM `km_note_config`
GROUP BY note_type, config_type
ORDER BY note_type, config_type;

