setlocal
cd /d %~dp0
killall java
call mvn-caller.bat "mvn clean install -f nkjmlab-utils-helper"
call helper-check.bat
