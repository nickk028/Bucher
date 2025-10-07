import { Link } from 'react-router-dom';
import './PublicacionCard.css';
import '../../elements/global.css';

const PublicacionCard = ({ urlFoto, titulo, usuarioNickname, estadoPublicacion, limiteDias }) => {
    return (
        <article className='card'>
            <img className='card__image' src={urlFoto} alt="Foto del libro" />
            <ul className='card__list'>
                <li className='card__list__item'>
                    <h1 className='card__list__item__title'>{titulo}</h1>
                </li>
                <li>
                    <span className='card__list__item__subtitle'>Usuario: </span>
                    {usuarioNickname}
                </li>
                <li>
                    <span className='card__list__item__subtitle'>Estado: </span>
                    {estadoPublicacion}
                </li>
                <li>
                    <span className='card__list__item__subtitle'>Límite de días: </span>
                    {limiteDias}
                </li>
            </ul>
        </article>
    )
}
export default PublicacionCard