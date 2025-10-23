import { useState } from "react";
import { Link } from 'react-router-dom';
import { useFetch, usePost } from '../../../utils/FetchUtils';
import './Biblioteca.css';
import { Autocompletar } from "../../../elements/autocomplete/Autocompletar";
import { Input } from "../../../elements/input/Input";
import { Button } from "../../../elements/buttons/Button";

export const Biblioteca = () => {
    const [titulo, setTitulo] = useState("");
    const [pagina, setPagina] = useState("");
    const [estadoLectura, setEstadoLectura] = useState("");
    const [puntuacion, setPuntuacion] = useState("");

    const { data : dataBiblioteca, error : errorBiblioteca, loading : loadingBiblioteca } = useFetch("biblioteca");
    const { data : dataPost, loading : loadingPost, error : errorPost, execute } = usePost("biblioteca");
    const { data : dataLibros, error : errorLibros, loading : loadingLibros } = useFetch("libro/todos");

    const handleAgregarLibroUsuario = async (e) => {
        e.preventDefault();
        await execute({ titulo, pagina, estadoLectura, puntuacion });
    };

    return (
        <main>
            <div>
            <h1>Ver Libros</h1>
            <p>{JSON.stringify(dataBiblioteca, null, 2)}</p>
            <p>{loadingBiblioteca && "Cargando biblioteca..."}</p>
            <p>{errorBiblioteca}</p>
        </div>

        <h1>Añadir libro</h1>
        <form onSubmit={handleAgregarLibroUsuario}>
            <Autocompletar
                options={dataLibros ? dataLibros.map(libro => libro.titulo) : []}
                placeholder="Título"
                onChange={e => setTitulo(e.target.value)}
                value = {titulo}
            />
            <Input type="number" value={pagina} onChange={e => setPagina(e.target.value)} placeholder="pagina" required={false}/>
            <Input type="text" value={estadoLectura} onChange={e => setEstadoLectura(e.target.value)} placeholder="estadoLectura" required={false}/>
            <Input type="number" value={puntuacion} onChange={e => setPuntuacion(e.target.value)} placeholder="puntuacion" required={false}/>
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