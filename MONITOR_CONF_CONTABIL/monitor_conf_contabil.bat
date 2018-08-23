@ECHO OFF

cd c:\proativo\cenarios\monitor_conf_contabil

java -jar monitor_conf_contabil.jar

echo ERRORLEVEL: %ERRORLEVEL%

exit %ERRORLEVEL%