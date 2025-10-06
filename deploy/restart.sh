#!/bin/bash
# 重启知识管理系统后端服务

cd /www/wwwroot/knowledge-backend

# 配置Java路径
if [ -d "/www/server/java/jdk1.8.0_371" ]; then
    export JAVA_HOME=/www/server/java/jdk1.8.0_371
    export PATH=$JAVA_HOME/bin:$PATH
elif [ -d "/www/server/java/jdk1.8" ]; then
    export JAVA_HOME=/www/server/java/jdk1.8
    export PATH=$JAVA_HOME/bin:$PATH
fi

echo "正在重启应用..."

# 停止服务
if [ -f app.pid ]; then
    ./stop.sh
    sleep 2
fi

# 启动服务
./start.sh

