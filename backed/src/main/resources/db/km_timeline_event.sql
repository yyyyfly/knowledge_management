-- 时光轨迹事件表（MySQL 5.7兼容）
-- 用于存储全局历史事件和专项时间轴里程碑

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
