import { Link } from 'react-router-dom';
import { Usuario } from '../../../elements/user/Usuario';
import { useState } from "react";
import { useFetch, usePost } from '../../../utils/FetchUtils';

export const Biblioteca = () => {
    const [titulo, setTitulo] = useState("");

    const { data : dataBiblioteca, error : errorBiblioteca, loading : loadingBiblioteca } = useFetch("biblioteca");
    const { data : dataPost, loading : loadingPost, error : errorPost, execute } = usePost("biblioteca");

    const handleAgregarLibroUsuario = async (e) => {
        e.preventDefault();
        await execute({ titulo });
    };

    return (
        <div>
            <Usuario/>
            <div>
                <h1>Ver Libros</h1>
                <p>{dataBiblioteca}</p>
                <p>{errorBiblioteca && "Cargando..."}</p>
                <p>{loadingBiblioteca}</p>
                <Link to="/index">Index</Link>
            </div>

            <h1>Añadir libro</h1>
			<form onSubmit={handleAgregarLibroUsuario}>
				<input type="text" value={titulo} onChange={e => setTitulo(e.target.value)} placeholder="Título" />
				<button type="submit" disabled={loadingPost}>Guardar Libro</button>

				{dataPost && <p>{JSON.stringify(dataPost, null, 2)}</p>}
				{errorPost && <p>{errorPost}</p>}
			</form>
        </div>
    );
}