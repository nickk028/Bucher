import { Link } from "react-router-dom";
import "./Footer.css";

export const Footer = () => {
    return (
        <footer className="footer">
            <Link to="/" className="footer__title">Bücher</Link>
            <div className="footer__content">
                <div>
                    <p>Registrarse</p>
                    <p>Iniciar Sesión</p>
                </div>

                <div>
                    <p>Calidad Vectorial</p>
                    <p>Ins. Ind. Luis A. Huergo</p>
                </div>
            </div>
        </footer>
    )
    }