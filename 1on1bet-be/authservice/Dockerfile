FROM maven:3.8.6-eclipse-temurin-17 AS builder
WORKDIR /app

# Copy the parent POM (Ensure it exists in the build context)
COPY ../pom.xml ./pom.xml

WORKDIR /app/authservice
# Copy the service POM
COPY pom.xml ./pom.xml
# Copy source files
COPY src ./src

# Build the JAR
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=builder /app/authservice/target/*.jar gi-1on1bet-auth-ser.jar

# Expose port
EXPOSE 8080

# Correct ENTRYPOINT
ENTRYPOINT ["java", "-jar", "gi-1on1bet-auth-ser.jar"]
