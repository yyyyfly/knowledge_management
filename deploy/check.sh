#!/bin/bash
# 检查系统环境和配置

# 配置Java路径
if [ -d "/www/server/java/jdk1.8.0_371" ]; then
    export JAVA_HOME=/www/server/java/jdk1.8.0_371
    export PATH=$JAVA_HOME/bin:$PATH
elif [ -d "/www/server/java/jdk1.8" ]; then
    export JAVA_HOME=/www/server/java/jdk1.8
    export PATH=$JAVA_HOME/bin:$PATH
fi

echo "======================================================================"
echo "知识管理系统 - 环境检查"
echo "======================================================================"
echo ""

# 检查当前目录
echo "1. 当前目录："
pwd
echo ""

# 检查目录内容
echo "2. 当前目录文件："
ls -lh
echo ""

# 检查Java
echo "3. Java环境："
if command -v java &> /dev/null; then
    java -version
else
    echo "✗ 未安装Java"
fi
echo ""

# 检查jar包
echo "4. jar包文件："
if [ -f "knowledge-management-system-1.0.0.jar" ]; then
    ls -lh knowledge-management-system-1.0.0.jar
    echo "✓ jar包存在"
else
    echo "✗ 未找到jar包"
fi
echo ""

# 检查端口
echo "5. 端口8080占用情况："
if command -v netstat &> /dev/null; then
    netstat -anp | grep 8080 || echo "端口8080未被占用"
elif command -v ss &> /dev/null; then
    ss -anp | grep 8080 || echo "端口8080未被占用"
else
    echo "无法检查端口（netstat/ss命令不可用）"
fi
echo ""

# 检查进程
echo "6. 应用进程："
if [ -f app.pid ]; then
    pid=$(cat app.pid)
    if ps -p $pid > /dev/null 2>&1; then
        echo "✓ 应用正在运行，进程ID: $pid"
        ps -p $pid -o pid,ppid,cmd,etime
    else
        echo "✗ PID文件存在但进程不存在"
    fi
else
    echo "未找到PID文件（应用可能未启动）"
fi
echo ""

# 检查日志
echo "7. 日志文件："
if [ -d logs ]; then
    ls -lh logs/
    if [ -f logs/app.log ]; then
        echo ""
        echo "最新的app.log内容（最后20行）："
        echo "----------------------------------------------------------------------"
        tail -n 20 logs/app.log
    fi
else
    echo "logs目录不存在"
fi
echo ""

echo "======================================================================"
echo "检查完成"
echo "======================================================================"

