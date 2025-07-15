# Stage 1: Build using Java 22 and Maven manually
FROM eclipse-temurin:22-jdk AS build

# Install Maven
RUN apt-get update && \
    apt-get install -y maven

WORKDIR /app

# Copy project files
COPY pom.xml .
COPY src ./src

# Build the Spring Boot app
RUN mvn clean package -DskipTests

# Stage 2: Slim Java 22 runtime
FROM eclipse-temurin:22-jdk-alpine

WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port Spring Boot uses
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
