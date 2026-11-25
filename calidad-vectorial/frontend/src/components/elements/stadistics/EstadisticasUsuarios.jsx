import { GraficoBarras } from '../charts/GraficoBarras';
import { GraficoTorta } from '../charts/GraficoTorta';
import { GraficoCircular } from '../charts/GraficoCircular';

import './Estadisticas.css';
import { useFetch } from '../../utils/FetchUtils';

const data2 = [
    { nombre: 'Denunciados', valor: 20.8 },
    { nombre: 'Bloqueados', valor: 12.8 },
    { nombre: 'Activos', valor: 43.8 },
    { nombre: 'Inactivos', valor: 22.6 },
];

export const EstadisticasUsuarios = () => {
    const { data: dataUsuarios, loading: loadingUsuarios, error: errorUsuarios } = useFetch('admin/estadisticas/usuarios');
    return (
        <section className='estadisticas-section'>
            <h2 className='estadisticas-section__titulo'>Usuarios</h2>
            {loadingUsuarios ? (
                <p>Cargando</p>
            ) : dataUsuarios.length !== 0 && (<>
                <section className='estadisticas__fila'>
                    <div className='estadisticas__fila__contenedor estadisticas__fila__contenedor--bloque'>
                        <h3 className='estadisticas__fila__contenedor__titulo'>Usuarios totales</h3>
                        <p className='estadisticas__fila__contenedor--bloque__contenido'>{dataUsuarios.usuariosTotales}</p>
                    </div>
                    {/* <div className='estadisticas__fila__contenedor estadisticas__fila__contenedor--bloque'>
                        <h3 className='estadisticas__fila__contenedor__titulo'>Usuarios bloqueados</h3>
                        <p className='estadisticas__fila__contenedor--bloque__contenido'>{dataUsuarios.usuariosBloqueados}</p>
                    </div>
                    <div className='estadisticas__fila__contenedor estadisticas__fila__contenedor--bloque'>
                        <h3 className='estadisticas__fila__contenedor__titulo'>Usuarios denunciados</h3>
                        <p className='estadisticas__fila__contenedor--bloque__contenido'>{dataUsuarios.usuariosDenunciados}</p>
                    </div> */}
                    <div className='estadisticas__fila__contenedor estadisticas__fila__contenedor--bloque'>
                        <h3 className='estadisticas__fila__contenedor__titulo'>Usuarios nuevos</h3>
                        <p className='estadisticas__fila__contenedor--bloque__contenido'>{dataUsuarios.usuariosNuevos}</p>
                    </div>
                </section>

                <section className='estadisticas__fila'>
                    <div className='estadisticas__fila__contenedor estadisticas__fila__contenedor--barra estadisticas__fila__contenedor--grande'>
                        <GraficoBarras titulo='Nuevos usuarios' data={dataUsuarios.usuariosNuevosPorMes} claveEtiqueta='mes' claveValor='cant' />
                    </div>
                </section>

                <section className='estadisticas__fila'>
                    <div className='estadisticas__fila__contenedor estadisticas__fila__contenedor--torta'>
                        <GraficoTorta titulo='Estado de los usuarios' data={data2} />
                    </div>
                </section>

                <section className='estadisticas__fila'>
                    <div className='estadisticas__fila__contenedor estadisticas__fila__contenedor--circular'>
                        <GraficoCircular titulo='Usuarios con préstamos activos' data={dataUsuarios.porcentajeUsuariosConPrestamosActivos}/>
                    </div>
                    <div className='estadisticas__fila__contenedor estadisticas__fila__contenedor--circular'>
                        <GraficoCircular titulo='Usuarios con publicaciones' data={dataUsuarios.porcentajeUsuariosConPublicaciones}/>
                    </div>
                    <div className='estadisticas__fila__contenedor estadisticas__fila__contenedor--circular'>
                        <GraficoCircular titulo='Usuarios con posteos' data={dataUsuarios.porcentajeUsuariosConPosteos}/>
                    </div>
                    {/* <div className='estadisticas__fila__contenedor estadisticas__fila__contenedor--circular'>
                        <GraficoCircular titulo='Usuarios con canjes hechos' data={}/>
                    </div> */}
                </section>
            </>)}

            {errorUsuarios && (
                <p>Error al cargar las estadísticas</p>
            )}
        </section>
    )
}