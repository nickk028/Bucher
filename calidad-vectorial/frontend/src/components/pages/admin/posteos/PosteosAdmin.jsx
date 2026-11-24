import { useFetch } from '../../../utils/FetchUtils';
import { Tabla } from '../../../elements/table/Tabla';
import { Indicador } from '../../../elements/table/Indicador';
import './PosteosAdmin.css';

const DENUNCIAS_ADVERTENCIA = 1;
const DENUNCIAS_CRITICAS = 3;

const obtenerColorDenuncias = (denuncias) => {
    if (denuncias >= DENUNCIAS_CRITICAS) return 'rojo';
    if (denuncias >= DENUNCIAS_ADVERTENCIA) return 'amarillo';
    return 'verde';
};

function formatearFecha(fecha) {
    const date = new Date(fecha);

    const dia = String(date.getDate()).padStart(2, '0');
    const mes = String(date.getMonth() + 1).padStart(2, '0');
    const anio = date.getFullYear();

    return `${dia}/${mes}/${anio}`;
}

const COLUMNAS = [
    {
        clave: 'id',
        titulo: 'ID del posteo',
        render: (posteo) => `N° ${posteo.id}`,
    },
    {
        clave: 'nickname',
        titulo: 'Usuario',
        render: (posteo) => `@${posteo.nickname}`,
    },
    { clave: 'textoPublicacion', titulo: 'Comentario', width: 35 },
    {
        clave: 'cantDenuncias',
        titulo: 'Denuncias',
        render: (posteo) => (
            <Indicador color={obtenerColorDenuncias(posteo.cantDenuncias)}>
                {posteo.cantDenuncias}
            </Indicador>
        ),
    },
    {
        clave: 'fechaCreacion',
        titulo: 'Fecha de publicación',
        render: (posteo) => `${formatearFecha(posteo.fechaCreacion)}`,
    },
];

export const PosteosAdmin = () => {
    const { data: dataPosteos, loading: loadingPosteos, error: errorPosteos } = useFetch('admin/publicacionSocial');

    return (
        <main className="body-posteos__admin">
            <header className="body-posteos__admin__encabezado">
                <h1 className="body-posteos__admin__encabezado__titulo">Posteos sociales</h1>
                <p className="body-posteos__admin__encabezado__subtitulo">Gestiona los posteos y comentarios de los usuarios</p>
            </header>

            <div className="body-posteos__admin__tabla">
                <Tabla columnas={COLUMNAS} items={dataPosteos.content} link={true}/>
            </div>
        </main>
    )
}
