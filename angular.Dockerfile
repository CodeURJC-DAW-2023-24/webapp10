#Descargamos la última versión de node
FROM node:latest as node
RUN mkdir /usr/src/app
WORKDIR /usr/src/app
#Instalamos dependencias
RUN npm install -g @angular/cli
RUN npm install -g npm@latest
RUN npm install @swimlane/ngx-charts --save
RUN ng add @angular/material
#Exponemos el puerto 4200
EXPOSE 4200
#Copiamos el contenido de frontend a la dirección
COPY /frontend /usr/src/app
CMD [ "npm", "start" ]