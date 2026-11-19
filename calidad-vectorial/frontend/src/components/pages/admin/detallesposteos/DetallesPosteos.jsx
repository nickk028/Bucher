import { useParams } from 'react-router-dom';
import { useFetch, usePost } from '../../../utils/FetchUtils';
import { EncabezadoPagina } from '../../../elements/headerpage/EncabezadoPagina';
import { LibroDetalle } from '../../../elements/book/LibroDetalle';
import { UsuarioDetalleCard } from '../../../elements/user/detail/UsuarioDetalleCard';
import { Button } from '../../../elements/buttons/Button';
import './DetallesPosteos.css';

const denuncias = [
    { id: 1, nickname: 'pipulichu', foto: '/assets/img/avatares/gandalf.png' },
    { id: 2, nickname: 'Juli_25', foto: '/assets/img/avatares/katniss.png' },
    { id: 3, nickname: 'pilu_okk', foto: '/assets/img/avatares/reinadecorazones.png' },
    { id: 4, nickname: 'lectora_92', foto: '/assets/img/avatares/dorothy.png' },
    { id: 5, nickname: 'mati_libros', foto: '/assets/img/avatares/detective.png' },
]

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
    const { data: dataPosteo, loading: loadingPosteo, error: errorPosteo } = useFetch(`publicacionSocial/${id}`);

    const {
        data: dataEliminarPosteo,
        loading: loadingEliminarPosteo,
        error: errorEliminarPosteo,
        execute: executeEliminarPosteo
    } = useFetch(`publicacionSocial/eliminar/${id}`, 'DELETE');

    return (
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
                                    {denuncias.length}
                                </span>
                            </header>
                            <ul className="detalles-posteo__contenido__principal__fila__denuncias__lista">
                                {denuncias.map((denuncia) => (
                                    <li
                                        key={denuncia.id}
                                        className="detalles-posteo__contenido__principal__fila__denuncias__lista__item"
                                    >
                                        <img
                                            className="detalles-posteo__contenido__principal__fila__denuncias__lista__item__foto"
                                            src={denuncia.foto}
                                            alt={`Foto del usuario ${denuncia.nickname}`}
                                        />
                                        <span className="detalles-posteo__contenido__principal__fila__denuncias__lista__item__nickname">
                                            @{denuncia.nickname}
                                        </span>
                                    </li>
                                ))}
                            </ul>
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
                        <Button variant='default' color='oscuro' onClick={() => executeEliminarPosteo()}>Eliminar posteo</Button>
                    </div>
                </aside>
            </div>
        </main>
    );
};