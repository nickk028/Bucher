import "./SideBarCard.css";

export const SideBarCard = ({titulo, texto, img}) => {
    return (
        <div className="side-bar-card">
            <div className="side-bar-card__encabezado">
                <div className="side-bar-card__encabezado__img">
                    {img}
                </div>
                <h2>{titulo}</h2>
            </div>
            <p>{texto}</p>
        </div>
    )
}
