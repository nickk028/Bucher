import { Link } from 'react-router-dom';
import { useState, useEffect } from "react";
import { getData } from '../../utils/FetchUtils';

export const Index = () => {
    const [message, setMessage] = useState("");

    const fetchPublicacion = async (signal) => {
        try {
            const respond = await getData("publicacion", signal);
            const text = await respond.text();
            setMessage(text);
        } catch (error) {
            if (error.name !== 'AbortError') {
                setMessage("Hubo un error: " + error.message);
            } 
        }
    }

    useEffect(() => {
        const controller = new AbortController();
        fetchPublicacion(controller.signal);
        return () => controller.abort();
    }, []);

  return (
    <div>
        <h1>Index</h1>
        <p>{message}</p>
        <Link to="/crear-publicacion">Crear Nueva Publicación</Link>
    </div>
  )
}
