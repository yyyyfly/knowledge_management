#!/bin/bash
# 停止知识管理系统后端服务

cd /www/wwwroot/knowledge-backend

# 配置Java路径
if [ -d "/www/server/java/jdk1.8.0_371" ]; then
    export JAVA_HOME=/www/server/java/jdk1.8.0_371
    export PATH=$JAVA_HOME/bin:$PATH
elif [ -d "/www/server/java/jdk1.8" ]; then
    export JAVA_HOME=/www/server/java/jdk1.8
    export PATH=$JAVA_HOME/bin:$PATH
fi

if [ ! -f app.pid ]; then
    echo "未找到运行的应用（app.pid文件不存在）"
    exit 1
fi

pid=$(cat app.pid)

if ! ps -p $pid > /dev/null 2>&1; then
    echo "进程不存在，清理PID文件"
    rm app.pid
    exit 1
fi

echo "正在停止应用，进程ID: $pid"
kill $pid

# 等待进程结束
count=0
while ps -p $pid > /dev/null 2>&1; do
    sleep 1
    count=$((count+1))
    if [ $count -gt 10 ]; then
        echo "进程未响应，强制终止"
        kill -9 $pid
        break
    fi
done

rm app.pid
echo "✓ 应用已停止"

