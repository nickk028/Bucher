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
                    <div alt="Logo de Bücher" className={`header__nav__item__bucher`}>
                        <div alt="Logo de Bücher" className={`header__nav__item__bucher__fondo ${click == "/index" ? "selected" : ""}`} />
                    </div>
                    <div className={`header__nav__item__circle ${click == "/index" ? "circle--selected" : ""}`}></div>
                </Link>

                <Link className="header__nav__item" to="/tendencias">
                    <div alt="Logo de Tendencias" className={`header__nav__item__tendencias`}>
                        <div alt="Logo de Tendencias" className={`header__nav__item__tendencias__fondo ${click == "/tendencias" ? "selected" : ""}`} />
                    </div>
                    <div className={`header__nav__item__circle ${click == "/tendencias" ? "circle--selected" : ""}`}></div>
                </Link>

                <Link className="header__nav__item" to="/crear-publicacion">
                    <div alt="Logo de Crear" className={`header__nav__item__crear`}>
                        <div alt="Logo de Crear" className={`header__nav__item__crear__fondo ${click == "/crear-publicacion" ? "selected" : ""}`} />
                    </div>
                    <div className={`header__nav__item__circle ${click == "/crear-publicacion" ? "circle--selected" : ""}`}></div>
                </Link>

                <Link className="header__nav__item" to="/libros">
                    <div alt="Logo de Libro" className={`header__nav__item__libro`}>
                        <div alt="Logo de Libro" className={`header__nav__item__libro__fondo ${click == "/libros" ? "selected" : ""}`} />
                    </div>
                    <div className={`header__nav__item__circle ${click == "/libros" ? "circle--selected" : ""}`}></div>
                </Link>

                <Link className="header__nav__item" to={click.includes("/usuario") ? click : "/usuario/configuracion"}>
                    <div alt="Logo de Usuario" className={`header__nav__item__usuario`}>
                        <div alt="Logo de Usuario" className={`header__nav__item__usuario__fondo ${click.includes("/usuario") ? "selected" : ""}`} />
                    </div>
                    <div className={`header__nav__item__circle ${click.includes("/usuario") ? "circle--selected" : ""}`}></div>
                </Link>
            </nav>
        </header>
    )
}
export default Header