FROM openjdk:21
ENV FOOBAR=BOB
WORKDIR /
COPY src/main/resources/keys/private.der ./src/main/resources/private.der
COPY src/main/resources/keys/public.der ./src/main/resources/public.der
COPY target/*.jar /app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]