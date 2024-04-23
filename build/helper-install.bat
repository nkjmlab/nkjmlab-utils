setlocal
cd /d %~dp0
killall java
mvn-caller.bat "mvn clean install -f nkjmlab-utils-helper"
