# Usar imagen base de OpenJDK 21
FROM openjdk:21-jdk-slim

# Crear directorio de trabajo
WORKDIR /app

# Copiar archivos del proyecto
COPY . .

# Otorgar permisos de ejecución al Maven wrapper
RUN chmod +x ./mvnw

# Construir la aplicación
RUN ./mvnw clean package -DskipTests

# Exponer el puerto
EXPOSE 8080

# Comando para ejecutar la aplicación
CMD ["java", "-Dserver.port=${PORT:-8080}", "-jar", "target/apirestmoviles-0.0.1-SNAPSHOT.jar"]
