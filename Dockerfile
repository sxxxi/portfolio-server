FROM openjdk:21
WORKDIR /
COPY target/*.jar /app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]