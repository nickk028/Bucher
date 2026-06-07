import "./LibroCard.css";
import Estrella from "../../../assets/img/icons/utils/estrella.svg?react";

export const LibroCard = ({urlFoto, titulo}) => {
    return (
        <div className="libro-card">
            <img className="libro-card__portada-libro" src={urlFoto} alt={`Portada del libro ${titulo}`}/>
            <h2 className="libro-card__titulo">{titulo}</h2>
            <ul className="libro-card__estrellas">
                <li><Estrella /></li>
                <li><Estrella /></li>
                <li><Estrella /></li>
                <li><Estrella /></li>
                <li><Estrella /></li>
            </ul>
        </div>
    )
};