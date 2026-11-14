import { useState } from 'react';
import { EstadisticasUsuarios } from '../../../elements/stadistics/EstadisticasUsuarios';

import IconoUsuario from '../../../../assets/img/icons/estadisticas/IconoUsuario.svg?react';
import IconoPosteo from '../../../../assets/img/icons/estadisticas/IconoPosteo.svg?react';
import IconoPrestamo from '../../../../assets/img/icons/estadisticas/IconoPrestamo.svg?react';
import IconoLibros from '../../../../assets/img/icons/estadisticas/IconoLibros.svg?react';
import IconoCanjes from '../../../../assets/img/icons/estadisticas/IconoCanjes.svg?react';

import './EstadisticasAdmin.css';

const SECCIONES = [
    {texto: 'Usuarios', icono: IconoUsuario},
    {texto: 'Posteos', icono: IconoPosteo},
    {texto: 'Préstamos', icono: IconoPrestamo},
    {texto: 'Libros', icono: IconoLibros},
    {texto: 'Canjes', icono: IconoCanjes},
]

export const EstadisticasAdmin = () => {
    const [pagina, setPagina] = useState('Usuarios');

    return (
            <main className="body-estadisticas__admin">
                <header className="body-estadisticas__admin__encabezado">
                    <h1 className="body-estadisticas__admin__encabezado__titulo">Estadísticas</h1>
                    <p className="body-estadisticas__admin__encabezado__subtitulo">Analiza el comportamiento de Bücher y sus usuarios</p>
                </header>
                <nav className='body-estadisticas__admin__nav'>
                    <ul className='body-estadisticas__admin__nav__items'>
                        {SECCIONES.map(({texto, icono: Icono}) => (
                            <li
                                key={texto}
                                onClick={() => setPagina(texto)}
                                className={`body-estadisticas__admin__nav__items__item body-estadisticas__admin__nav__items__item--${pagina == texto ? 'selected' : ''}`}
                            >
                                <p>{texto}</p>
                                <Icono className='body-estadisticas__admin__nav__items__item__icono' aria-hidden="true"/>
                            </li>
                        ))}
                    </ul>
                </nav>
                {pagina === 'Usuarios' ? (
                    <EstadisticasUsuarios />
                ) : (
                    <div>Selecciona una pestaña para ver las estadísticas</div>
                )}
            </main>
        );
};