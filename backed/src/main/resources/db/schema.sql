-- 个人知识管理系统数据库设计
-- 创建数据库
CREATE DATABASE IF NOT EXISTS knowledge_management DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE knowledge_management;

-- ============================================
-- 1. 项目表
-- ============================================
DROP TABLE IF EXISTS `km_project`;
CREATE TABLE `km_project` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` VARCHAR(200) NOT NULL COMMENT '项目名称',
  `description` TEXT COMMENT '项目描述',
  `status` VARCHAR(50) NOT NULL DEFAULT 'planning' COMMENT '项目状态:planning-规划中,active-进行中,completed-已完成,suspended-已暂停',
  `category` VARCHAR(50) NOT NULL COMMENT '项目类别:construction-工程建设,defense-战争行动,diplomatic-外交活动',
  `task_count` INT(11) DEFAULT 0 COMMENT '任务数量',
  `rec_creator` VARCHAR(50) COMMENT '创建人',
  `rec_create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `rec_revisor` VARCHAR(50) COMMENT '修改人',
  `rec_revise_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `arch_flag` TINYINT(1) DEFAULT 0 COMMENT '归档标志:0-未归档,1-已归档',
  PRIMARY KEY (`id`),
  INDEX `idx_category` (`category`),
  INDEX `idx_status` (`status`),
  INDEX `idx_create_time` (`rec_create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='项目表';

-- ============================================
-- 2. 任务表
-- ============================================
DROP TABLE IF EXISTS `km_task`;
CREATE TABLE `km_task` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `project_id` BIGINT(20) NOT NULL COMMENT '项目ID',
  `name` VARCHAR(200) NOT NULL COMMENT '任务名称',
  `description` TEXT COMMENT '任务描述',
  `status` VARCHAR(50) NOT NULL DEFAULT 'pending' COMMENT '任务状态:pending-待执行,in-progress-执行中,completed-已完成,cancelled-已取消,stopped-已停止',
  `deadline` DATETIME COMMENT '截止时间',
  `completed_time` DATETIME COMMENT '完成时间',
  `new_progress` TEXT COMMENT '最新进度',
  `execution_records` TEXT COMMENT '执行记录JSON',
  `rec_creator` VARCHAR(50) COMMENT '创建人',
  `rec_create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `rec_revisor` VARCHAR(50) COMMENT '修改人',
  `rec_revise_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `arch_flag` TINYINT(1) DEFAULT 0 COMMENT '归档标志:0-未归档,1-已归档',
  PRIMARY KEY (`id`),
  INDEX `idx_project_id` (`project_id`),
  INDEX `idx_status` (`status`),
  INDEX `idx_deadline` (`deadline`),
  CONSTRAINT `fk_task_project` FOREIGN KEY (`project_id`) REFERENCES `km_project` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='任务表';

-- ============================================
-- 3. 任务执行记录表
-- ============================================
DROP TABLE IF EXISTS `km_execution_record`;
CREATE TABLE `km_execution_record` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `task_id` BIGINT(20) NOT NULL COMMENT '任务ID',
  `date` DATE NOT NULL COMMENT '执行日期',
  `progress` TEXT NOT NULL COMMENT '执行进度',
  `rec_creator` VARCHAR(50) COMMENT '创建人',
  `rec_create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `rec_revisor` VARCHAR(50) COMMENT '修改人',
  `rec_revise_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `arch_flag` TINYINT(1) DEFAULT 0 COMMENT '归档标志:0-未归档,1-已归档',
  PRIMARY KEY (`id`),
  INDEX `idx_task_id` (`task_id`),
  INDEX `idx_date` (`date`),
  CONSTRAINT `fk_record_task` FOREIGN KEY (`task_id`) REFERENCES `km_task` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='任务执行记录表';

-- ============================================
-- 4. 时间节点表
-- ============================================
DROP TABLE IF EXISTS `km_time_node`;
CREATE TABLE `km_time_node` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` VARCHAR(200) NOT NULL COMMENT '节点标题',
  `description` TEXT COMMENT '节点描述',
  `time` DATETIME NOT NULL COMMENT '节点时间',
  `type` VARCHAR(50) NOT NULL COMMENT '节点类型:war-战争行动,diplomatic-外交活动',
  `status` VARCHAR(50) NOT NULL DEFAULT 'not-started' COMMENT '节点状态:not-started-未开始,upcoming-即将开始,ongoing-进行中,completed-已完成,overdue-已逾期,closed-已关闭',
  `rec_creator` VARCHAR(50) COMMENT '创建人',
  `rec_create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `rec_revisor` VARCHAR(50) COMMENT '修改人',
  `rec_revise_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `arch_flag` TINYINT(1) DEFAULT 0 COMMENT '归档标志:0-未归档,1-已归档',
  PRIMARY KEY (`id`),
  INDEX `idx_type` (`type`),
  INDEX `idx_status` (`status`),
  INDEX `idx_time` (`time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='时间节点表';

