import { useState } from 'react';
import { GraficoBarras } from '../../../elements/user/data/admin/GraficoBarras';
import { GraficoTorta } from '../../../elements/user/data/admin/GraficoTorta';
import { GraficoCircular } from '../../../elements/user/data/admin/GraficoCircular';

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

// const data = [
//     { nombre: 'Enero', valor: 72 },
//     { nombre: 'Febrero', valor: 80 },
//     { nombre: 'Marzo', valor: 59 },
//     { nombre: 'Abril', valor: 59 },
//     { nombre: 'Mayo', valor: 59 },
//     { nombre: 'Junio', valor: 59 },
//     { nombre: 'Julio', valor: 59 },
//     { nombre: 'Agosto', valor: 59 },
//     { nombre: 'Septiembre', valor: 59 },
// ];

// const data2 = [
//     { nombre: 'Solicitados', valor: 14.7 },
//     { nombre: 'Retirados', valor: 9 },
//     { nombre: 'En revisión', valor: 31 },
//     { nombre: 'A entregar', valor: 16 },
//     { nombre: 'Entregado', valor: 7.5 },
//     { nombre: 'A devolver', valor: 21.7 },
// ];

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
                <div>
                    
                </div>
            </main>
        );
};