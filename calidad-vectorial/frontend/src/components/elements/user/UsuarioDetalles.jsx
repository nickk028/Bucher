import logoUsuario from "../../../assets/img/logos/logoUsuario.png"
import "./UsuarioDetalles.css";

export const UsuarioDetalles = ({ children, nombre, foto }) => {
    return (
        <div className="card-user">
            {foto ? (
                <img className="card-user__foto" src={foto} alt="Imagen del usuario"/>
            ) : (
                <img className="card-user__foto" src={logoUsuario} alt="Imagen del usuario"/>
            )}
            <div className="card-user__content">
                <h1 className="card-user__content__name">{nombre}</h1>
                <p className="card-user__content__description">{children}</p>
            </div>
        </div>
    );
};