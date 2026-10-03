import { useFetch } from '../../../utils/FetchUtils';
import PublicacionCard from '../../../elements/publication/PublicacionCard';

export const UsuarioPrestamos = () => {
    const {
        data: dataPrestamos,
        loading: loadingPrestamos,
        error: errorPrestamos,
    } = useFetch('publicacion/propias');

    return (
        <section className="usuario__content__libros">
            {dataPrestamos.map((prestamo) => (
                <PublicacionCard
                    key={prestamo.id}
                    urlFoto={prestamo.urlFoto}
                    titulo={prestamo.titulo}
                    usuarioNickname={prestamo.nombre}
                    estadoPublicacion={prestamo.estadoPublicacion}
                    limiteDias={prestamo.limiteDias}
                />
            ))}

            {errorPrestamos && (
                <div>Ocurrió un error</div>
            )}
        </section>
    )
}
