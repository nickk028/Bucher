import { Link } from 'react-router-dom';
import { Usuario } from '../../../elements/user/Usuario';
import { useEffect } from "react";
import { useFetch } from '../../../utils/FetchUtils';

export const Biblioteca = () => {
    const { data, error, loading } = useFetch("biblioteca");

    useEffect(() => {
        const controller = new AbortController();
        return () => controller.abort();
    }, []);

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