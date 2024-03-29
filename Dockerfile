FROM alpine:3.19
EXPOSE 8080
WORKDIR /
ENV HOME=/
ENV AWS_ACCESS_KEY='${AWS_ACCESS_KEY}'
ENV AWS_SECRET_KEY='${AWS_SECRET_KEY}'

RUN apk add --no-cache openjdk21 aws-cli
RUN aws configure set aws_access_key_id $AWS_ACCESS_KEY && \
    aws configure set aws_secret_access_key $AWS_SECRET_KEY

ENV JWT_PRIVATE=/shared/keys/private.der
ENV JWT_PUBLIC=/shared/keys/public.der

COPY target/*.jar /app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
