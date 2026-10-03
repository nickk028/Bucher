import { Link } from 'react-router-dom';
import { Button } from '../../../elements/buttons/Button';
import LibroAbierto from '../../../../assets/img/icons/crear/crearPrestamo.svg?react';
import Camion from '../../../../assets/img/icons/suscripcion/camion.svg?react';
import Estrella from '../../../../assets/img/icons/suscripcion/estrella.svg?react';
import Tilde from '../../../../assets/img/icons/suscripcion/tilde.svg?react';
import Cruz from '../../../../assets/img/icons/suscripcion/cruz.svg?react';
import Marcador from '../../../../assets/img/icons/suscripcion/marcador.svg?react';
import './Suscripcion.css';

const IconoMarcador = () => (
    <svg
        className="suscripcion-content__plan__marcador"
        viewBox="0 0 28 40"
        aria-hidden="true"
    >
        <path d="M0 0h28v40l-14-10L0 40z" fill="currentColor" />
    </svg>
);

const beneficios = [
    {
        id: 'traslados',
        titulo: 'Traslados incluidos',
        descripcion: 'Los costos logísticos de cada préstamo ya están incluidos.',
        icono: Camion ,
    },
    {
        id: 'prestamos',
        titulo: 'Préstamo de libros',
        descripcion: 'Solicita un libro a la vez, las veces que quieras.',
        icono: LibroAbierto,
    },
    {
        id: 'puntos',
        titulo: 'Ganas más puntos',
        descripcion: 'Los libros devueltos en iguales condiciones te dan puntos.',
        icono: Estrella,
    },
];

const comparativa = [
    { id: 'lecturas', funcion: 'Registrar lecturas', gratis: true, plus: true },
    { id: 'posteos', funcion: 'Publicar posteos', gratis: true, plus: true },
    { id: 'biblioteca', funcion: 'Biblioteca personal', gratis: true, plus: true },
    { id: 'publicaciones', funcion: 'Crear publicaciones', gratis: true, plus: true },
    { id: 'prestamos', funcion: 'Solicitar préstamos', gratis: false, plus: true },
    { id: 'puntos', funcion: 'Sistema de puntos', gratis: true, plus: true },
    { id: 'traslados', funcion: 'Traslados gratis', gratis: false, plus: true },
];

const pasos = [
    'Encontrá una publicación de tu interés.',
    'Solicita el préstamo.',
    'Nosotros lo llevamos a tu puerta.',
    'Disfruta tu lectura.',
    'Devolvé el libro.',
];

export const Suscripcion = () => {
    return (
        <div className="suscripcion-content">
            <div>
                <h1 className="suscripcion-content__title">Mi suscripción</h1>
                <p>Accede a los préstamos de libros de forma segura.</p>
            </div>

            <section className="suscripcion-content__seccion">
                <span className="suscripcion-content__label">Tu plan actual</span>
                <div className="suscripcion-content__planes">
                    <article className="suscripcion-content__plan">
                        <h2>Plan gratuito</h2>
                        <p className='suscripcion-content__plan__p'>Actualmente no puedes solicitar préstamos de libros.</p>
                        <div className="suscripcion-content__plan__accion">
                            <Button type="button" variant="default" color="claro">
                                Comparar los planes
                            </Button>
                        </div>
                    </article>

                    <article className="suscripcion-content__plan suscripcion-content__plan--plus">
                        <Marcador className='suscripcion-content__plan__marcador'/>
                        <h2>Bücher +</h2>
                        <p>Que tu lectura no tenga límites.</p>
                        <p className="suscripcion-content__plan__precio">
                            $ 15000
                            <span className="suscripcion-content__plan__periodo"> /mes</span>
                        </p>
                        <div className="suscripcion-content__plan__accion">
                            <Button type="button" variant="default" color="oscuro">
                                Suscribirme
                            </Button>
                        </div>
                    </article>
                </div>
            </section>

            <section className="suscripcion-content__seccion">
                <span className="suscripcion-content__label">Beneficios de Bücher +</span>
                <div className="suscripcion-content__beneficios">
                    {beneficios.map(({ id, titulo, descripcion, icono: Icono }) => (
                        <article key={id} className="suscripcion-content__beneficio">
                            <Icono className="suscripcion-content__icono" aria-hidden="true" />
                            <div>
                                <h3>{titulo}</h3>
                                <p>{descripcion}</p>
                            </div>
                        </article>
                    ))}
                </div>
            </section>

            <div className="suscripcion-content__columnas">
                <div className="suscripcion-content__columna">
                    <section className="suscripcion-content__seccion">
                        <span className="suscripcion-content__label">
                            ¿Por qué necesito Bücher +?
                        </span>
                        <div className="suscripcion-content__card">
                            <p>
                                Bücher busca que compartir libros sea fácil y accesible para
                                todos. Para hacerlo posible, la suscripción financia los costos
                                logísticos asociados a los préstamos, el mantenimiento de la
                                plataforma y las herramientas que permiten conectar lectores de
                                manera segura. Gracias a este sistema, los usuarios pueden
                                solicitar libros sin preocuparse por los gastos de traslado y
                                disfrutar de préstamos sin límite mensual.
                            </p>
                        </div>
                    </section>

                    <section className="suscripcion-content__seccion">
                        <span className="suscripcion-content__label">Comparativa de planes</span>
                        <div className="suscripcion-content__card">
                            <table className="suscripcion-content__tabla">
                                <thead>
                                    <tr>
                                        <th></th>
                                        <th>Gratis</th>
                                        <th>Bücher +</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {comparativa.map((fila) => (
                                        <tr key={fila.id}>
                                            <td>{fila.funcion}</td>
                                            <td>{fila.gratis ? <Tilde /> : <Cruz />}</td>
                                            <td>{fila.plus ? <Tilde /> : <Cruz />}</td>
                                        </tr>
                                    ))}
                                </tbody>
                            </table>
                        </div>
                    </section>
                </div>

                <div className="suscripcion-content__columna">
                    <section className="suscripcion-content__seccion">
                        <span className="suscripcion-content__label">Información importante</span>
                        <div className="suscripcion-content__card suscripcion-content__info">
                            <h3>¿Cómo funcionan los préstamos?</h3>
                            <ol className="suscripcion-content__info__pasos">
                                {pasos.map((paso) => (
                                    <li key={paso}>{paso}</li>
                                ))}
                            </ol>
                        </div>
                    </section>

                    <div className="suscripcion-content__card suscripcion-content__info">
                        <h3>¿Qué pasa si pierdo o rompo el libro?</h3>
                        <p>
                            En caso de que el ejemplar que tomaste prestado sufra algún tipo de
                            daño o lo pierdas se te descontará el valor del libro para que
                            repongamos el mismo a su dueño.
                        </p>
                    </div>

                    <div className="suscripcion-content__card suscripcion-content__info">
                        <h3>¿Aún tienes dudas?</h3>
                        <p>
                            Consulta nuestras{' '}
                            <Link to="/configuracion/preguntas-frecuentes" className="suscripcion-content__link">
                                preguntas frecuentes
                            </Link>
                        </p>
                    </div>
                </div>
            </div>
        </div>
    );
};