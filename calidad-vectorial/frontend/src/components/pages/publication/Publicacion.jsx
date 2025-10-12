import { useParams } from "react-router-dom";
import { useFetch } from '../../utils/FetchUtils';
import Header from '../../elements/header/Header';
import Buscador from '../../elements/buscador/Buscador';
import { UsuarioDetalles } from '../../elements/user/UsuarioDetalles';
import { Button } from '../../elements/buttons/Button';
import './Publicacion.css';

export const Publicacion = () => {
    const { id } = useParams()
    const { data : publicacion , error, loading } = useFetch("publicacion/" + id);

    return (
        <main className='body-pub'>
        <Buscador />
            {loading ? (
                <p>Cargando...</p>
            ) : publicacion.titulo && (
                <article className='body-pub__publicacion'>

                    <aside className="body-pub__publicacion__aside">
                        <img className='body-pub__publicacion__aside__img' src = {publicacion.urlFoto} alt="Foto del libro" />
                        <Button variant="default" color="oscuro">Pedir libro</Button>
                    </aside>

                    <div className='body-pub__publicacion__text'>
                        <h1 className='body-pub__publicacion__text__title'>{publicacion.titulo}</h1>
                        <p className='body-pub__publicacion__text__author'>{publicacion.nombre}</p>

                        <div className='body-pub__publicacion__text__item'>
                            <span className='body-pub__publicacion__text__item__subtitle'>Estado de la publicación: </span>
                            {publicacion.estadoPublicacion}
                        </div>
                        <div className='body-pub__publicacion__text__item'>
                            <span className='body-pub__publicacion__text__item__subtitle'>Duración del préstamo: </span>
                            {publicacion.limiteDias}
                        </div>
                        <div className='body-pub__publicacion__text__item'>
                            <span className='body-pub__publicacion__text__item__subtitle'>Descripción del libro</span>
                            <p className='body-pub__publicacion__text__item__parrafo'>{publicacion.descripcion}</p>
                        </div>
                        <div>
                            <span className='body-pub__publicacion__text__item__subtitle'>Estado: </span>
                            {publicacion.estadoPublicacion}
                        </div>

                        <div>
                            <span className='body-pub__publicacion__text__item__subtitle'>Sobre su dueño</span>
                            <UsuarioDetalles nombre={publicacion.usuarioCreador}>{publicacion.descripcionUsuario}</UsuarioDetalles>
                        </div>

                        <div>
                            <span className='body-pub__publicacion__text__item__subtitle'>Fehca de creación: </span>
                            {publicacion.fechaCreacion}
                        </div>
                    </div>
                </article>
            )}
            <p>{error}</p>
        </main>
    )
}