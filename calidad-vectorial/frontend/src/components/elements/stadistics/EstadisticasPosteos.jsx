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
    { nombre: 'Ficción', valor: 20.8 },
];

export const EstadisticasPosteos = () => {
    return (
        <section className='estadisticas-section'>
            <h2 className='estadisticas-section__titulo'>Posteos</h2>
            <section className='estadisticas__fila'>
                <div className='estadisticas__fila__contenedor estadisticas__fila__contenedor--bloque'>
                    <h3 className='estadisticas__fila__contenedor__titulo'>Posteos publicados</h3>
                    <p className='estadisticas__fila__contenedor--bloque__contenido'>215</p>
                </div>
                <div className='estadisticas__fila__contenedor estadisticas__fila__contenedor--bloque'>
                    <h3 className='estadisticas__fila__contenedor__titulo'>Posteos con referencias</h3>
                    <p className='estadisticas__fila__contenedor--bloque__contenido'>12</p>
                </div>
                <div className='estadisticas__fila__contenedor estadisticas__fila__contenedor--bloque'>
                    <h3 className='estadisticas__fila__contenedor__titulo'>Posteos denunciados</h3>
                    <p className='estadisticas__fila__contenedor--bloque__contenido'>23</p>
                </div>
                <div className='estadisticas__fila__contenedor estadisticas__fila__contenedor--bloque'>
                    <h3 className='estadisticas__fila__contenedor__titulo'>Posteos eliminados</h3>
                    <p className='estadisticas__fila__contenedor--bloque__contenido'>15</p>
                </div>
            </section>

            <section className='estadisticas__fila'>
                <div className='estadisticas__fila__contenedor estadisticas__fila__contenedor--barra'>
                    <GraficoBarras titulo='Cantidad posteos' data={data}/>
                </div>
                <div className='estadisticas__fila__contenedor estadisticas__fila__contenedor--torta'>
                    <GraficoTorta titulo='Categorías más mencionadas' data={data2} />
                </div>
                <div className='estadisticas__fila__contenedor estadisticas__fila__contenedor--barra'>
                    <GraficoBarras titulo='Libros más mencionadso' data={data}/>
                </div>
            </section>

            <section className='estadisticas__fila'>
                <div className='estadisticas__fila__contenedor estadisticas__fila__contenedor--circular'>
                    <GraficoCircular titulo='Posteos denunciados' data={37}/>
                </div>
                <div className='estadisticas__fila__contenedor estadisticas__fila__contenedor--circular'>
                    <GraficoCircular titulo='Posteos con menciones' data={61}/>
                </div>
                <div className='estadisticas__fila__contenedor estadisticas__fila__contenedor--circular'>
                    <GraficoCircular titulo='Posteos eliminados' data={72}/>
                </div>
            </section>
        </section>
    )
}
