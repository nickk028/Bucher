import { Link } from "react-router-dom";
import logoTendencias from '../../../assets/img/logos/logoTendencias.png';
import logoCrear from '../../../assets/img/logos/logoCrear.png';
import logoLibro from '../../../assets/img/logos/logoLibro.png';
import logoUsuario from '../../../assets/img/logos/logoUsuario.png';
import './Header.css';

const Header = () => {
    return (
        <header className="header">
            <div className="header__space"></div>
            <nav className="header__nav">
                <Link className="header__nav__item" to="/index">
                    <div alt="Logo de Bücher" className="nav__item__bucher">
                        <div alt="Logo de Bücher" className="nav__item__bucher--claro" />
                    </div>
                    <div className="nav__item__circle"></div>
                </Link>
                <Link><img src={logoTendencias} alt="Logo de Tendencias" className="nav__item__img" /></Link>
                <Link to="/crear-publicacion"><img src={logoCrear} alt="Logo de Crear" className="nav__item__img" /></Link>
                <Link to="/biblioteca"><img src={logoLibro} alt="Logo de Libro" className="nav__item__img" /></Link>
                <Link><img src={logoUsuario} alt="Logo de Usuario" className="nav__item__img" /></Link>
            </nav>
        </header>
    )
}
export default Header