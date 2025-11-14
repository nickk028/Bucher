import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import { useFetch, usePost } from "../../utils/FetchUtils";
import Buscador from "../../elements/search/Buscador";
import { UsuarioDetalles } from "../../elements/user/UsuarioDetalles";
import { Button } from "../../elements/buttons/Button";
import { PopUp } from "../../elements/modal/PopUp";
import "./Publicacion.css";

export const Publicacion = () => {
    const { id } = useParams();
    const { data: publicacion, error: errorFetch, loading: loadingError, refetch } = useFetch("publicacion/" + id);
    const { data : respuestaPost , error : errorPost, loading : loadingPost, execute } = usePost("publicacion/prestamo/" + id);
    const disponible = publicacion.estadoPublicacion === "Disponible";

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
                <PopUp onClick={() => { setMostrarPopUp(false); refetch();}}
                    titulo="✔️ Préstamo creado con éxito ✔️">
                    Tu préstamo ya está procesándose. Te notificaremos cuando tu pedido esté listo.
                </PopUp>
            )}

            <main className="body-pub">
                <Buscador />
                {loadingError ? (
                    <p>Cargando...</p>
                ) : publicacion.titulo && (
                    <article className="body-pub__publicacion">

                        <div className="body-pub__publicacion__aside">
                            <img className="body-pub__publicacion__aside__img" src = {publicacion.urlFoto} alt="Foto del libro" />
                            <Button variant="default" color={disponible ? "oscuro" : "disabled"} isDisabled={!disponible} onClick={handlePedirLibro}>
                                {disponible ? "Pedir libro" : "Libro no disponible"} </Button>
                        </div>

                        <div className="body-pub__publicacion__text">
                            <h1 className="body-pub__publicacion__text__title">{publicacion.titulo}</h1>
                            <p className="body-pub__publicacion__text__author">{publicacion.nombre}</p>

                            <div className="body-pub__publicacion__text__item">
                                <span className="body-pub__publicacion__text__item__subtitle">Estado de la publicación: </span>
                                {publicacion.estadoPublicacion}
                            </div>
                            <div className="body-pub__publicacion__text__item">
                                <span className="body-pub__publicacion__text__item__subtitle">Duración del préstamo: </span>
                                {publicacion.limiteDias}
                            </div>
                            <div className="body-pub__publicacion__text__item">
                                <span className="body-pub__publicacion__text__item__subtitle">Descripción del libro</span>
                                <p className="body-pub__publicacion__text__item__parrafo">{publicacion.descripcion}</p>
                            </div>
                            <div>
                                <span className="body-pub__publicacion__text__item__subtitle">Estado: </span>
                                {publicacion.estadoPublicacion}
                            </div>

                            <div>
                                <span className="body-pub__publicacion__text__item__subtitle">Sobre su dueño</span>
                                <UsuarioDetalles nombre={publicacion.usuarioCreador}>{publicacion.descripcionUsuario}</UsuarioDetalles>
                            </div>

                            <div>
                                <span className="body-pub__publicacion__text__item__subtitle">Fehca de creación: </span>
                                {publicacion.fechaCreacion}
                            </div>
                        </div>
                    </article>
                )}
            </main>
        </>
    );
};