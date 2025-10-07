DROP TABLE IF EXISTS sys_user;
CREATE TABLE sys_user (
    id bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    username varchar(50) NOT NULL COMMENT '用户名',
    password varchar(100) NOT NULL COMMENT '密码(MD5加密)',
    nickname varchar(50) COMMENT '昵称',
    email varchar(100) COMMENT '邮箱',
    phone varchar(20) COMMENT '手机号',
    avatar varchar(255) COMMENT '头像URL',
    status int DEFAULT 1 COMMENT '状态：1-正常，0-禁用',
    last_login_time datetime COMMENT '最后登录时间',
    rec_creator varchar(50) COMMENT '创建人',
    rec_create_time datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    rec_revisor varchar(50) COMMENT '修改人',
    rec_revise_time datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    arch_flag tinyint DEFAULT 0 COMMENT '删除标志：0-未删除，1-已删除',
    PRIMARY KEY (id),
    UNIQUE KEY username (username),
    KEY idx_username (username),
    KEY idx_email (email),
    KEY idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统用户表';

INSERT INTO sys_user 
    (id, username, password, nickname, email, phone, avatar, status, last_login_time, rec_creator, rec_create_time, rec_revisor, rec_revise_time, arch_flag) 
VALUES 
    (1, 'admin', '827ccb0eea8a706c4c34a16891f84e7b', '邵一飞', 'admin@example.com', null, null, 1, '2025-10-07 18:43:43', 'system', '2025-10-07 18:33:43', null, '2025-10-07 18:44:11', 0);
