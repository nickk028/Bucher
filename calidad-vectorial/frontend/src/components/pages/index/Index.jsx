import { Link } from "react-router-dom";
import { useFetch } from "../../utils/FetchUtils";
import PublicacionCard from "../../elements/publication/PublicacionCard";
import { Input } from "../../elements/input/Input";
import { Button } from "../../elements/buttons/Button";
import "./Index.css";
import "../../../global.css";
import { useRef, useState } from "react";
import { AutoCompletarLibro } from "../../elements/autocomplete/types/AutoCompletarLibro";

export const Index = () => {
    const { data: publicacionesPorCategoria, error, loading } = useFetch("publicacion/ordenadas");
    const [mostrarFiltrador, setMostrarFiltrador] = useState(false);
    const ESTADOS = ["Disponible", "Entrega_pendiente", "Prestado", "Devolucion_pendiente", "No_disponible", "Indefinido"];
    const categoriasList = publicacionesPorCategoria ? Object.keys(publicacionesPorCategoria) : [];
    const [filtros, setFiltros] = useState({
        usuario: "",
        estado: "",
        limiteMin: "",
        limiteMax: "",
        libro: "",
        categoria: "",
    });

    const [filtrosAplicados, setFiltrosAplicados] = useState({
        usuario: "",
        estado: "",
        limiteMin: "",
        limiteMax: "",
        libro: "",
        categoria: "",
    });

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

        return correciones[categoria.toLowerCase()] || (categoria.charAt(0).toUpperCase() + categoria.slice(1));
    };

    const publicacionesFiltradas = (() => {
        if (!publicacionesPorCategoria) return publicacionesPorCategoria;

        const usuarioFiltro = filtrosAplicados.usuario.trim().toLowerCase();
        const estadoFiltro = filtrosAplicados.estado;
        const libroFiltro = filtrosAplicados.libro.trim().toLowerCase();
        const limiteMin = filtrosAplicados.limiteMin !== "" ? Number(filtrosAplicados.limiteMin) : null;
        const limiteMax = filtrosAplicados.limiteMax !== "" ? Number(filtrosAplicados.limiteMax) : null;
        const categoriaSeleccionada = filtrosAplicados.categoria;

        const resultado = {};

        const categoriasARecorrer = categoriaSeleccionada ? [categoriaSeleccionada] : Object.keys(publicacionesPorCategoria);

        categoriasARecorrer.forEach(categoria => {
            const lista = publicacionesPorCategoria[categoria] || [];
            resultado[categoria] = lista.filter(pub => {
                if (!pub) return false;

                if (usuarioFiltro) {
                    const nick = (pub.usuarioNickname || "").toString().toLowerCase();
                    if (!nick.includes(usuarioFiltro)) return false;
                }

                if (libroFiltro) {
                    const titulo = (pub.titulo || "").toString().toLowerCase();
                    if (!titulo.includes(libroFiltro)) return false;
                }

                if (estadoFiltro) {
                    if ((pub.estadoPublicacion || "") !== estadoFiltro) return false;
                }

                if (limiteMin !== null) {
                    const limiteDias = Number(pub.limiteDias ?? 0);
                    if (isNaN(limiteDias) || limiteDias < limiteMin) return false;
                }
                if (limiteMax !== null) {
                    const limiteDias = Number(pub.limiteDias ?? 0);
                    if (isNaN(limiteDias) || limiteDias > limiteMax) return false;
                }

                return true;
            });
        });

        return resultado;
    })();

    const aplicarFiltro = (e) => {
        e && e.preventDefault && e.preventDefault();
        setFiltrosAplicados({ ...filtros });
    };

    const limpiarFiltros = () => {
        const filtroVacio = { usuario: "", estado: "", limiteMin: "", limiteMax: "", libro: "", categoria: "" };
        setFiltros(filtroVacio);
        setFiltrosAplicados(filtroVacio);
    };

    return (
        <main className="body-index">
            <div className="filtrador-container ">
                <Button variant="default" color="oscuro" onClick={() => setMostrarFiltrador(!mostrarFiltrador)}>Filtrar</Button>
                {mostrarFiltrador && (
                    <div className="filtrador-opciones">
                        <form className="filtrador-form" onSubmit={aplicarFiltro}>
                            <div className="filtrador-row">
                                <Input type="text" name="usuario" value={filtros.usuario} onChange={e => setFiltros(f => ({ ...f, usuario: e.target.value }))} required={false}>
                                    Usuario
                                </Input>
                            </div>

                            <div className="filtrador-row">
                                <AutoCompletarLibro
                                    value={filtros.libro}
                                    onChange={e => setFiltros(f => ({ ...f, libro: e.target.value }))}
                                    required={false}>
                                        Título
                                </AutoCompletarLibro>
                            </div>

                            <div className="filtrador-row">
                                <label>Estado:</label>
                                <select value={filtros.estado} onChange={e => setFiltros(f => ({ ...f, estado: e.target.value }))}>
                                    <option value="">Todos</option>
                                    {ESTADOS.map(est => (
                                        <option key={est} value={est}>{est || '—'}</option>
                                    ))}
                                </select>
                            </div>

                            <div className="filtrador-row">
                                <label>Categoría:</label>
                                <select value={filtros.categoria} onChange={e => setFiltros(f => ({ ...f, categoria: e.target.value }))}>
                                    <option value="">Todas</option>
                                    {categoriasList.map(cat => (
                                        <option key={cat} value={cat}>{formatCategoryName(cat)}</option>
                                    ))}
                                </select>
                            </div>

                            <div className="filtrador-row">
                                <Input type="number" name="limiteMin" value={filtros.limiteMin} onChange={e => setFiltros(f => ({ ...f, limiteMin: e.target.value }))} required={false}>
                                    Límite días (min)
                                </Input>

                                <div style={{ width: 12 }} />

                                <Input type="number" name="limiteMax" value={filtros.limiteMax} onChange={e => setFiltros(f => ({ ...f, limiteMax: e.target.value }))} required={false}>
                                    Límite días (máx)
                                </Input>
                            </div>

                            <div className="filtrador-actions filtrador-row">
                                <Button variant="default" color="oscuro" type="submit">Aplicar</Button>
                                <Button variant="default" color="oscuro" type="button" onClick={limpiarFiltros}>Limpiar</Button>
                            </div>
                        </form>
                    </div>
                )}
            </div>

            <div className="">
                {loading ? (
                    <p>Cargando publicaciones...</p>
                ) : publicacionesFiltradas ? (
                    <div className="categories-container">
                        {Object.entries(publicacionesFiltradas).map(([categoria, lista]) =>
                            lista.length > 0 && (
                                <section key={categoria} className="pub-group">
                                    <h3 className="pub-group-title"> {formatCategoryName(categoria)} ({lista.length}) </h3>

                                    <div className="pub-row-wrapper">
                                        <button className="scroll-btn left" onClick={() => desplazarIzquierda(categoria)}>
                                            ‹
                                        </button>

                                        <ul className="pub-row" ref={el => (filasRef.current[categoria] = el)}>
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

                                        <button className="scroll-btn right" onClick={() => desplazarDerecha(categoria)}>
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
            </div>
        </main>
    );
};
