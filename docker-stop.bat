@echo off

:: Stop all containers
FOR /f "tokens=*" %%i IN ('docker ps -aq') DO docker stop %%i