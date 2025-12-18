-- =============================================
-- 项目分析表
-- 用于存储项目的底层逻辑分析内容
-- =============================================

CREATE TABLE IF NOT EXISTS `km_project_analysis` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `project_id` BIGINT NOT NULL COMMENT '关联项目ID',
    `logic_description` MEDIUMTEXT COMMENT '底层逻辑描述（富文本HTML）',
    `rec_creator` VARCHAR(64) DEFAULT NULL COMMENT '创建人',
    `rec_create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `rec_revisor` VARCHAR(64) DEFAULT NULL COMMENT '修改人',
    `rec_revise_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `arch_flag` TINYINT DEFAULT 1 COMMENT '归档标志:0-已归档,1-正常',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_project_id` (`project_id`),
    KEY `idx_rec_creator` (`rec_creator`),
    KEY `idx_arch_flag` (`arch_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='项目分析表';

