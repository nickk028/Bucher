import React, { useMemo } from "react";
import { useFetch } from "../../utils/FetchUtils";
import Buscador from "../../elements/search/Buscador";
import { Link } from "react-router-dom";

export const Libros = () => {
    const { data : libros , error : errorFetch, loading : loadingError } = useFetch("libro/todos");
    const { data : librosordenados , error , loading  } = useFetch("libro/ordenados");

    // Agrupar `librosordenados` de forma robusta soportando varias formas del JSON:
    // - objeto: { categoria1: [...], categoria2: [...] }
    // - array de grupos: [{ categoria: 'X', lista: [...] }, ...]
    // - array de libros: [{ titulo, categoria, ... }, ...]
    const groupedCategories = useMemo(() => {
        if (!librosordenados) return {};

        if (Array.isArray(librosordenados)) {
            const first = librosordenados[0];

            // Array de grupos con propiedad lista/libros/items
            if (first && (first.lista || first.libros || first.items)) {
                const map = {};
                librosordenados.forEach(g => {
                    const name = g.categoria || g.nombre || 'Sin categoría';
                    const list = g.lista || g.libros || g.items || [];
                    map[name] = list;
                });
                return map;
            }

            // Array de libros -> agrupar por su propiedad categoria
            return librosordenados.reduce((acc, b) => {
                const name = b.categoria || 'Sin categoría';
                acc[name] = acc[name] || [];
                acc[name].push(b);
                return acc;
            }, {});
        }

        // Si ya es un objeto con claves = categorías
        if (typeof librosordenados === 'object') {
            return librosordenados;
        }

        return {};
    }, [librosordenados]);

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
            {loadingError ? (
                <p>Cargando...</p>
            ) : libros.length > 0 ? (
                <div className="">
                    <div className="">
                        {Object.entries(groupedCategories).map(([categoria, lista]) => (
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
                <p>{errorFetch ? errorFetch : "Error al cargar los libros."}</p>
            )}
        </div>
    )
}