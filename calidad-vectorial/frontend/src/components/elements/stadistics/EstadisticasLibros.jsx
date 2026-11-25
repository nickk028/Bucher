import { GraficoBarras } from '../charts/GraficoBarras';
import { GraficoTorta } from '../charts/GraficoTorta';
import { GraficoCircular } from '../charts/GraficoCircular';

import './Estadisticas.css';
import { useFetch } from '../../utils/FetchUtils';

export const EstadisticasLibros = () => {
    const { data: dataLibros, loading: loadingLibros, error: errorLibros } = useFetch('admin/estadisticas/libros');
    return (
        <section className='estadisticas-section'>
            <h2 className='estadisticas-section__titulo'>Libros</h2>
            {loadingLibros ? (
                <p>Cargando</p>
            ) : dataLibros.length !== 0 && (<>
                <section className='estadisticas__fila'>
                    <div className='estadisticas__fila__contenedor estadisticas__fila__contenedor--bloque'>
                        <h3 className='estadisticas__fila__contenedor__titulo'>Libros registrados</h3>
                        <p className='estadisticas__fila__contenedor--bloque__contenido'>{dataLibros.librosRegistrados}</p>
                    </div>
                    <div className='estadisticas__fila__contenedor estadisticas__fila__contenedor--bloque'>
                        <h3 className='estadisticas__fila__contenedor__titulo'>Libros independientes</h3>
                        <p className='estadisticas__fila__contenedor--bloque__contenido'>{dataLibros.librosIndependientes}</p>
                    </div>
                </section>

                <section className='estadisticas__fila'>
                    <div className='estadisticas__fila__contenedor estadisticas__fila__contenedor--barra estadisticas__fila__contenedor--grande'>
                        <GraficoBarras titulo='Categorías de los libros' data={dataLibros.categoriasLibros} claveEtiqueta='categoria' claveValor='cantidad' />
                    </div>
                    
                </section>

                <section className='estadisticas__fila'>
                    <div className='estadisticas__fila__contenedor estadisticas__fila__contenedor--barra'>
                        <GraficoBarras titulo='Libros más guardados' data={dataLibros.librosMasGuardados} claveEtiqueta='titulo' claveValor='cantidad' />
                    </div>
                    <div className='estadisticas__fila__contenedor estadisticas__fila__contenedor--torta'>
                        <GraficoTorta titulo='Categorías más guardadas' data={dataLibros.categoriasMasGuardadas} claveEtiqueta='categoria' claveValor='porcentaje' />
                    </div>
                </section>

                <section className='estadisticas__fila'>
                    <div className='estadisticas__fila__contenedor estadisticas__fila__contenedor--circular'>
                        <GraficoCircular titulo='Libros guardados' data={dataLibros.porcentajeLibrosGuardados}/>
                    </div>
                    <div className='estadisticas__fila__contenedor estadisticas__fila__contenedor--circular'>
                        <GraficoCircular titulo='Libros nombrados en posteos' data={dataLibros.porcentajeLibrosNombradosEnPosteos}/>
                    </div>
                    <div className='estadisticas__fila__contenedor estadisticas__fila__contenedor--circular'>
                        <GraficoCircular titulo='Libros con publicaciones' data={dataLibros.porcentajeLibrosConPublicaciones}/>
                    </div>
                </section>
            </>)}

            {errorLibros && (
                <p>Error al cargar las estadísticas</p>
            )}
        </section>
    )
}