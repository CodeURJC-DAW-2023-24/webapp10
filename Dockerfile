#################################################
# Imagen base para el contenedor de Angular
#################################################
FROM node:20 as angular_builder

# Define el directorio de trabajo donde ejecutar comandos
WORKDIR /ang

# Copia las dependencias del proyecto
COPY frontend/* /ang/

# Compila proyecto y descarga librerías
RUN npm install

# Copia el contenido de src de frontend a ang
COPY frontend/src /ang/src

# Compila el proyecto
RUN npm run build

#################################################
# Imagen base para el contenedor de compilación
#################################################
FROM --platform=linux/amd64 ubuntu:jammy
FROM maven:3.9.6-amazoncorretto-21 as builder

WORKDIR /project

COPY ./pom.xml /project/

#RUN mvn clean verify

COPY /src /project/src

COPY --from=angular_builder /ang/dist/frontend/browser/ /project/src/main/resources/static/new

RUN mvn clean package -DskipTests=true
#RUN mvn clean verify -DskipTests=true

#################################################
# Imagen base para el contenedor de la aplicación
#################################################
FROM openjdk:21

# Define el directorio de trabajo donde se encuentra el JAR
WORKDIR /usr/src/app/

# Copia el JAR del contenedor de compilación
COPY --from=builder /project/target/*.jar /usr/src/app/

# Indica el puerto que expone el contenedor
EXPOSE 8443

# Comando que se ejecuta al hacer docker run
CMD [ "java", "-jar", "mortez365-0.0.1-SNAPSHOT.jar" ]