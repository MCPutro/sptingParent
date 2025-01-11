FROM amazoncorretto:21.0.5
WORKDIR /usr/app
COPY target/User-Service-0.0.1-SNAPSHOT.jar /usr/app/UserService.jar
EXPOSE 8082
RUN sh -c 'touch /usr/app/UserService.jar'
ENTRYPOINT ["java", "-jar","/usr/app/UserService.jar"]