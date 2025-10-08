import { Link } from 'react-router-dom';
import { useState, useEffect } from "react";
import { fetchUsuario } from '../../utils/UserUtils';

export const Usuario = () => {
    const [message, setMessage] = useState("");
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(false);

    const cargarUsuario = async (signal) => {
        setLoading(true);
        const response = await fetchUsuario(signal);
        if (response.error !== "abortado") {
            setError(response.error || "");
            setMessage(typeof response.message === "object" ? JSON.stringify(response.message, null, 2) : response.message || "");
        }
        setLoading(false);
    }

    useEffect(() => {
        const controller = new AbortController();
        cargarUsuario(controller.signal);
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