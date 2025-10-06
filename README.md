# 个人知识管理系统

一个基于 Spring Boot + Vue 3 的个人知识管理系统，支持项目管理、任务跟踪、笔记管理、荣誉记录等功能。

## 🎯 项目特点

- **前后端分离**：后端使用 Spring Boot，前端使用 Vue 3 + TypeScript
- **现代化UI**：基于 Tailwind CSS 的响应式设计
- **富文本编辑**：集成 TipTap 编辑器
- **数据可视化**：使用 Chart.js 进行数据展示
- **权限认证**：基于 JWT 的身份验证

## 📋 技术栈

### 后端
- Java 8
- Spring Boot 2.3.12
- MyBatis
- MySQL 5.7
- Druid 连接池
- JWT 认证

### 前端
- Vue 3
- TypeScript
- Vite
- Vue Router
- Axios
- Tailwind CSS
- TipTap Editor
- Chart.js

## 🚀 快速开始

### 本地开发

#### 前置要求
- JDK 1.8+
- Maven 3.6+
- Node.js 16+
- MySQL 5.7+

#### 后端启动

1. 创建数据库并导入数据：
```sql
CREATE DATABASE knowledge_management CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
```

2. 依次执行SQL文件：
   - `backed/src/main/resources/db/schema.sql`
   - `backed/src/main/resources/db/user-schema.sql`
   - `backed/src/main/resources/db/data.sql`

3. 修改配置文件 `backed/src/main/resources/application.properties`：
```properties
spring.datasource.username=root
spring.datasource.password=你的密码
```

4. 启动后端：
```bash
cd backed
mvn spring-boot:run
```

后端服务运行在：http://localhost:8080/api

#### 前端启动

1. 安装依赖：
```bash
cd fronted
npm install
```

2. 启动开发服务器：
```bash
npm run dev
```

前端应用运行在：http://localhost:5173

## 📦 生产部署

### 部署到宝塔面板

详细步骤请查看根目录的 `部署说明.md` 文档

#### 简要步骤

1. **准备环境**
   - 安装 OpenJDK 1.8
   - 安装 MySQL 5.7
   - 安装 Nginx

2. **配置文件**
   - 修改 `backed/src/main/resources/application-prod.properties` 数据库密码
   - 创建 `fronted/.env.production` 配置API地址（参考 `deploy/环境配置说明.txt`）

3. **打包项目**
   - 运行 `打包部署.bat` 或手动打包

4. **上传部署**
   - 上传 jar 包到服务器
   - 上传前端静态文件
   - 配置 Nginx 反向代理
   - 启动后端服务

## 📁 项目结构

```
个人知识管理系统/
├── backed/                 # 后端项目
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/knowledge/
│   │   │   │       ├── controller/    # 控制器
│   │   │   │       ├── service/       # 业务层
│   │   │   │       ├── mapper/        # 数据访问层
│   │   │   │       ├── entity/        # 实体类
│   │   │   │       ├── common/        # 公共类
│   │   │   │       └── util/          # 工具类
│   │   │   └── resources/
│   │   │       ├── mapper/            # MyBatis XML
│   │   │       ├── db/                # 数据库脚本
│   │   │       ├── application.properties
│   │   │       └── application-prod.properties
│   │   └── test/
│   └── pom.xml
│
├── fronted/                # 前端项目
│   ├── src/
│   │   ├── api/            # API接口
│   │   ├── components/     # 组件
│   │   ├── views/          # 页面
│   │   ├── router/         # 路由
│   │   ├── services/       # 业务服务
│   │   ├── types/          # 类型定义
│   │   ├── mock/           # Mock数据
│   │   └── assets/         # 静态资源
│   ├── package.json
│   └── vite.config.cjs
│
├── deploy/                 # 部署相关文件
│   ├── start.sh            # 启动脚本
│   ├── stop.sh             # 停止脚本
│   ├── restart.sh          # 重启脚本
│   ├── nginx.conf          # Nginx配置示例
│   ├── 部署说明.md         # 详细部署文档
│   ├── 快速部署checklist.md
│   └── 环境配置说明.txt
│
├── 打包部署.bat            # Windows打包脚本
├── .gitignore
└── README.md
```

## 🔐 默认账号

- 用户名：`admin`
- 密码：`12345`

⚠️ 生产环境请及时修改默认密码！

## 📝 功能模块

### 1. 项目管理
- 工程建设项目
- 战争行动项目
- 外交活动项目

### 2. 任务管理
- 任务创建与分配
- 执行进度跟踪
- 截止日期提醒

### 3. 笔记管理
- 框架笔记
- 求学笔记
- 背诵笔记
- 刷题笔记
- 实战笔记
- 碎片笔记

### 4. 荣誉战绩
- 荣誉记录
- 成就展示

### 5. 系统总结
- 日报、月报、季报、年报
- 满意度评分

## 🛠️ 开发工具

推荐使用以下工具进行开发：
- IDE：IntelliJ IDEA / VS Code
- API测试：Postman / Apifox
- 数据库管理：Navicat / DBeaver

## 📄 许可证

本项目仅供个人学习使用。

## 🤝 贡献

欢迎提交 Issue 和 Pull Request！

## 📞 联系方式

如有问题，请提交 Issue。

---

⭐ 如果这个项目对你有帮助，欢迎 Star！

