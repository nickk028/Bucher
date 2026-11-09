import { useFetch } from '../../../utils/FetchUtils';
import { Tabla } from '../../../elements/table/Tabla';
import { Indicador } from '../../../elements/table/Indicador';
import './UsuariosAdmin.css';

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
        titulo: 'ID del usuario',
        render: (usuario) => (
            <>
                N° <strong>{usuario.id}</strong>
            </>
        ),
    },
    {
        clave: 'nickname',
        titulo: 'Usuario',
        render: (usuario) => `@${usuario.nickname}`,
    },
    { clave: 'username', titulo: 'Correo' },
    { clave: 'publicaciones', titulo: 'Publicaciones', negrita: true },
    { clave: 'posteos', titulo: 'Posteos', negrita: true },
    {
        clave: 'denuncias',
        titulo: 'Denuncias',
        render: (usuario) => (
            <Indicador color={obtenerColorDenuncias(usuario.denuncias)} negrita>
                {usuario.denuncias}
            </Indicador>
        ),
    },
];

export const UsuariosAdmin = () => {
    const { data: dataUsuarios, loading: loadingUsuarios, error: errorUsuarios } = useFetch('usuario');

    return (
        <main className="body-usuarios__admin">
            <header className="body-usuarios__admin__encabezado">
                <h1 className="body-usuarios__admin__encabezado__titulo">Usuarios</h1>
                <p className="body-usuarios__admin__encabezado__subtitulo">Gestiona los usuarios de Bücher</p>
            </header>

            <div className="body-usuarios__admin__tabla">
                <Tabla columnas={COLUMNAS} items={dataUsuarios} />
            </div>
        </main>
    );
};
