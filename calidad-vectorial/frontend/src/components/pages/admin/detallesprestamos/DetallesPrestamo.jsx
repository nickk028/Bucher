import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { useFetch, usePost } from '../../../utils/FetchUtils';

import { EncabezadoPagina } from '../../../elements/headerpage/EncabezadoPagina';
import { LibroDetalle } from '../../../elements/book/LibroDetalle';
import { UsuarioDetalleCard } from '../../../elements/user/detail/UsuarioDetalleCard';
import { DetalleCard } from '../../../elements/detail/DetalleCard';
import { Button } from '../../../elements/buttons/Button';
import { PopUp } from '../../../elements/modal/PopUp';
import './DetallesPrestamo.css';

const PASOS = [
    { estado: 'solicitado', texto: 'Solicitado' },
    { estado: 'retirado', texto: 'Retirado' },
    { estado: 'enrevision', texto: 'En revisión' },
    { estado: 'aentregar', texto: 'A entregar' },
    { estado: 'entregado', texto: 'Entregado' }
];

function formatearFecha(fecha) {
    const [anio, mes, dia] = fecha.split("-");
    return `${dia}/${mes}/${anio}`;
}

export const DetallesPrestamo = () => {
    const { id } = useParams();
    const [mostrarPopUp, setMostrarPopUp] = useState(false);
    const { data: dataPrestamo, loading: loadingPrestamo, error: errorPrestamo } = useFetch(`registro/publicacion/${id}`);
    const { data: dataCompletar, loading: loadingCompletar, error: errorCompletar, execute: executeCompletar } = usePost(`admin/prestamos/control/completar/${id}`);

    const completado = dataPrestamo?.publicacion?.estadoPublicacion == 'solicitado' || dataPrestamo?.publicacion?.estadoPublicacion == 'retirado' ? true : false;

    const PASOS_COMPLETADOS = PASOS.findIndex(
        paso => paso.estado === dataPrestamo?.publicacion?.estadoPublicacion
    ) + 1;

    console.log(dataPrestamo?.publicacion?.estadoPublicacion)

    useEffect(() => {
        if (dataCompletar && !errorCompletar) {
            setMostrarPopUp(true);
        }
    }, [dataCompletar, errorCompletar]);

    return (
        <>
            {mostrarPopUp && (
                <PopUp
                    onClick={() => {
                        setMostrarPopUp(false);
                        navigate('/admin/prestamos/'+id)
                    }}
                    titulo="✔️ Control completado ✔️"
                >
                    El control del ejemplar se registró correctamente.
                </PopUp>
            )}
            <main className='detalles-prestamo'>
                <EncabezadoPagina
                    titulo='Detalles del préstamo'
                    subtitulo='Controla y actualiza los datos del préstamo'
                />
                <section className='detalles-prestamo__seccion'>
                    {loadingPrestamo ? (
                        <p>Cargando</p>
                    ) : dataPrestamo.length !== 0 && (<>
                        <LibroDetalle
                            urlFoto={dataPrestamo.publicacion.urlFoto}
                            titulo={dataPrestamo.publicacion.titulo}
                            autor={dataPrestamo.publicacion.nombre}
                            editorial='Algo'
                            caracteristicas={['Estado del ejemplar: ' + dataPrestamo.publicacion.detallesEstadoLibro]}
                            detalles={[
                                {label: 'Número de préstamo', texto: `N° ${dataPrestamo.id}`},
                                {label: 'Inicio del préstamo', texto: `${formatearFecha(dataPrestamo.fechaPrestamo)}`},
                                {label: 'Fin del préstamo', texto: `${dataPrestamo.fechaDevolucion ? formatearFecha(dataPrestamo.fechaDevolucion) : 'indefinido'}`},
                            ]}
                        />
                        <UsuarioDetalleCard
                            titulo='Usuario prestamista'
                            foto={'/assets/img/avatares/' + dataPrestamo.publicacion.avatarUsuarioCreador}
                            nickname={dataPrestamo.publicacion.nicknameUsuarioCreador}
                            username={dataPrestamo.publicacion.usuarioCreador}
                        />
                        <UsuarioDetalleCard
                            titulo='Usuario lector'
                            foto={'/assets/img/avatares/' + dataPrestamo.avatarUsuarioPrestamista}
                            nickname={dataPrestamo.nickanmeUsuarioPrestamista}
                            username={dataPrestamo.usuarioPrestamista}
                        />
                    </>)}
                    {errorPrestamo && (
                        <p>Error al cargar los datos del préstamo</p>
                    )}
                </section>
                <section className='detalles-prestamo__seccion'>
                        <div className='seccion__acciones'>
                            <h2>Acciones</h2>
                            <Button variant='default' color='oscuro' to='control' >Realizar revisión del ejemplar</Button>
                            <Button variant='default' color='oscuro' onClick={() => executeCompletar()} isDisabled={completado}>Revisión completada</Button>
                            <p>Completa el formulario de revisión y una vez listo marca la revisión como completada.</p>
                        </div>
                        <DetalleCard
                            titulo='Estado del préstamo'
                            pasos={PASOS.map(paso => paso.texto)}
                            pasosCompletados={PASOS_COMPLETADOS}
                        />
                </section>
            </main>
        </>
    );
};