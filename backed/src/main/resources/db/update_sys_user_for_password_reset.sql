DROP TABLE IF EXISTS sys_user;
CREATE TABLE sys_user (
    id bigint NOT NULL AUTO_INCREMENT COMMENT '�û�ID',
    username varchar(50) NOT NULL COMMENT '�û���',
    password varchar(100) NOT NULL COMMENT '����(MD5����)',
    nickname varchar(50) COMMENT '�ǳ�',
    email varchar(100) COMMENT '����',
    phone varchar(20) COMMENT '�ֻ���',
    avatar varchar(255) COMMENT 'ͷ��URL',
    status int DEFAULT 1 COMMENT '״̬��1-������0-����',
    last_login_time datetime COMMENT '����¼ʱ��',
    rec_creator varchar(50) COMMENT '������',
    rec_create_time datetime DEFAULT CURRENT_TIMESTAMP COMMENT '����ʱ��',
    rec_revisor varchar(50) COMMENT '�޸���',
    rec_revise_time datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '�޸�ʱ��',
    arch_flag tinyint DEFAULT 0 COMMENT 'ɾ����־��0-δɾ����1-��ɾ��',
    PRIMARY KEY (id),
    UNIQUE KEY username (username),
    KEY idx_username (username),
    KEY idx_email (email),
    KEY idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='ϵͳ�û���';

INSERT INTO sys_user 
    (id, username, password, nickname, email, phone, avatar, status, last_login_time, rec_creator, rec_create_time, rec_revisor, rec_revise_time, arch_flag) 
VALUES 
    (1, 'admin', '827ccb0eea8a706c4c34a16891f84e7b', '��һ��', 'admin@example.com', null, null, 1, '2025-10-07 18:43:43', 'system', '2025-10-07 18:33:43', null, '2025-10-07 18:44:11', 0);
