-- ===========================================================
-- 用户表密码重置功能支持SQL
-- 功能：确保sys_user表有email字段，用于密码重置
-- 创建时间：2025-10-07
-- ===========================================================

-- 检查并创建sys_user表（如果不存在）
CREATE TABLE IF NOT EXISTS sys_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(100) NOT NULL COMMENT '密码(MD5加密)',
    nickname VARCHAR(50) COMMENT '昵称',
    email VARCHAR(100) COMMENT '邮箱',
    phone VARCHAR(20) COMMENT '手机号',
    avatar VARCHAR(255) COMMENT '头像URL',
    status INT DEFAULT 1 COMMENT '状态：1-正常，0-禁用',
    last_login_time DATETIME COMMENT '最后登录时间',
    rec_creator VARCHAR(50) COMMENT '创建人',
    rec_create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    rec_revisor VARCHAR(50) COMMENT '修改人',
    rec_revise_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    arch_flag TINYINT DEFAULT 0 COMMENT '删除标志：0-未删除，1-已删除',
    INDEX idx_username (username),
    INDEX idx_email (email),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户表';

-- 如果表已存在但缺少email字段，则添加
-- 注意：如果字段已存在，ALTER语句会报错，但这是安全的
-- 在生产环境中，建议先检查字段是否存在再执行
ALTER TABLE sys_user 
ADD COLUMN IF NOT EXISTS email VARCHAR(100) COMMENT '邮箱' AFTER nickname;

-- 如果表已存在但缺少phone字段，则添加
ALTER TABLE sys_user 
ADD COLUMN IF NOT EXISTS phone VARCHAR(20) COMMENT '手机号' AFTER email;

-- 确保admin用户存在（密码：12345 的MD5值）
INSERT INTO sys_user (username, password, nickname, email, status, rec_creator, rec_create_time)
VALUES ('admin', '827ccb0eea8a706c4c34a16891f84e7b', '管理员', 'admin@example.com', 1, 'system', NOW())
ON DUPLICATE KEY UPDATE 
    email = VALUES(email),
    rec_revise_time = NOW();

-- 创建索引（如果不存在）
-- 注意：如果索引已存在，CREATE INDEX语句会报错
-- 在MySQL 5.7及以上版本，可以使用 IF NOT EXISTS
CREATE INDEX IF NOT EXISTS idx_email ON sys_user(email);
CREATE INDEX IF NOT EXISTS idx_status ON sys_user(status);

-- 验证结果
SELECT 'sys_user表结构更新完成' AS status;
SELECT COUNT(*) AS user_count FROM sys_user;
