import { useEffect, useState } from 'react';
import { usePost } from '../../../utils/FetchUtils';
import { Input } from '../../../elements/input/Input';
import { Button } from '../../../elements/buttons/Button';
import { PopUp } from '../../../elements/modal/PopUp';
import { LibroDetalle } from '../../../elements/book/LibroDetalle';
import './ControlEjemplar.css';

const estadosDisponibles = [
    { valor: 'excelente', nombre: 'Excelente' },
    { valor: 'muybueno', nombre: 'Muy bueno' },
    { valor: 'bueno', nombre: 'Bueno' },
    { valor: 'desgastado', nombre: 'Desgastado' },
];

const detallesDisponibles = [
    { valor: 'subrayado', label: 'Ejemplar subrayado.', chip: 'Subrayado' },
    { valor: 'anotaciones', label: 'Ejemplar con anotaciones.', chip: 'Con anotaciones' },
    { valor: 'manchas', label: 'Ejemplar con manchas.', chip: 'Con manchas' },
    { valor: 'paginasdobladas', label: 'Ejemplar con páginas dobladas.', chip: 'Con páginas dobladas' },
];

const ejemplar = {
    id: 1,
    titulo: 'Mi nombre es Emilia del Valle',
    autor: 'Isabel Allende',
    editorial: 'Sudamericana',
    urlFoto: '',
    detallesUsuario: ['subrayado'],
    comentarioUsuario: '',
};

