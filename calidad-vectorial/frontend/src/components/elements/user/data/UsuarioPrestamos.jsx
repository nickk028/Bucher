import { useFetch } from '../../../utils/FetchUtils';
import PublicacionCard from '../../publication/PublicacionCard';

export const UsuarioPrestamos = () => {
    const {
        data: dataPrestamos,
        loading: loadingPrestamos,
        error: errorPrestamos,
    } = useFetch('publicacion/propias');

    return (
        <section className="biblioteca__contenedor__libros">
            {loadingPrestamos ? (
                <p>Cargando prestamos...</p>
            ) : errorPrestamos ? (
                <p>Error al cargar los prestamos: {errorPrestamos}</p>
            ) : dataPrestamos.length === 0 ? (
                <p>No tienes ningún préstamo</p>
            ) : (
                dataPrestamos.map((prestamo) => (
                    <PublicacionCard
                        key={prestamo.id}
                        urlFoto={prestamo.urlFoto}
                        titulo={prestamo.titulo}
                        usuarioNickname={prestamo.nombre}
                        estadoPublicacion={prestamo.estadoPublicacion}
                        limiteDias={prestamo.limiteDias}
                    />
                ))
            )}
        </section>
    )
}
