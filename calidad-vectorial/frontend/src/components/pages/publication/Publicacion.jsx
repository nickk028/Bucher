import { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { useFetch, usePost } from '../../utils/FetchUtils';
import { UsuarioDetalles } from '../../elements/user/detail/UsuarioDetalles';
import { Button } from '../../elements/buttons/Button';
import { PopUp } from '../../elements/modal/PopUp';
import './Publicacion.css';

import iconoCheck from '../../../assets/img/icons/crear/tildeVistaPrevia.svg';
import iconoReloj from '../../../assets/img/icons/crear/relojVistaPrevia.svg';

const ESTADO_EJEMPLAR = 'Muy bueno';
const DETALLES_EJEMPLAR = ['Subrayado', 'Con anotaciones'];
const CANTIDAD_IMAGENES = 3;

export const Publicacion = () => {
    const { id } = useParams();
    const {
        data: publicacion,
        error: errorFetch,
        loading: loadingError,
        refetch,
    } = useFetch('publicacion/' + id);
    const {
        data: respuestaPost,
        error: errorPost,
        loading: loadingPost,
        execute,
    } = usePost('publicacion/prestamo/' + id);
    const disponible = publicacion?.estadoPublicacion === 'Disponible';

    const [mostrarPopUp, setMostrarPopUp] = useState(false);

    const handlePedirLibro = () => {
        execute();
    };

    useEffect(() => {
        if (respuestaPost && !errorPost) {
            setMostrarPopUp(true);
        }
    }, [respuestaPost, errorPost]);

    return (
        <>
            {mostrarPopUp && (
                <PopUp
                    onClick={() => {
                        setMostrarPopUp(false);
                        refetch();
                    }}
                    titulo="✔️ Préstamo creado con éxito ✔️"
                >
                    Tu préstamo ya está procesándose. Te notificaremos cuando tu pedido esté listo.
                </PopUp>
            )}

            <main className="body-pub">
                {loadingError ? (
                    <p>Cargando...</p>
                ) : (
                    publicacion?.titulo && (
                        <article className="body-pub__publicacion">
                            <div className="body-pub__publicacion__aside">
                                <img
                                    className="body-pub__publicacion__aside__img"
                                    src={publicacion.urlFoto}
                                    alt="Foto del libro"
                                />
                                <Button
                                    variant="default"
                                    color={disponible ? 'oscuro' : 'disabled'}
                                    isDisabled={!disponible}
                                    onClick={handlePedirLibro}
                                >
                                    {disponible ? 'Pedir libro' : 'Libro no disponible'}
                                </Button>
                            </div>

                            <div className="body-pub__publicacion__text">
                                <h1 className="body-pub__publicacion__text__title">
                                    {publicacion.titulo}
                                </h1>
                                <p className="body-pub__publicacion__text__author">
                                    {publicacion.nombre}
                                </p>

                                <div className="body-pub__publicacion__text__duracion">
                                    <img
                                        className="body-pub__publicacion__text__icono"
                                        src={iconoReloj}
                                        alt=""
                                    />
                                    Duración del préstamo: {publicacion.limiteDias}
                                </div>

                                <section className="body-pub__publicacion__text__card">
                                    <h2 className="body-pub__publicacion__text__card__title">
                                        Información del ejemplar
                                    </h2>

                                    <div className="body-pub__publicacion__text__fila">
                                        <img
                                            className="body-pub__publicacion__text__icono"
                                            src={iconoCheck}
                                            alt=""
                                        />
                                        Estado del ejemplar: {ESTADO_EJEMPLAR}
                                    </div>

                                    <div className="body-pub__publicacion__text__fila">
                                        <img
                                            className="body-pub__publicacion__text__icono"
                                            src={iconoCheck}
                                            alt=""
                                        />
                                        Detalles:
                                        {DETALLES_EJEMPLAR.map((detalle) => (
                                            <span
                                                key={detalle}
                                                className="body-pub__publicacion__text__chip"
                                            >
                                                {detalle}
                                            </span>
                                        ))}
                                    </div>

                                    <h3 className="body-pub__publicacion__text__card__subtitle">
                                        Comentarios adicionales
                                    </h3>
                                    <p className="body-pub__publicacion__text__card__parrafo">
                                        {publicacion.descripcion}
                                    </p>
                                </section>

                                <section className="body-pub__publicacion__text__card">
                                    <h2 className="body-pub__publicacion__text__card__title">
                                        Imágenes del ejemplar
                                    </h2>
                                    <div className="body-pub__publicacion__text__galeria">
                                        {Array.from({ length: CANTIDAD_IMAGENES }, (_, index) => (
                                            <div
                                                key={index}
                                                className="body-pub__publicacion__text__galeria__img"
                                            />
                                        ))}
                                    </div>
                                </section>

                                <UsuarioDetalles nombre={publicacion.usuarioCreador}>
                                    {publicacion.descripcionUsuario}
                                </UsuarioDetalles>

                            </div>
                        </article>
                    )
                )}
            </main>
        </>
    );
};