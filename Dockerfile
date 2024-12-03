FROM openjdk:17-jdk-slim
WORKDIR /app
COPY ${JAR_FILE_PATH} *.jar
ENTRYPOINT ["java", "-Dserver.port=${APP_PORT}", "-jar", "demo-0.0.1-SNAPSHOT.jar"]