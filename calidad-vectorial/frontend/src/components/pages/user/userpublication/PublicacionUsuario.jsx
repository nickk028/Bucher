import { Link } from 'react-router-dom';
import { Usuario } from '../../../elements/user/Usuario';
import { useFetch } from '../../../utils/FetchUtils';

export const PublicacionUsuario = () => {
    const { data, loading, error } = useFetch("publicacion/propias");

    return (
        <div>
            <Usuario/>
            <p>{data}</p>
            <p>{loading && "Cargando..."}</p>
            <p>{error}</p>
            <Link to="/index">Index</Link>
        </div>
    );
}