import { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { useFetch, usePost } from '../../../utils/FetchUtils';
import './Libro.css';
import { Button } from '../../../elements/buttons/Button';
import { UsuarioDetalles } from '../../../elements/user/detail/UsuarioDetalles';
import { PopUp } from '../../../elements/modal/PopUp';
import Estrella from '../../../../assets/img/icons/utils/estrellaGrande.svg?react';

export const Libro = () => {
    const { id } = useParams();
    const { data: dataUsuario, refetch: refetchUsuario } = useFetch('usuario/propio');
    const { data: libro, error: errorFetch, loading: loadingError } = useFetch('libro/' + id);
    const {
        data: dataPost,
        loading: loadingPost,
        error: errorPost,
        execute: executePost,
    } = usePost('libro-usuario');

    const [mostrarPopUp, setMostrarPopUp] = useState(false);

    const esLibroUsuario = dataUsuario && dataUsuario.librosUsuario ? dataUsuario.librosUsuario.find(item => item.titulo === libro?.titulo) : undefined;
    const libroUsuarioId = esLibroUsuario?.id;

    const {
        loading: loadingModificarLibro,
        execute: executeModificarLibro,
    } = usePost('libro-usuario/' + libroUsuarioId, 'PUT');

    const handleAgregarLibroUsuario = async (e) => {
        e.preventDefault();

        await executePost({ titulo: libro.titulo });
        await refetchUsuario();
    };

    const handleModificarLibroUsuario = async (e, estadoLectura) => {
        e.preventDefault();

        await executeModificarLibro({ estadoLectura });

        await refetchUsuario();
    };

    useEffect(() => {
        if (dataPost && !errorPost) {
            setMostrarPopUp(true);
        }
    }, [dataPost, errorPost]);

    return (
        <>
            {mostrarPopUp && (
                <PopUp onClick={() => setMostrarPopUp(false)} titulo="✔️ Libro agreado ✔️">
                    El libro ya ha sido agreagdo a tu biblioteca.
                </PopUp>
            )}
            <main className="body-libro">
                {loadingError ? (
                    <p>Cargando...</p>
                ) : libro.titulo ? (
                    <article className="body-pub__libro">
                        <div className="body-pub__libro__aside">
                            <img
                                className="body-pub__libro__aside__img"
                                src={libro.urlFoto}
                                alt="Foto del libro"
                            />
                            {!esLibroUsuario ? (
                                <Button
                                    onClick={handleAgregarLibroUsuario}
                                    disabled={loadingPost}
                                    variant="default"
                                    color="oscuro"
                                >
                                    Agregar a la biblioteca
                                </Button>
                            ) : esLibroUsuario.estadoLectura === 'Pendiente' ||
                                esLibroUsuario.estadoLectura === 'Leido' ||
                                esLibroUsuario.estadoLectura === 'Abandonado' ? (
                                <Button
                                    onClick={(e) => handleModificarLibroUsuario(e, 'Leyendo')}
                                    disabled={loadingModificarLibro}
                                    variant="default"
                                    color="oscuro"
                                >
                                    Comenzar a leer
                                </Button>
                            ) : (
                                <>
                                    <Button
                                        onClick={(e) => handleModificarLibroUsuario(e, 'Leido')}
                                        disabled={loadingModificarLibro}
                                        variant="default"
                                        color="oscuro"
                                    >
                                        Lo terminé de leer
                                    </Button>

                                    <Button
                                        onClick={(e) => handleModificarLibroUsuario(e, 'Abandonado')}
                                        disabled={loadingModificarLibro}
                                        variant="default"
                                        color="oscuro"
                                    >
                                        Abandoné el libro
                                    </Button>
                                </>
                            )}
                            
                        </div>
                        <div className="body-pub__libro__text">
                            <div>
                                <h1 className="body-pub__libro__text__title">{libro.titulo}</h1>
                                <p className="body-pub__libro__text__author">{libro.nombreAutor}</p>
                            </div>
                            <div className="body-pub__libro__text__estrellas">
                                <Estrella />
                                <Estrella />
                                <Estrella />
                                <Estrella />
                                <Estrella />
                                <div className="body-pub__libro__text__estrellas__valor">
                                    <h2>5 Estrellas</h2>
                                </div>
                            </div>

                            <div className="body-pub__libro__text__parrafo">
                                <h2>Sinopsis del libro</h2>
                                <p>{libro.descripcion}</p>
                            </div>

                            <div className="body-pub__libro__text__genero">
                                <p>Género:</p>
                                {libro.categorias.map((categoria, index) => (
                                    <div
                                        key={index}
                                        className="body-pub__libro__text__genero__item"
                                    >
                                        {categoria}
                                    </div>
                                ))}
                            </div>

                            <div className="body-pub__libro__text__item">
                                <UsuarioDetalles
                                    nombre={libro.nombreAutor}
                                    foto={libro.urlFotoAutor}
                                >
                                    {libro.descripcionAutor}
                                </UsuarioDetalles>
                            </div>
                        </div>
                    </article>
                ) : (
                    <p>{errorFetch}</p>
                )}
            </main>
        </>
    );
};
