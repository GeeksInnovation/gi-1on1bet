FROM maven:3.8.6-eclipse-temurin-17 AS builder
WORKDIR /app

COPY settings.xml /root/.m2/settings.xml
RUN cat /root/.m2/settings.xml 
COPY pom.xml ./pom.xml

WORKDIR /app/bettingservice

COPY bettingservice/pom.xml ./pom.xml
COPY bettingservice/src ./src

RUN mvn clean package -DskipTests -Dcheckstyle.skip=true

FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=builder /app/bettingservice/target/*.jar gi-1on1bet-betting-ser.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "gi-1on1bet-betting-ser.jar"]