FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/demo-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app_dontar.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app_dontar.jar"]