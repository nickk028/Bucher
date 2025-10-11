import { useEffect } from "react";
import { useFetch } from '../../utils/FetchUtils';
import Header from '../../elements/header/Header';
import Buscador from '../../elements/buscador/Buscador';
import { UsuarioDetalles } from '../../elements/user/UsuarioDetalles';
import './Publicacion.css';

export const Publicacion = (id) => {
    /*const [publicacion, setPublicacion] = useState(null);*/
    
    //const { data : publicacion , error, loading } = useFetch("publicacion/" + id);

    const publicacion = {
        titulo: "Harry Potter y la piedra filosofal",
        nombreAutor: "J. K. Rowling",
        descripcion: "Descipción super extensa de la publicación",
        estadoPublicacion: "Disponible",
        usuarioCreador: "lector@gmail.com",
        descripcionUsuario: "Descripción excesivamente larga del usuario",
        fechaCreacion:"07/10/2025"
    };

    return (
        <div className='body-pub'>
            <Header />
            <main className='body-pub__content'>
                <Buscador />
                <article className='body-pub__content__publicacion'>
                    <img className='body-pub__content__publicacion__img' src="https://upload.wikimedia.org/wikipedia/en/6/6b/Harry_Potter_and_the_Philosopher%27s_Stone_Book_Cover.jpg" alt="Foto del libro" />
                    <div className='body-pub__content__publicacion__text'>
                        <h1 className='body-pub__content__publicacion__text__title'>{publicacion.titulo}</h1>
                        <p className='body-pub__content__publicacion__text__author'>{publicacion.nombreAutor}</p>
                        <div className='body-pub__content__publicacion__text_item'>
                            <span className='body-pub__content__publicacion__text_item__subtitle'>Descripción del libro</span>
                            <p>{publicacion.descripcion}</p>
                        </div>
                        <div>
                            <span className='body-pub__content__publicacion__text_item__subtitle'>Estado</span>
                            {publicacion.estadoPublicacion}
                        </div>

                        <UsuarioDetalles nombre={publicacion.usuarioCreador}>{publicacion.descripcionUsuario}</UsuarioDetalles>

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