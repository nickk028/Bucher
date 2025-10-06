import { useEffect, useRef } from "react";
import "./OjosAnimados.css";

// Componente que renderiza tres rostros con ojos animados que siguen el cursor,
// parpadean automáticamente y se cierran al interactuar con el campo de contraseña.
export function OjosAnimados({ mensajeError }) {
    const rostrosRef = useRef([]); // Referencias a los elementos DOM de los rostros
    const ojosInfoRef = useRef([]); // Información de posición de cada ojo

    useEffect(() => {
        let eyesClosed = false; // Estado que indica si los ojos están cerrados
        let mouseX = 0, mouseY = 0, targetX = 0, targetY = 0; // Coordenadas del mouse y destino para animación suave

        // Recalcula las posiciones de los ojos en pantalla
        const recalc = () => {
            ojosInfoRef.current = [];
            rostrosRef.current.forEach(r => {
                if (!r) return;
                const ojos = r.querySelectorAll(".ojo");
                ojos.forEach(ojo => {
                    const rect = ojo.getBoundingClientRect();
                    ojosInfoRef.current.push({
                        el: ojo, // referencia al elemento del ojo
                        centerX: rect.left + rect.width / 2,
                        centerY: rect.top + rect.height / 2,
                    });
                });
            });
        };

        // Actualiza las coordenadas del mouse al moverlo
        const handleMouseMove = (e) => {
            targetX = e.clientX;
            targetY = e.clientY;
        };

        // Mueve las pupilas suavemente hacia el cursor
        const updateEyes = () => {
            mouseX += (targetX - mouseX) * 0.15;
            mouseY += (targetY - mouseY) * 0.15;

            ojosInfoRef.current.forEach(({ el, centerX, centerY }) => {
                const x = mouseX - centerX;
                const y = mouseY - centerY;
                const angle = Math.atan2(y, x);
                const dist = Math.min(6, Math.hypot(x, y) / 10);
                const pupila = el.querySelector(".pupila");
                // Mueve la pupila solo si los ojos no están cerrados
                if (pupila && !el.classList.contains("cerrado")) {
                    pupila.style.left = 8 + dist * Math.cos(angle) + "px";
                    pupila.style.top = 8 + dist * Math.sin(angle) + "px";
                }
            });

            requestAnimationFrame(updateEyes);
        };

        // Función para simular el parpadeo automático
        const blink = () => {
            if (eyesClosed) return;
            ojosInfoRef.current.forEach(({ el }) => {
                el.classList.add("cerrado");
            });

            setTimeout(() => {
                ojosInfoRef.current.forEach(({ el }) => {
                    el.classList.remove("cerrado");
                });
            }, 150);
        };

        // Intervalo para parpadeo cada 3 a 5 segundos
        const blinkInterval = setInterval(blink, 3000 + Math.random() * 2000);

        // Cierra los ojos al enfocar el campo de contraseña
        const handlePasswordFocus = () => {
            recalc(); // recalcula posiciones por si el layout cambió
            eyesClosed = true;
            ojosInfoRef.current.forEach(({ el }) => {
                el.classList.add("cerrado");
            });
        };

        // Abre los ojos al salir del campo de contraseña
        const handlePasswordBlur = () => {
            eyesClosed = false;
            ojosInfoRef.current.forEach(({ el }) => {
                el.classList.remove("cerrado");
            });
        };

        // Listeners para eventos globales
        window.addEventListener("resize", recalc);
        window.addEventListener("mousemove", handleMouseMove);
        window.addEventListener("passwordFocus", handlePasswordFocus);
        window.addEventListener("passwordBlur", handlePasswordBlur);

        // Recalcula posiciones iniciales tras un pequeño delay
        setTimeout(() => {
            recalc();
        }, 100);

        // Inicia animación de seguimiento de ojos
        requestAnimationFrame(updateEyes);

        // Limpieza de listeners e intervalos al desmontar el componente
        return () => {
            window.removeEventListener("mousemove", handleMouseMove);
            window.removeEventListener("resize", recalc);
            window.removeEventListener("passwordFocus", handlePasswordFocus);
            window.removeEventListener("passwordBlur", handlePasswordBlur);
            clearInterval(blinkInterval);
        };
    }, []);

    // Renderiza los tres rostros con sus respectivos ojos
    
  return (
    <div className="rostros">
      {[1, 2, 3].map((i, index) => (
        <div key={i} className={`rostro rostro-${i}`} ref={el => rostrosRef.current[index] = el}>
            <div className="ojos">
                <div className="ojo"><div className="pupila"></div></div>
                <div className="ojo"><div className="pupila"></div></div>
            </div>
          
            {/* Globo de texto si hay error */}  
            {mensajeError && index === 1 && (
            <div className="globo-error">
                {mensajeError}
            </div>
            )}


        </div>
      ))}
    </div>
  );
}
