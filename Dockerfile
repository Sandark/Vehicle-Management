FROM maven:3.6.3-openjdk-11

WORKDIR /usr/src/app

COPY . /usr/src/app
RUN mvn package -DskipTests

ENV PORT 8080
EXPOSE $PORT
CMD [ "sh", "-c", "mvn -Dserver.port=${PORT} spring-boot:run" ]
