import './Indicador.css';

export const Indicador = ({ children, color = 'verde'}) => {
    return (
        <span className="indicador">
            {children}
            <span className={`indicador__punto indicador__punto--${color}`} aria-hidden="true" />
        </span>
    );
};