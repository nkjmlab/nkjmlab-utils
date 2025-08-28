setlocal
cd /d %~dp0
killall java
call mvn-caller.bat "mvn clean deploy -P github -f nkjmlab-utils-core"
call core-check.bat
endlocal
