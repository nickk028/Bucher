import { Link } from 'react-router-dom';
import { Usuario } from '../../../elements/user/Usuario';
import { useState, useEffect } from "react";
import { getData } from '../../../utils/FetchUtils';

export const Biblioteca = () => {
    const [message, setMessage] = useState("");
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(false);

    const fetchBiblioteca = async (signal) => {
        try {
            setLoading(true);
            const respond = await getData("biblioteca", signal);
            if (respond.ok) {
                const data = await respond.json();
                setMessage(data);
            } else {
                setError("Error al obtener las publicaciones");
            }
        } catch (err) {
            if (err.name !== 'AbortError') {
                setError("Hubo un error: " + err.message);
            }
        }
        finally {
            setLoading(false);
        }
    }

    useEffect(() => {
        const controller = new AbortController();
        fetchBiblioteca(controller.signal);
        return () => controller.abort();
    }, []);

    return (
        <div>
            <Usuario/>
            <p>{message}</p>
            <p>{loading && "Cargando..."}</p>
            <p>{error}</p>
            <Link to="/index">Index</Link>
        </div>
    );
}