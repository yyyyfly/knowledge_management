#!/bin/bash
# 启动知识管理系统后端服务

# 进入应用目录
cd /www/wwwroot/knowledge-backend

# 创建日志目录
mkdir -p logs

echo "======================================================================"
echo "知识管理系统 - 启动脚本"
echo "======================================================================"

# 配置Java路径（宝塔面板默认安装路径）
if [ -d "/www/server/java/jdk1.8.0_371" ]; then
    export JAVA_HOME=/www/server/java/jdk1.8.0_371
    export PATH=$JAVA_HOME/bin:$PATH
    echo "✓ 使用宝塔Java: $JAVA_HOME"
elif [ -d "/www/server/java/jdk1.8" ]; then
    export JAVA_HOME=/www/server/java/jdk1.8
    export PATH=$JAVA_HOME/bin:$PATH
    echo "✓ 使用宝塔Java: $JAVA_HOME"
fi

# 检查Java是否可用
if ! command -v java &> /dev/null; then
    echo "✗ 错误：未找到Java"
    echo "请检查Java安装路径，当前尝试的路径："
    echo "  /www/server/java/jdk1.8.0_371"
    echo "  /www/server/java/jdk1.8"
    echo ""
    echo "如果Java安装在其他位置，请修改此脚本中的JAVA_HOME路径"
    exit 1
fi

echo "✓ Java版本："
java -version

# 检查jar包是否存在
if [ ! -f "knowledge-management-system-1.0.0.jar" ]; then
    echo "✗ 错误：未找到jar包文件"
    echo "请确保 knowledge-management-system-1.0.0.jar 已上传到当前目录"
    exit 1
fi

echo "✓ jar包文件存在"

# 检查是否已经在运行
if [ -f app.pid ]; then
    pid=$(cat app.pid)
    if ps -p $pid > /dev/null 2>&1; then
        echo "⚠ 应用已经在运行"
        echo "进程ID: $pid"
        echo "如需重启，请先执行: ./stop.sh"
        exit 1
    else
        echo "清理旧的PID文件"
        rm app.pid
    fi
fi

# 启动应用
echo ""
echo "正在启动应用..."
echo "======================================================================"

nohup java -jar knowledge-management-system-1.0.0.jar --spring.profiles.active=prod > logs/app.log 2>&1 &

# 保存进程ID
echo $! > app.pid

# 等待几秒检查启动状态
echo "等待应用启动..."
sleep 5

if ps -p $(cat app.pid) > /dev/null 2>&1; then
    echo ""
    echo "======================================================================"
    echo "✓ 应用启动成功！"
    echo "======================================================================"
    echo "进程ID: $(cat app.pid)"
    echo "控制台日志: logs/app.log"
    echo "应用日志: logs/application.log"
    echo ""
    echo "查看实时日志："
    echo "  tail -f logs/app.log"
    echo "  tail -f logs/application.log"
    echo ""
    echo "停止应用："
    echo "  ./stop.sh"
    echo "======================================================================"
else
    echo ""
    echo "======================================================================"
    echo "✗ 应用启动失败！"
    echo "======================================================================"
    echo "请查看错误日志："
    echo "  cat logs/app.log"
    echo ""
    echo "常见问题："
    echo "1. 检查Java是否正确安装"
    echo "2. 检查数据库配置是否正确"
    echo "3. 检查端口8080是否被占用"
    echo "======================================================================"
    rm -f app.pid
    exit 1
fi
