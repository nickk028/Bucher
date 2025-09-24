# Definition of Ready (DOR)

## Usuario
- Iniciar sesión con JWT de validacion.
- Registrarse con JWT de validacion.
- Obtener todos los usuarios (admin).

## Libro
- Libros pregenerados con Autor y Editorial.

## Publicacion
- Crear una publicación con un libro de nuestra base de datos (inventada por el momento).
- Obtener publicaciones.
- Obtener publicaciones propias.
- Modificar una publicación propia / modificar una publicacion agena como admin.
- Eliminar una publicación propia / eliminar una publicacion agena como admin.
- Pedir prestado un libro.
- Devolver libro (admin).

## Biblioteca
- Obtener biblioteca propia.
- Crear Libro de Usuario.
- Modificar Libro de Usuario.
- Eliminar Libro de Usuario.

## Registro Préstamo
- Obtener registros de prestamo propios.


# Requests (Postman)

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

### Obtener todos los usuarios (admin)
- **URL**: http://localhost:8080/usuario
- **Método**: GET

## Publicación

### Crear Publicación
- **URL**: http://localhost:8080/publicacion/crear
- **Método**: POST
- **Body** (Json):
{
    "titulo":"Harry Potter y la piedra filosofal",
    "descripcion":"Libro",
    "limiteDias":2
}

### Obtener todas las Publicaciones
- **URL**: http://localhost:8080/publicacion
- **Método**: GET

### Eliminar Publicación
- **URL**: http://localhost:8080/publicacion/eliminar/1
- **Método**: DELETE

### Obtener Publicaciones propias
- **URL**: http://localhost:8080/publicacion/propias
- **Método**: GET

### Modificar Publicación
- **URL**: http://localhost:8080/publicacion/modificar/1
- **Método**: PUT
- **Body** (Json):
{
    "limiteDias":10,
    "descripcion":"nueva descripcion",
    "detallesEstadoLibro":"Nuevo",
    "estadoPublicacion":"No_disponible"
}

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

### Crear Libro de Usuario
- **URL**: http://localhost:8080/biblioteca
- **Método**: POST
- **Body** (Json):
{
    "titulo":"Harry Potter y la piedra filosofal",
    "paginaActual":1,
    "estadoLectura":"Leyendo",
    "puntuacion":100
}

### Modificar Libro de Usuario
(estadoLectura solo admin)
- **URL**: http://localhost:8080/biblioteca/1
- **Método**: PUT
- **Body** (Json):
{
    "estadoLectura":"Leído",
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