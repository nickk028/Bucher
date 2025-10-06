import { useEffect, useRef } from "react";
import "./LibroAnimado.css";

/**
 * Representa un rostro animado con ojos que siguen el cursor y parpadean.
 * También reacciona a eventos personalizados como el foco en campos de contraseña.
 */
export const LibroAnimado = ({ children, variant = "medio", color, mensaje, mostrarMensaje }) => {
    // Referencias a los elementos DOM de los ojos y pupilas
    const ojoRef = useRef(null);
    const pupilaRef = useRef(null);
    const ojoRef2 = useRef(null);
    const pupilaRef2 = useRef(null);

    // Estado para controlar si los ojos están cerrados
    const ojosCerradosRef = useRef(false);

    // Referencia para manejar el ciclo de animación
    const animacionFrameRef = useRef(null);

    useEffect(() => {
        const ojo1 = ojoRef.current;
        const pupila1 = pupilaRef.current;
        const ojo2 = ojoRef2.current;
        const pupila2 = pupilaRef2.current;

        // Si no se encuentran los elementos, no se ejecuta la lógica
        if (!ojo1 || !pupila1 || !ojo2 || !pupila2) return;

        let mouseX = 0;
        let mouseY = 0;

        // Actualiza la posición del mouse
        const manejarMovimientoMouse = (e) => {
            mouseX = e.clientX;
            mouseY = e.clientY;
        };

        /**
         * Mueve la pupila dentro del ojo en dirección al cursor
         * @param {HTMLElement} ojo - Elemento del ojo
         * @param {HTMLElement} pupila - Elemento de la pupila
         */
        const actualizarPupila = (ojo, pupila) => {
            if (!ojo || !pupila) return;

            const rect = ojo.getBoundingClientRect();
            const centroOjoX = rect.left + rect.width / 2;
            const centroOjoY = rect.top + rect.height / 2;

            const dx = mouseX - centroOjoX;
            const dy = mouseY - centroOjoY;
            const angulo = Math.atan2(dy, dx);
            const distancia = Math.sqrt(dx * dx + dy * dy);

            const movimientoMaximo = 10;
            const movimiento = Math.min(movimientoMaximo, distancia / 20);

            // Solo mueve la pupila si el ojo está abierto
            if (!ojo.classList.contains("rostro__ojo--cerrado")) {
                const offsetCentro = 13;
                pupila.style.left = offsetCentro + movimiento * Math.cos(angulo) + "px";
                pupila.style.top = offsetCentro + movimiento * Math.sin(angulo) + "px";
            }
        };

        // Actualiza ambos ojos en cada frame de animación
        const actualizarOjos = () => {
            actualizarPupila(ojo1, pupila1);
            actualizarPupila(ojo2, pupila2);
            animacionFrameRef.current = requestAnimationFrame(actualizarOjos);
        };

        // Simula un parpadeo cerrando y abriendo los ojos brevemente
        const parpadear = () => {
            if (ojosCerradosRef.current) return;
            ojo1.classList.add("rostro__ojo--cerrado");
            ojo2.classList.add("rostro__ojo--cerrado");
            setTimeout(() => {
                ojo1.classList.remove("rostro__ojo--cerrado");
                ojo2.classList.remove("rostro__ojo--cerrado");
            }, 150);
        };

        // Intervalo aleatorio para parpadear cada 3 a 5 segundos
        const intervaloParpadeo = setInterval(parpadear, 3000 + Math.random() * 2000);

        // Cierra los ojos al enfocar un campo de contraseña
        const manejarFocoPassword = () => {
            ojosCerradosRef.current = true;
            ojo1.classList.add("rostro__ojo--cerrado");
            ojo2.classList.add("rostro__ojo--cerrado");
        };

        // Abre los ojos al perder el foco del campo de contraseña
        const manejarBlurPassword = () => {
            ojosCerradosRef.current = false;
            ojo1.classList.remove("rostro__ojo--cerrado");
            ojo2.classList.remove("rostro__ojo--cerrado");
        };

        // Listeners para movimiento del mouse y eventos personalizados
        window.addEventListener("mousemove", manejarMovimientoMouse);
        window.addEventListener("passwordFocus", manejarFocoPassword);
        window.addEventListener("passwordBlur", manejarBlurPassword);

        // Inicia la animación de seguimiento ocular
        animacionFrameRef.current = requestAnimationFrame(actualizarOjos);

        // Limpieza al desmontar el componente
        return () => {
            clearInterval(intervaloParpadeo);
            if (animacionFrameRef.current) {
                cancelAnimationFrame(animacionFrameRef.current);
            }
            window.removeEventListener("mousemove", manejarMovimientoMouse);
            window.removeEventListener("passwordFocus", manejarFocoPassword);
            window.removeEventListener("passwordBlur", manejarBlurPassword);
        };
    }, []);

    return (
        <div className={`rostro rostro--${variant} rostro--${color}`}>
            {/* Globo de mensaje si está habilitado */}
            {mostrarMensaje && mensaje && (
                <div className="rostro__globo">
                    <p className="rostro__globo__texto">{mensaje}</p>
                    <div className="rostro__globo__punta"></div>
                </div>
            )}

            {/* Sección de los ojos */}
            <div className="rostro__ojos">
                <div className="rostro__ojo rostro__ojo--izquierdo" ref={ojoRef}>
                    <div className="rostro__pupila" ref={pupilaRef}></div>
                    <div className="rostro__parpado"></div>
                </div>
                <div className="rostro__ojo rostro__ojo--derecho" ref={ojoRef2}>
                    <div className="rostro__pupila" ref={pupilaRef2}></div>
                    <div className="rostro__parpado"></div>
                </div>
            </div>

            {/* Contenido central del rostro */}
            <div className="rostro__content">
                <p className="rostro__content__text">{children}</p>
            </div>
        </div>
    );
};