import { GraficoBarras } from '../charts/GraficoBarras';
import { GraficoTorta } from '../charts/GraficoTorta';
import { GraficoCircular } from '../charts/GraficoCircular';

import './Estadisticas.css';
import { useFetch } from '../../utils/FetchUtils';

export const EstadisticasPosteos = () => {
    const { data: dataPosteos, loading: loadingPosteos, error: errorPosteos } = useFetch('admin/estadisticas/posteos');
    return (
        <section className='estadisticas-section'>
            <h2 className='estadisticas-section__titulo'>Posteos</h2>
            {loadingPosteos ? (
                <p>Cargando</p>
            ) : dataPosteos.length !== 0 && (<>
            <section className='estadisticas__fila'>
                <div className='estadisticas__fila__contenedor estadisticas__fila__contenedor--bloque'>
                    <h3 className='estadisticas__fila__contenedor__titulo'>Posteos publicados</h3>
                    <p className='estadisticas__fila__contenedor--bloque__contenido'>{dataPosteos.posteosPublicados}</p>
                </div>
                <div className='estadisticas__fila__contenedor estadisticas__fila__contenedor--bloque'>
                    <h3 className='estadisticas__fila__contenedor__titulo'>Posteos con referencias</h3>
                    <p className='estadisticas__fila__contenedor--bloque__contenido'>{dataPosteos.posteosConReferencias}</p>
                </div>
                <div className='estadisticas__fila__contenedor estadisticas__fila__contenedor--bloque'>
                    <h3 className='estadisticas__fila__contenedor__titulo'>Posteos denunciados</h3>
                    <p className='estadisticas__fila__contenedor--bloque__contenido'>{dataPosteos.posteosDenunciados}</p>
                </div>
                <div className='estadisticas__fila__contenedor estadisticas__fila__contenedor--bloque'>
                    <h3 className='estadisticas__fila__contenedor__titulo'>Posteos eliminados</h3>
                    <p className='estadisticas__fila__contenedor--bloque__contenido'>{dataPosteos.posteosEliminados}</p>
                </div>
            </section>

            <section className='estadisticas__fila'>
                <div className='estadisticas__fila__contenedor estadisticas__fila__contenedor--barra estadisticas__fila__contenedor--grande'>
                    <GraficoBarras titulo='Cantidad posteos' data={dataPosteos.posteosPorMes} claveEtiqueta='mes' claveValor='cant' />
                </div>
                
            </section>

            <section className='estadisticas__fila'>
                <div className='estadisticas__fila__contenedor estadisticas__fila__contenedor--barra'>
                    <GraficoBarras titulo='Libros más mencionadso' data={dataPosteos.librosMasMencionados} claveEtiqueta='titulo' claveValor='cantidad' />
                </div>
                <div className='estadisticas__fila__contenedor estadisticas__fila__contenedor--torta'>
                    <GraficoTorta titulo='Categorías más mencionadas' data={dataPosteos.categoriasMasMencionadas} claveEtiqueta='categoria' claveValor='porcentaje' />
                </div>
            </section>

            <section className='estadisticas__fila'>
                <div className='estadisticas__fila__contenedor estadisticas__fila__contenedor--circular'>
                    <GraficoCircular titulo='Posteos denunciados' data={dataPosteos.porcentajePosteosDenunciados}/>
                </div>
                <div className='estadisticas__fila__contenedor estadisticas__fila__contenedor--circular'>
                    <GraficoCircular titulo='Posteos con menciones' data={dataPosteos.porcentajePosteosConMenciones} />
                </div>
                <div className='estadisticas__fila__contenedor estadisticas__fila__contenedor--circular'>
                    <GraficoCircular titulo='Posteos eliminados' data={dataPosteos.porcentajePosteosEliminados}/>
                </div>
            </section>
            </>)}

            {errorPosteos && (
                <p>Error al cargar las estadísticas</p>
            )}
        </section>
    )
}
