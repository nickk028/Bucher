import { useState, useEffect } from "react";
import { Link, useLocation } from "react-router-dom";
import "./Header.css";

const Header = () => {
    const location = useLocation();
    const [click, setClick] = useState(location.pathname);

    useEffect(() => {
        setClick(location.pathname);
    }, [location]);

    return (
        <header className="header">
            <nav className="header__nav">
                <Link className="header__nav__item" to="/index">
                    <div alt="Logo de Bücher" className={`header__nav__item__bucher header__nav__item__bucher--${click=="/index" ? "selected" : ""}`} />
                </Link>

                <Link className="header__nav__item" to="/prestamos">
                    <div alt="Logo de Prestamos" className={`header__nav__item__prestamos header__nav__item__prestamos--${click=="/prestamos" ? "selected" : ""}`} />
                </Link>

                <Link className="header__nav__item" to="/comentarios-social">
                    <div alt="Logo de Comentario Social" className={`header__nav__item__comentario-social header__nav__item__comentario-social--${click=="/comentarios-social" ? "selected" : ""}`} />
                </Link>

                <Link className="header__nav__item" to="/biblioteca">
                    <div alt="Logo de Biblioteca" className={`header__nav__item__biblioteca header__nav__item__biblioteca--${click=="/biblioteca" ? "selected" : ""}`} />
                </Link>

                <Link className="header__nav__item" to={click.includes("/crear") ? click : "/crear"}>
                    <div alt="Logo de Crear" className={`header__nav__item__crear header__nav__item__crear--${click.includes("/crear")  ? "selected" : ""}`} />
                </Link>

                <Link className="header__nav__item" to="/tendencias">
                    <div alt="Logo de Tendencias" className={`header__nav__item__tendencias header__nav__item__tendencias--${click=="/tendencias" ? "selected" : ""}`} />
                </Link>

                <Link className="header__nav__item" to={click.includes("/configuracion") ? click : "/configuracion/editar-perfil"}>
                    <div alt="Logo de Configuracion" className={`header__nav__item__configuracion header__nav__item__configuracion--${click.includes("/configuracion") ? "selected" : ""}`}>
                    </div>
                </Link>
            </nav>
        </header>
    )
}
export default Header