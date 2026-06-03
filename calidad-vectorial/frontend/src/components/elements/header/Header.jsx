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

                <Link className="header__nav__item" to="/comentariossocial">
                    <div alt="Logo de Comentario Social" className={`header__nav__item__comentario-social header__nav__item__comentario-social--${click=="/comentariossocial" ? "selected" : ""}`} />
                </Link>

                <Link className="header__nav__item" to="/biblioteca">
                    <div alt="Logo de Biblioteca" className={`header__nav__item__biblioteca header__nav__item__biblioteca--${click=="/biblioteca" ? "selected" : ""}`} />
                </Link>

                <Link className="header__nav__item" to="/crear-publicacion">
                    <div alt="Logo de Crear" className={`header__nav__item__crear`}>
                        <div alt="Logo de Crear" className={`header__nav__item__crear__fondo ${click == "/crear-publicacion" ? "selected" : ""}`} />
                    </div>
                    <div className={`header__nav__item__circle ${click == "/crear-publicacion" ? "circle--selected" : ""}`}></div>
                </Link>

                <Link className="header__nav__item" to="/tendencias">
                    <div alt="Logo de Tendencias" className={`header__nav__item__tendencias header__nav__item__tendencias--${click=="/tendencias" ? "selected" : ""}`} />
                </Link>

                <Link className="header__nav__item" to={click.includes("/usuario") ? click : "/usuario/configuracion"}>
                    <div alt="Logo de Usuario" className={`header__nav__item__configuracion header__nav__item__configuracion--${click.includes("/configuracion") ? "selected" : ""}`}>
                    </div>
                </Link>
            </nav>
        </header>
    )
}
export default Header