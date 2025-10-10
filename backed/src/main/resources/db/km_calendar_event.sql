-- 日历事件表
CREATE TABLE `km_calendar_event` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `event_title` VARCHAR(200) NOT NULL COMMENT '事件标题',
  `event_date` DATE NOT NULL COMMENT '事件日期',
  `event_type` VARCHAR(20) NOT NULL DEFAULT 'custom' COMMENT '事件类型：custom-自定义, holiday-节假日',
  `repeat_type` VARCHAR(20) NOT NULL DEFAULT 'once' COMMENT '重复类型：once-单次, daily-每天, monthly-每月, yearly-每年',
  `description` TEXT COMMENT '事件描述',
  `color` VARCHAR(20) DEFAULT '#3b82f6' COMMENT '事件颜色',
  `show_countdown` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否显示倒计时:0-否,1-是',
  `rec_creator` VARCHAR(64) NOT NULL DEFAULT 'system' COMMENT '创建人',
  `rec_create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `rec_revisor` VARCHAR(64) NOT NULL DEFAULT 'system' COMMENT '修订人',
  `rec_revise_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修订时间',
  `arch_flag` INT(1) NOT NULL DEFAULT 1 COMMENT '归档标志:0-已删除,1-正常',
  PRIMARY KEY (`id`),
  KEY `idx_event_date` (`event_date`),
  KEY `idx_event_type` (`event_type`),
  KEY `idx_repeat_type` (`repeat_type`),
  KEY `idx_show_countdown` (`show_countdown`),
  KEY `idx_arch_flag` (`arch_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='日历事件表';

-- 如果表已存在，添加倒计时字段（迁移用）
-- ALTER TABLE `km_calendar_event` ADD COLUMN `show_countdown` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否显示倒计时:0-否,1-是' AFTER `color`;
-- ALTER TABLE `km_calendar_event` ADD KEY `idx_show_countdown` (`show_countdown`);

