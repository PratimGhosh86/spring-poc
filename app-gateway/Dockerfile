FROM openjdk:8-jdk-alpine

RUN addgroup -S pratim && adduser -S pratim -G pratim
USER pratim:pratim

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]