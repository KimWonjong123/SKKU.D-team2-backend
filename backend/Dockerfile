FROM gradle:8.4.0-jdk17 AS build
WORKDIR /gradle
COPY gradle ./gradle
COPY gradlew build.gradle settings.gradle ./
COPY src ./src
RUN ./gradlew clean
RUN ./gradlew bootJar

FROM openjdk:17-jdk
WORKDIR /app
COPY .env ./
COPY --from=build /gradle/build/libs/*.jar /app.jar
ADD https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh /
RUN chmod +x /wait-for-it.sh