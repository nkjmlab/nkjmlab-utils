setlocal
cd /d %~dp0
call mvn-caller.bat "mvn clean compile -f nkjmlab-utils-core"
endlocal
