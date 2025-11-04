import { Button } from "../../../elements/buttons/Button";
import Buscador from "../../../elements/search/Buscador";
import { useFetch, usePost } from "../../../utils/FetchUtils";
import { useParams } from "react-router-dom";

export const Libro = () => {
    const { id } = useParams();
    const { data : libro , error : errorFetch, loading : loadingError } = useFetch("libro/" + id);
    const { data : dataPost, loading : loadingPost, error : errorPost, execute } = usePost("biblioteca");

    const handleAgregarLibroUsuario = async (e) => {
        e.preventDefault();
        await execute({ titulo: libro.titulo });
    };


    return (
        <div className="">
            <Buscador />
            {loadingError ? (
                <p>Cargando...</p>
            ) : libro.titulo ? (
                <div className="">
                    <img src={libro.urlFoto} alt="Imagen del libro" height="315px" width="202px" />
                    <h2>{libro.titulo}</h2>
                    <Button  onClick={handleAgregarLibroUsuario} disabled={loadingPost} variant="default" color="oscuro">Agregar a mi biblioteca</Button>
                </div>
            ) : (
                <p>{errorFetch}</p>
            )}
        </div>
    )
}