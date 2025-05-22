FROM openjdk:17-jdk-alpine
LABEL authors="VVMakarov"

COPY target/subscription-0.0.1-SNAPSHOT.jar subscription-microservice.jar
ENTRYPOINT ["java","-jar","/subscription-microservice.jar"]