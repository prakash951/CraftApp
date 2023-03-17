#FROM openjdk:11-slim as build
#COPY gradlew .
#COPY .gradle .
#COPY gradle .
#COPY build.gradle .
#COPY settings.gradle .
#COPY src .
#RUN ./gradlew build

FROM openjdk:11-slim
COPY /build/libs /app
COPY /build/resources /app
ENTRYPOINT ["java","-jar","/app/CraftApp-1.0.jar"]