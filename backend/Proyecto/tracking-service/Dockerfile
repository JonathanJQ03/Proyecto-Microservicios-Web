# Etapa 1: Build con Maven
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app

# Copiar pom.xml y descargar dependencias
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiar código fuente
COPY src ./src

# Construir el JAR (omitiendo tests)
RUN mvn clean package -DskipTests

# Etapa 2: Imagen final ligera con OpenJDK
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copiar el JAR generado
COPY --from=build /app/target/tracking-service-0.0.1-SNAPSHOT.jar app.jar

# Puerto configurable vía variable de entorno
EXPOSE 8085

# Ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
