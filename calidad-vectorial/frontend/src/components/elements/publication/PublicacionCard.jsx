import "./PublicacionCard.css";

const PublicacionCard = ({ urlFoto, titulo, usuarioNickname, estadoPublicacion, limiteDias }) => {
    return (
        <article className="pub-card">
            <div className="pub-card__image">
                <img src={urlFoto} alt="Foto del libro" />
            </div>
            <h2 className="pub-card__title">{titulo} asdsadasdasdas dasdasda</h2>
            <div className="pub-card__content">
                <p>{usuarioNickname}</p>
                <p>{limiteDias} días</p>
            </div>
        </article>
    )
}
export default PublicacionCard