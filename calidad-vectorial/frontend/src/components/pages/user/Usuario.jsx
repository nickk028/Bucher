import { Link } from 'react-router-dom';
import { useState, useEffect, useRef} from "react";
import { getData } from '../../utils/FetchUtils';

export const Usuario= () => {
    const [message, setMessage] = useState("");
    const [error, setError] = useState("");
    const [loading, setLoading] = useRef(false);

    const fetchUsuario = async (signal) => {
        try {
            setLoading(true);
            const respond = await getData("usuario/propio", signal);
            const text = await respond.text();
            setMessage(text);
        } catch (err) {
            if (err.name !== 'AbortError') {
                setError("Hubo un error: " + err.message);
            } 
        } finally {
            setLoading(false);
        }
    }

    useEffect(() => {
        const controller = new AbortController();
        fetchUsuario(controller.signal);
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