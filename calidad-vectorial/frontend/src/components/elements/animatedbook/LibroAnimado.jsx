import { useEffect, useRef } from "react";
import { OjosAnimados } from "./OjosAnimados";
import "./LibroAnimado.css";

/**
 * Representa un rostro animado con ojos que siguen el cursor y parpadean.
 * También reacciona a eventos personalizados como el foco en campos de contraseña.
 */
export const LibroAnimado = ({ children, variant="medio", color="amarillo", mensaje, mostrarMensaje }) => {
    return (
        <div className={`rostro rostro--${variant} rostro--${color}`}>

            {/* Globo de mensaje si está habilitado */}
            {mostrarMensaje && mensaje && (
                <>
                    {variant == "büchi" ? (
                        <div className={`globo globo--${color}`}>
                            <p className={`globo__text globo__text--${color}`}>{mensaje}</p>
                        </div>
                    ) : (
                        <div className="rostro__globo">
                            <p className="rostro__globo__texto">{mensaje}</p>
                            <div className="rostro__globo__punta"></div>
                        </div>
                    )}
                </>
            )}

            {/* Sección de los ojos */}
            <OjosAnimados />

            {/* Contenido central del rostro */}
            <div className="rostro__content">
                <p className="rostro__content__text">{children}</p>
            </div>
        </div>
    );
};