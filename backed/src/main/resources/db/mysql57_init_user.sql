-- ============================================================
-- MySQL 5.7 Compatible User Table Initialization
-- Purpose: Create sys_user table and insert default admin user
-- Date: 2025-10-07
-- ============================================================

-- Drop existing table
DROP TABLE IF EXISTS sys_user;

-- Create sys_user table (MySQL 5.7 compatible)
CREATE TABLE sys_user (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'User ID',
    username VARCHAR(50) NOT NULL COMMENT 'Username',
    password VARCHAR(100) NOT NULL COMMENT 'Password (MD5 encrypted)',
    nickname VARCHAR(50) DEFAULT NULL COMMENT 'Nickname',
    email VARCHAR(100) DEFAULT NULL COMMENT 'Email',
    phone VARCHAR(20) DEFAULT NULL COMMENT 'Phone',
    avatar VARCHAR(255) DEFAULT NULL COMMENT 'Avatar URL',
    status INT DEFAULT 1 COMMENT 'Status: 1-Active, 0-Disabled',
    last_login_time DATETIME DEFAULT NULL COMMENT 'Last login time',
    rec_creator VARCHAR(50) DEFAULT NULL COMMENT 'Creator',
    rec_create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Create time',
    rec_revisor VARCHAR(50) DEFAULT NULL COMMENT 'Last revisor',
    rec_revise_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Last revise time',
    arch_flag TINYINT DEFAULT 0 COMMENT 'Delete flag: 0-Not deleted, 1-Deleted',
    PRIMARY KEY (id),
    UNIQUE KEY uk_username (username),
    KEY idx_email (email),
    KEY idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='System User Table';

-- Insert default admin user
-- Username: admin
-- Password: 12345 (MD5: 827ccb0eea8a706c4c34a16891f84e7b)
INSERT INTO sys_user 
    (username, password, nickname, email, status, rec_creator, rec_create_time) 
VALUES 
    ('admin', '827ccb0eea8a706c4c34a16891f84e7b', 'Administrator', 'admin@example.com', 1, 'system', NOW());

-- Verify the insertion
SELECT 
    id, 
    username, 
    nickname, 
    email, 
    status,
    'Password is MD5 encrypted' AS password_info
FROM sys_user 
WHERE username = 'admin';

-- Show password MD5 for verification
-- 12345 -> 827ccb0eea8a706c4c34a16891f84e7b
SELECT '12345 MD5 hash is: 827ccb0eea8a706c4c34a16891f84e7b' AS verification_info;


