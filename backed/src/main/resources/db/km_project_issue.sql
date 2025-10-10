-- 项目问题表
CREATE TABLE `km_project_issue` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `project_id` BIGINT NOT NULL COMMENT '关联项目ID',
  `issue_title` VARCHAR(200) NOT NULL COMMENT '问题标题',
  `issue_description` TEXT COMMENT '问题描述',
  `issue_type` VARCHAR(20) NOT NULL COMMENT '问题类型：technical-技术问题, requirement-需求问题, design-设计问题, other-其他问题',
  `priority` VARCHAR(20) NOT NULL DEFAULT 'medium' COMMENT '优先级：low-低, medium-中, high-高, urgent-紧急',
  `status` VARCHAR(20) NOT NULL DEFAULT 'open' COMMENT '问题状态：open-待处理, in_progress-处理中, resolved-已解决, closed-已关闭',
  `solution` TEXT COMMENT '解决方案',
  `resolve_time` DATETIME COMMENT '解决时间',
  `rec_creator` VARCHAR(64) NOT NULL DEFAULT 'system' COMMENT '创建人',
  `rec_create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `rec_revisor` VARCHAR(64) NOT NULL DEFAULT 'system' COMMENT '修订人',
  `rec_revise_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修订时间',
  `arch_flag` INT(1) NOT NULL DEFAULT 1 COMMENT '归档标志:0-已删除,1-正常',
  PRIMARY KEY (`id`),
  KEY `idx_project_id` (`project_id`),
  KEY `idx_issue_type` (`issue_type`),
  KEY `idx_status` (`status`),
  KEY `idx_priority` (`priority`),
  KEY `idx_arch_flag` (`arch_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='项目问题表';

