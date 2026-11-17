import iconoCheck from '../../../assets/img/icons/crear/tildeVistaPrevia.svg';
import pasoCompletado from '../../../assets/img/icons/prestamo/pasoCompletado.svg';
import pasoPendiente from '../../../assets/img/icons/prestamo/pasoPendiente.svg';
import './DetalleCard.css';

export const DetalleCard = ({ titulo, pasos = [], pasosCompletados = 0, eventos = [], progreso }) => {
    const progresoCalculado =
        pasos.length > 1
            ? Math.min(Math.max((pasosCompletados - 1) / (pasos.length - 1), 0), 1)
            : 0;

    const progresoFinal = progreso ?? progresoCalculado;

    return (
        <section className="detalle-card">
            <h2 className="detalle-card__titulo">{titulo}</h2>

            {pasos.length > 0 && (
                <ol
                    className="detalle-card__stepper"
                    style={{
                        '--progreso': progresoFinal,
                        '--total-pasos': pasos.length,
                    }}
                >
                    {pasos.map((paso, index) => {
                        const completado = index < pasosCompletados;
                        const posicion = index % 2 === 0 ? 'arriba' : 'abajo';

                        return (
                            <li
                                key={paso}
                                className={`detalle-card__paso detalle-card__paso--${posicion}`}
                            >
                                <span
                                    className={`detalle-card__label${
                                        completado ? ' detalle-card__label--completado' : ''
                                    }`}
                                >
                                    {paso}
                                </span>
                                <img
                                    className="detalle-card__marcador"
                                    src={completado ? pasoCompletado : pasoPendiente}
                                    alt=""
                                />
                            </li>
                        );
                    })}
                </ol>
            )}

            {eventos.length > 0 && (
                <ul className="detalle-card__eventos">
                    {eventos.map((evento, index) => (
                        <li
                            key={`${evento.texto}-${index}`}
                            className="detalle-card__evento"
                        >
                            <img
                                className="detalle-card__icono"
                                src={iconoCheck}
                                alt=""
                            />
                            <span>{evento.texto}</span>
                            <span className="detalle-card__fecha">{evento.fecha}</span>
                        </li>
                    ))}
                </ul>
            )}
        </section>
    );
}