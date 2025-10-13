-- 打卡记录重复问题修复脚本
-- 
-- 问题描述：
-- 当用户取消打卡后再次打卡时，会出现唯一键冲突错误
-- 原因：取消打卡时将记录标记为归档(arch_flag=1)，但唯一键约束不包含arch_flag字段
-- 
-- 解决方案：
-- 1. 将取消打卡改为物理删除记录（已在代码中修复）
-- 2. 清理已存在的归档记录，避免冲突

-- 查看当前归档的打卡记录
SELECT * FROM km_checkin_record WHERE arch_flag = 1;

-- 清理所有归档的打卡记录（建议执行）
DELETE FROM km_checkin_record WHERE arch_flag = 1;

-- 或者，如果需要保留历史记录，可以修改唯一键约束，将arch_flag包含进去
-- 但不推荐这种方式，因为会使逻辑复杂化

-- 验证清理结果
SELECT COUNT(*) as archived_count FROM km_checkin_record WHERE arch_flag = 1;
-- 应该返回 0


