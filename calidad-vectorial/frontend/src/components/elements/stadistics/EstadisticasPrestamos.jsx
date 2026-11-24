import { GraficoBarras } from '../charts/GraficoBarras';
import { GraficoTorta } from '../charts/GraficoTorta';
import { GraficoCircular } from '../charts/GraficoCircular';

import './Estadisticas.css';
import { useFetch } from '../../utils/FetchUtils';

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

export const EstadisticasPrestamos = () => {
    const { data: dataPrestamos, loading: loadingPrestamos, error: errorPrestamos } = useFetch('admin/estadisticas/prestamos');
    return (
        <section className='estadisticas-section'>
            <h2 className='estadisticas-section__titulo'>Préstamos</h2>
            {loadingPrestamos ? (
                <p>Cargando</p>
            ) : dataPrestamos.length !== 0 && (<>
            <section className='estadisticas__fila'>
                <div className='estadisticas__fila__contenedor estadisticas__fila__contenedor--bloque'>
                    <h3 className='estadisticas__fila__contenedor__titulo'>Préstamos totales</h3>
                    <p className='estadisticas__fila__contenedor--bloque__contenido'>{dataPrestamos.prestamosTotales}</p>
                </div>
                <div className='estadisticas__fila__contenedor estadisticas__fila__contenedor--bloque'>
                    <h3 className='estadisticas__fila__contenedor__titulo'>Préstamos activos</h3>
                    <p className='estadisticas__fila__contenedor--bloque__contenido'>{dataPrestamos.prestamosActivos}</p>
                </div>
                <div className='estadisticas__fila__contenedor estadisticas__fila__contenedor--bloque'>
                    <h3 className='estadisticas__fila__contenedor__titulo'>Préstamos incompletos</h3>
                    <p className='estadisticas__fila__contenedor--bloque__contenido'>{dataPrestamos.prestamosIncompletos}</p>
                </div>
                <div className='estadisticas__fila__contenedor estadisticas__fila__contenedor--bloque'>
                    <h3 className='estadisticas__fila__contenedor__titulo'>Préstamos incompletos</h3>
                    <p className='estadisticas__fila__contenedor--bloque__contenido'>{dataPrestamos.PrestamosPublicados}</p>
                </div>
            </section>

            <section className='estadisticas__fila'>
                <div className='estadisticas__fila__contenedor estadisticas__fila__contenedor--barra estadisticas__fila__contenedor--grande'>
                    <GraficoBarras titulo='Préstamos completados' data={dataPrestamos.prestamosCompletadosPorMes} claveEtiqueta='mes' claveValor='cant' />
                </div>
            </section>

            <section className='estadisticas__fila'>
                <div className='estadisticas__fila__contenedor estadisticas__fila__contenedor--barra estadisticas__fila__contenedor--grande'>
                    <GraficoBarras titulo='Categorías de los préstamos' data={dataPrestamos.categoriasPrestamos} claveEtiqueta='categoria' claveValor='cantidad' />
                </div>
            </section>

            <section className='estadisticas__fila'>
                <div className='estadisticas__fila__contenedor estadisticas__fila__contenedor--torta'>
                    <GraficoTorta titulo='Estado de los préstamos activos' data={data2} />
                </div>
            </section>

            <section className='estadisticas__fila'>
                <div className='estadisticas__fila__contenedor estadisticas__fila__contenedor--circular'>
                    <GraficoCircular titulo='Préstamos exitosos' data={.5}/>
                </div>
                <div className='estadisticas__fila__contenedor estadisticas__fila__contenedor--circular'>
                    <GraficoCircular titulo='Préstamos devueltos en mal estado' data={.7}/>
                </div>
                <div className='estadisticas__fila__contenedor estadisticas__fila__contenedor--circular'>
                    <GraficoCircular titulo='Préstamos dados en mal estado' data={.3}/>
                </div>
                <div className='estadisticas__fila__contenedor estadisticas__fila__contenedor--circular'>
                    <GraficoCircular titulo='Préstamos activos' data={dataPrestamos.porcentajePrestamosActivos}/>
                </div>
            </section>
            </>)}

            {errorPrestamos && (
                <p>Error al cargar las estadísticas</p>
            )}
        </section>
    )
}