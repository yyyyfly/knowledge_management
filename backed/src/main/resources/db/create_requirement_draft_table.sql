-- 创建需求草案表
CREATE TABLE IF NOT EXISTS km_requirement_draft (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    version VARCHAR(50) NOT NULL COMMENT '版本号，如v3.0、v4.0等',
    title VARCHAR(200) NOT NULL COMMENT '标题，简短描述功能想法',
    description MEDIUMTEXT COMMENT '详细描述，详细的功能说明和实现思路',
    status VARCHAR(20) DEFAULT 'drafting' COMMENT '版本状态：drafting-草拟中,approved-已通过,abandoned-已废弃',
    sort_order INT DEFAULT 0 COMMENT '排序号，同一版本内的排序',
    rec_creator VARCHAR(50) COMMENT '创建人',
    rec_create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    rec_revisor VARCHAR(50) COMMENT '修改人',
    rec_revise_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    arch_flag INT DEFAULT 0 COMMENT '归档标志:0-未归档,1-已归档',
    INDEX idx_version (version),
    INDEX idx_status (status),
    INDEX idx_sort_order (sort_order),
    INDEX idx_create_time (rec_create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='需求草案表';

