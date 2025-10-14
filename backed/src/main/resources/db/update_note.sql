

ALTER TABLE `km_note` ADD COLUMN review_count INT DEFAULT 0 COMMENT '巩固次数';
ALTER TABLE `km_note` ADD COLUMN last_review_time DATETIME COMMENT '最后巩固时间';
