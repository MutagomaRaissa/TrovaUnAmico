# Use Java 17
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

# Copy the jar
COPY target/*.jar app.jar

# Expose port 8080
EXPOSE 8080

# Run the jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]