import './PublicacionCard.css';

const PublicacionCard = ({ urlFoto, titulo, usuarioNickname, estadoPublicacion, limiteDias }) => {
    return (
        <article className='card'>
            <img className='card__image' src={urlFoto} alt="Foto del libro" />
            <ul className='card__list'>
                <li className='card__list__item'>
                    <span className='card__list__item__title'>{titulo}</span>
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