import { useFetch } from '../../../utils/FetchUtils';
import './UsuariosAdmin.css';

const DENUNCIAS_ADVERTENCIA = 1;
const DENUNCIAS_CRITICAS = 3;

const obtenerColorDenuncias = (denuncias) => {
    if (denuncias >= DENUNCIAS_CRITICAS) return 'rojo';
    if (denuncias >= DENUNCIAS_ADVERTENCIA) return 'amarillo';
    return 'verde';
};

export const UsuariosAdmin = () => {
    const { data: dataUsuarios, loading: loadingUsuarios, error: errorUsuarios } = useFetch('usuario');

    return (
        <main className="body-usuarios-admin">
            <header className="body-usuarios-admin__encabezado">
                <h1 className="body-usuarios-admin__encabezado__titulo">Usuarios</h1>
                <p className="body-usuarios-admin__encabezado__subtitulo">Gestiona los usuarios de Bücher</p>
            </header>

            <div className="body-usuarios-admin__tabla">
                <table className="body-usuarios-admin__tabla__elemento">
                    <thead className="body-usuarios-admin__tabla__elemento__cabecera">
                        <tr>
                            <th>ID del usuario</th>
                            <th>Usuario</th>
                            <th>Correo</th>
                            <th>Publicaciones</th>
                            <th>Posteos</th>
                            <th>Denuncias</th>
                        </tr>
                    </thead>
                    <tbody>
                        {dataUsuarios.map((usuario) => (
                            <tr key={usuario.id} className="body-usuarios-admin__tabla__elemento__fila">
                                <td>
                                    N° {usuario.id}
                                </td>
                                <td>@{usuario.nickname}</td>
                                <td>{usuario.username}</td>
                                <td>
                                    {/* {usuario.publicaciones} */}
                                </td>
                                <td>
                                    {/* {usuario.posteos} */}
                                </td>
                                <td>
                                    <span className="body-usuarios-admin__tabla__elemento__fila__denuncias">
                                        {/* {usuario.denuncias} */}
                                        <span
                                            className={`body-usuarios-admin__tabla__elemento__fila__denuncias__punto body-usuarios-admin__tabla__elemento__fila__denuncias__punto--${obtenerColorDenuncias(usuario.denuncias)}`}
                                            aria-hidden="true"
                                        />
                                    </span>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </main>
    );
};