-- ============================================
-- 5. 笔记基础表
-- ============================================
DROP TABLE IF EXISTS `km_note`;
CREATE TABLE `km_note` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `type` VARCHAR(50) NOT NULL COMMENT '笔记类型:framework-框架笔记,study-求学笔记,memorization-背诵笔记,exercise-刷题笔记,practical-实战笔记,fragment-碎片笔记',
  `title` VARCHAR(200) NOT NULL COMMENT '标题',
  `summary` TEXT COMMENT '摘要',
  `content` LONGTEXT COMMENT '内容',
  `tags` VARCHAR(500) COMMENT '标签，JSON数组格式',
  `rec_creator` VARCHAR(50) COMMENT '创建人',
  `rec_create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `rec_revisor` VARCHAR(50) COMMENT '修改人',
  `rec_revise_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `arch_flag` TINYINT(1) DEFAULT 0 COMMENT '归档标志:0-未归档,1-已归档',
  PRIMARY KEY (`id`),
  INDEX `idx_type` (`type`),
  INDEX `idx_create_time` (`rec_create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='笔记基础表';

-- ============================================
-- 6. 框架笔记扩展表
-- ============================================
DROP TABLE IF EXISTS `km_note_framework`;
CREATE TABLE `km_note_framework` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `note_id` BIGINT(20) NOT NULL COMMENT '笔记ID',
  `subject_type` VARCHAR(500) COMMENT '学科类型，JSON数组格式',
  `knowledge_point` VARCHAR(500) COMMENT '知识点，JSON数组格式',
  `rec_creator` VARCHAR(50) COMMENT '创建人',
  `rec_create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `rec_revisor` VARCHAR(50) COMMENT '修改人',
  `rec_revise_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `arch_flag` TINYINT(1) DEFAULT 0 COMMENT '归档标志:0-未归档,1-已归档',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_note_id` (`note_id`),
  CONSTRAINT `fk_framework_note` FOREIGN KEY (`note_id`) REFERENCES `km_note` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='框架笔记扩展表';

-- ============================================
-- 7. 求学笔记扩展表
-- ============================================
DROP TABLE IF EXISTS `km_note_study`;
CREATE TABLE `km_note_study` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `note_id` BIGINT(20) NOT NULL COMMENT '笔记ID',
  `course` VARCHAR(500) COMMENT '课程，JSON数组格式',
  `study_subject` VARCHAR(500) COMMENT '学科，JSON数组格式',
  `book_name` VARCHAR(200) COMMENT '书名',
  `book_subject` VARCHAR(500) COMMENT '书籍学科，JSON数组格式',
  `rec_creator` VARCHAR(50) COMMENT '创建人',
  `rec_create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `rec_revisor` VARCHAR(50) COMMENT '修改人',
  `rec_revise_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `arch_flag` TINYINT(1) DEFAULT 0 COMMENT '归档标志:0-未归档,1-已归档',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_note_id` (`note_id`),
  CONSTRAINT `fk_study_note` FOREIGN KEY (`note_id`) REFERENCES `km_note` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='求学笔记扩展表';

-- ============================================
-- 8. 背诵笔记扩展表
-- ============================================
DROP TABLE IF EXISTS `km_note_memorization`;
CREATE TABLE `km_note_memorization` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `note_id` BIGINT(20) NOT NULL COMMENT '笔记ID',
  `project` VARCHAR(200) NOT NULL COMMENT '所属项目',
  `knowledge_point` VARCHAR(500) COMMENT '知识点，JSON数组格式',
  `review_count` INT(11) DEFAULT 0 COMMENT '复习次数',
  `last_review_time` DATETIME COMMENT '最后复习时间',
  `original_text` TEXT COMMENT '原文',
  `explanation` TEXT COMMENT '解释',
  `cue` VARCHAR(500) COMMENT '提示词',
  `rec_creator` VARCHAR(50) COMMENT '创建人',
  `rec_create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `rec_revisor` VARCHAR(50) COMMENT '修改人',
  `rec_revise_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `arch_flag` TINYINT(1) DEFAULT 0 COMMENT '归档标志:0-未归档,1-已归档',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_note_id` (`note_id`),
  INDEX `idx_project` (`project`),
  CONSTRAINT `fk_memorization_note` FOREIGN KEY (`note_id`) REFERENCES `km_note` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='背诵笔记扩展表';

-- ============================================
-- 9. 刷题笔记扩展表
-- ============================================
DROP TABLE IF EXISTS `km_note_exercise`;
CREATE TABLE `km_note_exercise` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `note_id` BIGINT(20) NOT NULL COMMENT '笔记ID',
  `problem_name` VARCHAR(200) NOT NULL COMMENT '题目名称',
  `exercise_source` VARCHAR(100) COMMENT '题目来源',
  `exercise_subject` VARCHAR(100) COMMENT '学科类型',
  `exercise_knowledge` VARCHAR(500) COMMENT '知识点，JSON数组格式',
  `accuracy` DECIMAL(5,2) DEFAULT 0.00 COMMENT '正确率',
  `rec_creator` VARCHAR(50) COMMENT '创建人',
  `rec_create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `rec_revisor` VARCHAR(50) COMMENT '修改人',
  `rec_revise_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `arch_flag` TINYINT(1) DEFAULT 0 COMMENT '归档标志:0-未归档,1-已归档',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_note_id` (`note_id`),
  INDEX `idx_source` (`exercise_source`),
  CONSTRAINT `fk_exercise_note` FOREIGN KEY (`note_id`) REFERENCES `km_note` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='刷题笔记扩展表';

-- ============================================
-- 10. 实战笔记扩展表
-- ============================================
DROP TABLE IF EXISTS `km_note_practical`;
CREATE TABLE `km_note_practical` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `note_id` BIGINT(20) NOT NULL COMMENT '笔记ID',
  `tech_stack` VARCHAR(500) COMMENT '技术栈，JSON数组格式',
  `project_type` VARCHAR(500) COMMENT '项目类型，JSON数组格式',
  `project_name` VARCHAR(200) COMMENT '项目名称',
  `rec_creator` VARCHAR(50) COMMENT '创建人',
  `rec_create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `rec_revisor` VARCHAR(50) COMMENT '修改人',
  `rec_revise_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `arch_flag` TINYINT(1) DEFAULT 0 COMMENT '归档标志:0-未归档,1-已归档',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_note_id` (`note_id`),
  CONSTRAINT `fk_practical_note` FOREIGN KEY (`note_id`) REFERENCES `km_note` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='实战笔记扩展表';

-- ============================================
-- 11. 碎片笔记扩展表
-- ============================================
DROP TABLE IF EXISTS `km_note_fragment`;
CREATE TABLE `km_note_fragment` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `note_id` BIGINT(20) NOT NULL COMMENT '笔记ID',
  `fragment_category` VARCHAR(500) COMMENT '碎片分类，JSON数组格式',
  `fragment_theme` VARCHAR(500) COMMENT '碎片主题，JSON数组格式',
  `importance` VARCHAR(20) DEFAULT 'medium' COMMENT '重要程度:low-低,medium-中,high-高',
  `rec_creator` VARCHAR(50) COMMENT '创建人',
  `rec_create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `rec_revisor` VARCHAR(50) COMMENT '修改人',
  `rec_revise_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `arch_flag` TINYINT(1) DEFAULT 0 COMMENT '归档标志:0-未归档,1-已归档',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_note_id` (`note_id`),
  INDEX `idx_importance` (`importance`),
  CONSTRAINT `fk_fragment_note` FOREIGN KEY (`note_id`) REFERENCES `km_note` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='碎片笔记扩展表';

-- ============================================
-- 12. 荣誉战绩表
-- ============================================
DROP TABLE IF EXISTS `km_honor`;
CREATE TABLE `km_honor` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` VARCHAR(200) NOT NULL COMMENT '荣誉标题',
  `description` TEXT COMMENT '荣誉描述',
  `category` VARCHAR(50) NOT NULL COMMENT '荣誉类别:defense-战争行动,construction-工程建设,diplomatic-外交活动',
  `achieved_time` DATETIME NOT NULL COMMENT '获得时间',
  `project_id` BIGINT(20) COMMENT '关联项目ID',
  `icon` VARCHAR(50) COMMENT '图标类型',
  `rec_creator` VARCHAR(50) COMMENT '创建人',
  `rec_create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `rec_revisor` VARCHAR(50) COMMENT '修改人',
  `rec_revise_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `arch_flag` TINYINT(1) DEFAULT 0 COMMENT '归档标志:0-未归档,1-已归档',
  PRIMARY KEY (`id`),
  INDEX `idx_category` (`category`),
  INDEX `idx_achieved_time` (`achieved_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='荣誉战绩表';

-- ============================================
-- 13. 系统总结表
-- ============================================
DROP TABLE IF EXISTS `km_summary`;
CREATE TABLE `km_summary` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` VARCHAR(200) NOT NULL COMMENT '总结标题',
  `type` VARCHAR(50) NOT NULL COMMENT '总结类型:daily-日报,monthly-月报,quarterly-季报,yearly-年报',
  `period` VARCHAR(100) NOT NULL COMMENT '总结周期',
  `description` TEXT COMMENT '长段描述',
  `content` LONGTEXT COMMENT '总结内容，JSON格式',
  `satisfaction` INT(11) DEFAULT 5 COMMENT '满意度评分:1-10分',
  `tags` VARCHAR(500) COMMENT '标签，JSON数组格式',
  `rec_creator` VARCHAR(50) COMMENT '创建人',
  `rec_create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `rec_revisor` VARCHAR(50) COMMENT '修改人',
  `rec_revise_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `arch_flag` TINYINT(1) DEFAULT 0 COMMENT '归档标志:0-未归档,1-已归档',
  PRIMARY KEY (`id`),
  INDEX `idx_type` (`type`),
  INDEX `idx_period` (`period`),
  INDEX `idx_create_time` (`rec_create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统总结表';

