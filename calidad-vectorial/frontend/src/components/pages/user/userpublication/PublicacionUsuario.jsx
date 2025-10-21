import { Link } from 'react-router-dom';
import { useFetch } from '../../../utils/FetchUtils';

export const PublicacionUsuario = () => {
    const { data, loading, error } = useFetch("publicacion/propias");

    return (
        <div>
            <p>{JSON.stringify(data, null, 2)}</p>
            <p>{loading && "Cargando..."}</p>
            <p>{error}</p>
            <Link to="/index">Index</Link>
        </div>
    );
}