export const ControlEjemplar = () => {
    const { data: dataControl, loading: loadingControl, error: errorControl, execute: executeControl } = usePost('ejemplar/controlar');

    const [titulo, setTitulo] = useState(ejemplar.titulo);
    const [autor, setAutor] = useState(ejemplar.autor);
    const [tituloConfirmado, setTituloConfirmado] = useState(false);
    const [autorConfirmado, setAutorConfirmado] = useState(false);

    const [estado, setEstado] = useState('');
    const [detallesRevisados, setDetallesRevisados] = useState([]);
    const [comentarios, setComentarios] = useState('');

    const [enCondiciones, setEnCondiciones] = useState(true);
    const [motivos, setMotivos] = useState('');

    const [mensaje, setMensaje] = useState('');
    const [mostrarPopUp, setMostrarPopUp] = useState(false);

    useEffect(() => {
        if (dataControl && !errorControl) {
            setMostrarPopUp(true);
        }
    }, [dataControl, errorControl]);

    const estadoSeleccionado = estadosDisponibles.find(
        (actual) => actual.valor === estado
    );

    const formularioValido =
        titulo.trim() !== '' &&
        tituloConfirmado &&
        (autor.trim() === '' || autorConfirmado) &&
        estado !== '' &&
        (enCondiciones || motivos.trim() !== '');

    const handleToggleDetalle = (valor) => {
        setDetallesRevisados(prev =>
            prev.includes(valor)
                ? prev.filter(actual => actual !== valor)
                : [...prev, valor]
        );
    };

    const handleCondiciones = (valor) => {
        setEnCondiciones(valor);

        if (valor) setMotivos('');
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!formularioValido || loadingControl) return;

        setMensaje('');

        const datos = {
            idEjemplar: ejemplar.id,
            titulo: titulo.trim(),
            autor: autor.trim(),
            estado,
            detalles: detallesRevisados,
            comentarios: comentarios.trim(),
            enCondiciones,
            motivos: enCondiciones ? '' : motivos.trim(),
        };

        try {
            await executeControl(datos);
        } catch (error) {
            setMensaje('No se pudo registrar el control. Intentá nuevamente.');
        }
    };

    const caracteristicas = [
        ...(estadoSeleccionado ? [`Estado del ejemplar: ${estadoSeleccionado.nombre}`] : []),
        ...(enCondiciones ? ['El libro se encuentra en condiciones'] : []),
    ];

    const detallesEjemplar = detallesDisponibles
        .filter((detalle) => detallesRevisados.includes(detalle.valor))
        .map((detalle) => detalle.chip);

    return (
        <>
            {mostrarPopUp && (
                <PopUp
                    onClick={() => {
                        setMostrarPopUp(false);
                    }}
                    titulo="✔️ Control registrado ✔️"
                >
                    El control del ejemplar se registró correctamente.
                </PopUp>
            )}

            <div className="control-ejemplar">
                <div>
                    <h1 className="control-ejemplar__title">Control del ejemplar prestado</h1>
                    <p>La descripción siempre debe coincidir con el ejemplar</p>
                </div>

                <form className="control-ejemplar__layout" onSubmit={handleSubmit}>
                    <div className="control-ejemplar__form">

                        <section className="control-ejemplar__seccion">
                            <h2>Confirmar datos del ejemplar</h2>

                            <div className="control-ejemplar__campo">
                                <label className="control-ejemplar__label" htmlFor="titulo">
                                    Título
                                </label>
                                <div className="control-ejemplar__confirmar">
                                    <div className="control-ejemplar__confirmar__input">
                                        <Input
                                            id="titulo"
                                            type="text"
                                            name="titulo"
                                            value={titulo}
                                            onChange={(e) => setTitulo(e.target.value)}
                                            required={true}
                                        />
                                    </div>
                                    <input
                                        className="control-ejemplar__checkbox"
                                        type="checkbox"
                                        checked={tituloConfirmado}
                                        onChange={(e) => setTituloConfirmado(e.target.checked)}
                                        aria-label="Confirmar título"
                                    />
                                </div>
                            </div>

                            <div className="control-ejemplar__campo">
                                <label className="control-ejemplar__label" htmlFor="autor">
                                    Autor
                                </label>
                                <div className="control-ejemplar__confirmar">
                                    <div className="control-ejemplar__confirmar__input control-ejemplar__confirmar__input--medio">
                                        <Input
                                            id="autor"
                                            type="text"
                                            name="autor"
                                            value={autor}
                                            onChange={(e) => setAutor(e.target.value)}
                                        />
                                    </div>
                                    <input
                                        className="control-ejemplar__checkbox"
                                        type="checkbox"
                                        checked={autorConfirmado}
                                        onChange={(e) => setAutorConfirmado(e.target.checked)}
                                        aria-label="Confirmar autor"
                                    />
                                </div>
                            </div>
                        </section>

                        {/* Estado del ejemplar */}
                        <section className="control-ejemplar__seccion">
                            <h2>Estado del ejemplar</h2>

                            <div className="control-ejemplar__campo">
                                <span className="control-ejemplar__label" id="estado-general">
                                    Estado general
                                </span>
                                <div
                                    className="control-ejemplar__opciones"
                                    role="radiogroup"
                                    aria-labelledby="estado-general"
                                >
                                    {estadosDisponibles.map((actual) => (
                                        <button
                                            key={actual.valor}
                                            type="button"
                                            role="radio"
                                            aria-checked={estado === actual.valor}
                                            className={
                                                'control-ejemplar__opcion' +
                                                (estado === actual.valor ? ' control-ejemplar__opcion--activa' : '')
                                            }
                                            onClick={() => setEstado(actual.valor)}
                                        >
                                            {actual.nombre}
                                        </button>
                                    ))}
                                </div>
                            </div>

                            <div className="control-ejemplar__detalles">
                                <div className="control-ejemplar__detalles__header">
                                    <span className="control-ejemplar__label">
                                        Detalles adicionales, marcados por el usuario
                                    </span>
                                    <span className="control-ejemplar__label">
                                        Detalles del ejemplar
                                    </span>
                                </div>

                                <ul className="control-ejemplar__detalles__lista">
                                    {detallesDisponibles.map((detalle) => (
                                        <li key={detalle.valor} className="control-ejemplar__detalles__item">
                                            <label className="control-ejemplar__detalles__usuario">
                                                <input
                                                    className="control-ejemplar__checkbox control-ejemplar__checkbox--chico"
                                                    type="checkbox"
                                                    checked={ejemplar.detallesUsuario.includes(detalle.valor)}
                                                    disabled
                                                    readOnly
                                                />
                                                <span>{detalle.label}</span>
                                            </label>
                                            <input
                                                className="control-ejemplar__checkbox"
                                                type="checkbox"
                                                checked={detallesRevisados.includes(detalle.valor)}
                                                onChange={() => handleToggleDetalle(detalle.valor)}
                                                aria-label={detalle.label}
                                            />
                                        </li>
                                    ))}
                                </ul>
                            </div>

                            <div className="control-ejemplar__campo">
                                <label className="control-ejemplar__label" htmlFor="comentario-usuario">
                                    Comentarios adicionales del usuario
                                </label>
                                <textarea
                                    id="comentario-usuario"
                                    className="control-ejemplar__textarea control-ejemplar__textarea--lectura"
                                    value={ejemplar.comentarioUsuario}
                                    placeholder="Comentario escrito por el dueño del ejemplar, sobre el mismo."
                                    readOnly
                                />
                            </div>

                            <div className="control-ejemplar__campo">
                                <label className="control-ejemplar__label" htmlFor="comentarios">
                                    Comentarios adicionales
                                </label>
                                <textarea
                                    id="comentarios"
                                    className="control-ejemplar__textarea"
                                    value={comentarios}
                                    onChange={(e) => setComentarios(e.target.value)}
                                    placeholder="Describe cualquier detalle sobre el ejemplar que no se haya mencionado."
                                />
                            </div>
                        </section>

                        {/* Condiciones del ejemplar */}
                        <section className="control-ejemplar__seccion">
                            <h2>Condiciones del ejemplar</h2>

                            <div className="control-ejemplar__campo">
                                <span className="control-ejemplar__label" id="condiciones">
                                    El libro se encuentra en condiciones de ser prestado
                                </span>
                                <div
                                    className="control-ejemplar__opciones"
                                    role="radiogroup"
                                    aria-labelledby="condiciones"
                                >
                                    <button
                                        type="button"
                                        role="radio"
                                        aria-checked={enCondiciones}
                                        className={
                                            'control-ejemplar__opcion control-ejemplar__opcion--ancha' +
                                            (enCondiciones ? ' control-ejemplar__opcion--activa' : '')
                                        }
                                        onClick={() => handleCondiciones(true)}
                                    >
                                        Se encuentra en condiciones
                                    </button>
                                    <button
                                        type="button"
                                        role="radio"
                                        aria-checked={!enCondiciones}
                                        className={
                                            'control-ejemplar__opcion control-ejemplar__opcion--ancha' +
                                            (!enCondiciones ? ' control-ejemplar__opcion--activa' : '')
                                        }
                                        onClick={() => handleCondiciones(false)}
                                    >
                                        No se encuentra en condiciones
                                    </button>
                                </div>
                            </div>

                            <div className="control-ejemplar__campo">
                                <label className="control-ejemplar__label" htmlFor="motivos">
                                    Motivos
                                </label>
                                <textarea
                                    id="motivos"
                                    className="control-ejemplar__textarea"
                                    value={motivos}
                                    onChange={(e) => setMotivos(e.target.value)}
                                    placeholder="Comenta por qué el ejemplar no esta en condiciones de ser prestado..."
                                    disabled={enCondiciones}
                                    required={!enCondiciones}
                                />
                            </div>
                        </section>
                    </div>

                    <div className="control-ejemplar__lateral">
                        <LibroDetalle
                            urlFoto={ejemplar.urlFoto}
                            titulo={titulo}
                            autor={autor}
                            editorial={ejemplar.editorial}
                            caracteristicas={caracteristicas}
                            detallesEjemplar={detallesEjemplar}
                        />

                        {mensaje && (
                            <p className="control-ejemplar__error" role="alert">
                                {mensaje}
                            </p>
                        )}

                        <div className="control-ejemplar__accion">
                            <Button
                                type="submit"
                                variant="default"
                                color="oscuro"
                                isDisabled={!formularioValido || loadingControl}
                            >
                                {loadingControl ? 'Guardando...' : 'Aceptar'}
                            </Button>
                        </div>
                    </div>
                </form>
            </div>
        </>
    );
};