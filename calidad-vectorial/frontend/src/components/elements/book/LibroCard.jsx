import "./LibroCard.css";
import Estrella from "../../../assets/img/icons/utils/estrella.svg?react";

export const LibroCard = ({urlFoto, titulo, estrllas=false, clasificacion, autor}) => {
    return (
        <article className="libro-card">
            <div className="libro-card__portada-libro">
                <div className={`libro-card__portada-libro__triangulo libro-card__portada-libro__triangulo--${clasificacion}`}/>
                <img src={urlFoto} alt={`Portada del libro ${titulo}`}/>
            </div>
            <h2 className="libro-card__titulo">{titulo}</h2>
            {(autor && (
                <p className="libro-card__titulo__autor">{autor}</p>
            ))}
            {(estrllas && (
                <ul className="libro-card__estrellas">
                    <li><Estrella /></li>
                    <li><Estrella /></li>
                    <li><Estrella /></li>
                    <li><Estrella /></li>
                    <li><Estrella /></li>
                </ul>
            ))}
        </article>
    )
};