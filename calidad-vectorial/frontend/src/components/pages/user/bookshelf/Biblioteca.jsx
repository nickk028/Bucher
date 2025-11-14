import { useState } from "react";
import { Link } from "react-router-dom";
import { useFetch, usePost } from "../../../utils/FetchUtils";
import { AutoCompletarLibro } from "../../../elements/autocomplete/types/AutoCompletarLibro";
import { LibroCard } from "../../../elements/book/LibroCard";
import { Input } from "../../../elements/input/Input";
import { Button } from "../../../elements/buttons/Button";
import { useEffect } from "react";
import { PopUp } from "../../../elements/modal/PopUp";

import "./Biblioteca.css";

export const Biblioteca = () => {
    const [tituloPost, setTituloPost] = useState("");
    const [paginaPost, setPaginaPost] = useState("");
    const [estadoLecturaPost, setEstadoLecturaPost] = useState("");
    const [puntuacionPost, setPuntuacionPost] = useState("");
    const [mostrarPopUp, setMostrarPopUp] = useState(false);

    const { data : dataBiblioteca, error : errorBiblioteca, loading : loadingBiblioteca } = useFetch("biblioteca");
    const { data : dataPost, loading : loadingPost, error : errorPost, execute } = usePost("biblioteca");
    const { data : dataLibros, error : errorLibros, loading : loadingLibros } = useFetch("libro/todos");

    const handleAgregarLibroUsuario = async (e) => {
        e.preventDefault();
        await execute({ titulo: tituloPost, paginaActual: paginaPost, estadoLectura: estadoLecturaPost, puntuacion: puntuacionPost });
    };

    useEffect(() => {
        if (dataPost && !errorPost) {
            setMostrarPopUp(true);

            // Limpiar formulario
            setTituloPost("");
            setPaginaPost("");
            setEstadoLecturaPost("");
            setPuntuacionPost("");
        }
    }, [dataPost, errorPost]);

    return (
        <>
            {mostrarPopUp && (
                <PopUp onClick={() => setMostrarPopUp(false)} titulo="✔️ Libro agregado con éxito ✔️">
                    El libro fue añadido a tu biblioteca.
                </PopUp>
            )}

        <main className="biblioteca-body">
            <div>
                <h1 className="biblioteca-body__title">Ver biblioteca </h1>
                {loadingBiblioteca ? (
                    <p>Cargando biblioteca...</p>
                ) : dataBiblioteca ? (
                    <ul>
                        {dataBiblioteca.map(bookUser =>(
                            <li key = {bookUser.id} >
                                <Link to={`/usuario/biblioteca/${bookUser.id}`}>
                                    <LibroCard urlFoto={bookUser.urlFoto} titulo={bookUser.titulo} />
                                </Link>
                            </li>
                        ))}
                    </ul>
                ) : (
                    <p>Error al cargar la biblioteca : {errorBiblioteca}</p>
                )}
            </div>

            <div className="">
                <div className="">
                    <h1 className="biblioteca-body__title--margen">Añadir libro a la biblioteca</h1>
                    <div className="biblioteca-agregar">
                        <form className="biblioteca-agregar__form" onSubmit={handleAgregarLibroUsuario}>
                            <div className="biblioteca-agregar__largo">
                                <AutoCompletarLibro placeholder="Título" onChange={e => setTituloPost(e.target.value)} value={tituloPost}/>
                                <Input type="text" value={estadoLecturaPost} onChange={e => setEstadoLecturaPost(e.target.value)} placeholder="Estado de lectura"/>
                            </div>
                            <div className="biblioteca-agregar__corto">
                                <Input type="number" value={paginaPost} onChange={e => setPaginaPost(e.target.value)} placeholder="Página actual"/>
                                <Input type="number" value={puntuacionPost} onChange={e => setPuntuacionPost(e.target.value)} placeholder="Puntuación"/>
                            </div>
                            <Button type="submit" variant="default" color="oscuro" disabled={loadingPost}>
                                Guardar Libro
                            </Button>
                        </form>
                    </div>

                </div>
                <div className="biblioteca-seleccionar-container">
                    <Button to = "/libros" variant="default" color="oscuro">Seleccionar libro</Button>
                </div>
            </div>
        </main>
    </>
    );
}