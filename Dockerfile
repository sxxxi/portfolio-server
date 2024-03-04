FROM openjdk:21
WORKDIR /
COPY ./src/main/resources/keys /keys
ENV JWT_PRIVATE=/keys/private.der
ENV JWT_PUBLIC=/keys/public.der
COPY target/*.jar /app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]