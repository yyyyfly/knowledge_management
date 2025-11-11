# Framework 重命名为 Skill 完整迁移指南

## 一、数据库迁移步骤

### 1. 执行数据库迁移脚本

按顺序执行以下SQL脚本：

```bash
# 1. 创建指导大纲表
mysql -u your_username -p your_database < src/main/resources/db/create_guidance_outline_table.sql

# 2. 更新技能笔记字段
mysql -u your_username -p your_database < src/main/resources/db/update_framework_to_skill.sql

# 3. 更新note_config配置
mysql -u your_username -p your_database < src/main/resources/db/update_note_config_for_skill.sql

# 4. 迁移所有 framework 数据为 skill
mysql -u your_username -p your_database < src/main/resources/db/migrate_framework_to_skill.sql
```

### 2. 验证数据迁移结果

执行以下SQL查询验证：

```sql
-- 检查技能笔记数量
SELECT COUNT(*) as skill_notes_count FROM km_note WHERE type = 'skill';

-- 应该返回 0
SELECT COUNT(*) as framework_notes_count FROM km_note WHERE type = 'framework';

-- 检查配置表
SELECT * FROM km_note_config WHERE note_type = 'skill';

-- 应该返回空
SELECT * FROM km_note_config WHERE note_type = 'framework';

-- 检查指导大纲表是否创建成功
SHOW TABLES LIKE 'km_guidance_outline';
```

## 二、后端重启

数据库迁移完成后，重启后端服务：

```bash
cd backed
mvn clean package
java -jar target/knowledge-management-1.0.jar
```

或者在IDE中重新启动 `KnowledgeManagementApplication`

## 三、前端清理缓存

清理前端缓存和重新构建：

```bash
cd fronted

# 清理 node_modules（可选）
rm -rf node_modules
npm install

# 清理缓存
rm -rf .vite
rm -rf dist

# 重新启动开发服务器
npm run dev
```

## 四、主要变更内容

### 1. 数据库变更

#### km_note 表
- `type='framework'` → `type='skill'`
- `subject_type` → `skill_type`
- `knowledge_point` → `skill_point`
- 新增字段：
  - `expected_description` TEXT - 预期描述
  - `thinking_summary` TEXT - 思考总结
  - `final_effect` TEXT - 最终效果

#### km_note_config 表
- `note_type='framework'` → `note_type='skill'`
- `config_type='subjectType'` → `config_type='skillType'`
- `config_type='knowledgePoint'` → `config_type='skillPoint'`

#### 新增表 km_guidance_outline
- `id` - 主键
- `subject_name` - 学科名称
- `subject_category` - 学科分类
- `knowledge_tree` - 知识体系树（JSON）
- `tags` - 标签
- 标准字段：`rec_creator`, `rec_create_time`, `rec_revisor`, `rec_revise_time`, `arch_flag`

### 2. 后端变更

- ✅ Controller: 无需修改（后端没有硬编码 'framework' 字符串）
- ✅ Service: 无需修改
- ✅ Entity: 字段已更新（skillType, skillPoint, expectedDescription, thinkingSummary, finalEffect）
- ✅ Mapper XML: 已更新字段映射
- ✅ 新增 GuidanceOutlineController/Service/Mapper

### 3. 前端变更

#### 路由
- `/framework-notes` → `/skill-notes`

#### 导航菜单
- "框架笔记" → "技能笔记"

#### 所有涉及的文件
- `router/index.ts` - 路由路径更新
- `components/Sidebar.vue` - 菜单项更新
- `views/materials/SkillNotes.vue` - API调用更新
- `views/daily/MaterialRecord.vue` - 类型判断和字段更新
- `views/daily/ProjectRecord.vue` - 类型样式更新
- `views/daily/ReadyToDeploy.vue` - 类型显示更新
- `views/overview/DataOverview.vue` - 统计数据更新
- `api/guidanceOutline.ts` - 新增指导大纲API
- `views/overview/GuidanceOutline.vue` - 新增指导大纲页面
- `components/TreeNode.vue` - 新增树节点组件
- `mock/notes.ts` - 类型定义更新
- `mock/frameworkNotes.ts` - 重命名为技能笔记

## 五、功能测试清单

### 1. 技能笔记功能测试

- [ ] 访问 `/skill-notes` 页面
- [ ] 创建新的技能笔记
  - [ ] 填写标题、摘要
  - [ ] 选择技能类型
  - [ ] 选择技能点
  - [ ] 填写预期描述（富文本）
  - [ ] 填写思考总结（富文本）
  - [ ] 填写最终效果（富文本）
- [ ] 编辑已有技能笔记
- [ ] 删除技能笔记
- [ ] 搜索和筛选技能笔记

### 2. 素材记录测试

- [ ] 在素材记录页面选择"技能笔记"类型
- [ ] 验证技能类型和技能点选择器正常工作
- [ ] 验证三个富文本编辑器正常显示
- [ ] 验证保存时不要求填写 content 字段
- [ ] 验证加载已有技能笔记数据正常

### 3. 指导大纲功能测试

- [ ] 访问 `/overview/guidance-outline` 页面
- [ ] 创建新学科
- [ ] 添加学科分类
- [ ] 构建知识体系树
  - [ ] 添加一级节点
  - [ ] 添加子节点
  - [ ] 编辑节点
  - [ ] 删除节点
- [ ] 保存知识树
- [ ] 编辑和删除学科

### 4. 数据统计测试

- [ ] 主仪表盘显示技能笔记统计
- [ ] 技能笔记数量正确
- [ ] 最近更新时间正确

## 六、常见问题

### Q1: 前端显示 Network Error
**A:** 检查后端服务是否启动，确认端口 8080 可访问

### Q2: 后端启动报错 "Table 'km_guidance_outline' doesn't exist"
**A:** 执行 `create_guidance_outline_table.sql` 创建表

### Q3: 技能笔记保存提示字段不存在
**A:** 执行 `update_framework_to_skill.sql` 添加新字段

### Q4: 旧的框架笔记数据不见了
**A:** 执行 `migrate_framework_to_skill.sql` 迁移数据

### Q5: 配置管理器中的技能类型/技能点为空
**A:** 执行 `update_note_config_for_skill.sql` 更新配置表

## 七、回滚方案（如果需要）

如果迁移后发现问题，可以执行以下SQL回滚：

```sql
-- 回滚笔记类型
UPDATE km_note SET type = 'framework' WHERE type = 'skill';

-- 回滚配置类型
UPDATE km_note_config SET note_type = 'framework' WHERE note_type = 'skill';
UPDATE km_note_config SET config_type = 'subjectType' WHERE config_type = 'skillType';
UPDATE km_note_config SET config_type = 'knowledgePoint' WHERE config_type = 'skillPoint';

-- 删除新增字段（需要先备份数据）
-- ALTER TABLE km_note DROP COLUMN expected_description;
-- ALTER TABLE km_note DROP COLUMN thinking_summary;
-- ALTER TABLE km_note DROP COLUMN final_effect;
```

## 八、总结

本次迁移完成了以下内容：

1. ✅ 数据库层面彻底重命名 framework → skill
2. ✅ 后端代码适配新的字段结构
3. ✅ 前端所有页面、路由、组件更新
4. ✅ 新增指导大纲功能模块
5. ✅ 技能笔记使用三个专用富文本字段

所有修改已完成，执行上述迁移步骤后即可正常使用！

