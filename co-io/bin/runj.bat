@echo off

setlocal
 
set CLASSPATH=.;..\target\classes;..\target\test-classes;^
D:\project\coroutine\coroutines\1.4.2\lib\*; rem D:\Project\java\co\coroutines\lib\1.4.2\*;

java -javaagent:..\lib\java-agent-1.4.2.jar %*

endlocal
