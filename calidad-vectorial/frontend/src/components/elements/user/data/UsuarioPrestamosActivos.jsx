import { useState, useMemo } from 'react';
import { useFetch } from '../../../utils/FetchUtils';
import { LibroCard } from '../../book/LibroCard';
import './UsuarioData.css';

const CLASIFICACIONES = [
    { key: 'todos', label: 'Todos' },
    { key: 'activos', label: 'Activos' },
    { key: 'pendientes', label: 'Pendientes' },
    { key: 'pasados', label: 'Pasados' },
];

const clasificarPrestamo = (prestamo) => {
    const estado = prestamo.publicacion?.estadoPublicacion
        ?.toLowerCase();

    if (estado === 'entrega_pendiente') return 'pendientes';

    if (prestamo.fechaDevolucion) return 'pasados';

    return 'activos';
};

export const UsuarioPrestamosActivos = () => {
    const {
        data: dataRegistro,
        loading: loadingRegistro,
        error: errorRegistro,
    } = useFetch('registro');

    const [filtroActivo, setFiltroActivo] = useState('todos');

    const prestamos = useMemo(() => {
        if (!dataRegistro) return [];

        return dataRegistro.map((prestamo) => ({
            ...prestamo,
            titulo: prestamo.publicacion?.titulo ?? '',
            autor: prestamo.publicacion?.nombre ?? '',
            urlFoto: prestamo.publicacion?.urlFoto,
            clasificacion: clasificarPrestamo(prestamo),
        }));
    }, [dataRegistro]);

    const conteos = useMemo(() => {
        const base = {
            todos: prestamos.length,
            activos: 0,
            pendientes: 0,
            pasados: 0,
        };

        for (const prestamo of prestamos) {
            base[prestamo.clasificacion] += 1;
        }

        return base;
    }, [prestamos]);

    const prestamosFiltrados = useMemo(() => {
        if (filtroActivo === 'todos') return prestamos;

        return prestamos.filter(
            (prestamo) => prestamo.clasificacion === filtroActivo
        );
    }, [prestamos, filtroActivo]);

    return (
        <>
            <section className="biblioteca__contenedor__aside">
                <section className="biblioteca__contenedor__aside__clasificacion">
                    {CLASIFICACIONES.map(({ key, label }) => (
                        <div
                            key={key}
                            className={`biblioteca__contenedor__aside__clasificacion__card biblioteca__contenedor__aside__clasificacion__card--${key}${filtroActivo === key ? ' biblioteca__contenedor__aside__clasificacion__card--activo' : ''}`}
                            onClick={() => setFiltroActivo(key)}
                        >
                            {label}
                            <br />
                            {conteos[key]}
                        </div>
                    ))}
                </section>
            </section>

            <section className="biblioteca__contenedor__libros">
                {loadingRegistro ? (
                    <p>Cargando registro...</p>
                ) : errorRegistro ? (
                    <p>Error al cargar el registro: {errorRegistro}</p>
                ) : prestamosFiltrados.length === 0 && filtroActivo === 'todos'? (
                    <p>No hay préstamos.</p>
                ) : prestamosFiltrados.length === 0 ? (
                    <p>No hay préstamos en esta clasificación.</p>
                ) : (
                    prestamosFiltrados.map((prestamo) => (
                        <LibroCard
                            key={prestamo.publicacion?.id}
                            titulo={prestamo.titulo}
                            autor={prestamo.autor}
                            urlFoto={prestamo.urlFoto}
                            clasificacion={prestamo.clasificacion}
                            estrllas={false}
                        />
                    ))
                )}
            </section>
        </>
    );
};
