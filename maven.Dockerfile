FROM maven:3.8.6-openjdk-11-slim AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src src
RUN mvn package

FROM openjdk:11-slim
COPY --from=build /app/target/forverkliga-0.0.1-SNAPSHOT.jar ./
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "forverkliga-0.0.1-SNAPSHOT.jar"]
