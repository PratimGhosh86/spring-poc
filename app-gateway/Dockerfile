FROM adoptopenjdk:11-jre-hotspot
LABEL owner="pratim.ghosh@outlook.com"
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
