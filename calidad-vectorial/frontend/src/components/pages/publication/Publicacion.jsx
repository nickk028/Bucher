import { useParams } from 'react-router-dom';
import { useState, useEffect } from "react";
import { getData } from '../../utils/FetchUtils';
import Header from '../../elements/header/Header';
import './Publicacion.css';

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
        <div className='body-pub'>
            <Header />
            <main className='body-pub__content'>
                <nav className='body-pub__content__nav'></nav>
                {loading && <p>Cargando...</p>}
                {publicacion && !loading && (
                    <article className='body-pub__content__publicacion'>
                        <img className='body-pub__content__publicacion__img' src="https://upload.wikimedia.org/wikipedia/commons/8/88/Map_of_Arrakis_from_Dune_first_edition_dust_jacket.jpg" alt="Foto del libro" />
                        <div className='body-pub__content__publicacion__text'>
                            <h1 className='body-pub__content__publicacion__text__title'>{publicacion.titulo}</h1>
                            <p>{publicacion.descripcion}</p>
                            <p>{publicacion.estadoPublicacion}</p>
                            <p>{publicacion.usuarioCreador}</p>
                            <p>{publicacion.descripcionUsuario}descripcion</p>
                            {/*<p>{error}</p>*/}
                        </div>
                    </article>
                )}
            </main>
        </div>
    )
}