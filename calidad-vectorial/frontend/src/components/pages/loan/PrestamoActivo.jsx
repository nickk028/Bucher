import { useParams } from 'react-router-dom';
import { useFetch, usePost } from '../../utils/FetchUtils';
import { Button } from '../../elements/buttons/Button';
import { DetalleCard } from '../../elements/detail/DetalleCard';
import './PrestamoActivo.css';

import iconoCheck from '../../../assets/img/icons/crear/tildeVistaPrevia.svg';
import iconoReloj from '../../../assets/img/icons/crear/relojVistaPrevia.svg';
import pasoCompletado from '../../../assets/img/icons/prestamo/pasoCompletado.svg';
import pasoPendiente from '../../../assets/img/icons/prestamo/pasoPendiente.svg';

const PASOS = [
    { estado: 'solicitado', texto: 'Solicitado' },
    { estado: 'retirado', texto: 'Retirado' },
    { estado: 'enrevisión', texto: 'En revisión' },
    { estado: 'aentregar', texto: 'A entregar' },
    { estado: 'entregado', texto: 'Entregado' }
];

export const PrestamoActivo = () => {
    const { id } = useParams();

    const {data: dataRegistro, loading: loadingRegistro, error: errorRegistro} = useFetch(`registro/${id}`);
    const prestamo = dataRegistro ? dataRegistro.publicacion : undefined;
    const {data: dataDevolver, loading: loadingDevolver, error: errorDevolver, execute: executeDevolver} = usePost(`publicacion/devolucion/${prestamo?.id}`);

    const handleCancelarPrestamo = () => {
    };

    const handleDevolverEjemplar = () => {
        executeDevolver();
    };

    const PASOS_COMPLETADOS = PASOS.findIndex(
        paso => paso.estado === dataRegistro?.publicacion?.estadoPublicacion
    ) + 1;

    const progreso = (PASOS_COMPLETADOS - 1) / (PASOS.length - 1);

    return (
        <main className="body-prest">
            {dataRegistro.length !== 0 && (
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
                        <p className="body-prest__prestamo__text__author">{prestamo.nombre}</p>

                        <DetalleCard
                            titulo={prestamo.titulo}
                            pasos={PASOS.map(paso => paso.texto)}
                            pasosCompletados={PASOS_COMPLETADOS}
                            eventos={prestamo.eventos}
                        />

                        <div className="body-prest__prestamo__text__duracion">
                            <img
                                className="body-prest__prestamo__text__icono"
                                src={iconoReloj}
                                alt=""
                            />
                            Duración del préstamo: {prestamo.limiteDias} días
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
                                Estado del ejemplar: {prestamo.detallesEstadoLibro}
                            </div>

                            <div className="body-prest__prestamo__text__fila">
                                <img
                                    className="body-prest__prestamo__text__icono"
                                    src={iconoCheck}
                                    alt=""
                                />
                                Detalles:
                                {prestamo.detallesAdicionales.map((detalle) => (
                                    <span key={detalle} className="body-prest__prestamo__text__chip">
                                        {detalle}
                                    </span>
                                ))}
                            </div>

                            <h3 className="body-prest__prestamo__text__card__subtitle">
                                Comentarios adicionales
                            </h3>
                            <p className="body-prest__prestamo__text__card__parrafo">
                                {prestamo.descripcion}
                            </p>
                        </section>
                    </div>
                </article>
            )}
        </main>
    );
};