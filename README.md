# Proyecto Bücher
Este es el proyecto Bücher, realizado para la materia de Programación Orientada Objetos en conjunto con Proyecto Informático II.

## Detalles del proyecto
Los detalles del D.O.R. (Definition Of Ready) están especificados en el archivo *DOR.md* dentro de la carpeta *calidad-vectorial*.
Adenás, dentro de la carpeta */calidad-vectorial/backend*, se encuentra el archivo *POSTMAN_REQUESTS*, con las requests para los endpoints del **BackEnd**.

## Tecnologías del proyecto
Los detalles de las tecnologías utilizadas y los requisitos para hacer correr el programa se encuentran especificados en el archivo *HELP.md* dentro de la carpeta *calidad-vectorial*.

## Iniciar el proyecto
Para levantar el proyecto, se necesita ejecutar el **Back End** por un lado, y el **Front End** por el otro. Para ello hay dos formas:
- Ejecutando el archivo *iniciar.bat* dentro de la carpeta *calidad-vectorial*.
- Ejecutando de forma manual ambas partes del proyecto:
    - ### BackEnd
    En la carpeta */calidad-vectorial/backend*, ejecutar el comando *gradlew bootRun* en la terminal o ejecutando el archivo main del backend llamado *CalidadVectorialApplication.java*, en */calidad-vectorial/backend/src/main/java/ar/edu/huergo/vectorial/calidad/bucher*
    - ### FrontEnd
    Ejecutar el comando *npm run dev* en la carpeta */calidad-vectorial/frontend*