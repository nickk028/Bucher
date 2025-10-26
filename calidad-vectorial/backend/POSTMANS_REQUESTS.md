# Requests de Postman - Back End

Este documento describe las peticiones realizadas con **Postman** para probar los endpoints del Back End del proyecto Bucher.

## Usuario

### Registro de Usuario
- **URL**: http://localhost:8080/usuario/registrar
- **Método**: POST
- **Body** (Json):
{
    "username":"ejemplo@ej",
    "password":"1234567890123456Aa@",
    "verificationPassword":"1234567890123456Aa@"
}

### Obtener todos los usuarios (admin)
- **URL**: http://localhost:8080/usuario
- **Método**: GET

### Obtener datos de usuario propios
- **URL**: http://localhost:8080/usuario/propio
- **Método**: GET

### Modificar datos de usuario propios
- **URL**: http://localhost:8080/usuario/modificar
- **Método**: PUT
- **Body** (Json):
{
    "pronombres": ["pronombre1"],
    "descripcion": "Descripción del usuario"
}

### Iniciar Sesión

#### Login ejemplo del Registro
- **URL**: http://localhost:8080/auth/login
- **Método**: POST
- **Body** (Json):
{
    "username":"ejemplo@ej",
    "password":"1234567890123456Aa@"
}

#### Login lector
- **URL**: http://localhost:8080/auth/login
- **Método**: POST
- **Body** (Json):
{
    "username":"lector@gmail.com",
    "password":"LectorSuperSegura@123"
}

#### Login admin
- **URL**: http://localhost:8080/auth/login
- **Método**: POST
- **Body** (Json):
{
    "username":"admin@gmail.com",
    "password":"AdminSuperSegura@123"
}

### Logout
- **URL**: http://localhost:8080/auth/logout
- **Método**: POST

### Validar token
- **URL**: http://localhost:8080/auth/validar-token
- **Método**: GET

## Publicación

### Obtener todas las Publicaciones
- **URL**: http://localhost:8080/publicacion
- **Método**: GET

### Obtener Publicaciones propias
- **URL**: http://localhost:8080/publicacion/propias
- **Método**: GET

### Obtener Publicacion por id
- **URL**: http://localhost:8080/publicacion/1
- **Método**: GET

### Obtener Publicaciones por categoria del libro
- **URL**: http://localhost:8080/publicacion/categoria/aventura
- **Método**: GET

### Obtener Publicaciones por estado (admin)
- **URL**: http://localhost:8080/publicacion/estado/Disponible
- **Método**: GET


### Crear Publicación
- **URL**: http://localhost:8080/publicacion/crear
- **Método**: POST
- **Body** (Json):
{
    "titulo":"Harry Potter y la piedra filosofal",
    "descripcion":"Libro",
    "limiteDias":2
}

### Modificar Publicación
- **URL**: http://localhost:8080/publicacion/actualizar/1
- **Método**: PUT
- **Body** (Json):
{
    "limiteDias":10,
    "descripcion":"nueva descripcion",
    "detallesEstadoLibro":"Nuevo",
    "estadoPublicacion":"No_disponible"
}

### Eliminar Publicación
- **URL**: http://localhost:8080/publicacion/eliminar/1
- **Método**: DELETE

### Pedir Préstamo
- **URL**: http://localhost:8080/publicacion/prestamo/1
- **Método**: POST

### Devolver Préstamo (admin)
- **URL**: http://localhost:8080/publicacion/devolucion/1
- **Método**: POST

## Biblioteca

### Obtener Biblioteca propia
- **URL**: http://localhost:8080/biblioteca
- **Método**: GET

### Obtener Libro Usuario
- **URL**: http://localhost:8080/biblioteca/1
- **Método**: GET

### Obtener Libros Usuario por estado de lectura
- **URL**: http://localhost:8080/biblioteca/estado/leyendo
- **Método**: GET

### Crear Libro de Usuario
- **URL**: http://localhost:8080/biblioteca
- **Método**: POST
- **Body** (Json):
{
    "titulo":"Harry Potter y la piedra filosofal",
    "paginaActual":1,
    "estadoLectura":"leyendo",
    "puntuacion":100
}

### Modificar Libro de Usuario
(estadoLectura solo admin)
- **URL**: http://localhost:8080/biblioteca/1
- **Método**: PUT
- **Body** (Json):
{
    "estadoLectura":"leido",
    "paginaActual":100,
    "puntuacion":95
}

### Eliminar Libro de Usuario
- **URL**: http://localhost:8080/biblioteca/1
- **Método**: DELETE

## Registro Préstamo

### Obtener Registros Préstamo propios
- **URL**: http://localhost:8080/registro
- **Método**: GET

## Libro

### Obtener todos los libros
- **URL**: http://localhost:8080/libro/todos
- **Método**: GET

### Obtener libro por id
- **URL**: http://localhost:8080/libro/1
- **Método**: GET

### Obtener libro por categoria
- **URL**: http://localhost:8080/libro/categoria/aventura
- **Método**: GET

### Obtener tendencias
- **URL**: http://localhost:8080/libro/tendencias
- **Método**: GET