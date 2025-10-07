# MySQL 5.7 数据库初始化指南

## 问题说明

之前的SQL文件存在编码问题（中文注释显示为乱码），现在提供MySQL 5.7完全兼容的版本。

## 执行步骤

### 方法1：命令行执行（推荐）

```bash
# 1. 登录MySQL
mysql -u root -p

# 2. 创建数据库（如果还没创建）
CREATE DATABASE IF NOT EXISTS knowledge_management 
    DEFAULT CHARACTER SET utf8mb4 
    COLLATE utf8mb4_unicode_ci;

# 3. 使用数据库
USE knowledge_management;

# 4. 执行初始化脚本
source D:/项目本地/个人知识管理系统/backed/src/main/resources/db/mysql57_init_user.sql

# 或者直接执行
\. D:/项目本地/个人知识管理系统/backed/src/main/resources/db/mysql57_init_user.sql
```

### 方法2：一行命令执行

```bash
mysql -u root -p knowledge_management < backed/src/main/resources/db/mysql57_init_user.sql
```

### 方法3：使用MySQL Workbench

1. 打开 MySQL Workbench
2. 连接到本地MySQL服务器
3. 选择 `knowledge_management` 数据库
4. 打开文件：`backed/src/main/resources/db/mysql57_init_user.sql`
5. 点击执行按钮（⚡图标）

## 验证是否成功

执行以下SQL查询：

```sql
-- 查看用户是否插入成功
SELECT * FROM sys_user WHERE username = 'admin';

-- 应该显示：
-- id: 1
-- username: admin
-- password: 827ccb0eea8a706c4c34a16891f84e7b
-- nickname: Administrator
-- email: admin@example.com
-- status: 1
```

## 登录凭据

执行成功后，使用以下信息登录：

| 字段 | 值 |
|------|-----|
| 用户名 | `admin` |
| 密码 | `12345` |
| 邮箱 | `admin@example.com` |

⚠️ **重要：密码是 `12345`（5位数字），不是 `123456`**

## 密码MD5验证

如果需要验证密码是否正确，可以在浏览器控制台执行：

```javascript
// 方法1：使用在线MD5工具
// 访问：https://www.md5hashgenerator.com/
// 输入：12345
// 结果应该是：827ccb0eea8a706c4c34a16891f84e7b

// 方法2：使用浏览器控制台（需要先引入crypto-js或使用Java后端）
```

## MySQL 5.7 特性说明

此SQL文件兼容MySQL 5.7的所有特性：

1. ✅ 使用 `utf8mb4` 字符集（支持emoji和特殊字符）
2. ✅ 使用 `DEFAULT CURRENT_TIMESTAMP` 和 `ON UPDATE CURRENT_TIMESTAMP`
3. ✅ 避免使用MySQL 8.0+的新特性
4. ✅ 使用英文注释避免编码问题

## 常见问题排查

### 问题1：登录仍然失败

**解决步骤：**

```sql
-- 1. 检查用户是否存在
SELECT * FROM sys_user WHERE username = 'admin';

-- 2. 检查密码MD5值
SELECT username, password FROM sys_user WHERE username = 'admin';
-- 应该显示：password = 827ccb0eea8a706c4c34a16891f84e7b

-- 3. 如果密码不对，手动更新
UPDATE sys_user 
SET password = '827ccb0eea8a706c4c34a16891f84e7b' 
WHERE username = 'admin';
```

### 问题2：表已存在错误

**解决方法：**

```sql
-- 方案1：删除重建（会丢失数据）
DROP TABLE IF EXISTS sys_user;
-- 然后重新执行初始化脚本

-- 方案2：仅插入admin用户
INSERT INTO sys_user 
    (username, password, nickname, email, status, rec_creator, rec_create_time) 
VALUES 
    ('admin', '827ccb0eea8a706c4c34a16891f84e7b', 'Administrator', 'admin@example.com', 1, 'system', NOW())
ON DUPLICATE KEY UPDATE 
    password = '827ccb0eea8a706c4c34a16891f84e7b',
    email = 'admin@example.com';
```

### 问题3：字符集错误

**解决方法：**

```sql
-- 修改数据库字符集
ALTER DATABASE knowledge_management 
    CHARACTER SET utf8mb4 
    COLLATE utf8mb4_unicode_ci;

-- 修改表字符集
ALTER TABLE sys_user 
    CONVERT TO CHARACTER SET utf8mb4 
    COLLATE utf8mb4_unicode_ci;
```

### 问题4：后端连接不上

**检查配置：** `backed/src/main/resources/application.properties`

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/knowledge_management?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
```

## 测试登录

### 前端测试

1. 启动前端：`npm run dev`
2. 访问：`http://localhost:5173/login`
3. 输入：
   - 用户名：`admin`
   - 密码：`12345`
4. 点击"登录"按钮
5. 应该成功登录并跳转到首页

### 后端API测试（使用Postman或curl）

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "12345"
  }'
```

**成功响应：**
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiJ9...",
    "user": {
      "id": 1,
      "username": "admin",
      "nickname": "Administrator",
      "email": "admin@example.com"
    }
  }
}
```

**失败响应：**
```json
{
  "code": 500,
  "message": "用户名或密码错误",
  "data": null
}
```

## 完成清单

执行完成后请确认：

- [ ] sys_user表创建成功
- [ ] admin用户插入成功
- [ ] 密码MD5值正确：`827ccb0eea8a706c4c34a16891f84e7b`
- [ ] 可以使用 admin/12345 登录
- [ ] 后端服务正常启动
- [ ] 前端可以成功登录

---

**创建时间：** 2025-10-07  
**MySQL版本：** 5.7.x  
**字符集：** utf8mb4  
**状态：** ✅ 已就绪


