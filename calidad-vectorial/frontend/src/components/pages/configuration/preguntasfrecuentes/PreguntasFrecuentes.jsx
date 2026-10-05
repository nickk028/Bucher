import { useState } from 'react';
import { categorias } from '../../../data/preguntasFrecuentesText';
import IconoflechaDesplegar from '../../../../assets/img/icons/preguntasfrecuentes/flechaDesplegar.svg?react';
import './PreguntasFrecuentes.css';

export const PreguntasFrecuentes = () => {
    const [preguntaAbierta, setPreguntaAbierta] = useState(null);

    const handleToggle = (id) => {
        setPreguntaAbierta((actual) => (actual === id ? null : id));
    };

    return (
        <div className="preguntas-content">
            <div>
                <h1 className="preguntas-content__title">Preguntas frecuentes</h1>
                <p>Encuentra respuestas a las dudas más comunes sobre Bücher.</p>
            </div>

            {categorias.map(({ id, titulo, icono: Icono, preguntas }) => (
                <section key={id} className="preguntas-content__categoria">
                    <div className="preguntas-content__categoria__header">
                        <Icono className="preguntas-content__icono" aria-hidden="true" />
                        <h2>{titulo}</h2>
                    </div>

                    <ul className="preguntas-content__lista">
                        {preguntas.map((item) => {
                            const abierta = preguntaAbierta === item.id;

                            return (
                                <li key={item.id} className="preguntas-content__item">
                                    <button
                                        type="button"
                                        id={`pregunta-${item.id}`}
                                        className="preguntas-content__pregunta"
                                        aria-expanded={abierta}
                                        aria-controls={`respuesta-${item.id}`}
                                        onClick={() => handleToggle(item.id)}
                                    >
                                        <span>{item.pregunta}</span>
                                        <IconoflechaDesplegar
                                            className={`preguntas-content__flecha preguntas-content__flecha--${abierta ? 'abierto' : 'cerrado'}`}
                                            aria-hidden="true"
                                        />
                                    </button>

                                    <div
                                        id={`respuesta-${item.id}`}
                                        role="region"
                                        aria-labelledby={`pregunta-${item.id}`}
                                        className={`preguntas-content__respuesta preguntas-content__respuesta--${abierta ? 'abierta' : 'cerrada'}`}
                                    >
                                        <div className="preguntas-content__respuesta__contenido">
                                            <p>{item.respuesta}</p>
                                        </div>
                                    </div>
                                </li>
                            );
                        })}
                    </ul>
                </section>
            ))}
        </div>
    );
};
