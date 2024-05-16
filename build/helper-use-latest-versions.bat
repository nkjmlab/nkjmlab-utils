setlocal
cd /d %~dp0
call mvn-caller.bat "mvn versions:use-latest-versions -f nkjmlab-utils-helper"
endlocal
