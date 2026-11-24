import './UsuarioDetalleCard.css';

export const UsuarioDetalleCard = ({titulo, foto, nickname, username}) => {
    return (
        <article className='usuario-detalle-card'>
            <h3 className='usuario-detalle-card__titulo'>{titulo}</h3>
            <div className='usuario-detalle-card__content'>
                <div className='usuario-detalle-card__content__aside'>
                    <img className='usuario-detalle-card__content__aside__img' src={foto} alt='Imagen del usuario' />
                </div>
                <div className='usuario-detalle-card__content__text'>
                    <p className='usuario-detalle-card__content__text__nickname'>@{nickname}</p>
                    <div className='usuario-detalle-card__content__text__data'>
                        <span className='usuario-detalle-card__content__text__data__label'>Correo</span>
                        <p className='usuario-detalle-card__content__text__data__p'>{username}</p>
                    </div>
                </div>
            </div>
        </article>
    );
};