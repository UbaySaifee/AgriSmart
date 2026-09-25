FROM maven:3.9-eclipse-temurin-25-alpine AS build
WORKDIR /workspace
COPY pom.xml .
COPY src src
RUN mvn -B -q -DskipTests package

FROM eclipse-temurin:25-jre-alpine
RUN addgroup -S agrismart && adduser -S agrismart -G agrismart
WORKDIR /app
COPY --from=build /workspace/target/agrismart-backend-0.0.1-SNAPSHOT.jar app.jar
USER agrismart
EXPOSE 8080
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75.0", "-jar", "/app/app.jar"]
