import './Indicador.css';

export const Indicador = ({ children, color = 'verde', negrita = false }) => {
    return (
        <span className="indicador">
            <span className={`indicador__valor${negrita ? ' indicador__valor--negrita' : ''}`}>
                {children}
            </span>
            <span className={`indicador__punto indicador__punto--${color}`} aria-hidden="true" />
        </span>
    );
};