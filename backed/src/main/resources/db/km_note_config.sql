-- ===================================================================
-- 笔记配置表（通用，适用于所有笔记类型）
-- 兼容 MySQL 5.7
-- ===================================================================

DROP TABLE IF EXISTS `km_note_config`;

CREATE TABLE `km_note_config` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `note_type` VARCHAR(20) NOT NULL COMMENT '笔记类型：fragment-碎片,study-学习,memorization-背诵,exercise-刷题,practical-实战,framework-框架',
  `config_type` VARCHAR(30) NOT NULL COMMENT '配置类型：根据笔记类型不同而不同，如category/theme/subject/knowledgePoint/source/domain/techStack/subjectType等',
  `config_name` VARCHAR(50) NOT NULL COMMENT '配置名称',
  `sort_order` INT(11) DEFAULT 999 COMMENT '排序序号',
  `rec_creator` VARCHAR(50) NOT NULL COMMENT '创建人',
  `rec_create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `rec_revisor` VARCHAR(50) DEFAULT NULL COMMENT '修订人',
  `rec_revise_time` DATETIME DEFAULT NULL COMMENT '修订时间',
  `arch_flag` INT(1) DEFAULT 0 COMMENT '归档标志:0-未归档,1-已归档',
  PRIMARY KEY (`id`),
  KEY `idx_creator` (`rec_creator`),
  KEY `idx_note_type` (`note_type`),
  KEY `idx_config_type` (`config_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='笔记配置表';

-- ===================================================================
-- 初始化碎片笔记默认分类数据
-- ===================================================================

INSERT INTO `km_note_config` (`note_type`, `config_type`, `config_name`, `sort_order`, `rec_creator`) VALUES
('fragment', 'category', 'DevOps', 1, 'admin'),
('fragment', 'category', '学习', 2, 'admin'),
('fragment', 'category', '思考', 3, 'admin'),
('fragment', 'category', '技术', 4, 'admin'),
('fragment', 'category', '生活', 5, 'admin'),
('fragment', 'category', '职场', 6, 'admin'),
('fragment', 'category', '项目管理', 7, 'admin');

-- ===================================================================
-- 初始化碎片笔记默认主题数据
-- ===================================================================

INSERT INTO `km_note_config` (`note_type`, `config_type`, `config_name`, `sort_order`, `rec_creator`) VALUES
('fragment', 'theme', '个人成长', 1, 'admin'),
('fragment', 'theme', '代码质量', 2, 'admin'),
('fragment', 'theme', '代码重构', 3, 'admin'),
('fragment', 'theme', '前端技术', 4, 'admin'),
('fragment', 'theme', '后端技术', 5, 'admin'),
('fragment', 'theme', '团队协作', 6, 'admin'),
('fragment', 'theme', '学习方法', 7, 'admin'),
('fragment', 'theme', '技术债务', 8, 'admin'),
('fragment', 'theme', '技术分享', 9, 'admin'),
('fragment', 'theme', '技术选型', 10, 'admin'),
('fragment', 'theme', '持续集成', 11, 'admin'),
('fragment', 'theme', '效率提升', 12, 'admin'),
('fragment', 'theme', '时间管理', 13, 'admin'),
('fragment', 'theme', '架构设计', 14, 'admin'),
('fragment', 'theme', '沟通技巧', 15, 'admin'),
('fragment', 'theme', '知识传播', 16, 'admin'),
('fragment', 'theme', '职场感悟', 17, 'admin');

-- ===================================================================
-- 初始化学习笔记默认配置数据
-- ===================================================================

INSERT INTO `km_note_config` (`note_type`, `config_type`, `config_name`, `sort_order`, `rec_creator`) VALUES
-- 学科配置
('study', 'subject', '数学', 1, 'admin'),
('study', 'subject', '英语', 2, 'admin'),
('study', 'subject', '计算机科学', 3, 'admin'),
('study', 'subject', '数据结构与算法', 4, 'admin'),
('study', 'subject', '操作系统', 5, 'admin'),
('study', 'subject', '计算机网络', 6, 'admin'),
('study', 'subject', '数据库原理', 7, 'admin'),
('study', 'subject', '软件工程', 8, 'admin'),
-- 知识点配置
('study', 'knowledgePoint', '代码规范', 1, 'admin'),
('study', 'knowledgePoint', '微积分', 2, 'admin'),
('study', 'knowledgePoint', '操作系统', 3, 'admin'),
('study', 'knowledgePoint', '数据库', 4, 'admin'),
('study', 'knowledgePoint', '数据结构', 5, 'admin'),
('study', 'knowledgePoint', '机器学习算法', 6, 'admin'),
('study', 'knowledgePoint', '概率统计', 7, 'admin'),
('study', 'knowledgePoint', '深度学习', 8, 'admin'),
('study', 'knowledgePoint', '离散数学', 9, 'admin'),
('study', 'knowledgePoint', '算法', 10, 'admin'),
('study', 'knowledgePoint', '系统设计', 11, 'admin'),
('study', 'knowledgePoint', '线性代数', 12, 'admin'),
('study', 'knowledgePoint', '编程语言', 13, 'admin'),
('study', 'knowledgePoint', '自然语言处理', 14, 'admin'),
('study', 'knowledgePoint', '计算机网络', 15, 'admin'),
('study', 'knowledgePoint', '计算机视觉', 16, 'admin'),
('study', 'knowledgePoint', '设计模式', 17, 'admin'),
('study', 'knowledgePoint', '软件架构', 18, 'admin');

