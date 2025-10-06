# Deploy 目录说明

本目录包含部署到服务器时需要的脚本和配置文件。

## 📁 文件说明

### Shell 脚本（Linux服务器使用）

- `start.sh` - 启动后端服务
- `stop.sh` - 停止后端服务
- `restart.sh` - 重启后端服务

### 配置文件

- `nginx.conf` - Nginx配置示例（参考用）

## 📤 上传说明

将以下文件上传到服务器 `/www/wwwroot/knowledge-backend/` 目录：
- start.sh
- stop.sh
- restart.sh

## ⚙️ 使用方法

```bash
# 进入目录
cd /www/wwwroot/knowledge-backend

# 赋予执行权限
chmod +x start.sh stop.sh restart.sh

# 启动服务
./start.sh

# 停止服务
./stop.sh

# 重启服务
./restart.sh
```

## 📖 详细部署文档

请查看项目根目录的 `部署说明.md` 文件。

