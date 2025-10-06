import { useEffect, useRef } from "react";
import "./OjosAnimado.css";

export const OjosAnimado = ({ children, variant="medio", color, mensaje, mostrarMensaje }) => {
    const ojoRef = useRef(null);
    const pupilaRef = useRef(null);
    const ojoRef2 = useRef(null);
    const pupilaRef2 = useRef(null);
    const ojosCerradosRef = useRef(false);
    const animacionFrameRef = useRef(null);

    useEffect(() => {
        const ojo1 = ojoRef.current;
        const pupila1 = pupilaRef.current;
        const ojo2 = ojoRef2.current;
        const pupila2 = pupilaRef2.current;
        
        if (!ojo1 || !pupila1 || !ojo2 || !pupila2) return;

        let mouseX = 0;
        let mouseY = 0;

        const manejarMovimientoMouse = (e) => {
            mouseX = e.clientX;
            mouseY = e.clientY;
        };

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

            if (!ojo.classList.contains("rostro__ojo--cerrado")) {
                const offsetCentro = 13;
                pupila.style.left = offsetCentro + movimiento * Math.cos(angulo) + "px";
                pupila.style.top = offsetCentro + movimiento * Math.sin(angulo) + "px";
            }
        };

        const actualizarOjos = () => {
            actualizarPupila(ojo1, pupila1);
            actualizarPupila(ojo2, pupila2);
            animacionFrameRef.current = requestAnimationFrame(actualizarOjos);
        };

        const parpadear = () => {
            if (ojosCerradosRef.current) return;
            if (ojo1) ojo1.classList.add("rostro__ojo--cerrado");
            if (ojo2) ojo2.classList.add("rostro__ojo--cerrado");
            setTimeout(() => {
                if (ojo1) ojo1.classList.remove("rostro__ojo--cerrado");
                if (ojo2) ojo2.classList.remove("rostro__ojo--cerrado");
            }, 150);
        };

        const intervaloParpadeo = setInterval(parpadear, 3000 + Math.random() * 2000);

        const manejarFocoPassword = () => {
            ojosCerradosRef.current = true;
            if (ojo1) ojo1.classList.add("rostro__ojo--cerrado");
            if (ojo2) ojo2.classList.add("rostro__ojo--cerrado");
        };

        const manejarBlurPassword = () => {
            ojosCerradosRef.current = false;
            if (ojo1) ojo1.classList.remove("rostro__ojo--cerrado");
            if (ojo2) ojo2.classList.remove("rostro__ojo--cerrado");
        };

        window.addEventListener("mousemove", manejarMovimientoMouse);
        window.addEventListener("passwordFocus", manejarFocoPassword);
        window.addEventListener("passwordBlur", manejarBlurPassword);

        animacionFrameRef.current = requestAnimationFrame(actualizarOjos);

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
            {mostrarMensaje && mensaje && (
                <div className="rostro__globo">
                    <p className="rostro__globo__texto">{mensaje}</p>
                    <div className="rostro__globo__punta"></div>
                </div>
            )}
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
            <div className="rostro__content">
                <p className="rostro__content__text">{children}</p>
            </div>
        </div>
    );
}