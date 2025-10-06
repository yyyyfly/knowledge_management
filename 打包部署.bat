@echo off
chcp 65001 >nul
echo ================================================================================
echo 个人知识管理系统 - 打包脚本
echo ================================================================================
echo.

:menu
echo 请选择操作：
echo [1] 打包后端（Maven）
echo [2] 打包前端（npm）
echo [3] 打包全部
echo [4] 清理打包文件
echo [0] 退出
echo.
set /p choice=请输入选项 [0-4]: 

if "%choice%"=="1" goto backend
if "%choice%"=="2" goto frontend
if "%choice%"=="3" goto all
if "%choice%"=="4" goto clean
if "%choice%"=="0" goto end
echo 无效选项，请重新选择
echo.
goto menu

:backend
echo.
echo ================================================================================
echo 正在打包后端项目...
echo ================================================================================
cd backed
call mvn clean package -DskipTests
if %errorlevel%==0 (
    echo.
    echo ✓ 后端打包成功！
    echo 打包文件位置：backed\target\knowledge-management-system-1.0.0.jar
) else (
    echo.
    echo ✗ 后端打包失败！请检查错误信息
)
cd ..
echo.
pause
goto menu

:frontend
echo.
echo ================================================================================
echo 正在打包前端项目...
echo ================================================================================
echo.
echo 提示：已自动配置环境变量文件
echo API地址：http://www.personalmanange.xyz/api
echo.
cd fronted
call npm run build
if %errorlevel%==0 (
    echo.
    echo ✓ 前端打包成功！
    echo 打包文件位置：fronted\dist\
) else (
    echo.
    echo ✗ 前端打包失败！请检查错误信息
)
cd ..
echo.
pause
goto menu

:all
echo.
echo ================================================================================
echo 正在打包全部项目...
echo ================================================================================
call :backend
call :frontend
goto menu

:clean
echo.
echo ================================================================================
echo 正在清理打包文件...
echo ================================================================================
if exist "backed\target" (
    rd /s /q "backed\target"
    echo ✓ 已清理后端打包文件
)
if exist "fronted\dist" (
    rd /s /q "fronted\dist"
    echo ✓ 已清理前端打包文件
)
echo.
echo ✓ 清理完成！
echo.
pause
goto menu

:end
echo.
echo 感谢使用！
exit /b 0

