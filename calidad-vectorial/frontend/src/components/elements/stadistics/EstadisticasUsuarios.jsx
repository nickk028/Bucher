import { GraficoBarras } from '../charts/GraficoBarras';
import { GraficoTorta } from '../charts/GraficoTorta';
import { GraficoCircular } from '../charts/GraficoCircular';

import './Estadisticas.css';

const data = [
    { nombre: 'Enero', valor: 72 },
    { nombre: 'Febrero', valor: 80 },
    { nombre: 'Marzo', valor: 59 },
    { nombre: 'Abril', valor: 42 },
    { nombre: 'Mayo', valor: 54 },
    { nombre: 'Junio', valor: 65 },
    { nombre: 'Julio', valor: 38 },
    { nombre: 'Agosto', valor: 20 },
];

const data2 = [
    { nombre: 'Denunciados', valor: 20.8 },
    { nombre: 'Bloqueados', valor: 12.8 },
    { nombre: 'Activos', valor: 43.8 },
    { nombre: 'Inactivos', valor: 22.6 },
];

export const EstadisticasUsuarios = () => {
    return (
        <section className='estadisticas-section'>
            <h2 className='estadisticas-section__titulo'>Usuarios</h2>
            <section className='estadisticas__fila'>
                <div className='estadisticas__fila__contenedor estadisticas__fila__contenedor--bloque'>
                    <h3 className='estadisticas__fila__contenedor__titulo'>Usuarios totales</h3>
                    <p className='estadisticas__fila__contenedor--bloque__contenido'>215</p>
                </div>
                <div className='estadisticas__fila__contenedor estadisticas__fila__contenedor--bloque'>
                    <h3 className='estadisticas__fila__contenedor__titulo'>Usuarios bloqueados</h3>
                    <p className='estadisticas__fila__contenedor--bloque__contenido'>12</p>
                </div>
                <div className='estadisticas__fila__contenedor estadisticas__fila__contenedor--bloque'>
                    <h3 className='estadisticas__fila__contenedor__titulo'>Usuarios denunciados</h3>
                    <p className='estadisticas__fila__contenedor--bloque__contenido'>23</p>
                </div>
                <div className='estadisticas__fila__contenedor estadisticas__fila__contenedor--bloque'>
                    <h3 className='estadisticas__fila__contenedor__titulo'>Usuarios nuevos</h3>
                    <p className='estadisticas__fila__contenedor--bloque__contenido'>15</p>
                </div>
            </section>

            <section className='estadisticas__fila'>
                <div className='estadisticas__fila__contenedor estadisticas__fila__contenedor--barra'>
                    <GraficoBarras titulo='Nuevos usuarios' data={data}/>
                </div>
                <div className='estadisticas__fila__contenedor estadisticas__fila__contenedor--torta'>
                    <GraficoTorta titulo='Estado de los usuarios' data={data2} />
                </div>
            </section>

            <section className='estadisticas__fila'>
                <div className='estadisticas__fila__contenedor estadisticas__fila__contenedor--circular'>
                    <GraficoCircular titulo='Usuarios con préstamos activos' data={37}/>
                </div>
                <div className='estadisticas__fila__contenedor estadisticas__fila__contenedor--circular'>
                    <GraficoCircular titulo='Usuarios con publicaciones' data={61}/>
                </div>
                <div className='estadisticas__fila__contenedor estadisticas__fila__contenedor--circular'>
                    <GraficoCircular titulo='Usuarios con posteos' data={72}/>
                </div>
                <div className='estadisticas__fila__contenedor estadisticas__fila__contenedor--circular'>
                    <GraficoCircular titulo='Usuarios con canjes hechos' data={8}/>
                </div>
            </section>
        </section>
    )
}