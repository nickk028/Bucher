import { useState, useEffect } from 'react';
import { Link, useLocation } from 'react-router-dom';

import './Header.css';

export const HeaderAdmin = () => {
    const location = useLocation();
    const [click, setClick] = useState(location.pathname);

    useEffect(() => {
        setClick(location.pathname);
    }, [location]);

    return (
        <header className="header">
            <nav className="header__nav">
                <Link className="header__nav__item" to="/admin">
                    <div
                        alt="Logo de Bücher"
                        className={`header__nav__item__bucher header__nav__item__bucher--${click == '/admin' ? 'selected' : ''}`}
                    />
                </Link>

                <Link className="header__nav__item" to="#">
                    <div
                        alt="Logo de Estadísticas"
                        className={`header__nav__item__estadisticas header__nav__item__estadisticas--${click == '/#' ? 'selected' : ''}`}
                    />
                </Link>

                <Link className="header__nav__item" to="#">
                    <div
                        alt="Logo de Comentario Social"
                        className={`header__nav__item__comentario-social header__nav__item__comentario-social--${click == '/#' ? 'selected' : ''}`}
                    />
                </Link>

                <Link className="header__nav__item" to="/admin/prestamos">
                    <div
                        alt="Logo de Biblioteca"
                        className={`header__nav__item__biblioteca header__nav__item__biblioteca--${click == '/#' ? 'selected' : ''}`}
                    />
                </Link>

                <Link className="header__nav__item" to="/admin/usuarios">
                    <div
                        alt="Logo de Usuarios"
                        className={`header__nav__item__usuarios header__nav__item__usuarios--${click == '/#' ? 'selected' : ''}`}
                    />
                </Link>

                <Link className="header__nav__item" to="#">
                    <div
                        alt="Logo de Carrito"
                        className={`header__nav__item__carrito header__nav__item__carrito--${click == '/#' ? 'selected' : ''}`}
                    />
                </Link>

                <Link className="header__nav__item" to="#">
                    <div
                        alt="Logo de Configuración"
                        className={`header__nav__item__configuracion header__nav__item__configuracion--${click == '/#' ? 'selected' : ''}`}
                    />
                </Link>
            </nav>
        </header>
    );
};