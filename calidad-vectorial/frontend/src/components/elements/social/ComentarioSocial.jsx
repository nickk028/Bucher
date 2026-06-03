import "./ComentarioSocial.css";

export const ComentarioSocial = ({children, nickname, urlFoto, tiempoPublicacion}) => {
    return (
        <article className="com-social">
            <div className="com-social__img">
                <img src={urlFoto} alt={`Foto del usuario ${nickname}`} />
            </div>
            <div className="com-social__content">
                <h1>{nickname}</h1>
                <p>{children}</p>
                <p className="com-social__content__tiempo">Hace {tiempoPublicacion} horas</p>
            </div>
        </article>
    )
}