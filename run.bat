@echo off
chcp 65001
echo ==========================================
echo Running Phung Loc Coffee JavaFX App
echo ==========================================

set JAVA_TOOL_OPTIONS=-Dfile.encoding=UTF-8

where mvn >nul 2>nul
if %errorlevel%==0 (
    call mvn clean javafx:run
) else if exist mvnw.cmd (
    call mvnw.cmd clean javafx:run
) else (
    echo Maven is not installed and Maven Wrapper is not available.
)

pause
