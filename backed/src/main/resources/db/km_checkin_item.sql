-- 打卡项目表
CREATE TABLE IF NOT EXISTS `km_checkin_item` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` VARCHAR(100) NOT NULL COMMENT '打卡项目名称',
  `description` VARCHAR(500) DEFAULT NULL COMMENT '项目描述',
  `frequency` VARCHAR(20) NOT NULL DEFAULT 'daily' COMMENT '打卡频率:daily-日,weekly-周,monthly-月,quarterly-季,yearly-年',
  `username` VARCHAR(50) NOT NULL COMMENT '用户名',
  `status` VARCHAR(20) NOT NULL DEFAULT 'active' COMMENT '状态:active-启用,inactive-停用',
  `rec_creator` VARCHAR(50) DEFAULT NULL COMMENT '记录创建人',
  `rec_create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
  `rec_revisor` VARCHAR(50) DEFAULT NULL COMMENT '记录修改人',
  `rec_revise_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录修改时间',
  `arch_flag` TINYINT(1) DEFAULT 0 COMMENT '归档标志:0-未归档,1-已归档',
  PRIMARY KEY (`id`),
  KEY `idx_username` (`username`),
  KEY `idx_status` (`status`),
  KEY `idx_frequency` (`frequency`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='打卡项目表';

