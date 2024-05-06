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
FROM maven:3.8.4-openjdk-17 as builder

# Define el directorio de trabajo donde ejecutar comandos
WORKDIR /project

# Copia las dependencias del proyecto
COPY pom.xml .

# Compila proyecto y descarga librerías
RUN mvn clean verify

# Copia el código del proyecto
COPY src /project/src

# Compila proyecto y descarga librerías
RUN mvn package -Dmaven.test.skip=true

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