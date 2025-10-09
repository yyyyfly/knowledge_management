-- km_topic.sql
-- 时间轴主题表

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

-- 插入示例数据
INSERT INTO `km_topic` (`id`, `name`, `rec_creator`) VALUES
('km-system', '知识管理系统', 'admin'),
('vue-learning', 'Vue3学习路线', 'admin');

