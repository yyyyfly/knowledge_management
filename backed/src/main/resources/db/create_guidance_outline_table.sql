-- ===================================================================
-- 指导大纲表
-- 用于管理学科知识体系的思维导图和大纲结构
-- 兼容 MySQL 5.7
-- ===================================================================

DROP TABLE IF EXISTS `km_guidance_outline`;

CREATE TABLE `km_guidance_outline` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `subject_name` VARCHAR(200) NOT NULL COMMENT '学科名称',
  `subject_category` VARCHAR(100) DEFAULT NULL COMMENT '学科分类',
  `knowledge_tree` LONGTEXT COMMENT '知识体系树形结构（JSON格式）',
  `tags` VARCHAR(500) DEFAULT NULL COMMENT '标签（逗号分隔）',
  `rec_creator` VARCHAR(50) NOT NULL COMMENT '创建人',
  `rec_create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `rec_revisor` VARCHAR(50) DEFAULT NULL COMMENT '修订人',
  `rec_revise_time` DATETIME DEFAULT NULL COMMENT '修订时间',
  `arch_flag` INT(1) DEFAULT 0 COMMENT '归档标志:0-未归档,1-已归档',
  PRIMARY KEY (`id`),
  KEY `idx_creator` (`rec_creator`),
  KEY `idx_category` (`subject_category`),
  KEY `idx_subject_name` (`subject_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='指导大纲表';

-- ===================================================================
-- 初始化学科分类配置数据（插入到 km_note_config 表）
-- ===================================================================

INSERT INTO `km_note_config` (`note_type`, `config_type`, `config_name`, `sort_order`, `rec_creator`) VALUES
('guidance', 'subjectCategory', '编程开发', 1, 'admin'),
('guidance', 'subjectCategory', '数学理论', 2, 'admin'),
('guidance', 'subjectCategory', '语言学习', 3, 'admin'),
('guidance', 'subjectCategory', '职业技能', 4, 'admin'),
('guidance', 'subjectCategory', '自然科学', 5, 'admin'),
('guidance', 'subjectCategory', '人文社科', 6, 'admin'),
('guidance', 'subjectCategory', '艺术设计', 7, 'admin'),
('guidance', 'subjectCategory', '工程技术', 8, 'admin');

