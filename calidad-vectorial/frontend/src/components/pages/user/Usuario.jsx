import { Link } from 'react-router-dom';

import { useState, useEffect } from "react";

import { fetchUsuario } from '../../utils/UserUtils';

export const Usuario = () => {
    const [message, setMessage] = useState("");
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(false);

    const cargarUsuario = async () => {
        setLoading(true);
        const response = await fetchUsuario(controller.signal);
        if (response.error === 'aborted') {
            setLoading(false);
            return;
        }
        setError(data.error || "");
        setMessage(data.message || "");
        setLoading(false);
    }

    useEffect(() => {
        const controller = new AbortController();
        cargarUsuario();
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