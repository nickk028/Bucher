import { useFetch } from "../../utils/FetchUtils";
import Buscador from "../../elements/search/Buscador";
import { Link } from "react-router-dom";

export const Libros = () => {
    const { data : libros , error : errorFetch, loading : loadingError } = useFetch("libro/todos");

    return (
        <div className="">
            <Buscador />
            {loadingError ? (
                <p>Cargando...</p>
            ) : libros.length > 0 ? (
                <div className="">
                    <ul className="body-index__pub-list">
                        {libros.map(libro => (
                            <li className="body-index__pub-list__item" key={libro.id}>
                                <Link to={`/libros/${libro.id}`}>
                                    <img src={libro.urlFoto} alt="" height="315px" width="202px" />
                                    <h2>{libro.titulo}</h2>
                                    <p>{libro.categoria}</p>
                                </Link>
                            </li>
                        ))}
                    </ul>
                </div>
            ) : (
                <p>{errorFetch ? errorFetch : "Error al cargar los libros."}</p>
            )}
        </div>
    )
}