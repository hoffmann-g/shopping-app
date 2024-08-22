FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

COPY mvnw pom.xml ./
COPY .mvn .mvn

COPY src ./src

RUN chmod +x ./mvnw

RUN ./mvnw package -DskipTests

FROM eclipse-temurin:21-jdk-alpine AS runtime
WORKDIR /app

COPY --from=build /app/target/security-service.jar /app/security-service.jar

EXPOSE 8067

ENTRYPOINT ["java", "-jar", "/app/security-service.jar"]
