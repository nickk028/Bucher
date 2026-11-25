import { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { useFetch, usePost } from '../../../utils/FetchUtils';
import { EncabezadoPagina } from '../../../elements/headerpage/EncabezadoPagina';
import { LibroDetalle } from '../../../elements/book/LibroDetalle';
import { UsuarioDetalleCard } from '../../../elements/user/detail/UsuarioDetalleCard';
import { Button } from '../../../elements/buttons/Button';
import { Input } from '../../../elements/input/Input';
import { PopUp } from '../../../elements/modal/PopUp';
import './DetallesPosteos.css';

function formatearFecha(fecha) {
    const date = new Date(fecha);

    const dia = String(date.getDate()).padStart(2, '0');
    const mes = String(date.getMonth() + 1).padStart(2, '0');
    const anio = date.getFullYear();

    return `${dia}/${mes}/${anio}`;
}

function formatearHora(fecha) {
    const date = new Date(fecha);

    const horas = String(date.getHours()).padStart(2, '0');
    const minutos = String(date.getMinutes()).padStart(2, '0');

    return `${horas}:${minutos}`;
}

export const DetallesPosteos = () => {
    const { id } = useParams();
    const navigate = useNavigate();
    const { data: dataPosteo, loading: loadingPosteo, error: errorPosteo } = useFetch(`publicacionSocial/${id}`);

    const {
        data: dataEliminarPosteo,
        loading: loadingEliminarPosteo,
        error: errorEliminarPosteo,
        execute: executeEliminarPosteo
    } = usePost(`publicacionSocial/eliminar/${id}`, 'DELETE');

    const [mostrarPopUp, setMostrarPopUp] = useState(false);
    const [razonEliminacion, setRazonEliminacion] = useState('');

    const handleEliminarPosteo = () => {
        executeEliminarPosteo({ razon: razonEliminacion.trim() });
    };

    useEffect(() => {
        if (dataEliminarPosteo && !errorEliminarPosteo) {
            setMostrarPopUp(true);
        }
    }, [dataEliminarPosteo, errorEliminarPosteo]);

    return (
        <>
            {mostrarPopUp && (
                <PopUp onClick={() => {setMostrarPopUp(false); navigate('/admin/posteos');}} titulo="✔️ Publicación social eliminada ✔️">
                    La publicación social ha sido eliminada correctamente.
                </PopUp>
            )}
            <main className="detalles-posteo">
                <EncabezadoPagina
                    titulo='Detalles del posteo'
                    subtitulo='Revisa el comentario y los elementos que lo componen.'
                />

                <div className="detalles-posteo__contenido">
                    <div className="detalles-posteo__contenido__principal">
                        <section className="detalles-posteo__contenido__principal__comentario">
                            <p className="detalles-posteo__contenido__principal__comentario__texto">
                                {dataPosteo.textoPublicacion}
                            </p>
                        </section>

                        <div className="detalles-posteo__contenido__principal__fila">
                            <LibroDetalle
                                urlFoto={dataPosteo.libro?.urlFoto}
                                titulo={dataPosteo.libro?.titulo}
                                autor={dataPosteo.libro?.nombreAutor}
                                detalles={[
                                    {label: 'Número de posteo', texto: `N°${dataPosteo.id}`},
                                    {label: 'Fecha de publicación', texto: `${formatearFecha(dataPosteo.fechaCreacion)}`},
                                    {label: 'Hora de publicación', texto: `${formatearHora(dataPosteo.fechaCreacion)} hs`},
                                ]}
                            />

                            <section className="detalles-posteo__contenido__principal__fila__denuncias">
                                <header className="detalles-posteo__contenido__principal__fila__denuncias__encabezado">
                                    <h2 className="detalles-posteo__contenido__principal__fila__denuncias__encabezado__titulo">
                                        Denuncias
                                    </h2>
                                    <span className="detalles-posteo__contenido__principal__fila__denuncias__encabezado__cantidad">
                                        {dataPosteo.denuncias?.length ?? 0}
                                    </span>
                                </header>
                                {dataPosteo.denuncias && (
                                    <ul className="detalles-posteo__contenido__principal__fila__denuncias__lista">
                                        {dataPosteo.denuncias.map((denuncia) => (
                                            <li
                                                key={dataPosteo.denuncia.id}
                                                className="detalles-posteo__contenido__principal__fila__denuncias__lista__item"
                                            >
                                                <img
                                                    className="detalles-posteo__contenido__principal__fila__denuncias__lista__item__foto"
                                                    src={dataPosteo.denuncia.foto}
                                                    alt={`Foto del usuario ${denuncia.nickname}`}
                                                />
                                                <span className="detalles-posteo__contenido__principal__fila__denuncias__lista__item__nickname">
                                                    @{dataPosteo.denuncia.nickname}
                                                </span>
                                            </li>
                                        ))}
                                    </ul>
                                )}
                            </section>
                        </div>
                    </div>

                    <aside className="detalles-posteo__contenido__lateral">
                        <UsuarioDetalleCard
                            titulo='Usuario lector'
                            foto={`/assets/img/avatares/${dataPosteo.usuario?.avatar}`}
                            nickname={dataPosteo.usuario?.nickname}
                            username={dataPosteo.usuario?.username}
                        />

                        <div className='detalles-posteo__contenido__acciones'>
                            <h2>Acciones</h2>
                            {dataPosteo.registroEliminacion ? (<>
                                <Input
                                    type="text"
                                    name="razonEliminacion"
                                    placeholder="Éste posteo ya ha sido eliminado"
                                    isDisabled={true}
                                    disabled={true}
                                />
                                <Button
                                    variant='default'
                                    color='oscuro'
                                    isDisabled={true}
                                >
                                    Eliminar posteo
                                </Button>
                            </>) : (<>
                                <Input
                                    type="text"
                                    name="razonEliminacion"
                                    placeholder="Razón de eliminación"
                                    value={razonEliminacion}
                                    onChange={(e) => setRazonEliminacion(e.target.value)}
                                    required={false}
                                />
                                <Button
                                    variant='default'
                                    color='oscuro'
                                    isDisabled={!razonEliminacion.trim() || loadingEliminarPosteo}
                                    onClick={handleEliminarPosteo}
                                >
                                    Eliminar posteo
                                </Button>
                            </>)}
                        </div>
                    </aside>
                </div>
            </main>
        </>
    );
};