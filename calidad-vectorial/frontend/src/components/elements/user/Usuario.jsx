import { useFetch, usePost } from "../../utils/FetchUtils";
import "./Usuario.css";
import { Button } from "../buttons/Button";
import { Input } from "../input/Input";

export const Usuario = () => {
    const { data: respuestaUsuario, loading: loadingUsuario, error: errorUsuario } = useFetch("usuario/propio");
    const { data: respuestaLogout, error: errorLogout, loading: loadingLogout, execute : executeLogout} = usePost("auth/logout");

    const handleLogout = async () => {
        await executeLogout();
    };

    const avatarUsuario = (respuestaUsuario?.avatar?.toLowerCase() ?? "logoUsuario") + ".png";

    return (
        <aside className="aside-user">
            <div className="aside-user__content">
                <div className="aside-user__content__data">
                    <img className="aside-user__img" src={`/assets/img/avatares/${avatarUsuario}`} alt="Avatar del usuario" />
            
                    <h1 className="aside-user__content__data__text__username">{respuestaUsuario.username }</h1>

                    <div>
                        <label className={`aside-user__content__data__text__subtitle aside-user__content__data__text__subtitle--`}>Pronombres:</label>
                        <Input type="text" name="pronombres" value={respuestaUsuario.pronombres} disabled={true} required={false}/>
                    </div>

                    <div>
                        <label className={`aside-user__content__data__text__subtitle aside-user__content__data__text__subtitle--`}>Descripción:</label>
                        <Input className="aside-user__content__data__text__descripcion" variant="medio" type="text" name="descripcion" value={respuestaUsuario.descripcion} disabled={true} required={false}/>
                    </div>

                    <Button variant="default" color="rojo" onClick={handleLogout}>
                        Cerrar sesión
                    </Button>
                </div>
                <div className="aside-user__barra" />
            </div>
        </aside>
    );
};