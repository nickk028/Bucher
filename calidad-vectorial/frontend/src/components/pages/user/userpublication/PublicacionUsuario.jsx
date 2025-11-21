import { Link } from "react-router-dom";
import { useFetch } from "../../../utils/FetchUtils";
import PublicacionCard from "../../../elements/publication/PublicacionCard";
import "./PublicacionUsuario.css";

export const PublicacionUsuario = () => {
    const { data : PublicacionUsuario, loading :loadingPublicacionUsuario, error : errorpublicacionUsuario } = useFetch("publicacion/propias");
    PublicacionUsuario ? console.log(PublicacionUsuario) : null;
    return (
        <main className="pub-user-body">
            <h1 className="pub-user-body__title">Tus publicaciones</h1>
            {loadingPublicacionUsuario ? (
                <p>Cargando biblioteca...</p>
            ) : PublicacionUsuario ? (
                <ul className="pub-user-bod__list">
                    {PublicacionUsuario.map(userPublication =>(
                        <Link to={`/publicacion/${userPublication.id}`} className="pub-user-bod__list__item"><li key = {userPublication.id}>
                            <PublicacionCard
                                urlFoto={userPublication.urlFoto}
                                titulo={userPublication.titulo}
                                usuarioNickname={userPublication.usuarioCreador}
                                estadoPublicacion={userPublication.estadoPublicacion}
                                limiteDias={userPublication.limiteDias}>
                            </PublicacionCard>
                        </li></Link>
                    ))}
                </ul>
            ) : (
                <p>Error al cargar la biblioteca : {errorpublicacionUsuario}</p>
            )}
        </main>
    );
}