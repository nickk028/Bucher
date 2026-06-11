import "./ToS.css";
import { SECCIONES } from "../../../assets/text/ToSText";

export const ToS = () => {
    return (
        <div className="tos-content">
            <div>
                <h1 className="tos-content__title">Términos y servicios</h1>
                <p className="tos-content__subtitle">
                    Leé con atención las condiciones que rigen el uso de Bücher.
                </p>
            </div>

            <div className="tos-content__body">
                {SECCIONES.map((seccion) => (
                    <section key={seccion.id} className="tos-section">
                        <div className="tos-section__heading">
                            <span className="tos-section__number">{seccion.id}</span>
                            <h2 className="tos-section__title">{seccion.titulo}</h2>
                        </div>
                        <div className="tos-section__divider" />
                        <p className="tos-section__text">{seccion.contenido}</p>
                    </section>
                ))}
            </div>

            <footer className="tos-footer">
                <div className="tos-footer__inner">
                    <p className="tos-footer__notice">
                        Al realizar un préstamo o crear una publicación dentro de la plataforma,{" "}
                        <strong>aceptás automáticamente estos Términos y Servicios</strong> en su
                        versión vigente al momento de la acción.
                    </p>
                </div>
            </footer>
        </div>
    );
};