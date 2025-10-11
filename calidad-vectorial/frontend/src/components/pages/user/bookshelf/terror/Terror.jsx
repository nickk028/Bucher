import { useFetch } from '../../../../utils/FetchUtils';

export const Terror = () => {
    const { data, loading, error } = useFetch("biblioteca/terror");

    return (
        <div>
            <p>{data}</p>
            <p>{loading && "Cargando..."}</p>
            <p>{error}</p>
        </div>
    )
}