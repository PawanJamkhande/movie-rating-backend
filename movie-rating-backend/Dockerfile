# --- Stage 1: build the jar with Maven ---
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# copy pom.xml first and pre-fetch dependencies so Docker can cache this layer
# and skip re-downloading everything when only source code changes
COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests -B

# --- Stage 2: lightweight runtime image ---
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

COPY --from=build /app/target/movie-rating-system-backend.jar app.jar

EXPOSE 8080

# -Xmx400m caps heap size - Render's free tier gives the whole container only
# 512MB, and an untuned JVM can exceed that under load
ENTRYPOINT ["java", "-Xmx400m", "-jar", "app.jar"]
