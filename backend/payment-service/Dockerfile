FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

COPY mvnw pom.xml ./
COPY .mvn .mvn

COPY src ./src

RUN chmod +x ./mvnw

RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jdk-alpine AS runtime
WORKDIR /app

COPY --from=build /app/target/payment-service.jar /app/payment-service.jar

EXPOSE 8065

ENTRYPOINT ["java", "-jar", "/app/payment-service.jar"]
