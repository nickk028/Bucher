import { useState } from "react";
import { Link } from "react-router-dom";
import './Header.css';

const Header = () => {

    const [click, setClick] = useState("");

    const handleClick = (boton) => {
        setClick(boton);
    };

    return (
        <header className="header">
            <nav className="header__nav">
                <Link className="header__nav__item" to="/index" onClick={() => handleClick("bucher")}>
                    <div alt="Logo de Bücher" className={`header__nav__item__bucher`}>
                        <div alt="Logo de Bücher" className={`header__nav__item__bucher__fondo ${click == "bucher" ? 'selected' : ''}`} />
                    </div>
                    <div className={`header__nav__item__circle ${click == "bucher" ? 'circle--selected' : ''}`}></div>
                </Link>

                <Link className="header__nav__item" to="/tendencias" onClick={() => handleClick("tendencias")}>
                    <div alt="Logo de Tendencias" className={`header__nav__item__tendencias`}>
                        <div alt="Logo de Tendencias" className={`header__nav__item__tendencias__fondo ${click == "tendencias" ? 'selected' : ''}`} />
                    </div>
                    <div className={`header__nav__item__circle ${click == "tendencias" ? 'circle--selected' : ''}`}></div>
                </Link>

                <Link className="header__nav__item" to="/crear-publicacion" onClick={() => handleClick("crear")}>
                    <div alt="Logo de Crear" className={`header__nav__item__crear`}>
                        <div alt="Logo de Crear" className={`header__nav__item__crear__fondo ${click == "crear" ? 'selected' : ''}`} />
                    </div>
                    <div className={`header__nav__item__circle ${click == "crear" ? 'circle--selected' : ''}`}></div>
                </Link>

                <Link className="header__nav__item" to="/biblioteca" onClick={() => handleClick("libro")}>
                    <div alt="Logo de Libro" className={`header__nav__item__libro`}>
                        <div alt="Logo de Libro" className={`header__nav__item__libro__fondo ${click == "libro" ? 'selected' : ''}`} />
                    </div>
                    <div className={`header__nav__item__circle ${click == "libro" ? 'circle--selected' : ''}`}></div>
                </Link>

                <Link className="header__nav__item" onClick={() => handleClick("usuario")}>
                    <div alt="Logo de Usuario" className={`header__nav__item__usuario`}>
                        <div alt="Logo de Usuario" className={`header__nav__item__usuario__fondo ${click == "usuario" ? 'selected' : ''}`} />
                    </div>
                    <div className={`header__nav__item__circle ${click == "usuario" ? 'circle--selected' : ''}`}></div>
                </Link>
            </nav>
        </header>
    )
}
export default Header