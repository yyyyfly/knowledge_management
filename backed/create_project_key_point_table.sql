-- 创建项目关键备忘表
CREATE TABLE IF NOT EXISTS `project_key_point` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `project_id` BIGINT NOT NULL COMMENT '项目ID',
  `type` VARCHAR(20) NOT NULL COMMENT '备忘类型：core-核心要点, error-易错陷阱, reminder-待办事项',
  `title` VARCHAR(200) NOT NULL COMMENT '备忘标题',
  `content` TEXT COMMENT '备忘内容',
  `sort_order` INT DEFAULT 0 COMMENT '排序序号',
  
  -- iPlatv6 标准字段
  `REC_CREATOR` VARCHAR(50) COMMENT '记录创建人',
  `REC_CREATE_TIME` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
  `REC_REVISOR` VARCHAR(50) COMMENT '记录修改人',
  `REC_REVISE_TIME` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录修改时间',
  `ARCH_FLAG` CHAR(1) DEFAULT '1' COMMENT '记录归档标识：0-已归档，1-未归档',
  
  PRIMARY KEY (`id`),
  KEY `idx_project_id` (`project_id`),
  KEY `idx_type` (`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='项目关键备忘表';





















