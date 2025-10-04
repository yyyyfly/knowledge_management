-- 用户表
CREATE TABLE IF NOT EXISTS sys_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(200) NOT NULL COMMENT '密码（加密）',
    nickname VARCHAR(50) COMMENT '昵称',
    email VARCHAR(100) COMMENT '邮箱',
    phone VARCHAR(20) COMMENT '手机号',
    avatar VARCHAR(500) COMMENT '头像URL',
    status TINYINT DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
    last_login_time DATETIME COMMENT '最后登录时间',
    rec_creator VARCHAR(50) COMMENT '创建人',
    rec_create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    rec_revisor VARCHAR(50) COMMENT '修改人',
    rec_revise_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    arch_flag TINYINT DEFAULT 0 COMMENT '归档标志：0-未归档，1-已归档'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户表';

-- 插入默认管理员账号（密码：12345，使用简单加密）
INSERT INTO sys_user (username, password, nickname, status, rec_creator) 
VALUES ('admin', 'e10adc3949ba59abbe56e057f20f883e', 'Administrator', 1, 'system')
ON DUPLICATE KEY UPDATE password = 'e10adc3949ba59abbe56e057f20f883e';

