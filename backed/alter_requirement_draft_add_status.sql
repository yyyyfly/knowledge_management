-- 为需求草案表添加status字段
ALTER TABLE km_requirement_draft 
ADD COLUMN status VARCHAR(20) DEFAULT 'drafting' COMMENT '版本状态：drafting-草拟中,approved-已通过,abandoned-已废弃' 
AFTER description;

-- 添加索引
ALTER TABLE km_requirement_draft ADD INDEX idx_status (status);

-- 更新现有数据的状态为草拟中
UPDATE km_requirement_draft SET status = 'drafting' WHERE status IS NULL OR status = '';








