# Vehicle CRUD
Simple Spring boot application to manage vehicles. You can see them all, create, update or delete.
# Running application
By default, application uses in-file H2 databases to persist information between runs. Upon first run schema.sql and data.sql will be used to generate needed tables and insert initial data.<br>
Can be changed to in-memory database to have a clean setup each run by setting property **spring.datasource.url=jdbc:h2:mem:vehicledb** in application.properties.<br>
Tests are using in-memory H2 database and clean it up after each test to ensure test independence. Test data created from test-data.sql.
## Run from IDE
1.  Build using `mvn clean install -DskipTests`
2.  Run application root in IDE (or `mvn spring-boot:run`)

## Use Docker to host an application

Create an image with following parameters:<br>

```
docker image build -t vehiclecrud:1.0
```
<p>
After image was created publish it in docker, exposing the port you would like to access (change 8000 to a port you would like)<br>

```
docker container run --publish 8000:8080 --detach --name vc vehiclecrud:1.0
```
<p>
Application should be available at localhost:8000/ (Message "Application root" should be shown). Now REST endpoints, WebServer endpoints can be used. 
<p>
Stop container when it's not needed<br>

```
docker container rm --force vc
```
<p>
Check list of running containers<br>

```
docker ps
```

## Use run.cmd from root foolder
run.cmd automatically executes needed commands to run application using maven or docker. Run run.cmd and follow instructions to load application

# Application
By default application will be available at http://localhost:8080/ (or 8000 for docker). 
After loading root address "Application root" will be shown to verify that application is running.

## REST endpoints
Application has 5 REST endpoints:
*  **GET /vehicle/list** - returns all vehicles records from application
*  **GET /vehicle/{id}** - returns one record of vehicle found by id or throws an error if record is not found
*  **POST /vehicle/create** - creates new vehicle record from provided body or throws an error if VIN code is not unique
*  **PUT /vehicle/update/{id}** - updates record by following id and provided body, throws an error if VIN is not unique
*  **DELETE /vehicle/delete/{id}** - deletes record by following id, returns flag deleted if removed, or throws an error if record is not found
<br>
Examples of using REST are in file **rest_request_examples.http**
## Webservice
http://localhost:8080/ws/vehicles.wsdl - service definition<br>
WebService supports 2 operations: createVehicle and findVehicleById<br>
Examples of requests and responses are in file **request_example.xml**

## Swagger
Swagger documentation is available at following list:
* http://localhost:8080/api - json with 
* http://localhost:8080/swagger-ui.html - swagger ui page

# Gitlab CI
Application uses simple CI to ensure that master branch in green state - sources are compiling and tests are working.