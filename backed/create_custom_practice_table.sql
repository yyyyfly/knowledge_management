-- 创建定制模拟实战表（专项实战）
CREATE TABLE km_custom_practice (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    practice_name VARCHAR(200) NOT NULL COMMENT '实战名称',
    scenario_type VARCHAR(50) NOT NULL COMMENT '场景类型（考试/项目/竞赛）',
    scenario_detail VARCHAR(200) COMMENT '场景详情（具体考试名称、项目类型、竞赛名称等）',
    difficulty VARCHAR(20) COMMENT '难度等级（简单/中等/困难）',
    background TEXT COMMENT '背景说明（考试大纲、项目背景、竞赛规则等）',
    description TEXT COMMENT '详细描述',
    target_skills TEXT COMMENT '目标技能点（JSON格式）',
    tech_stack TEXT COMMENT '指定技术栈（JSON格式）',
    constraints TEXT COMMENT '限制条件（JSON格式：时间、资源、技术限制等）',
    practice_content TEXT COMMENT '实战内容（JSON格式）',
    evaluation_criteria TEXT COMMENT '评估标准（JSON格式）',
    reference_materials TEXT COMMENT '参考资料（JSON格式）',
    time_limit INT COMMENT '建议完成时间（分钟）',
    compatibility_notes TEXT COMMENT '模板兼容性说明',
    tags VARCHAR(500) COMMENT '标签（逗号分隔）',
    -- 标准字段
    rec_creator VARCHAR(50) COMMENT '创建人',
    rec_create_time DATETIME COMMENT '创建时间',
    rec_revisor VARCHAR(50) COMMENT '修改人',
    rec_revise_time DATETIME COMMENT '修改时间',
    arch_flag TINYINT DEFAULT 0 COMMENT '删除标记：0-未删除，1-已删除',
    KEY idx_scenario_type (scenario_type),
    KEY idx_difficulty (difficulty),
    KEY idx_rec_creator (rec_creator),
    KEY idx_arch_flag (arch_flag)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='专项实战表（针对特定考试、项目、竞赛的定制化训练）';

-- 注意：专项实战由每个用户自己创建，用于特定场景的专项训练
-- 场景类型包括但不限于：考试模拟、项目实战、竞赛训练等

