import { Link } from 'react-router-dom';
import { useState, useEffect } from "react";
import { getData } from '../../utils/FetchUtils';
import Header from '../../elements/header/Header';
import PublicacionCard from '../../elements/publication/PublicacionCard';

export const Index = () => {
    const [error, setError] = useState("");  
    const [loading, setLoading] = useState(false);
    const [publicaciones, setPublicaciones] = useState([]);

    const fetchPublicaciones = async (signal) => {
        try {  
            setLoading(true); 
            const respond = await getData("publicacion", signal);
            if (respond.ok) {
                const data = await respond.json();
                setPublicaciones(data);  
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
        fetchPublicaciones(controller.signal);
        return () => controller.abort();
    }, []);

  return (
    <div>
        <h1>Index</h1>
        <p>{error}</p>
        {loading ? (
            <p>Cargando...</p>
        ) : publicaciones.length > 0 ? (
            <ul>
                {publicaciones.map(pub => (
                    <li key={pub.id}>
                        <div className='body-index'>
                            <Header></Header>
                            <div className='body-index__content'>
                                <PublicacionCard
                                    urlFoto="https://upload.wikimedia.org/wikipedia/en/6/6b/Harry_Potter_and_the_Philosopher%27s_Stone_Book_Cover.jpg"
                                    titulo="Harry Potter y la piedra filosofal"
                                    usuarioNickname="lector@gmail.com"
                                    estadoPublicacion="Disponible"
                                    limiteDias="10">
                                </PublicacionCard>
                            </div>
                        </div>
                    </li>
                ))}
            </ul>
        ) : (
            <p>{error ? 'No fue posible obtener publicaciones.' : 'No hay publicaciones.'}</p>
        )}
        <Link to="/crear-publicacion">Crear Nueva Publicación</Link>
    </div>
  )
}