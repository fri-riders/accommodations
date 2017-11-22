@echo off

:: Stop all containers
FOR /f "tokens=*" %%i IN ('docker ps -aq') DO docker stop %%i
:: Delete all containers
FOR /f "tokens=*" %%i IN ('docker ps -aq') DO docker rm %%i
:: Delete all images
FOR /f "tokens=*" %%i IN ('docker images --format "{{.ID}}"') DO docker rmi %%i