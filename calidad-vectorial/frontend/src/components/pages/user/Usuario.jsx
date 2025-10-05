import { Link } from 'react-router-dom';
import { useState, useEffect, useRef} from "react";
import { getData } from '../../utils/FetchUtils';

export const Usuario= () => {
    const [message, setMessage] = useState("");

    const fetchUsuario = async (signal) => {
        try {
            const respond = await getData("usuario", signal);
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
        fetchUsuario(controller.signal);
        return () => controller.abort();
    }, []);

  return (
    <div>{message}</div>
  );
}