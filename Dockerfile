# Utiliza una imagen base de Java 11
FROM openjdk:11-jre-slim

# Establece un directorio de trabajo en el contenedor
WORKDIR /app

# Copia el archivo JAR de la aplicación Spring Boot al contenedor
COPY app.jar /app/app.jar

# Expone el puerto en el que la aplicación Spring Boot se ejecutará en el contenedor (por defecto 8080)
EXPOSE 8080

# Comando para ejecutar la aplicación cuando se inicie el contenedor
CMD ["java", "-jar", "app.jar"]
