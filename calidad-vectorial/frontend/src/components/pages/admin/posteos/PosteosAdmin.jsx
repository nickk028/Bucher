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
    { clave: 'comentario', titulo: 'Comentario', width: 35 },
    {
        clave: 'denuncias',
        titulo: 'Denuncias',
        render: (usuario) => (
            <Indicador color={obtenerColorDenuncias(usuario.denuncias)}>
                {usuario.denuncias}
            </Indicador>
        ),
    },
    {
        clave: 'fechaEntrega',
        titulo: 'Fecha de entrega',
    },
    {
        clave: 'fechaDevolucion',
        titulo: 'Fecha de devolución',
    },
];

const dataPosteos = [
    {
        'id': 1,
        'nickname': 'Usuario de ejemplo',
        'comentario': 'Comentario de ejemplo',
        'denuncias': 1,
        'fechaEntrega': '2026-09-01',
        'fechaDevolucion': '2026-10-01'
    },
]

export const PosteosAdmin = () => {
    return (
        <main className="body-posteos__admin">
            <header className="body-posteos__admin__encabezado">
                <h1 className="body-posteos__admin__encabezado__titulo">Posteos sociales</h1>
                <p className="body-posteos__admin__encabezado__subtitulo">Gestiona los posteos y comentarios de los usuarios</p>
            </header>

            <div className="body-posteos__admin__tabla">
                <Tabla columnas={COLUMNAS} items={dataPosteos} />
            </div>
        </main>
    )
}
