@echo off
CLS
:MENU
echo.Run Vehicle Management System
echo.1. Maven build and run (available at localhost:8080)
echo.2. Docker image build and deploy (available at localhost:8000)
echo.3. Stop running Docker container
echo.9. Exit
SET M=
SET /P M=Select command and then press ENTER:
if "%M%"=="" set "M=9"
IF %M%==1 GOTO MAVENRUN
IF %M%==2 GOTO DOCKERRUN
IF %M%==3 GOTO DOCKERSTOP
IF %M%==9 GOTO EXITSCRIPT

:MAVENRUN
call mvnw clean install -DskipTests -T 1C
mvnw spring-boot:run -DskipTests
GOTO MENU
:DOCKERRUN
docker image build -t vehiclecrud:1.0 .
docker container rm --force vc || true
docker container run --publish 8000:8080 --detach --name vc vehiclecrud:1.0
GOTO MENU
:DOCKERSTOP
docker container rm --force vc
GOTO MENU
:EXITSCRIPT
EXIT /B