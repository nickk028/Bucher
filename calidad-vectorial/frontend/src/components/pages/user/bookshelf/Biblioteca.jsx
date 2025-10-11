import { Link } from 'react-router-dom';
import { Usuario } from '../../../elements/user/Usuario';
import { useState } from "react";
import { useFetch, usePost } from '../../../utils/FetchUtils';

export const Biblioteca = () => {
    const [titulo, setTitulo] = useState("");
    const [pagina, setPagina] = useState("");
    const [estadoLectura, setEstadoLectura] = useState("");
    const [puntuacion, setPuntuacion] = useState("");

    const { data : dataBiblioteca, error : errorBiblioteca, loading : loadingBiblioteca } = useFetch("biblioteca");
    const { data : dataPost, loading : loadingPost, error : errorPost, execute } = usePost("biblioteca");

    const handleAgregarLibroUsuario = async (e) => {
        e.preventDefault();
        await execute({ titulo, pagina, estadoLectura, puntuacion });
    };

    return (
        <div>
            <Usuario/>
            <div>
                <h1>Ver Libros</h1>
                <p>{JSON.stringify(dataBiblioteca, null, 2)}</p>
                <p>{loadingBiblioteca && "Cargando..."}</p>
                <p>{errorBiblioteca}</p>
            </div>

            <h1>Añadir libro</h1>
			<form onSubmit={handleAgregarLibroUsuario}>
				<input type="text" value={titulo} onChange={e => setTitulo(e.target.value)} placeholder="Título" />
                <input type="number" value={pagina} onChange={e => setPagina(e.target.value)} placeholder="pagina" />
                <input type="text" value={estadoLectura} onChange={e => setEstadoLectura(e.target.value)} placeholder="estadoLectura" />
                <input type="number" value={puntuacion} onChange={e => setPuntuacion(e.target.value)} placeholder="puntuacion" />
				<button type="submit" disabled={loadingPost}>Guardar Libro</button>

				{dataPost && <p> Operacion Exitosa </p>}
				{errorPost && <p>{errorPost}</p>}
			</form>
            <Link to="/index">Index</Link> 
            <Link to="/biblioteca/categoria/terror">Terror</Link>
        </div>
    );
}