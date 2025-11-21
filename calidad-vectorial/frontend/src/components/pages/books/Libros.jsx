import { useFetch } from "../../utils/FetchUtils";
import Buscador from "../../elements/search/Buscador";
import { Link } from "react-router-dom";
import { LibroCard } from "../../elements/book/LibroCard";
import { useRef } from 'react';
import './Libros.css';

export const Libros = () => {
    const { data : librosOrdenados , errorLibros , loadingLibros  } = useFetch("libro/ordenados");

    const filasRef = useRef({});

    // Desplaza la fila por una página
    const desplazarPorPagina = (categoria, direccion) => {
        const elemento = filasRef.current[categoria];
        if (!elemento) return;
        const anchoPagina = elemento.clientWidth || 300;
        elemento.scrollBy({ left: direccion * anchoPagina, behavior: 'smooth' });
    };

    const desplazarIzquierda = (categoria) => desplazarPorPagina(categoria, -1);
    const desplazarDerecha = (categoria) => desplazarPorPagina(categoria, 1);

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
                    <div className="categories-container">
                        {Object.entries(librosOrdenados).map(([categoria, lista]) => (
                            <section key={categoria} className="book-group">
                                <h3 className="book-group-title">{formatCategoryName(categoria)} ({lista.length})</h3>
                                <div className="books-row-wrapper">
                                    <button className="scroll-btn left" onClick={() => desplazarIzquierda(categoria)} aria-label={`Desplazar ${categoria} a la izquierda`}>
                                        ‹
                                    </button>

                                    <ul className="books-row" ref={el => { filasRef.current[categoria] = el; }}>
                                        {lista.map(libro => (
                                            <li className="book-item" key={libro.id || libro.titulo}>
                                                <Link to={`/libros/${libro.id || ''}`}>
                                                    <LibroCard titulo={libro.titulo} urlFoto={libro.urlFoto} />
                                                </Link>
                                            </li>
                                        ))}
                                    </ul>

                                    <button className="scroll-btn right" onClick={() => desplazarDerecha(categoria)} aria-label={`Desplazar ${categoria} a la derecha`}>
                                        ›
                                    </button>
                                </div>
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