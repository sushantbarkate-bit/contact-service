# ---- BUILD PHASE ----
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn -q -e -DskipTests dependency:go-offline
COPY . .
RUN mvn -q -e -DskipTests package

# ---- RUN PHASE ----
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar contact-service.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "contact-service.jar"]
