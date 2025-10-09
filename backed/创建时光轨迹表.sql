-- =====================================================
-- 时光轨迹功能数据库初始化脚本
-- 数据库：knowledge_management
-- 执行顺序：先创建 km_topic，再创建 km_timeline_event（有外键依赖）
-- =====================================================

USE knowledge_management;

-- =====================================================
-- 1. 创建主题表
-- =====================================================

DROP TABLE IF EXISTS `km_topic`;

CREATE TABLE `km_topic` (
  `id` VARCHAR(50) NOT NULL COMMENT '主题ID',
  `name` VARCHAR(100) NOT NULL COMMENT '主题名称',
  `rec_creator` VARCHAR(64) NOT NULL DEFAULT 'system' COMMENT '创建人',
  `rec_create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `rec_revisor` VARCHAR(64) NOT NULL DEFAULT 'system' COMMENT '修改人',
  `rec_revise_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `arch_flag` INT(1) NOT NULL DEFAULT 1 COMMENT '归档标志:0-已删除,1-正常',
  PRIMARY KEY (`id`),
  KEY `idx_arch_flag` (`arch_flag`),
  KEY `idx_create_time` (`rec_create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='时间轴主题表';

-- =====================================================
-- 2. 创建时光轨迹事件表
-- =====================================================

DROP TABLE IF EXISTS `km_timeline_event`;

CREATE TABLE `km_timeline_event` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `event_type` VARCHAR(20) NOT NULL COMMENT '事件类型：global-全局历史事件, milestone-专项时间轴里程碑',
  `title` VARCHAR(200) NOT NULL COMMENT '事件标题',
  `description` TEXT COMMENT '事件描述',
  `event_date` DATE NOT NULL COMMENT '事件日期',
  
  -- 专项时间轴相关字段（仅milestone类型使用）
  `topic_id` VARCHAR(50) COMMENT '主题ID（关联km_topic表）',
  
  -- 标准字段
  `rec_creator` VARCHAR(64) NOT NULL DEFAULT 'system' COMMENT '创建人',
  `rec_create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `rec_revisor` VARCHAR(64) NOT NULL DEFAULT 'system' COMMENT '修订人',
  `rec_revise_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修订时间',
  `arch_flag` INT(1) NOT NULL DEFAULT 1 COMMENT '归档标志:0-已删除,1-正常',
  
  PRIMARY KEY (`id`),
  KEY `idx_event_type` (`event_type`),
  KEY `idx_event_date` (`event_date`),
  KEY `idx_topic_id` (`topic_id`),
  CONSTRAINT `fk_timeline_topic` FOREIGN KEY (`topic_id`) REFERENCES `km_topic` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='时光轨迹事件表';

-- =====================================================
-- 3. 验证表是否创建成功
-- =====================================================

SHOW TABLES LIKE 'km_%';

SELECT 
    TABLE_NAME AS '表名',
    TABLE_COMMENT AS '表注释',
    TABLE_ROWS AS '行数'
FROM information_schema.TABLES 
WHERE TABLE_SCHEMA = 'knowledge_management' 
  AND TABLE_NAME IN ('km_topic', 'km_timeline_event');

-- =====================================================
-- 完成提示
-- =====================================================
SELECT '✅ 时光轨迹功能数据表创建完成！' AS '执行结果';

