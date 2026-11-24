import { useParams } from 'react-router-dom';
import { useFetch } from '../../../utils/FetchUtils';

import { EncabezadoPagina } from '../../../elements/headerpage/EncabezadoPagina';
import { LibroDetalle } from '../../../elements/book/LibroDetalle';
import { UsuarioDetalleCard } from '../../../elements/user/detail/UsuarioDetalleCard';
import { DetalleCard } from '../../../elements/detail/DetalleCard';
import { Button } from '../../../elements/buttons/Button';
import './DetallesPrestamo.css';

const PASOS = ['Solicitado', 'Retirado', 'En revisión', 'A entregar', 'Entregado']
const PASOS_COMPLETADOS = 2;

export const DetallesPrestamo = () => {
    const { id } = useParams();
    const { data: dataPrestamo, loading: loadingPrestamo, error: errorPrestamo } = useFetch(`registro/publicacion/${id}`);

    return (
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
                        urlFoto='https://images.cdn1.buscalibre.com/fit-in/360x360/c5/9f/c59fa2cfcda0ceb5d1a64e0a199d2973.jpg'
                        titulo='Emma'
                        autor='Jane Austen'
                        editorial='Algo'
                        caracteristicas={['Estado del ejemplar: Muy bueno', 'El libro se encuentra en condiciones']}
                        detalles={[
                            {label: 'Número de préstamo', texto: `N° ${dataPrestamo.publicacion.id}`},
                            {label: 'Inicio del préstamo', texto: `${dataPrestamo.fechaPrestamo}`},
                            {label: 'Fin del préstamo', texto: `${dataPrestamo.fechaDevolucion}`},
                        ]}
                    />
                    <UsuarioDetalleCard
                        titulo='Usuario prestamista'
                        foto='/assets/img/avatares/alicia.png'
                        nickname='usuario'
                        username='usuario@gmail.com'
                    />
                    <UsuarioDetalleCard
                        titulo='Usuario lector'
                        foto='/assets/img/avatares/reinadecorazones.png'
                        nickname='usuario'
                        username='usuario@gmail.com'
                    />
                </>)}
                {errorPrestamo && (
                    <p>Error al cargar los datos del préstamo</p>
                )}
            </section>
            <section className='detalles-prestamo__seccion'>
                    <div className='seccion__acciones'>
                        <h2>Acciones</h2>
                        <Button variant='default' color='oscuro'>Realizar revisión del ejemplar</Button>
                        <Button variant='default' color='oscuro'>Revisión completada</Button>
                        <p>Completa el formulario de revisión y una vez listo marca la revisión como completada.</p>
                    </div>
                    <DetalleCard
                        titulo='Estado del préstamo'
                        pasos={PASOS}
                        pasosCompletados={PASOS_COMPLETADOS}
                        eventos={[
                            { texto: 'El ejemplar fue retirado del domicilio.', fecha: 'Vie, 23:02 hs.' },
                            { texto: 'El pedido del préstamo fue procesado exitosamente.', fecha: 'Vie, 10:26 hs.' },
                        ]}
                    />
            </section>
        </main>
    );
};