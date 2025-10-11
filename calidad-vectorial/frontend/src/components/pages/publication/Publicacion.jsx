import { useFetch } from '../../utils/FetchUtils';
import Header from '../../elements/header/Header';
import Buscador from '../../elements/buscador/Buscador';
import { UsuarioDetalles } from '../../elements/user/UsuarioDetalles';
import './Publicacion.css';
import { useParams } from "react-router-dom";

export const Publicacion = () => {
    /*const [publicacion, setPublicacion] = useState(null);*/
    const { id } = useParams()
    const { data : publicacion , error, loading } = useFetch("publicacion/" + id);

    return (
        <div className='body-pub'>
            <Header />
            <main className='body-pub__content'>
                <Buscador />
                <article className='body-pub__content__publicacion'>
                    <img className='body-pub__content__publicacion__img' src = {publicacion.urlFoto} alt="Foto del libro" />
                    <div className='body-pub__content__publicacion__text'>
                        <h1 className='body-pub__content__publicacion__text__title'>{publicacion.titulo}</h1>
                        <p className='body-pub__content__publicacion__text__author'>{publicacion.nombre}</p>
                        <div className='body-pub__content__publicacion__text__item'>
                            <span className='body-pub__content__publicacion__text__item__subtitle'>Descripción del libro</span>
                            <p>{publicacion.descripcion}</p>
                        </div>
                        <div>
                            <span className='body-pub__content__publicacion__text__item__subtitle'>Estado: </span>
                            {publicacion.estadoPublicacion}
                        </div>

                        <div>
                            <span className='body-pub__content__publicacion__text__item__subtitle'>Sobre su dueño</span>
                            <UsuarioDetalles nombre={publicacion.usuarioCreador}>{publicacion.descripcionUsuario}</UsuarioDetalles>
                        </div>

                        <div>
                            <span className='body-pub__content__publicacion__text__item__subtitle'>Fehca de creación: </span>
                            {publicacion.fechaCreacion}
                        </div>
                        
                        {/*<p>{error}</p>*/}
                    </div>
                </article>
            </main>
            <p>{error}</p>
            <p>{loading && "Cargando..."}</p>
        </div>
    )
}