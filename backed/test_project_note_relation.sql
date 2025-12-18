-- 项目笔记关联功能测试脚本

-- ====================================
-- 1. 查看现有数据
-- ====================================

-- 查看所有项目
SELECT id, name, category FROM project WHERE arch_flag = '1' LIMIT 10;

-- 查看所有笔记（根据实际表名调整）
-- SELECT id, title, type FROM fragment_note WHERE arch_flag = '1' LIMIT 5;
-- SELECT id, title, type FROM framework_note WHERE arch_flag = '1' LIMIT 5;
-- SELECT id, title FROM study_note WHERE arch_flag = '1' LIMIT 5;

-- ====================================
-- 2. 测试数据准备（可选）
-- ====================================

-- 如果需要，可以插入测试项目
-- INSERT INTO project (name, description, category, status, rec_creator, arch_flag) 
-- VALUES ('测试项目', '用于测试笔记关联功能', 'defense', 'active', 'admin', '1');

-- 获取刚插入的项目ID
-- SELECT LAST_INSERT_ID();

-- ====================================
-- 3. 测试关联功能
-- ====================================

-- 假设项目ID=1，笔记ID=5

-- 3.1 添加关联（手动插入测试）
INSERT INTO project_note_relation 
(project_id, note_id, note_type, rec_creator, arch_flag) 
VALUES 
(1, 5, 'framework', 'admin', '1');

-- 3.2 查看关联记录
SELECT * FROM project_note_relation WHERE arch_flag = '1';

-- 3.3 查看特定项目的关联
SELECT * FROM project_note_relation WHERE project_id = 1 AND arch_flag = '1';

-- 3.4 查看特定笔记的关联
SELECT * FROM project_note_relation WHERE note_id = 5 AND arch_flag = '1';

-- ====================================
-- 4. 测试查询接口
-- ====================================

-- 4.1 查询项目1的所有笔记ID
SELECT note_id FROM project_note_relation 
WHERE project_id = 1 AND arch_flag = '1'
ORDER BY rec_create_time DESC;

-- 4.2 查询笔记5关联的所有项目ID
SELECT project_id FROM project_note_relation 
WHERE note_id = 5 AND arch_flag = '1'
ORDER BY rec_create_time DESC;

-- 4.3 统计项目1的笔记数量
SELECT COUNT(*) as note_count FROM project_note_relation 
WHERE project_id = 1 AND arch_flag = '1';

-- 4.4 统计笔记5关联的项目数量
SELECT COUNT(*) as project_count FROM project_note_relation 
WHERE note_id = 5 AND arch_flag = '1';

-- ====================================
-- 5. 测试重复关联检测
-- ====================================

-- 尝试再次插入相同的关联（应该失败）
-- INSERT INTO project_note_relation 
-- (project_id, note_id, note_type, rec_creator, arch_flag) 
-- VALUES 
-- (1, 5, 'framework', 'admin', '1');
-- 预期结果: ERROR 1062 (23000): Duplicate entry '1-5' for key 'uk_project_note'

-- ====================================
-- 6. 测试删除关联
-- ====================================

-- 6.1 删除特定关联
-- DELETE FROM project_note_relation 
-- WHERE project_id = 1 AND note_id = 5;

-- 6.2 删除项目的所有关联
-- DELETE FROM project_note_relation WHERE project_id = 1;

-- 6.3 删除笔记的所有关联
-- DELETE FROM project_note_relation WHERE note_id = 5;

-- ====================================
-- 7. 查看关联统计
-- ====================================

-- 7.1 每个项目的笔记数量
SELECT 
    p.id,
    p.name,
    COUNT(pnr.note_id) as note_count
FROM project p
LEFT JOIN project_note_relation pnr ON p.id = pnr.project_id AND pnr.arch_flag = '1'
WHERE p.arch_flag = '1'
GROUP BY p.id, p.name
ORDER BY note_count DESC;

-- 7.2 笔记类型分布
SELECT 
    note_type,
    COUNT(*) as count
FROM project_note_relation
WHERE arch_flag = '1'
GROUP BY note_type
ORDER BY count DESC;

-- 7.3 最近关联的记录
SELECT 
    pnr.*,
    p.name as project_name
FROM project_note_relation pnr
LEFT JOIN project p ON pnr.project_id = p.id
WHERE pnr.arch_flag = '1'
ORDER BY pnr.rec_create_time DESC
LIMIT 10;

-- ====================================
-- 8. 数据清理（测试完成后）
-- ====================================

-- 清空所有测试关联数据
-- TRUNCATE TABLE project_note_relation;

-- 或者删除特定测试数据
-- DELETE FROM project_note_relation WHERE project_id = 1;

-- ====================================
-- 9. 索引性能测试
-- ====================================

-- 查看表的索引
SHOW INDEX FROM project_note_relation;

-- 查看查询执行计划
EXPLAIN SELECT note_id FROM project_note_relation 
WHERE project_id = 1 AND arch_flag = '1';

EXPLAIN SELECT project_id FROM project_note_relation 
WHERE note_id = 5 AND arch_flag = '1';

-- ====================================
-- 10. 完整性测试脚本
-- ====================================

-- 批量插入测试数据
-- INSERT INTO project_note_relation 
-- (project_id, note_id, note_type, rec_creator, arch_flag) 
-- VALUES 
-- (1, 1, 'fragment', 'admin', '1'),
-- (1, 2, 'framework', 'admin', '1'),
-- (1, 3, 'study', 'admin', '1'),
-- (2, 1, 'fragment', 'admin', '1'),
-- (2, 4, 'exercise', 'admin', '1'),
-- (3, 5, 'practical', 'admin', '1');

-- 验证数据
SELECT 
    project_id,
    COUNT(*) as note_count,
    GROUP_CONCAT(note_id ORDER BY note_id) as note_ids
FROM project_note_relation
WHERE arch_flag = '1'
GROUP BY project_id;

-- ====================================
-- 说明
-- ====================================
-- 1. 所有带注释的SQL都可以取消注释执行
-- 2. 根据实际的项目ID和笔记ID调整测试数据
-- 3. 测试完成后记得清理测试数据
-- 4. 生产环境中不要使用TRUNCATE





























