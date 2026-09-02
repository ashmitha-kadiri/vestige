# ==============================================================================
# Stage 1: Build & Package Backend with Maven
# ==============================================================================
FROM eclipse-temurin:21-jdk-alpine AS builder

WORKDIR /app

# Copy Maven wrapper and POM first for dependency layer caching
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN chmod +x mvnw && ./mvnw dependency:go-offline -B

# Copy source code and build production artifact
COPY src/ ./src/
RUN ./mvnw clean package -DskipTests -B

# ==============================================================================
# Stage 2: Minimal Production JRE Runtime
# ==============================================================================
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Create a non-privileged application user
RUN addgroup -S vestigegroup && adduser -S vestigeuser -G vestigegroup
USER vestigeuser:vestigegroup

# Copy built JAR artifact from builder stage
COPY --from=builder /app/target/vestige-backend-*.jar app.jar

# Expose standard production port
EXPOSE 8080

# Configure container JVM flags for memory optimization & security
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -Djava.security.egd=file:/dev/./urandom"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
