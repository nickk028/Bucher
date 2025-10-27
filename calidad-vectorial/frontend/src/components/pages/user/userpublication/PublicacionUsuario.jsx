import { Link } from 'react-router-dom';
import { useFetch } from '../../../utils/FetchUtils';
import { LibroUsuario } from '../bookshelf/bookuser/LibroUsuario';

export const PublicacionUsuario = () => {
    const { data : PublicacionUsuario, loading :loadingPublicacionUsuario, error : errorpublicacionUsuario } = useFetch("publicacion/propias");

    return (
        <div>
            <div>
                <h1>Ver Libros</h1>
                {loadingPublicacionUsuario ? (
                    <p>Cargando biblioteca...</p>
                ) : PublicacionUsuario ? (
                    <ul>
                        {PublicacionUsuario.map(userPublication =>(
                            <li key = {userPublication.id} >
                                <Link to={`/publicacion/${userPublication.id}`}>
                                    <img src={userPublication.urlFoto} alt="Portada de la publicacion" height = "200px" width="160px"/>
                                    <p>Libro: {userPublication.titulo}</p>
                                    <p>Descripcion: {userPublication.descripcion}</p>
                                    <p>Estado de la Publicacion: {userPublication.estadoPublicacion}</p>
                                    <p>Limite de dias: {userPublication.limiteDias}</p>
                                    <p>Fecha de creacion: {userPublication.fechaCreacion}</p>
                                </Link>
                            </li>
                        ))}
                    </ul>
                ) : (
                    <p>Error al cargar la biblioteca : {errorpublicacionUsuario}</p>
                )}
            </div>
        </div>
    );
}