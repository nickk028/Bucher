import { Link } from 'react-router-dom';
import { useFetch } from '../../utils/FetchUtils';
import Header from '../../elements/header/Header';
import PublicacionCard from '../../elements/publication/PublicacionCard';
import './Index.css';
import '../../elements/global.css';

export const Index = () => {
    const { data : publicaciones, error, loading } = useFetch("publicacion");

    return (
        <div className='body-index'>
            <Header />
            <main className='body-index__content'>
                {loading ? (
                    <p>Cargando...</p>
                ) : publicaciones.length > 0 ? (
                    <ul className='body-index__content__pub-list'>
                        {publicaciones.map(pub => (
                            <li className='body-index__content__pub-list__item' key={pub.id}> <Link to={`/publicacion/${pub.id}`}>
                                <PublicacionCard
                                    urlFoto={pub.urlFoto}
                                    titulo={pub.titulo}
                                    usuarioNickname={pub.usuarioNickname}
                                    estadoPublicacion={pub.estadoPublicacion}
                                    limiteDias={pub.limiteDias}>
                                </PublicacionCard>
                            </Link></li>
                        ))}
                    </ul>
                ) : (
                    <p>{error ? 'No fue posible obtener publicaciones.' : 'No hay publicaciones.'}</p>
                )}
                <Link to="/crear-publicacion">Crear Nueva Publicación</Link>
            </main>
        </div>
    )
}
