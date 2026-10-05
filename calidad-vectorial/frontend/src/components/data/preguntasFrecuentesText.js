import IconoPrestamos from '../../assets/img/icons/crear/crearPrestamo.svg?react';
import IconoCuenta from '../../assets/img/icons/preguntasfrecuentes/cuentaPerfil.svg?react';
import IconoSuscripcion from '../../assets/img/icons/preguntasfrecuentes/suscripcion.svg?react';
import IconoPuntos from '../../assets/img/icons/suscripcion/estrella.svg?react';
import IconoPublicaciones from '../../assets/img/icons/crear/crearPosteo.svg?react';
import IconoBiblioteca from '../../assets/img/icons/preguntasfrecuentes/biblioteca.svg?react';

export const categorias = [
    {
        id: 'prestamos',
        titulo: 'Préstamos',
        icono: IconoPrestamos,
        preguntas: [
            {
                id: 'solicitar-prestamo',
                pregunta: '¿Cuál es el proceso para solicitar un préstamo?',
                respuesta:
                    'Encuentra una publicación de tu interés y solicita el préstamo. Nosotros lo llevamos a tu puerta. Para solicitar préstamos necesitas tener el plan Bücher +.',
            },
            {
                id: 'cuantos-libros',
                pregunta: '¿Cuántos libros puedo pedir prestados?',
                respuesta:
                    'Con el plan Bücher + puedes solicitar hasta tres libros a las vez, con un máximo de dos meses. No hay límite mensual de préstamos.',
            },
            {
                id: 'perdida-libro',
                pregunta: '¿Qué pasa si pierdo o rompo un libro prestado?',
                respuesta:
                    'Si el ejemplar sufre algún tipo de daño o lo pierdes, se te descontará el valor del libro para que repongamos el mismo a su dueño.',
            },
            {
                id: 'devolucion',
                pregunta: '¿Cómo es el proceso de devolución?',
                respuesta:
                    'Se activará un canal privado de comunicación con el prestador para acordar lugar y hora de devolución. Si vuelve en iguales condiciones a las que lo recibiste, ganas puntos.',
            },
        ],
    },
    {
        id: 'cuenta',
        titulo: 'Cuenta y perfil',
        icono: IconoCuenta,
        preguntas: [
            {
                id: 'editar-datos',
                pregunta: '¿Cómo edito mis datos?',
                respuesta:
                    'Desde la sección de configuración, en "Editar perfil", puedes modificar tu apodo, pronombres, descripción, avatar y dirección.',
            },
            {
                id: 'eliminar-cuenta',
                pregunta: '¿Cómo elimino mi cuenta?',
                respuesta: 'Respuesta pendiente.',
            },
            {
                id: 'cambiar-contrasena',
                pregunta: '¿Cómo cambio mi contraseña?',
                respuesta: 'Respuesta pendiente.',
            },
        ],
    },
    {
        id: 'suscripcion',
        titulo: 'Suscripción',
        icono: IconoSuscripcion,
        preguntas: [
            {
                id: 'que-cubre',
                pregunta: '¿Qué cubre la suscripción?',
                respuesta:
                    'Cubre los costos logísticos asociados a los préstamos, el mantenimiento de la plataforma y las herramientas que permiten conectar lectores de manera segura.',
            },
            {
                id: 'por-que-necesaria',
                pregunta: '¿Por qué es necesaria?',
                respuesta:
                    'Gracias a la suscripción, los usuarios pueden solicitar libros sin preocuparse por los gastos de traslado y disfrutar de los préstamos.',
            },
            {
                id: 'baja-suscripcion',
                pregunta: '¿Cómo doy de baja la suscripción?',
                respuesta: 'Respuesta pendiente.',
            },
        ],
    },
    {
        id: 'puntos',
        titulo: 'Puntos y recompensas',
        icono: IconoPuntos,
        preguntas: [
            {
                id: 'para-que-puntos',
                pregunta: '¿Para qué sirven los puntos?',
                respuesta: 'Los puntos sirven para canjear recompensas de dicha sección.',
            },
            {
                id: 'ganar-puntos',
                pregunta: '¿Cómo gano puntos?',
                respuesta:
                    'Ganas puntos cuando devuelves los libros prestados en iguales condiciones a las que los recibiste.',
            },
            {
                id: 'canjear-puntos',
                pregunta: '¿Dónde puedo canjearlos?',
                respuesta: 'Respuesta pendiente.',
            },
        ],
    },
    {
        id: 'publicaciones',
        titulo: 'Publicaciones y posteos',
        icono: IconoPublicaciones,
        preguntas: [
            {
                id: 'publicar-prestamo',
                pregunta: '¿Cómo publico un préstamo?',
                respuesta: 'En el apartado de Préstamo de la barra lateral de Crear puedes ingresar los datos y publicar el préstamo.',
            },
            {
                id: 'publicar-posteo',
                pregunta: '¿Cómo publico un posteo?',
                respuesta: 'En el apartado de Posteo de la barra lateral de Crear puedes escribir y publicar tus posteos.',
            },
        ],
    },
    {
        id: 'biblioteca',
        titulo: 'Biblioteca',
        icono: IconoBiblioteca,
        preguntas: [
            {
                id: 'agregar-libro',
                pregunta: '¿Cómo agrego un libro a la biblioteca?',
                respuesta: 'Busca tu libro de preferencia en la barra de búsqueda y agrégalo a tu biblioteca.',
            },
            {
                id: 'cambiar-sector',
                pregunta: '¿Cómo cambio un libro de sector?',
                respuesta: 'Respuesta pendiente.',
            },
        ],
    },
];
