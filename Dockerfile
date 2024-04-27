FROM alpine:3.19 AS base
USER root
WORKDIR /root
RUN apk add --no-cache openjdk21

FROM base as builder
USER root
WORKDIR /root/app
RUN apk add maven
COPY . .
RUN mvn clean package

FROM base as runner
USER root
WORKDIR /root
ENV HOME=/root
ENV JWT_PRIVATE=/shared/keys/private.der
ENV JWT_PUBLIC=/shared/keys/public.der
COPY --from=builder /root/app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
