import { useFetch } from "../../../utils/FetchUtils";
import PrestamoCard from "../../../elements/loan/PrestamoCard";
import { Link } from "react-router-dom";
import "./Prestamo.css";

export const Prestamo = () => {
    const { data: prestamos, loading, error } = useFetch("registro");

    return (
        <div className="prestamos-page">
            <h1 className="prestamos-user-body__title">Tus préstamos</h1>

            {loading && <p>Cargando...</p>}
            {error && <p>{error}</p>}

            {!loading && prestamos?.length > 0 ? (
                <ul className="prestamos-list">
                    {prestamos.map((prestamo, i) => (
                        <li key={i}>
                            <Link to={`/publicacion/${prestamo.publicacion.id}`}>
                                <PrestamoCard prestamo={prestamo} />
                            </Link>
                        </li>
                    ))}
                </ul>
            ) : (
                !loading && <p>No tenés préstamos.</p>
            )}
        </div>
    );
};
