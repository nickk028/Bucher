import { Link } from "react-router-dom";
import logo from '../../../assets/img/logo.png';
import logoBucher from '../../../assets/img/logos/logoBucher.png';
import logoTendencias from '../../../assets/img/logos/logoTendencias.png';
import logoCrear from '../../../assets/img/logos/logoCrear.png';
import logoLibro from '../../../assets/img/logos/logoLibro.png';
import logoUsuario from '../../../assets/img/logos/logoUsuario.png';
import './Header.css';

const Header = () => {
    return (
        <header className="header">
            <Link to="/index"><img src={logoBucher} alt="Logo de Bücher" className="header__img" /></Link>
            <Link><img src={logoTendencias} alt="Logo de Tendencias" className="header__img" /></Link>
            <Link to="/crear-publicacion"><img src={logoCrear} alt="Logo de Crear" className="header__img" /></Link>
            <Link to="/biblioteca"><img src={logoLibro} alt="Logo de Libro" className="header__img" /></Link>
            <Link><img src={logoUsuario} alt="Logo de Usuario" className="header__img" /></Link>
        </header>
    )
}
export default Header