import { useState } from 'react';
import { useParams, useNavigate, useLocation } from 'react-router-dom';
import { useFetch } from '../../../utils/FetchUtils';

import { EncabezadoPagina } from '../../../elements/headerpage/EncabezadoPagina';
import { LibroDetalle } from '../../../elements/book/LibroDetalle';
import { UsuarioDetalleCard } from '../../../elements/user/detail/UsuarioDetalleCard';
import { DetalleCard } from '../../../elements/detail/DetalleCard';
import { Button } from '../../../elements/buttons/Button';
import './DetallesPrestamo.css';

const PASOS = [
    { estado: 'solicitado', texto: 'Solicitado' },
    { estado: 'retirado', texto: 'Retirado' },
    { estado: 'enrevision', texto: 'En revisión' },
    { estado: 'aentregar', texto: 'A entregar' },
    { estado: 'entregado', texto: 'Entregado' },
];

const ACCIONES_POR_ESTADO = {
    solicitado: { texto: 'Marcar como retirado', siguienteEstado: 'retirado' },
    retirado: { texto: 'Marcar en revisión', siguienteEstado: 'enrevision' },
    enrevision: { texto: 'Marcar como a entregar', siguienteEstado: 'aentregar' },
    aentregar: { texto: 'Marcar como entregado', siguienteEstado: 'entregado' },
};

// Si el backend devuelve estados con otro nombre, mapealos acá a uno de los
// pasos de PASOS (las claves van ya normalizadas: minúsculas, sin tildes,
// sin espacios ni guiones bajos). Ajustá según lo que realmente devuelva tu API.
const ALIAS_ESTADOS = {
    entregapendiente: 'solicitado',
    prestado: 'retirado',
    devolucionpendiente: 'aentregar',
};

// Deja el estado en minúsculas, sin tildes, espacios ni guiones bajos,
// y resuelve alias del backend hacia los pasos del componente.
const normalizarEstado = (estado) => {
    const limpio = (estado ?? '')
        .toString()
        .toLowerCase()
        .normalize('NFD')
        .replace(/[\u0300-\u036f]/g, '')
        .replace(/[\s_-]/g, '');

    return ALIAS_ESTADOS[limpio] ?? limpio;
};

function formatearFecha(fecha) {
    if (!fecha) return 'indefinido';
    const [anio, mes, dia] = String(fecha).split('T')[0].split('-');
    return `${dia}/${mes}/${anio}`;
}

export const DetallesPrestamo = () => {
    const { id } = useParams();
    const navigate = useNavigate();
    const location = useLocation();
    const {
        data: dataPrestamo,
        loading: loadingPrestamo,
        error: errorPrestamo,
    } = useFetch(`registro/publicacion/${id}`);

    const [estadoModificado, setEstadoModificado] = useState(
        location.state?.estado ? normalizarEstado(location.state.estado) : null,
    );

    // useFetch arranca con data = [], por eso se valida que exista publicacion
    const prestamoCargado = Boolean(dataPrestamo?.publicacion);

    const estadoActual = normalizarEstado(
        estadoModificado ?? dataPrestamo?.publicacion?.estadoPublicacion,
    );
    const accionActual = ACCIONES_POR_ESTADO[estadoActual];
    const revisionEditable = estadoActual === 'enrevision';

    const pasosCompletados = PASOS.findIndex((paso) => paso.estado === estadoActual) + 1;

    const handleVerRevision = () => {
        navigate('control', { state: { estado: estadoActual } });
    };

    const handleCambiarEstado = () => {
        if (!accionActual) return;
        setEstadoModificado(accionActual.siguienteEstado);
    };

    return (
        <main className="detalles-prestamo">
            <EncabezadoPagina
                titulo="Detalles del préstamo"
                subtitulo="Controla y actualiza los datos del préstamo"
            />
            <section className="detalles-prestamo__seccion">
                {loadingPrestamo ? (
                    <p>Cargando</p>
                ) : (
                    prestamoCargado && (
                        <>
                            <LibroDetalle
                                urlFoto={dataPrestamo.publicacion.urlFoto}
                                titulo={dataPrestamo.publicacion.titulo}
                                autor={dataPrestamo.publicacion.nombre}
                                editorial="Algo"
                                caracteristicas={[
                                    'Estado del ejemplar: ' +
                                        dataPrestamo.publicacion.detallesEstadoLibro,
                                ]}
                                detalles={[
                                    {
                                        label: 'Número de préstamo',
                                        texto: `N° ${dataPrestamo.id}`,
                                    },
                                    {
                                        label: 'Inicio del préstamo',
                                        texto: formatearFecha(dataPrestamo.fechaPrestamo),
                                    },
                                    {
                                        label: 'Fin del préstamo',
                                        texto: formatearFecha(dataPrestamo.fechaDevolucion),
                                    },
                                ]}
                            />
                            <UsuarioDetalleCard
                                titulo="Usuario prestamista"
                                foto={
                                    '/assets/img/avatares/' +
                                    dataPrestamo.publicacion.avatarUsuarioCreador
                                }
                                nickname={dataPrestamo.publicacion.nicknameUsuarioCreador}
                                username={dataPrestamo.publicacion.usuarioCreador}
                            />
                            <UsuarioDetalleCard
                                titulo="Usuario lector"
                                foto={
                                    '/assets/img/avatares/' + dataPrestamo.avatarUsuarioPrestamista
                                }
                                nickname={dataPrestamo.nickanmeUsuarioPrestamista}
                                username={dataPrestamo.usuarioPrestamista}
                            />
                        </>
                    )
                )}
                {errorPrestamo && <p>Error al cargar los datos del préstamo</p>}
            </section>
            <section className="detalles-prestamo__seccion">
                <div className="seccion__acciones">
                    <h2>Acciones</h2>
                    <Button
                        variant="default"
                        color="oscuro"
                        isDisabled={!prestamoCargado}
                        onClick={handleVerRevision}
                    >
                        {revisionEditable
                            ? 'Realizar revisión del ejemplar'
                            : 'Ver revisión del ejemplar'}
                    </Button>
                    {accionActual ? (
                        <Button variant="default" color="oscuro" onClick={handleCambiarEstado}>
                            {accionActual.texto}
                        </Button>
                    ) : (
                        <Button variant="default" color="oscuro" isDisabled={true}>
                            {estadoActual === 'entregado'
                                ? 'Préstamo entregado'
                                : 'Sin acciones disponibles'}
                        </Button>
                    )}
                    {revisionEditable && (
                        <p>
                            Completa el formulario de revisión y una vez listo marca el préstamo
                            como a entregar.
                        </p>
                    )}
                </div>
                <DetalleCard
                    titulo="Estado del préstamo"
                    pasos={PASOS.map((paso) => paso.texto)}
                    pasosCompletados={pasosCompletados}
                />
            </section>
        </main>
    );
};