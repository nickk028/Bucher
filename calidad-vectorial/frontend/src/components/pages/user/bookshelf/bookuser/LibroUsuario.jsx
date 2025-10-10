import { Link } from 'react-router-dom';
import { useState, useEffect } from "react";
import { getData } from '../../../../utils/FetchUtils';

export const LibroUsuario = (posicion) => {
    const [message, setMessage] = useState("");
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(false);

    const fetchLibroUsuario = async (signal) => {
        try {
            setLoading(true);
            const respond = await getData("biblioteca/" + posicion, signal);
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
        fetchLibroUsuario(controller.signal);
        return () => controller.abort();
    }, []);

    return (
        <div>
            <p>{message}</p>
            <p>{loading && "Cargando..."}</p>
            <p>{error}</p>
            <Link to="/index">Index</Link>
        </div>
    );
}