import { useMemo, useState } from 'react';
import { useFetch } from '../../../utils/FetchUtils';
import './PrestamosAdmin.css';

const ESTADOS = [
    'Disponible',
    'Entrega_pendiente',
    'Prestado',
    'Devolucion_pendiente',
    'No_disponible',
    'Indefinido',
];

const OPCIONES_ORDEN = [
    { valor: 'nuevos', texto: 'Más nuevos' },
    { valor: 'antiguos', texto: 'Más antiguos' },
];

const COLOR_ESTADO = {
    'Devolucion_pendiente': 'amarillo',
    'Indefinido': 'rojo',
};

export const PrestamosAdmin = () => {
    const [estadoActivo, setEstadoActivo] = useState(null);
    const [orden, setOrden] = useState('nuevos');

    const { data: dataPrestamos, loading: loadingPrestamos, error: errorPrestamos } = useFetch('publicacion');

    const prestamosVisibles = useMemo(() => {
        const filtrados = estadoActivo
            ? dataPrestamos.filter((prestamo) => prestamo.estadoPublicacion === estadoActivo)
            : dataPrestamos;

        return [...filtrados].sort((a, b) =>
            orden === 'nuevos'
                ? '2026-08-26'.localeCompare('2026-08-25')
                : '2026-08-25'.localeCompare('2026-08-26'),
        );
    }, [dataPrestamos, estadoActivo, orden]);

    const alternarEstado = (estado) => {
        setEstadoActivo((actual) => (actual === estado ? null : estado));
    };

    return (
        <main className="body-prestamos-admin">
            <nav className="body-prestamos-admin__filtros" aria-label="Filtrar préstamos por estado">
                <span className="body-prestamos-admin__filtros__titulo">Filtrar por:</span>
                <ul className="body-prestamos-admin__filtros__lista">
                    {ESTADOS.map((estado) => (
                        <li key={estado} className="body-prestamos-admin__filtros__item">
                            <button
                                type="button"
                                className={`body-prestamos-admin__filtros__chip${estadoActivo === estado ? ' body-prestamos-admin__filtros__chip--activo' : ''}`}
                                aria-pressed={estadoActivo === estado}
                                onClick={() => alternarEstado(estado)}
                            >
                                {estado}
                            </button>
                        </li>
                    ))}
                    <li className="body-prestamos-admin__filtros__item">
                        <button
                            type="button"
                            className="body-prestamos-admin__filtros__chip body-prestamos-admin__filtros__chip--mas"
                            aria-label="Más filtros"
                        >
                            +
                        </button>
                    </li>
                </ul>
            </nav>

            <section className="body-prestamos-admin__contenido">
                <header className="body-prestamos-admin__contenido__encabezado">
                    <div className="body-prestamos-admin__contenido__encabezado__texto">
                        <h1 className="body-prestamos-admin__contenido__encabezado__titulo">
                            Préstamos vigentes
                        </h1>
                        <p className="body-prestamos-admin__contenido__encabezado__subtitulo">
                            Gestiona los préstamos activos.
                        </p>
                    </div>

                    <div className="body-prestamos-admin__contenido__encabezado__orden">
                        <label
                            className="body-prestamos-admin__contenido__encabezado__orden__etiqueta"
                            htmlFor="orden-prestamos"
                        >
                            Ordenar por
                        </label>
                        <select
                            id="orden-prestamos"
                            className="body-prestamos-admin__contenido__encabezado__orden__select"
                            value={orden}
                            onChange={(e) => setOrden(e.target.value)}
                        >
                            {OPCIONES_ORDEN.map(({ valor, texto }) => (
                                <option key={valor} value={valor}>
                                    {texto}
                                </option>
                            ))}
                        </select>
                    </div>
                </header>

                <div className="body-prestamos-admin__contenido__tabla">
                    <table className="body-prestamos-admin__contenido__tabla__elemento">
                        <thead className="body-prestamos-admin__contenido__tabla__elemento__cabecera">
                            <tr>
                                <th>ID del préstamo</th>
                                <th>Usuario</th>
                                <th>Libro</th>
                                <th>Estado</th>
                                <th>Fecha de entrega</th>
                                <th>Fecha de devolución</th>
                            </tr>
                        </thead>
                        <tbody>
                            {prestamosVisibles.map((prestamo) => (
                                <tr
                                    key={prestamo.id}
                                    className="body-prestamos-admin__contenido__tabla__elemento__fila"
                                >
                                    <td>
                                        N° {prestamo.id}
                                    </td>
                                    <td>@{prestamo.usuarioNickname}</td>
                                    <td>{prestamo.titulo}</td>
                                    <td>
                                        <span className="body-prestamos-admin__contenido__tabla__elemento__fila__estado">
                                            {prestamo.estadoPublicacion}
                                            <span
                                                className={`body-prestamos-admin__contenido__tabla__elemento__fila__estado__punto body-prestamos-admin__contenido__tabla__elemento__fila__estado__punto--${COLOR_ESTADO[prestamo.estadoPublicacion] ?? 'verde'}`}
                                                aria-hidden="true"
                                            />
                                        </span>
                                    </td>
                                    <td>
                                        {'2026-08-26'}
                                    </td>
                                    <td>
                                        {'2026-08-25'}
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>

                    {prestamosVisibles.length === 0 && (
                        <p className="body-prestamos-admin__contenido__tabla__vacio">
                            No hay préstamos con el estado seleccionado.
                        </p>
                    )}
                </div>
            </section>
        </main>
    );
};