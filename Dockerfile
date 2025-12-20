# Stage 1: Build Application
FROM eclipse-temurin:24 AS build
WORKDIR /home/gradle/src
COPY . .
RUN ./gradlew buildFatJar --no-daemon

# Stage 2: Minimal Runtime
FROM eclipse-temurin:24-jre AS runtime
EXPOSE 8080
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/f1-graphql-service.jar
ENTRYPOINT ["java", "-ea", "-jar", "/app/f1-graphql-service.jar"]
