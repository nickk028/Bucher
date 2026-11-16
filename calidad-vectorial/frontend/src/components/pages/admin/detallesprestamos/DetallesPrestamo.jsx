import { useParams } from 'react-router-dom';
import { useFetch } from '../../../utils/FetchUtils';

import { EncabezadoPagina } from '../../../elements/headerpage/EncabezadoPagina';
import { LibroDetalle } from '../../../elements/book/LibroDetalle';
import { UsuarioDetalleCard } from '../../../elements/user/detail/UsuarioDetalleCard';
import './DetallesPrestamo.css';

export const DetallesPrestamo = () => {
    const { id } = useParams();
    const {data: dataPrestamo, loading: loadingPrestamo, error: errorPrestamo} = useFetch(`publicacion/${id}`);

    return (
        <main className='detalles-prestamo'>
            <EncabezadoPagina
                titulo='Detalles del préstamo'
                subtitulo='Controla y actualiza los datos del préstamo'
            />
            <section className='detalles-prestamo__seccion'>
                <LibroDetalle
                    urlFoto='https://images.cdn1.buscalibre.com/fit-in/360x360/c5/9f/c59fa2cfcda0ceb5d1a64e0a199d2973.jpg'
                    titulo='Emma'
                    autor='Jane Austen'
                    caracteristicas={['Estado del ejemplar: Muy bueno', 'El libro se encuentra en condiciones']}
                    detalles={[
                        {label: 'Número de préstamo', texto: 'N°01258'},
                        {label: 'Inicio del préstamo', texto: '12/08/2026'},
                        {label: 'Fin del préstamo', texto: '30/09/2026'},
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
                    foto='/assets/img/avatares/alicia.png'
                    nickname='usuario'
                    username='usuario@gmail.com'
                />
            </section>
        </main>
    );
};