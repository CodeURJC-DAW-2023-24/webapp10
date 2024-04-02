#!/bin/bash

# Define las variables
DOCKER_HUB_USERNAME="josrob13"
DOCKER_IMAGE_NAME="josrob13/mortez365"
DOCKER_IMAGE_TAG="1.0"

# Compila la aplicación utilizando Docker con compilación multi-stage
docker build -t $DOCKER_IMAGE_NAME:$DOCKER_IMAGE_TAG -f Dockerfile .

# Inicia sesión en Docker Hub
echo "Iniciando sesión en Docker Hub..."
docker login -u $DOCKER_HUB_USERNAME

# Publica la imagen en Docker Hub
echo "Publicando la imagen en Docker Hub..."
docker push $DOCKER_IMAGE_NAME:$DOCKER_IMAGE_TAG

# Finaliza
echo "La imagen se ha publicado exitosamente en Docker Hub."
