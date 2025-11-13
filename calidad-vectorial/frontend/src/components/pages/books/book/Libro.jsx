import { useFetch, usePost } from "../../../utils/FetchUtils";
import { useParams } from "react-router-dom";
import "./Libro.css";
import { Button } from "../../../elements/buttons/Button";
import Buscador from "../../../elements/search/Buscador";
import { UsuarioDetalles } from "../../../elements/user/UsuarioDetalles";

export const Libro = () => {
    const { id } = useParams();
    const { data : libro , error : errorFetch, loading : loadingError } = useFetch("libro/" + id);
    const { data : dataPost, loading : loadingPost, error : errorPost, execute } = usePost("biblioteca");

    const handleAgregarLibroUsuario = async (e) => {
        e.preventDefault();
        await execute({ titulo: libro.titulo });
    };


    return (
        <main className="body-libro">
            <Buscador />
            {loadingError ? (
                <p>Cargando...</p>
            ) : libro.titulo ? (
                <article className="body-pub__libro">
                    <div className="body-pub__libro__aside">
                        <img className="body-pub__libro__aside__img" src = {libro.urlFoto} alt="Foto del libro" />
                        <Button  onClick={handleAgregarLibroUsuario} disabled={loadingPost} variant="default" color="oscuro">Agregar a mi biblioteca</Button>
                    </div>
                    <div className="body-pub__libro__text">
                        <h1 className="body-pub__libro__text__title">{libro.titulo}</h1>
                        <p className="body-pub__libro__text__author">{libro.nombreAutor}</p>

                        <div className="body-pub__libro__text__item">
                            <span className="body-pub__libro__text__item__subtitle">Géneros: </span>
                            {libro.categorias}
                        </div>

                        <div className="body-pub__libro__text__item">
                            <span className="body-pub__libro__text__item__subtitle">Descripción: </span>
                            <p className="body-pub__libro__text__item__parrafo">{libro.descripcion}</p>
                        </div>

                        <div className="body-pub__libro__text__item">
                            <span className="body-pub__libro__text__item__subtitle">Sobre su autor</span>
                            <UsuarioDetalles nombre={libro.nombreAutor} foto={libro.urlFotoAutor}>
                                {libro.descripcionAutor}
                                <div>
                                    Conoce más sobre este autor: <a className="body-pub__libro__text__item__link" href={libro.urlWikipediaAutor} target="_blank"> {libro.urlWikipediaAutor}</a>
                                </div>
                            </UsuarioDetalles>
                        </div>

                    </div>
                </article>
            ) : (
                <p>{errorFetch}</p>
            )}
        </main>
    );
};