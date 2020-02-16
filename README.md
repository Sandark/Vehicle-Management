# Vehicle CRUD

# Running application
## 1. Run from IDE
#####1. Build using mvn clean install -DskipTests
#####2. Run application root in IDE (or mvn spring-boot:run)


## 2. Use Docker to host an application

Create an image with following parameters:<br>
docker image build -t vehiclecrud:1.0
<p>
After image was created publish it in docker, exposing the port you would like to access (change 8000 to a port you would like)<br>
docker container run --publish 8000:8080 --detach --name vc vehiclecrud:1.0
<p>
Application should be available at localhost:8000/ (Message "Application root" should be shown). Now you can use REST endpoints 
<p>
Stop container when it's not needed<br>
docker container rm --force vc
<p>
Check list of running containers<br>
docker ps