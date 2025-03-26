# Use an official Maven image to build the application
FROM maven:3.9.6-eclipse-temurin-17 AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml and download dependencies
COPY pom.xml ./
RUN mvn dependency:go-offline

# Copy the source code and build the application
COPY src ./src
RUN mvn clean package -DskipTests

# Use an OpenJDK runtime image for the final container
FROM eclipse-temurin:17-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file from the builder stage
COPY --from=builder /app/target/vehicle-information-1.0.0.jar app.jar

# Expose the application port
EXPOSE 8090

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
