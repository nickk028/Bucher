import { Link } from "react-router-dom";
import "./PublicacionCard.css";

const PublicacionCard = ({ urlFoto, titulo, usuarioNickname, estadoPublicacion, limiteDias }) => {
    return (
        <article className="pub-card">
            <img className="pub-card__image" src={urlFoto} alt="Foto del libro" />
            <ul className="pub-card__list">
                <li className="pub-card__list__item">
                    <h1 className="pub-card__list__item__title">{titulo}</h1>
                </li>
                <li>
                    <span className="pub-card__list__item__subtitle">Usuario: </span>
                    {usuarioNickname}
                </li>
                <li>
                    <span className="pub-card__list__item__subtitle">Estado: </span>
                    {estadoPublicacion}
                </li>
                <li>
                    <span className="pub-card__list__item__subtitle">Límite de días: </span>
                    {limiteDias}
                </li>
            </ul>
        </article>
    )
}
export default PublicacionCard