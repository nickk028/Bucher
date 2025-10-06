import { Link, useParams } from 'react-router-dom';
import { useState, useEffect } from "react";
import { getData } from '../../utils/FetchUtils';

export const Publicacion = () => {
    const [error, setError] = useState("");
    const { id } = useParams();
    const [publicacion, setPublicacion] = useState(null);
    const [loading, setLoading] = useState(false);

    const fetchPublicacion = async (signal, id) => {
        try {
            setLoading(true);
            const respond = await getData("publicacion/" + id, signal);
            if (respond.ok) {
                const data = await respond.json();
                setPublicacion(data);
                
            } else {
                setError("Error al obtener la publicación");
            }
        } catch (err) {
            if (err.name !== 'AbortError') {
                setError("Hubo un error: " + err.message);
            } 
        } finally {
            setLoading(false);
        }
    }

    useEffect(() => {
        if (!id) {
            setError("ID no proporcionado");
            setLoading(false);
            return;
        }
        const controller = new AbortController();
        fetchPublicacion(controller.signal, id);
        return () => controller.abort();
    }, [id]);

  return (
    <div>
        <h1>Publicacion</h1>
        {loading && <p>Cargando...</p>}
        {publicacion && !loading && (
            <div>
                <h2>{publicacion.titulo}</h2>
                <p>{publicacion.descripcion}</p>
                <p>{error}</p>
            </div>
        )}
        <Link to="/index">Index</Link>
    </div>
  )
}
