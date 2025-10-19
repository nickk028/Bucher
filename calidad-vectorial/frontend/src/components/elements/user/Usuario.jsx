import { useFetch, usePost } from "../../utils/FetchUtils"
import { Button } from "../buttons/Button";
import './Usuario.css';

export const Usuario = () => {
    const { data : respuestaUsuario, loading : loadingUsuario, error : errorUsuario } = useFetch("usuario/propio");
    const { data : respuestaLogout , error : errorLogout, loading : loadingLogout, execute } = usePost("auth/logout", {});
    
    const handleLogout = () => {
        execute();
    }

    const avatarUsuario = (respuestaUsuario?.avatar?.toLowerCase() || "logoUsuario") + ".png";

    return (
        <aside className="aside-user">
            <div className="aside-user__content">
                <div className="aside-user__content__data">
                    <img className="aside-user__img" src={`/assets/img/avatares/${avatarUsuario}`} alt="Avatar del usuario" />

                    <div className="aside-user__content__data__text">
                        <h1 className="aside-user__content__data__text__username">{respuestaUsuario.username}</h1>

                        <p>{respuestaUsuario.pronombres}Pronombres</p>

                        <div>
                            <span className="aside-user__content__data__text__subtitle">Descripción</span>
                            <p className="aside-user__content__data__text__descripcion">{respuestaUsuario.descripcion} Descripción</p>
                        </div>
                        
                    </div>

                    <div className="aside-user__buttons">
                        <Button variant="default" color="oscuro">Editar perfil</Button>

                        <Button variant="default" color="rojo" onClick={handleLogout}>Cerrar sesión</Button>
                    </div>
                </div>

                <div className="aside-user__barra" />
            </div>
        </aside>
    );
};