-- ===================================================================
-- 初始化背诵笔记默认配置数据
-- ===================================================================

INSERT INTO `km_note_config` (`note_type`, `config_type`, `config_name`, `sort_order`, `rec_creator`) VALUES
-- 科目配置
('memorization', 'subject', '面试题', 1, 'admin'),
('memorization', 'subject', 'Java核心', 2, 'admin'),
('memorization', 'subject', 'Spring框架', 3, 'admin'),
('memorization', 'subject', '数据库', 4, 'admin'),
('memorization', 'subject', '算法', 5, 'admin'),
('memorization', 'subject', '系统设计', 6, 'admin'),
-- 知识点配置
('memorization', 'knowledgePoint', 'JVM知识', 1, 'admin'),
('memorization', 'knowledgePoint', '多线程', 2, 'admin'),
('memorization', 'knowledgePoint', '集合框架', 3, 'admin'),
('memorization', 'knowledgePoint', '网络编程', 4, 'admin'),
('memorization', 'knowledgePoint', '设计模式', 5, 'admin'),
('memorization', 'knowledgePoint', '性能优化', 6, 'admin');

-- ===================================================================
-- 初始化刷题笔记默认配置数据
-- ===================================================================

INSERT INTO `km_note_config` (`note_type`, `config_type`, `config_name`, `sort_order`, `rec_creator`) VALUES
-- 学科配置
('exercise', 'subject', '算法', 1, 'admin'),
('exercise', 'subject', '数据结构', 2, 'admin'),
('exercise', 'subject', '数学', 3, 'admin'),
('exercise', 'subject', '编程基础', 4, 'admin'),
('exercise', 'subject', '系统设计', 5, 'admin'),
-- 题目来源配置
('exercise', 'source', 'LeetCode', 1, 'admin'),
('exercise', 'source', '牛客网', 2, 'admin'),
('exercise', 'source', 'HackerRank', 3, 'admin'),
('exercise', 'source', 'CodeForces', 4, 'admin'),
('exercise', 'source', '企业笔试', 5, 'admin'),
('exercise', 'source', '面试真题', 6, 'admin');

-- ===================================================================
-- 初始化实战笔记默认配置数据
-- ===================================================================

INSERT INTO `km_note_config` (`note_type`, `config_type`, `config_name`, `sort_order`, `rec_creator`) VALUES
-- 领域配置
('practical', 'domain', 'Web开发', 1, 'admin'),
('practical', 'domain', '移动开发', 2, 'admin'),
('practical', 'domain', '后端服务', 3, 'admin'),
('practical', 'domain', '数据分析', 4, 'admin'),
('practical', 'domain', '机器学习', 5, 'admin'),
('practical', 'domain', 'DevOps', 6, 'admin'),
-- 技术栈配置
('practical', 'techStack', 'Vue.js', 1, 'admin'),
('practical', 'techStack', 'React', 2, 'admin'),
('practical', 'techStack', 'Spring Boot', 3, 'admin'),
('practical', 'techStack', 'Node.js', 4, 'admin'),
('practical', 'techStack', 'MySQL', 5, 'admin'),
('practical', 'techStack', 'Redis', 6, 'admin'),
('practical', 'techStack', 'Docker', 7, 'admin'),
('practical', 'techStack', 'Kubernetes', 8, 'admin');

-- ===================================================================
-- 初始化框架笔记默认配置数据
-- ===================================================================

INSERT INTO `km_note_config` (`note_type`, `config_type`, `config_name`, `sort_order`, `rec_creator`) VALUES
-- 学科类型配置
('framework', 'subjectType', '数学', 1, 'admin'),
('framework', 'subjectType', '文学', 2, 'admin'),
('framework', 'subjectType', '法学', 3, 'admin'),
('framework', 'subjectType', '物理学', 4, 'admin'),
('framework', 'subjectType', '生物学', 5, 'admin'),
('framework', 'subjectType', '管理学', 6, 'admin'),
('framework', 'subjectType', '经济学', 7, 'admin'),
('framework', 'subjectType', '计算机科学', 8, 'admin'),
-- 知识点类型配置
('framework', 'knowledgePoint', '人工智能', 1, 'admin'),
('framework', 'knowledgePoint', '前端开发', 2, 'admin'),
('framework', 'knowledgePoint', '后端开发', 3, 'admin'),
('framework', 'knowledgePoint', '数据库设计', 4, 'admin'),
('framework', 'knowledgePoint', '数据结构', 5, 'admin'),
('framework', 'knowledgePoint', '机器学习', 6, 'admin'),
('framework', 'knowledgePoint', '测试方法', 7, 'admin'),
('framework', 'knowledgePoint', '移动开发', 8, 'admin'),
('framework', 'knowledgePoint', '算法设计', 9, 'admin'),
('framework', 'knowledgePoint', '系统架构', 10, 'admin'),
('framework', 'knowledgePoint', '编程语言', 11, 'admin'),
('framework', 'knowledgePoint', '网络协议', 12, 'admin'),
('framework', 'knowledgePoint', '软件工程', 13, 'admin'),
('framework', 'knowledgePoint', '运维部署', 14, 'admin'),
('framework', 'knowledgePoint', '项目管理', 15, 'admin');

