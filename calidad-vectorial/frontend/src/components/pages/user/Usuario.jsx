import { useFetch } from '../../utils/FetchUtils';
import { useState } from 'react';
import PublicacionCard from '../../elements/publication/PublicacionCard';
import { UsuarioBiblioteca } from '../../elements/user/data/UsuarioBiblioteca';
import { UsuarioPosteos } from '../../elements/user/data/UsuarioPosteos';
import { UsuarioPrestamos } from '../../elements/user/data/UsuarioPrestamos';
import { UsuarioPrestamosActivos } from '../../elements/user/data/UsuarioPrestamosActivos';
import './Usuario.css';

export const Usuario = () => {
    const {
        data: respuestaUsuario,
        loading: loadingUsuario,
        error: errorUsuario,
    } = useFetch("usuario/propio");

    const [pagina, setPagina] = useState('prestamos');

    return (
        <main className="usuario">
            <section className="usuario__section">
                <div className="usuario__section__img">
                    <img src={`/assets/img/avatares/${respuestaUsuario.avatar}`} alt="Foto del usuario" />
                </div>
                <div className="usuario__section__content">
                    <div className="usuario__section__content__datos">
                        <div className="usuario__section__content__datos__encabezado">
                            <h1>{respuestaUsuario.username && (respuestaUsuario.nickname)}</h1>
                            <p>Lector / Escritor</p>
                        </div>
                        <ul className="usuario__section__content__datos__lista">
                            <li>
                                <p>12</p>
                                <p>Libros</p>
                            </li>
                            <li>
                                <p>5</p>
                                <p>Publicaciones</p>
                            </li>
                            <li>
                                <p>13</p>
                                <p>Posteos</p>
                            </li>
                        </ul>
                    </div>
                    <div className="usuario__section__content__descripcion">
                        <p>{respuestaUsuario.descripcion && (respuestaUsuario.descripcion)}</p>
                    </div>
                </div>
            </section>

            <section className="usuario__content">
                <ul className="usuario__content__clasificacion">
                    <li
                        className={`usuario__content__clasificacion__item usuario__content__clasificacion__item--${pagina === 'prestamos' ? 'selected' : ''}`}
                        onClick={() => setPagina('prestamos')}
                    >
                        Publicaciones de préstamos
                    </li>
                    <li
                        className={`usuario__content__clasificacion__item usuario__content__clasificacion__item--${pagina === 'posteos' ? 'selected' : ''}`}
                        onClick={() => setPagina('posteos')}
                    >
                        Posteos sociales
                    </li>
                    <li
                        className={`usuario__content__clasificacion__item usuario__content__clasificacion__item--${pagina === 'biblioteca' ? 'selected' : ''}`}
                        onClick={() => setPagina('biblioteca')}
                    >
                        Biblioteca
                    </li>
                    <li
                        className={`usuario__content__clasificacion__item usuario__content__clasificacion__item--${pagina === 'prestamos activos' ? 'selected' : ''}`}
                        onClick={() => setPagina('prestamos activos')}
                    >
                        Prestamos activos
                    </li>
                </ul>

                {pagina === 'prestamos' ? (
                    <UsuarioPrestamos />
                ) : pagina === 'posteos' ? (
                    <UsuarioPosteos />
                ) : pagina === 'biblioteca' ? (
                    <UsuarioBiblioteca />
                ) : pagina === 'prestamos activos' ? (
                    <UsuarioPrestamosActivos />
                ) : (
                    <></>
                )}
            </section>
        </main>
    );
};
