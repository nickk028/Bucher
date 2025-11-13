import { Link } from "react-router-dom";
import { useFetch } from "../../utils/FetchUtils";
import PublicacionCard from "../../elements/publication/PublicacionCard";
import "./Index.css";
import "../../../global.css";
import { useRef } from "react";

export const Index = () => {
    const { data: publicacionesPorCategoria, error, loading } = useFetch("publicacion/ordenadas");

    const filasRef = useRef({});

    const desplazarPorPagina = (categoria, direccion) => {
        const elemento = filasRef.current[categoria];
        if (!elemento) return;
        const anchoPagina = elemento.clientWidth || 300;
        elemento.scrollBy({ left: direccion * anchoPagina, behavior: "smooth"});
    };

    const desplazarIzquierda = (categoria) => desplazarPorPagina(categoria, -1);
    const desplazarDerecha = (categoria) => desplazarPorPagina(categoria, 1);

    const formatCategoryName = (rawCategoria) => {
        if (!rawCategoria) return '';
        let categoria = String(rawCategoria);

        const correciones = {
            aventura: 'Aventura',
            cienciaficcion: 'Ciencia Ficción',
            fantastico: 'Fantástico',
            suspenso: 'Suspenso',
            policial: 'Policial',
            realismomagico: 'Realismo Mágico',
            romance: 'Romance',
            terror: 'Terror',
            historico: 'Histórico',
            psicologia: 'Psicología',
            drama: 'Drama',
            humor: 'Humor',
            infantil: 'Infantil',
        };

        return correciones[categoria.toLowerCase()] || Categoria;
    };

    return (
        <main className="body-index">
            {loading ? (
                <p>Cargando publicaciones...</p>
            ) : publicacionesPorCategoria ? (
                <div className="categories-container">
                    {Object.entries(publicacionesPorCategoria).map(([categoria, lista]) =>
                        lista.length > 0 && (
                            <section key={categoria} className="pub-group">
                                <h3 className="pub-group-title"> {formatCategoryName(categoria)} ({lista.length}) </h3>

                                <div className="pub-row-wrapper">
                                    <button
                                        className="scroll-btn left"
                                        onClick={() => desplazarIzquierda(categoria)}
                                    >
                                        ‹
                                    </button>

                                    <ul
                                        className="pub-row"
                                        ref={el => (filasRef.current[categoria] = el)}
                                    >
                                        {lista.map(pub => (
                                            <li className="pub-item" key={pub.id}>
                                                <Link to={`/publicacion/${pub.id}`}>
                                                    <PublicacionCard
                                                        urlFoto={pub.urlFoto}
                                                        titulo={pub.titulo}
                                                        usuarioNickname={pub.usuarioNickname}
                                                        estadoPublicacion={pub.estadoPublicacion}
                                                        limiteDias={pub.limiteDias}
                                                    />
                                                </Link>
                                            </li>
                                        ))}
                                    </ul>

                                    <button
                                        className="scroll-btn right"
                                        onClick={() => desplazarDerecha(categoria)}
                                    >
                                        ›
                                    </button>
                                </div>
                            </section>
                        )
                    )}
                </div>
            ) : (
                <p>{error ? "Error cargando publicaciones." : "No hay publicaciones."}</p>
            )}
        </main>
    );
};
