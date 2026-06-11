import "./NotFound.css"
import { Button } from "../../../elements/buttons/Button";
import { useNavigate } from "react-router-dom";

export const NotFound = () => {
    const navigate = useNavigate();

    return (
        <div className="not-found">
            <div className="not-found__box">
                <h1 className="not-found__code">404</h1>
                <p className="not-found__title">Página no encontrada</p>
                <p className="not-found__subtitle">La dirección que buscás no existe.</p>
                <Button variant="default" color="oscuro" onClick={() => navigate("/")}>
                    Volver al inicio
                </Button>
            </div>
        </div>
    );
}
