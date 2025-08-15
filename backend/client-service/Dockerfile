# Usar una imagen base con Maven
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app

# Copiar archivos necesarios
COPY pom.xml .
COPY src ./src

# Construir la aplicación
RUN mvn clean package -DskipTests

# Imagen final más ligera
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
