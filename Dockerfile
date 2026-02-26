# ---------- Stage 1: Build ----------
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copy pom first for better caching
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source
COPY src ./src

# Build application
RUN mvn clean package -DskipTests


# ---------- Stage 2: Run ----------
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Create non-root user
RUN addgroup -S spring && adduser -S spring -G spring
USER spring

# Copy jar from build stage
COPY --from=build /app/target/*.jar app.jar

# Expose container port
EXPOSE 8080

# Start with dynamic port support (Render compatible)
ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=${PORT:-8080}"]