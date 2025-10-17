import { useFetch } from '../../utils/FetchUtils';
export const Tendencias = () => {
    const { data, loading, error } = useFetch("registro/podio");

    return (
        <div>
            <p>{JSON.stringify(data, null, 2)}</p>
            <p>{loading && "Cargando..."}</p>
            <p>{error}</p>
        </div>
    )
}