FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

COPY mvnw pom.xml ./
COPY .mvn .mvn

COPY src ./src

RUN chmod +x ./mvnw

RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jdk-alpine AS runtime
WORKDIR /app

COPY --from=build /app/target/coupon-service.jar /app/coupon-service.jar

EXPOSE 8062

ENTRYPOINT ["java", "-jar", "/app/coupon-service.jar"]
