import { Link, useLocation } from 'react-router-dom';
import './SideBarCard.css';

export const SideBarCard = ({ titulo, opciones, img, variant='default' }) => {
    const location = useLocation();

    if (variant === 'button') {
        return (
            <Link to={opciones[0].to} className="sidebar-card">
                <div className="sidebar-card__encabezado">
                    <div className="sidebar-card__encabezado__img">{img}</div>
                    <h2>{titulo}</h2>
                </div>
                <ul className="sidebar-card__options">
                    <li
                        key={opciones[0].text}
                        className={`sidebar-card__options__option--${location.pathname == opciones[0].to ? 'selected' : ''}`}
                    >
                        <p>{opciones[0].text}</p>
                    </li>
                </ul>
            </Link>
        );
    }

    return (
        <div className="sidebar-card">
            <div className="sidebar-card__encabezado">
                <div className="sidebar-card__encabezado__img">{img}</div>
                <h2>{titulo}</h2>
            </div>
            <ul className="sidebar-card__options">
                {opciones.map((opcion) => (
                    <li
                        key={opcion.text}
                        className={`sidebar-card__options__option--${location.pathname == opcion.to ? 'selected' : ''}`}
                    >
                        <Link to={opcion.to}>{opcion.text}</Link>
                    </li>
                ))}
            </ul>
        </div>
    );
};
