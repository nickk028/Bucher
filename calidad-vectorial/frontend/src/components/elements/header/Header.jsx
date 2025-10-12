import { Link } from "react-router-dom";
import './Header.css';

const Header = () => {
    return (
        <header className="header">
            <nav className="header__nav">
                <Link className="header__nav__item" to="/index">
                    <div alt="Logo de Bücher" className="header__nav__item__bucher">
                        <div alt="Logo de Bücher" className="header__nav__item__bucher__fondo" />
                    </div>
                    <div className="header__nav__item__circle"></div>
                </Link>

                <Link className="header__nav__item">
                    <div alt="Logo de Tendencias" className="header__nav__item__tendencias">
                        <div alt="Logo de Tendencias" className="header__nav__item__tendencias__fondo" />
                    </div>
                    <div className="header__nav__item__circle"></div>
                </Link>

                <Link className="header__nav__item" to="/crear-publicacion">
                    <div alt="Logo de Crear" className="header__nav__item__crear">
                        <div alt="Logo de Crear" className="header__nav__item__crear__fondo" />
                    </div>
                    <div className="header__nav__item__circle"></div>
                </Link>

                <Link className="header__nav__item" to="/biblioteca">
                    <div alt="Logo de Libro" className="header__nav__item__libro">
                        <div alt="Logo de Libro" className="header__nav__item__libro__fondo" />
                    </div>
                    <div className="header__nav__item__circle"></div>
                </Link>

                <Link className="header__nav__item">
                    <div alt="Logo de Usuario" className="header__nav__item__usuario">
                        <div alt="Logo de Usuario" className="header__nav__item__usuario__fondo" />
                    </div>
                    <div className="header__nav__item__circle"></div>
                </Link>
            </nav>
        </header>
    )
}
export default Header