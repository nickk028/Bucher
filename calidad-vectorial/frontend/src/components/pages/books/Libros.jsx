import React, { useMemo } from "react";
import { useFetch } from "../../utils/FetchUtils";
import Buscador from "../../elements/search/Buscador";
import { Link } from "react-router-dom";

export const Libros = () => {
    const { data : librosOrdenados , errorLibros , loadingLibros  } = useFetch("libro/ordenados");

    // Formatea una clave de categoría a una etiqueta legible para mostrar
    const formatCategoryName = (rawCategoria) => {
        if (!rawCategoria) return '';
        // Asegurar que sea un String para evitar errores
        let categoria = String(rawCategoria);

        // Separar palabras y capitalizar la primera letra de cada una
        const correcciones = {
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

        return correcciones[categoria.toLowerCase()] || categoria;
    };

    return (
        <div className="">
            <Buscador />
            {loadingLibros ? (
                <p>Cargando...</p>
            ) : librosOrdenados ? (
                <div className="">
                    <div className="">
                        {Object.entries(librosOrdenados).map(([categoria, lista]) => (
                            <section key={categoria} className="book-group">
                                <h3>{formatCategoryName(categoria)} ({lista.length})</h3>
                                <ul className="">
                                    {lista.map(libro => (
                                        <li className="" key={libro.id || libro.titulo}>
                                            <Link to={`/libros/${libro.id || ''}`}>
                                                <img src={libro.urlFoto} alt={libro.titulo || ''} height="315px" width="202px" />
                                                <h4>{libro.titulo}</h4>
                                            </Link>
                                        </li>
                                    ))}
                                </ul>
                            </section>
                            ))}
                    </div>
                </div>
            ) : (
                <p>{errorLibros}</p>
            )}
        </div>
    )
}