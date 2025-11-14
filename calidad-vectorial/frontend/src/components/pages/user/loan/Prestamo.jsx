import { useFetch } from "../../../utils/FetchUtils";
import PrestamoCard from "../../../elements/loan/PrestamoCard";
import "./Prestamo.css";

export const Prestamo = () => {
    const { data: prestamos, loading, error } = useFetch("registro");

    return (
        <div className="prestamos-page">
            <h1>Tus préstamos</h1>

            {loading && <p>Cargando...</p>}
            {error && <p>{error}</p>}

            {!loading && prestamos?.length > 0 ? (
                <ul className="prestamos-list">
                    {prestamos.map((prestamo, i) => (
                        <li key={i}>
                            <PrestamoCard prestamo={prestamo} />
                        </li>
                    ))}
                </ul>
            ) : (
                !loading && <p>No tenés préstamos.</p>
            )}
        </div>
    );
};