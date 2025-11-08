-- 创建项目笔记关联表
CREATE TABLE IF NOT EXISTS project_note_relation (
    id INT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    project_id INT NOT NULL COMMENT '项目ID',
    note_id INT NOT NULL COMMENT '笔记ID',
    note_type VARCHAR(50) NOT NULL COMMENT '笔记类型(fragment/framework/study/memorization/exercise/practical)',
    rec_creator VARCHAR(50) COMMENT '创建人',
    rec_create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    rec_revisor VARCHAR(50) COMMENT '修改人',
    rec_revise_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    arch_flag CHAR(1) DEFAULT '1' COMMENT '归档标志(1-正常 0-归档)',
    UNIQUE KEY uk_project_note (project_id, note_id) COMMENT '项目笔记唯一索引',
    INDEX idx_project (project_id) COMMENT '项目索引',
    INDEX idx_note (note_id) COMMENT '笔记索引',
    INDEX idx_note_type (note_type) COMMENT '笔记类型索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='项目笔记关联表';
















