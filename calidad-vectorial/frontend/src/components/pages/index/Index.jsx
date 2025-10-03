import { Link } from 'react-router-dom';
import { useState, useEffect } from "react";

export const Index = () => {
    const [message, setMessage] = useState("");

    const fetchPublicacion = async (signal) => {
        try {
            const respond = await fetch("http://localhost:8080/publicacion", {
                method: "GET",
                credentials: "include",
                signal,
            });
            const text = await respond.text();
            setMessage(text);
        } catch (error) {
            if (error.name === 'AbortError') {
                console.log("Solicitud cancelada (" + error.message + ")");
                return;
            } 
            setMessage("Hubo un error: " + error.message);
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
