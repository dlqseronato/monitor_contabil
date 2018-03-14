@ECHO OFF

cd c:\proativo\cenarios\cenario_template

java -jar cenario_template.jar

echo ERRORLEVEL: %ERRORLEVEL%

exit %ERRORLEVEL%