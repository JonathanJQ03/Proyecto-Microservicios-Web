# Etapa 1: Build con Maven
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app

# Copiar pom y descargar dependencias
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiar código fuente
COPY src ./src

# Construir el JAR con Spring Boot y omitiendo tests
RUN mvn clean package -DskipTests -Dspring-boot.repackage.skip=false

# Etapa 2: Imagen final ligera con OpenJDK
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copiar el JAR generado (con nombre fijo)
COPY --from=build /app/target/auth-service-0.0.1-SNAPSHOT.jar app.jar

# Puerto configurado según application.properties (Docker-friendly)
EXPOSE 8081

# Ejecutar la aplicación
ENTRYPOINT ["java","-jar","app.jar"]
