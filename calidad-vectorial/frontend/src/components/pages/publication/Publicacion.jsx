import { useParams } from 'react-router-dom';
import { useState, useEffect } from "react";
import { getData } from '../../utils/FetchUtils';
import Header from '../../elements/header/Header';
import Buscador from '../../elements/buscador/Buscador';
import './Publicacion.css';

export const Publicacion = () => {
    const [error, setError] = useState("");
    const { id } = useParams();
    /*const [publicacion, setPublicacion] = useState(null);*/
    const [loading, setLoading] = useState(false);

    const publicacion = {
        titulo: "Harry Potter y la piedra filosofal",
        descripcion: "Descipción super extensa de la publicación",
        estadoPublicacion: "Disponible",
        usuarioCreador: "lector@gmail.com",
        descripcionUsuario: "Descripción excesivamente larga del usuario",
        fechaCreacion:"07/10/2025"
    };

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
                <Buscador />
                <article className='body-pub__content__publicacion'>
                    <img className='body-pub__content__publicacion__img' src="https://upload.wikimedia.org/wikipedia/en/6/6b/Harry_Potter_and_the_Philosopher%27s_Stone_Book_Cover.jpg" alt="Foto del libro" />
                    <div className='body-pub__content__publicacion__text'>
                        <h1 className='body-pub__content__publicacion__text__title'>{publicacion.titulo}</h1>
                        <p>Autor</p>
                        <p>{publicacion.descripcion}</p>
                        <p>{publicacion.estadoPublicacion}</p>
                        <p>{publicacion.usuarioCreador}</p>
                        <p>{publicacion.descripcionUsuario}</p>
                        <p>{publicacion.fechaCreacion}</p>
                        {/*<p>{error}</p>*/}
                    </div>
                </article>
            </main>
        </div>
    )
}
