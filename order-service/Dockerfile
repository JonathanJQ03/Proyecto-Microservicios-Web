# Etapa 1: Build con Maven
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app

# Copiar pom.xml y descargar dependencias offline
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiar código fuente
COPY src ./src

# Construir el JAR con Spring Boot, omitiendo tests
RUN mvn clean package -DskipTests

# Etapa 2: Imagen final ligera con OpenJDK
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copiar el JAR generado (nombre fijo)
COPY --from=build /app/target/order-service-0.0.1-SNAPSHOT.jar app.jar

# Puerto configurable con variable de entorno
EXPOSE 8083

# Ejecutar la aplicación
ENTRYPOINT ["java","-jar","app.jar"]
