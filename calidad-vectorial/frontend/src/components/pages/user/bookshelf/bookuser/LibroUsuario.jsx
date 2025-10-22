import { Link, useParams } from 'react-router-dom';
import { useFetch } from "../../../../utils/FetchUtils"

export const LibroUsuario = () => {
    const { posicion } = useParams()
    const { data : bookUser, loading : loadingBookUser, error : ErrorBookUser } = useFetch("biblioteca/" + posicion);

    return (
        <div>
            {loadingBookUser ? (
                <p>Cargando Libro Usuario...</p>
            ) : bookUser ? (
                    <li>
                        <p> Titulo: {bookUser.titulo} </p>
                        <p> Pagina actual: {bookUser.paginaActual} </p>
                        <p> Estado Lectura: {bookUser.estadoLectura} </p>
                        <p> Puntuacion: {bookUser.puntuacion} </p>
                    </li>
            ) : (
                <p>{ErrorBookUser}</p>
            )}
            <Link to="/index">Index</Link>
        </div>
    );
}