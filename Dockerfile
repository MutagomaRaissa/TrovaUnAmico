FROM maven:3.9.3-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src
COPY mvnw .
COPY .mvn .mvn

# Build the project
RUN chmod +x mvnw && ./mvnw clean package -DskipTests

# Run stage
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app
COPY --from=build /app/target/TrovaUnAmico-0.0.1-SNAPSHOT.jar ./app.jar

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]

