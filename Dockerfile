# https://spring.io/guides/gs/spring-boot-docker/
FROM maven:3.8.1-openjdk-17-slim AS build
COPY /src /app/src
COPY /pom.xml /app
RUN mvn -f /app/pom.xml clean package -Dmaven.test.skip

FROM openjdk:17-jdk-slim
RUN echo "America/Sao_Paulo" > /etc/timezone
RUN export TZ=America/Sao_Paulo

VOLUME /tmp
RUN mkdir /tmp/logs

EXPOSE ${DOCKER_EXPOSE_PORT}
COPY --from=build /app/target/*.jar app.jar

CMD ["java","-Dspring.profiles.active=${SPRING_PROFILE}","-jar","/app.jar"]