# Tecnologías necesarias para ejecutar el proyecto

Este proyecto está dividido en dos partes:
- **Back End (API REST en Java con Spring Boot)**
- **Front End (interfaz en React con Vite)**.

## Base de Datos

La aplicación utiliza **MySQL** como sistema gestor de base de datos.

### Requisitos
- **MySQL Server** instalado.
- **Servicio de MySQL** en ejecución antes de iniciar el Back End.
  - Puede iniciarse desde **MySQL Workbench** o ejecutando únicamente el servicio de MySQL.
- Base de datos creada y credenciales configuradas en el archivo de propiedades del proyecto.

## Back End

El **Back End** está desarrollado en **Java 21**, utilizando **Gradle** para la gestión de las dependencias, y **Spring Boot** como framework principal para hacer funcionar la API REST.

### Requisitos
- **Java 21**
    [Descargar JDK](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
- **Gradle** instalado globalmente
    [Instrucciones de instalación](https://gradle.org/install/)
- **Extensión de Spring Boot** para **Visual Studio Code**
    *(Permite ejecutar y depurar fácilmente el proyecto Spring Boot desde el entorno de desarrollo)*


## Front End

El **Front End** está desarrollado en **React** para crear la interfaz visual y **Vite** como empaquetador y gestor de las dependencias.

### Requisitos
- **Node.js**
    [Descargar Node.js](https://nodejs.org/es/download)
- **Vite**
    Instalado dentro del entorno de Node.js desde la terminal:
    npm install vite --save-dev
    **Vite plugin svrg**
    Instalado dentro del entorno de Node.js desde la terminal:
    npm install --save-dev vite-plugin-svgr