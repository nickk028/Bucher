import { useMemo, useState } from "react";
import { LibroCard } from "../../elements/book/LibroCard";
import { useFetch } from "../../utils/FetchUtils";
import "./Biblioteca.css";

// Mapea el enum EstadoLectura del backend (leyendo, abandonado, pendiente, leido, indefinido)
// a las clasificaciones visuales que usa LibroCard / el aside.
const ESTADO_A_CLASIFICACION = {
    leyendo: "leyendo",
    abandonado: "abandonados",
    pendiente: "quiero-leer",
    leido: "leidos",
    indefinido: null
};

const CLASIFICACIONES = [
    { key: "todos", label: "Todos" },
    { key: "leidos", label: "Leídos" },
    { key: "leyendo", label: "Leyendo" },
    { key: "quiero-leer", label: "Quiero leer" },
    { key: "abandonados", label: "Abandonados" }
];

const OPCIONES_ORDEN = [
    { key: "progreso", label: "Progreso" },
    { key: "autor", label: "Autor" },
    { key: "clasificacion", label: "Clasificación" },
    { key: "titulo", label: "Título" }
];

export const Biblioteca = () => {
    const { data: dataBiblioteca, error: errorBiblioteca, loading: loadingBiblioteca } = useFetch("biblioteca/6");

    const [filtroActivo, setFiltroActivo] = useState("todos");
    const [ordenActivo, setOrdenActivo] = useState(null);

    // Normaliza cada libro con su clasificación visual ya calculada
    const libros = useMemo(() => {
        if (!dataBiblioteca) return [];
        return dataBiblioteca.map((libro) => ({
            ...libro,
            clasificacion: ESTADO_A_CLASIFICACION[libro.estadoLectura?.toLowerCase?.()] ?? null
        }));
    }, [dataBiblioteca]);

    // Cuenta cuántos libros hay por cada clasificación (para las cards del aside)
    const conteos = useMemo(() => {
        const base = { todos: libros.length, leidos: 0, leyendo: 0, "quiero-leer": 0, abandonados: 0 };
        for (const libro of libros) {
            if (libro.clasificacion && base[libro.clasificacion] !== undefined) {
                base[libro.clasificacion] += 1;
            }
        }
        return base;
    }, [libros]);

    const librosFiltrados = useMemo(() => {
        const filtrados = filtroActivo === "todos"
            ? libros
            : libros.filter((libro) => libro.clasificacion === filtroActivo);

        if (!ordenActivo) return filtrados;

        const copia = [...filtrados];
        copia.sort((a, b) => {
            switch (ordenActivo) {
                case "progreso":
                    return (b.paginaActual ?? 0) - (a.paginaActual ?? 0);
                case "autor":
                    return (a.autor ?? "").localeCompare(b.autor ?? "");
                case "clasificacion":
                    return (a.estadoLectura ?? "").localeCompare(b.estadoLectura ?? "");
                case "titulo":
                    return (a.titulo ?? "").localeCompare(b.titulo ?? "");
                default:
                    return 0;
            }
        });
        return copia;
    }, [libros, filtroActivo, ordenActivo]);

    return (
        <main className="biblioteca">
            <header className="biblioteca__encabezado">
                <h1>Mi biblioteca</h1>
                <p>LLeva registro de tus lecturas y libros</p>
            </header>

            <section className="biblioteca__contenedor">
                <section className="biblioteca__contenedor__aside">
                    <section className="biblioteca__contenedor__aside__clasificacion">
                        {CLASIFICACIONES.map(({ key, label }) => (
                            <div
                                key={key}
                                className={`biblioteca__contenedor__aside__clasificacion__card biblioteca__contenedor__aside__clasificacion__card--${key}${filtroActivo === key ? " biblioteca__contenedor__aside__clasificacion__card--activo" : ""}`}
                                onClick={() => setFiltroActivo(key)}
                            >
                                {label} <br /> {conteos[key] ?? 0}
                            </div>
                        ))}
                    </section>

                    <section className="biblioteca__contenedor__aside__ordenar">
                        <h2>Ordenar por:</h2>
                        <ul className="biblioteca__contenedor__aside__ordenar__opciones">
                            {OPCIONES_ORDEN.map(({ key, label }) => (
                                <li
                                    key={key}
                                    className={`biblioteca__contenedor__aside__ordenar__opciones__opcion${ordenActivo === key ? " biblioteca__contenedor__aside__ordenar__opciones__opcion--activo" : ""}`}
                                    onClick={() => setOrdenActivo(ordenActivo === key ? null : key)}
                                >
                                    {label}
                                </li>
                            ))}
                        </ul>
                    </section>
                </section>

                <section className="biblioteca__contenedor__libros">
                    {loadingBiblioteca ? (
                        <p>Cargando biblioteca...</p>
                    ) : errorBiblioteca ? (
                        <p>Error al cargar la biblioteca: {errorBiblioteca}</p>
                    ) : librosFiltrados.length === 0 ? (
                        <p>No hay libros en esta clasificación.</p>
                    ) : (
                        librosFiltrados.map((libro) => (
                            <LibroCard
                                key={libro.id}
                                titulo={libro.titulo}
                                autor={libro.autor}
                                urlFoto={libro.urlFoto}
                                clasificacion={libro.clasificacion}
                                estrllas={false}
                            />
                        ))
                    )}
                </section>
            </section>
        </main>
    );
};