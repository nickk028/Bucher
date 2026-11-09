import { useMemo, useState } from 'react';
import { Tabla } from '../../../elements/table/Tabla';
import { Indicador } from '../../../elements/table/Indicador';
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

const COLUMNAS = [
    {
        clave: 'id',
        titulo: 'ID del préstamo',
        render: (prestamo) => `N° ${prestamo.id}`,
    },
    {
        clave: 'usuarioNickname',
        titulo: 'Usuario',
        render: (prestamo) => `@${prestamo.usuarioNickname}`,
    },
    { clave: 'titulo', titulo: 'Libro', width: 30 },
    {
        clave: 'estadoPublicacion',
        titulo: 'Estado',
        render: (prestamo) => (
            <Indicador color={COLOR_ESTADO[prestamo.estadoPublicacion] ?? 'verde'}>
                {prestamo.estadoPublicacion}
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
                    <Tabla
                        columnas={COLUMNAS}
                        items={prestamosVisibles}
                        mensajeVacio="No hay préstamos con el estado seleccionado."
                    />
                </div>
            </section>
        </main>
    );
};