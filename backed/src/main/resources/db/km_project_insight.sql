-- 项目心得表
DROP TABLE IF EXISTS `km_project_insight`;

CREATE TABLE `km_project_insight` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `project_id` BIGINT NOT NULL COMMENT '关联项目ID',
  `title` VARCHAR(200) NOT NULL COMMENT '心得标题',
  `summary` VARCHAR(500) COMMENT '心得摘要',
  `content` TEXT NOT NULL COMMENT '心得内容（富文本HTML）',
  `tags` VARCHAR(500) COMMENT '标签，多个标签用逗号分隔',
  `rec_creator` VARCHAR(64) NOT NULL DEFAULT 'system' COMMENT '创建人',
  `rec_create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `rec_revisor` VARCHAR(64) NOT NULL DEFAULT 'system' COMMENT '修订人',
  `rec_revise_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修订时间',
  `arch_flag` INT(1) NOT NULL DEFAULT 1 COMMENT '归档标志:0-已删除,1-正常',
  PRIMARY KEY (`id`),
  KEY `idx_project_id` (`project_id`),
  KEY `idx_rec_creator` (`rec_creator`),
  KEY `idx_arch_flag` (`arch_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='项目心得表';


