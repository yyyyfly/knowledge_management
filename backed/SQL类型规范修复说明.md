# SQL 类型规范修复说明

## 🐛 **问题发现**

在生产环境部署时，发现时光轨迹功能报错：
```
java.sql.SQLSyntaxErrorException: Table 'knowledge_management.km_timeline_event' doesn't exist
```

经排查，问题不是表不存在，而是 **SQL 类型使用不规范** 导致生产环境（严格模式）下执行失败。

## 🔍 **问题根源**

### ❌ 错误写法（之前）

#### SQL 建表语句
```sql
`arch_flag` INT(1) NOT NULL DEFAULT '1'  -- ❌ INT类型用了字符串默认值
```

#### MyBatis Mapper
```xml
WHERE arch_flag = '1'  -- ❌ INT类型用了字符串比较
SET arch_flag = '0'    -- ❌ INT类型用了字符串赋值
```

### ✅ 正确写法（修复后）

#### SQL 建表语句
```sql
`arch_flag` INT(1) NOT NULL DEFAULT 1  -- ✅ INT类型用数字默认值
```

#### MyBatis Mapper
```xml
WHERE arch_flag = 1  -- ✅ INT类型用数字比较
SET arch_flag = 0    -- ✅ INT类型用数字赋值
```

## 📋 **修复内容**

### 1. TimelineEventMapper.xml
**文件**：`backed/src/main/resources/mapper/TimelineEventMapper.xml`

修改：
- `arch_flag = '1'` → `arch_flag = 1`
- `arch_flag = '0'` → `arch_flag = 0`

涉及语句：
- `selectById`
- `selectGlobalEvents`
- `selectMilestones`
- `selectByTopicId`
- `deleteById`
- `deleteByTopicId`
- `insert`

### 2. SQL 建表脚本

#### km_topic.sql
**文件**：`backed/src/main/resources/db/km_topic.sql`

修改：
- `arch_flag` INT(1) NOT NULL DEFAULT `'1'` → DEFAULT `1`

#### km_timeline_event.sql
**文件**：`backed/src/main/resources/db/km_timeline_event.sql`

修改：
- `arch_flag` INT(1) NOT NULL DEFAULT `'1'` → DEFAULT `1`

#### 创建时光轨迹表.sql
**文件**：`backed/创建时光轨迹表.sql`

修改：
- 两处 `arch_flag` INT(1) NOT NULL DEFAULT `'1'` → DEFAULT `1`

## 🎯 **类型规范总结**

| 数据类型 | SQL 默认值 | Mapper 查询/更新 | 示例 |
|---------|-----------|----------------|------|
| **INT** | 数字（无引号） | 数字（无引号） | `DEFAULT 1`<br>`WHERE id = 1` |
| **VARCHAR** | 字符串（有引号） | 字符串（有引号） | `DEFAULT 'system'`<br>`WHERE type = 'global'` |
| **DATETIME** | 函数/字符串 | 字符串（有引号） | `DEFAULT CURRENT_TIMESTAMP`<br>`WHERE date > '2024-01-01'` |

## 💡 **为什么会这样？**

### 开发环境（宽松模式）
- MySQL 默认设置较宽松
- 自动进行类型转换：`'1'` → `1`
- 不会报错，但性能较差

### 生产环境（严格模式）
- 通常开启 `STRICT_TRANS_TABLES` 模式
- 类型不匹配直接报错
- 这是**正确且推荐**的设置

### 严格模式的好处
1. ✅ **性能更好**：避免隐式类型转换
2. ✅ **错误发现早**：开发阶段就发现问题
3. ✅ **数据更安全**：防止类型错误导致的数据异常

## 🚀 **部署步骤**

### 已部署的环境
如果生产环境已经部署了旧版本，需要：

1. **重新执行建表脚本**（先删除旧表）：
```bash
mysql -u root -p knowledge_management < backed/创建时光轨迹表.sql
```

2. **重新编译后端**：
```bash
cd backed
mvn clean package
```

3. **重启后端服务**

4. **刷新前端页面**

### 新环境
直接执行修复后的 SQL 脚本即可：
```bash
mysql -u root -p knowledge_management < backed/创建时光轨迹表.sql
```

## 📝 **开发规范建议**

### 1. SQL 建表规范
- INT 类型：`DEFAULT 1` ✅
- VARCHAR 类型：`DEFAULT 'system'` ✅
- 日期时间：`DEFAULT CURRENT_TIMESTAMP` ✅

### 2. MyBatis Mapper 规范
```xml
<!-- INT 类型 -->
<insert>
    arch_flag VALUES (1)  <!-- ✅ 数字 -->
</insert>
<select>
    WHERE arch_flag = 1   <!-- ✅ 数字 -->
</select>

<!-- VARCHAR 类型 -->
<select>
    WHERE type = 'global' <!-- ✅ 字符串 -->
</select>
```

### 3. 参考现有代码
写新功能时，参考项目中已有的 Mapper：
- `NoteMapper.xml`
- `UserMapper.xml`
- `ProjectMapper.xml`

都是正确的写法，可以作为模板。

## ✅ **验证方法**

### 1. 检查 SQL 文件
```bash
# 查找所有 INT 类型使用字符串默认值的情况
grep -r "INT.*DEFAULT '[0-9]'" backed/src/main/resources/db/
```
应该没有任何结果 ✅

### 2. 检查 Mapper 文件
```bash
# 查找所有 arch_flag 使用字符串的情况
grep -r "arch_flag.*=.*'[0-9]'" backed/src/main/resources/mapper/
```
应该没有任何结果 ✅

### 3. 本地启用严格模式测试
在本地 MySQL 配置文件添加：
```ini
[mysqld]
sql_mode=STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION
```

重启 MySQL，再次测试功能是否正常。

## 📅 **修复记录**

- **日期**：2025-10-08
- **版本**：v1.7.0
- **影响范围**：
  - TimelineEventMapper.xml（7处修改）
  - km_topic.sql（1处修改）
  - km_timeline_event.sql（1处修改）
  - 创建时光轨迹表.sql（2处修改）

## 🎓 **经验教训**

1. **类型要严格匹配**：INT 就用数字，VARCHAR 才用字符串
2. **参考现有代码**：保持项目代码风格统一
3. **本地开严格模式**：尽早发现生产环境可能出现的问题
4. **代码审查重要**：新功能上线前仔细检查类型使用

---

**总结**：这次修复解决了时光轨迹功能在生产环境的部署问题，统一了项目中 SQL 类型使用规范，为后续开发提供了参考标准。

