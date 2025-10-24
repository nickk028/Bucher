import { useState } from "react";
import { Link } from 'react-router-dom';
import { useFetch, usePost } from '../../../utils/FetchUtils';
import './Biblioteca.css';
import { Autocompletar } from "../../../elements/autocomplete/Autocompletar";
import { Input } from "../../../elements/input/Input";
import { Button } from "../../../elements/buttons/Button";

export const Biblioteca = () => {
    const [tituloPost, setTituloPost] = useState("");
    const [paginaPost, setPaginaPost] = useState("");
    const [estadoLecturaPost, setEstadoLecturaPost] = useState("");
    const [puntuacionPost, setPuntuacionPost] = useState("");

    const { data : dataBiblioteca, error : errorBiblioteca, loading : loadingBiblioteca } = useFetch("biblioteca");
    const { data : dataPost, loading : loadingPost, error : errorPost, execute } = usePost("biblioteca");
    const { data : dataLibros, error : errorLibros, loading : loadingLibros } = useFetch("libro/todos");

    const handleAgregarLibroUsuario = async (e) => {
        e.preventDefault();
        await execute({ titulo: tituloPost, paginaActual: paginaPost, estadoLectura: estadoLecturaPost, puntuacion: puntuacionPost });
    };

    return (
        <main>
            <div>
                <h1>Ver Libros</h1>
                {loadingBiblioteca ? (
                    <p>Cargando biblioteca...</p>
                ) : dataBiblioteca ? (
                    <ul>
                        {dataBiblioteca.map(bookUser =>(
                            <li key = {bookUser.id} > 
                                <Link to={`usuario/biblioteca/${bookUser.id}`}>
                                    <p>Titulo: {bookUser.titulo}</p>
                                    <p>Pagina actual: {bookUser.paginaActual}</p>
                                    <p>Estado de lectura: {bookUser.estadoLectura}</p>
                                    <p>Puntuacion personal: {bookUser.puntuacion}</p>
                                </Link>
                            </li>
                        ))}
                    </ul>
                ) : (
                    <p>Error al cargar la biblioteca : {errorBiblioteca}</p>
                )}
            </div>   

            <h1>Añadir libro</h1>
            <form onSubmit={handleAgregarLibroUsuario}>
                <Autocompletar
                    options={dataLibros ? dataLibros.map(libro => libro.titulo) : []}
                    placeholder="Título"
                    onChange={e => setTituloPost(e.target.value)}
                    value = {tituloPost}
                />
                <Input type="number" value={paginaPost} onChange={e => setPaginaPost(e.target.value)} placeholder="Pagina" required={false}/>
                <Input type="text" value={estadoLecturaPost} onChange={e => setEstadoLecturaPost(e.target.value)} placeholder="Estado de Lectura" required={false}/>
                <Input type="number" value={puntuacionPost} onChange={e => setPuntuacionPost(e.target.value)} placeholder="Puntuacion" required={false}/>
                <Button type="submit" variant="default" color="oscuro" disabled={loadingPost}>Guardar Libro</Button>

                {dataPost && <p> Operacion post Exitosa </p>}
                {errorPost && <p>{errorPost}</p>}
                {loadingPost && <p>Cargando Post...</p>}
                {loadingLibros && <p>Cargando libros...</p>}
                {errorLibros && <p>{errorLibros}</p>}
            </form>
            <Link to="/index">Index</Link>
            <Link to="/biblioteca/categoria/terror">Terror</Link>
        </main>
    );
}