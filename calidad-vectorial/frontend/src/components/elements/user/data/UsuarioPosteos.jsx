import { useFetch } from '../../../utils/FetchUtils';
import { ComentarioSocial } from '../../social/ComentarioSocial';

export const UsuarioPosteos = () => {
    const {
        data: dataPosteos,
        loading: loadingPosteos,
        error: errorPosteos,
    } = useFetch('publicacionSocial');

    return (
        <section className="usuario__content__libros">
            {dataPosteos.map((posteo) => (
                <ComentarioSocial
                    
                >
                    
                </ComentarioSocial>
            ))}
        </section>
    )
}
