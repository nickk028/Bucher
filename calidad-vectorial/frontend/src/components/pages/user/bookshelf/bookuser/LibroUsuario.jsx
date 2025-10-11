import { Link } from 'react-router-dom';
import { useFetch } from "../../../../utils/FetchUtils"

export const LibroUsuario = (posicion) => {
    const { data, loading, error } = useFetch("biblioteca/" + posicion);

    return (
        <div>
            <p>{data}</p>
            <p>{loading && "Cargando..."}</p>
            <p>{error}</p>
            <Link to="/index">Index</Link>
        </div>
    );
}