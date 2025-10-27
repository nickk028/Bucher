import { useFetch } from "../../../../utils/FetchUtils";
import { useParams } from "react-router-dom";
export const CategoriaLibro = () => {
    const { categoria } = useParams()
    const { data, loading, error } = useFetch("libro/categoria/" + categoria);

    return (
        <div>
            <p>{JSON.stringify(data, null, 2)}</p>
            <p>{loading && "Cargando..."}</p>
            <p>{error}</p>
        </div>
    )
}