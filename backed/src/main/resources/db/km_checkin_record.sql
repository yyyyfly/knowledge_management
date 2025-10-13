-- 打卡记录表
CREATE TABLE IF NOT EXISTS `km_checkin_record` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `checkin_item_id` BIGINT NOT NULL COMMENT '打卡项目ID',
  `username` VARCHAR(50) NOT NULL COMMENT '用户名',
  `checkin_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '打卡时间',
  `cycle_key` VARCHAR(50) NOT NULL COMMENT '周期标识(如:2025-10-11,2025-W41,2025-10,2025-Q4,2025)',
  `rec_creator` VARCHAR(50) DEFAULT NULL COMMENT '记录创建人',
  `rec_create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
  `rec_revisor` VARCHAR(50) DEFAULT NULL COMMENT '记录修改人',
  `rec_revise_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录修改时间',
  `arch_flag` TINYINT(1) DEFAULT 0 COMMENT '归档标志:0-未归档,1-已归档',
  PRIMARY KEY (`id`),
  KEY `idx_checkin_item_id` (`checkin_item_id`),
  KEY `idx_username` (`username`),
  KEY `idx_cycle_key` (`cycle_key`),
  KEY `idx_checkin_time` (`checkin_time`),
  UNIQUE KEY `uk_item_user_cycle` (`checkin_item_id`, `username`, `cycle_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='打卡记录表';

