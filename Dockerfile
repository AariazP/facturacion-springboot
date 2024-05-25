# Etapa 1: Compilar y empaquetar el proyecto usando Maven
FROM maven:3.8.4-openjdk-17 AS build

# Establecer el directorio de trabajo en /app
WORKDIR /app

# Copiar el archivo pom.xml y descargar las dependencias
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiar el código fuente del proyecto
COPY src ./src

# Compilar y empaquetar el proyecto
RUN mvn clean package -DskipTests

# Etapa 2: Ejecutar el archivo JAR empaquetado
FROM openjdk:17-jdk-slim

# Crear un directorio para la aplicación
WORKDIR /app

# Copiar el archivo JAR desde la etapa de compilación
COPY --from=build /app/target/*.jar app.jar

# Exponer el puerto en el que se ejecutará la aplicación
EXPOSE 8080

# Ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
