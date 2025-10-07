-- 创建项目模板表
CREATE TABLE km_project_template (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    domain VARCHAR(100) NOT NULL COMMENT '项目分类/领域',
    name VARCHAR(200) NOT NULL COMMENT '模板名称',
    description TEXT COMMENT '模板描述',
    requirements TEXT COMMENT '功能性需求（JSON格式）',
    common_features TEXT COMMENT '非功能性需求/通用特性（JSON格式）',
    -- 标准字段
    rec_creator VARCHAR(50) COMMENT '创建人',
    rec_create_time DATETIME COMMENT '创建时间',
    rec_revisor VARCHAR(50) COMMENT '修改人',
    rec_revise_time DATETIME COMMENT '修改时间',
    arch_flag TINYINT DEFAULT 0 COMMENT '删除标记：0-未删除，1-已删除',
    KEY idx_domain (domain),
    KEY idx_rec_creator (rec_creator),
    KEY idx_arch_flag (arch_flag)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='项目模板表';

-- 注意：项目模板由每个用户自己创建，不插入预置数据

