import { useFetch } from '../../../utils/FetchUtils';
import { ComentarioSocial } from '../../social/ComentarioSocial';

export const UsuarioPosteos = () => {
    const { data: dataUsuario, refetch: refetchUsuario } = useFetch('usuario/propio');
    const usuarioId = dataUsuario && dataUsuario.id ? dataUsuario.id : undefined;
    const {
        data: dataPosteos,
        loading: loadingPosteos,
        error: errorPosteos,
    } = dataUsuario.id ? useFetch('publicacionSocial/usuario/' + dataUsuario.id) : useFetch('');

    return (
        <section className="usuario-posteos">
            {dataPosteos && dataPosteos?.content && (
                dataPosteos.content.map((posteo) => (
                    <ComentarioSocial
                        key={posteo.id}
                        tiempoPublicacion={posteo.tiempoTranscurrido}
                    >
                        {posteo.textoPublicacion}
                    </ComentarioSocial>
                ))
            )}
        </section>
    )
}
