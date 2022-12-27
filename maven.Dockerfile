FROM maven:3.8.6-openjdk-11-slim
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src src
RUN mvn package
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "target/forverkliga-0.0.1-SNAPSHOT.jar"]
