import { Link } from 'react-router-dom';
import { useState, useEffect } from "react";
import { getData } from '../../utils/FetchUtils';
import Header from '../../elements/header/Header';
import PublicacionCard from '../../elements/publication/PublicacionCard';
import './Index.css';
import '../../elements/global.css';

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
        <div className='body-index'>
            <Header></Header>
            <div className='body-index__content'>
                {loading ? (
                    <p>Cargando...</p>
                ) : publicaciones.length > 0 ? (
                    <ul>
                        {publicaciones.map(pub => (
                            <li key={pub.id}>
                                <PublicacionCard
                                    urlFoto={pub.urlFoto}
                                    titulo={pub.titulo}
                                    usuarioNickname={pub.usuarioNickname}
                                    estadoPublicacion={pub.estadoPublicacion}
                                    limiteDias={pub.limiteDias}>
                                </PublicacionCard>
                            </li>
                        ))}
                    </ul>
                ) : (
                    <p>{error ? 'No fue posible obtener publicaciones.' : 'No hay publicaciones.'}</p>
                )}
                <Link to="/crear-publicacion">Crear Nueva Publicación</Link>
            </div>
        </div>
    )
}