import { useState } from "react";
import "./Apariencia.css";
import { getConfig, setConfig } from "../../../utils/ConfigUtils";

const COLORES_BUCHY = [
    { id: "dorado",   dark: "#B8860B", light: "#FFCC33" },
    { id: "azul",     dark: "#3C49A6", light: "#5B6DF6" },
    { id: "rojo",     dark: "#A40F0F", light: "#DB1515" },
    { id: "amarillo", dark: "#9F9F2E", light: "#F3F346" },
];

export const Apariencia = () => {
    const [config, setLocalConfig] = useState(() => getConfig());

    const update = (key, value) => {
        const next = setConfig({ [key]: value });
        setLocalConfig(next);
    };

    return (
        <main className="apariencia">
            <header className="apariencia__header">
                <h1>Apariencia</h1>
                <p>Personaliza tu experiencia en nuestro sitio.</p>
            </header>

            {/* ---- Büchy ---- */}
            <section className="apariencia__form">
                <h2>Büchy</h2>

                <div className="apariencia__form__group">
                    <label className="apariencia__form__label">Visibilidad</label>
                    <div className="apariencia__form__container">
                        <label className="apariencia__checkbox-label">
                            <input
                                type="checkbox"
                                className="apariencia__checkbox"
                                checked={!!config.buchy}
                                onChange={(e) => update("buchy", e.target.checked)}
                            />
                            <span className="apariencia__checkbox-custom" />
                            Mostrar a Büchy
                        </label>
                        <label className="apariencia__checkbox-label">
                            <input
                                type="checkbox"
                                className="apariencia__checkbox"
                                checked={!!config.consejos}
                                onChange={(e) => update("consejos", e.target.checked)}
                            />
                            <span className="apariencia__checkbox-custom" />
                            Mostrar consejos y ayuda
                        </label>
                    </div>
                </div>

                <div className="apariencia__form__group">
                    <label className="apariencia__form__label">Color de la mascota</label>
                    <div className="apariencia__form__container apariencia__form__container--colors">
                        {COLORES_BUCHY.map((c) => (
                            <button
                                key={c.id}
                                type="button"
                                className={`apariencia__color-btn ${config.colorBuchy === c.id ? "apariencia__color-btn--active" : ""}`}
                                onClick={() => update("colorBuchy", c.id)}
                                aria-label={`Color ${c.id}`}
                            >
                                <span
                                    className="apariencia__color-circle apariencia__color-circle--back"
                                    style={{ background: c.light }}
                                />
                                <span
                                    className="apariencia__color-circle apariencia__color-circle--front"
                                    style={{ background: c.dark }}
                                />
                            </button>
                        ))}
                    </div>
                </div>
            </section>

            {/* ---- Tema ---- */}
            <section className="apariencia__form">
                <h2>Tema</h2>
                    <label className="apariencia__checkbox-label">
                        <input
                            type="radio"
                            name="tema"
                            className="apariencia__checkbox"
                            checked={config.tema === "claro" || !config.tema}
                            onChange={() => update("tema", "claro")}
                        />
                        <span className="apariencia__checkbox-custom" />
                        Claro
                    </label>
                    <label className="apariencia__checkbox-label">
                        <input
                            type="radio"
                            name="tema"
                            className="apariencia__checkbox"
                            checked={config.tema === "oscuro"}
                            onChange={() => update("tema", "oscuro")}
                        />
                        <span className="apariencia__checkbox-custom" />
                        Oscuro
                    </label>
            </section>
        </main>
    );
};
