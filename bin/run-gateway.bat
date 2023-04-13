@echo off
echo.
echo [��Ϣ] ʹ��Jar��������Gateway���̡�
echo.

cd %~dp0
cd ../yozu-gateway/target

set JAVA_OPTS=-Xms512m -Xmx1024m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=512m

java -Dfile.encoding=utf-8 %JAVA_OPTS% -jar yozu-gateway.jar

cd bin
pause
