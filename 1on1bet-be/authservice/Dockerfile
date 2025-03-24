FROM maven:3.8.6-eclipse-temurin-17 AS builder
WORKDIR /app
COPY pom.xml ./pom.xml

WORKDIR /app/authservice

COPY authservice/pom.xml ./pom.xml
COPY authservice/src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=builder /app/authservice/target/*.jar gi-1on1bet-auth-ser.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "gi-1on1bet-auth-ser.jar"]