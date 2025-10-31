import logoUsuario from "../../../assets/img/logos/logoUsuario.png"
import "./UsuarioDetalles.css";

export const UsuarioDetalles = ({ children, nombre }) => {
    return (
        <div className="card-user">
            <div className="card-user__content">
                <h1 className="card-user__content__name">{nombre}</h1>
                <p className="card-user__content__description">{children}</p>
            </div>
            <img src={logoUsuario} alt="Imagen del usuario"/>
        </div>
    )
}