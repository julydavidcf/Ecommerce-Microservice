FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY . /app
COPY target/OrderService-0.0.1-SNAPSHOT.jar OrderService-0.0.1-SNAPSHOT.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","OrderService-0.0.1-SNAPSHOT.jar"]
