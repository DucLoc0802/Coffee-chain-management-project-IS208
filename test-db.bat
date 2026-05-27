@echo off
chcp 65001
echo ==========================================
echo Testing Oracle Database Connection
echo ==========================================

set JAVA_TOOL_OPTIONS=-Dfile.encoding=UTF-8

where mvn >nul 2>nul
if %errorlevel%==0 (
    call mvn clean compile exec:java "-Dexec.mainClass=com.phungloccoffee.util.TestDBConnection" -e
) else if exist mvnw.cmd (
    call mvnw.cmd clean compile exec:java "-Dexec.mainClass=com.phungloccoffee.util.TestDBConnection" -e
) else (
    echo Maven is not installed and Maven Wrapper is not available.
)

pause
