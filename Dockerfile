FROM gradle:7-jdk17 AS build-master
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle :master:buildFatJar --no-daemon

FROM openjdk:17 AS run-master
RUN mkdir /app
COPY --from=build-master /home/gradle/src/master/build/libs/master.jar /app/master.jar
ENTRYPOINT ["java", "-jar", "/app/master.jar"]

FROM gradle:7-jdk17 AS build-secondary
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle :secondary:buildFatJar --no-daemon

FROM openjdk:17 AS run-secondary
RUN mkdir /app
COPY --from=build-secondary /home/gradle/src/secondary/build/libs/secondary.jar /app/secondary.jar
ENTRYPOINT ["java", "-jar", "/app/secondary.jar"]


