import { useFetch } from "../../utils/FetchUtils" 

export const Usuario = () => {
    const { data, loading, error } = useFetch("usuario/propio");

    return (
        <div>
            <p>{JSON.stringify(data, null, 2)}</p>
            <p>{loading && "Cargando..."}</p>
            <p>{error}</p>
        </div>
    );
}