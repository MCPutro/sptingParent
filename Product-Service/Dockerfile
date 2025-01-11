FROM openjdk:21
WORKDIR /usr/app
COPY target/Product-Service-0.0.1-SNAPSHOT.jar /usr/app/ProductService.jar
EXPOSE 8081
RUN sh -c 'touch /usr/app/ProductService.jar'
ENTRYPOINT ["java", "-jar","/usr/app/ProductService.jar"]