import { useParams } from 'react-router-dom';
import { useFetch } from '../../utils/FetchUtils';
import { Button } from '../../elements/buttons/Button';
import { DetalleCard } from '../../elements/detail/DetalleCard';
import './PrestamoActivo.css';

import iconoCheck from '../../../assets/img/icons/crear/tildeVistaPrevia.svg';
import iconoReloj from '../../../assets/img/icons/crear/relojVistaPrevia.svg';
import pasoCompletado from '../../../assets/img/icons/prestamo/pasoCompletado.svg';
import pasoPendiente from '../../../assets/img/icons/prestamo/pasoPendiente.svg';

const PASOS = ['Solicitado', 'Retirado', 'En revisión', 'A entregar', 'Entregado'];
const PASOS_COMPLETADOS = 2;

const prestamo = {
    titulo: 'Érase una vez un corazón roto',
    autor: 'Stephanie Garber',
    duracion: '1 semana',
    estadoEjemplar: 'Muy bueno',
    detalles: ['Subrayado', 'Con anotaciones'],
    comentarios:
        'El libro se encuentra en muy buen estado, las anotaciones son en lápiz y no molestan ni interrumpen en ningún momento la lectura (todas ellas se encuentran en los márgenes, de forma prolija).',
    eventos: [
        { texto: 'El ejemplar fue retirado del domicilio.', fecha: 'Vie, 23:02 hs.' },
        { texto: 'El pedido del préstamo fue procesado exitosamente.', fecha: 'Vie, 10:26 hs.' },
    ],
};

export const PrestamoActivo = () => {
    const { id } = useParams();

    const {data: dataRegistro, loading: loadingRegistro, error: errorRegistro} = useFetch('registro/' + id);

    const handleCancelarPrestamo = () => {
    };

    const handleDevolverEjemplar = () => {
    };

    const progreso = (PASOS_COMPLETADOS - 1) / (PASOS.length - 1);

    return (
        <main className="body-prest">
            {dataRegistro && (
                <article className="body-prest__prestamo">
                    <div className="body-prest__prestamo__aside">
                        <img
                            className="body-prest__prestamo__aside__img"
                            src={dataRegistro?.publicacion?.urlFoto}
                            alt="Foto del libro"
                        />
                        <div className="body-prest__prestamo__aside__botones">
                            <Button variant="default" color="oscuro" onClick={handleCancelarPrestamo}>
                                Cancelar préstamo
                            </Button>
                            <Button variant="default" color="oscuro" onClick={handleDevolverEjemplar}>
                                Devolver ejemplar
                            </Button>
                        </div>
                    </div>

                    <div className="body-prest__prestamo__text">
                        <h1 className="body-prest__prestamo__text__title">{prestamo.titulo}</h1>
                        <p className="body-prest__prestamo__text__author">{prestamo.autor}</p>

                        <DetalleCard
                            titulo={prestamo.titulo}
                            pasos={PASOS}
                            pasosCompletados={PASOS_COMPLETADOS}
                            eventos={prestamo.eventos}
                        />

                        <div className="body-prest__prestamo__text__duracion">
                            <img
                                className="body-prest__prestamo__text__icono"
                                src={iconoReloj}
                                alt=""
                            />
                            Duración del préstamo: {prestamo.duracion}
                        </div>

                        <section className="body-prest__prestamo__text__card">
                            <h2 className="body-prest__prestamo__text__card__title">
                                Información del ejemplar
                            </h2>

                            <div className="body-prest__prestamo__text__fila">
                                <img
                                    className="body-prest__prestamo__text__icono"
                                    src={iconoCheck}
                                    alt=""
                                />
                                Estado del ejemplar: {prestamo.estadoEjemplar}
                            </div>

                            <div className="body-prest__prestamo__text__fila">
                                <img
                                    className="body-prest__prestamo__text__icono"
                                    src={iconoCheck}
                                    alt=""
                                />
                                Detalles:
                                {prestamo.detalles.map((detalle) => (
                                    <span key={detalle} className="body-prest__prestamo__text__chip">
                                        {detalle}
                                    </span>
                                ))}
                            </div>

                            <h3 className="body-prest__prestamo__text__card__subtitle">
                                Comentarios adicionales
                            </h3>
                            <p className="body-prest__prestamo__text__card__parrafo">
                                {prestamo.comentarios}
                            </p>
                        </section>
                    </div>
                </article>
            )}
        </main>
    );
};