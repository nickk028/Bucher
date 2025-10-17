import { useFetch, usePost } from "../../utils/FetchUtils"
import { Button } from "../buttons/Button";
import './Usuario.css';

export const Usuario = () => {
    const { data : respuestaUsuario, loading : loadingUsuario, error : errorUsuario } = useFetch("usuario/propio");
    const { data : respuestaLogout , error : errorLogout, loading : loadingLogout, execute } = usePost("auth/logout", {});
    
    const handleLogout = () => {
        execute();
    }

    const avatarUsuario = respuestaUsuario?.avatar?.toLowerCase() + ".png";

    return (
        <aside className="aside-user">
            <img src={`/assets/img/avatares/${avatarUsuario}`} alt="" />

            <h1>{respuestaUsuario.username}</h1>

            <p>{respuestaUsuario.pronombres}</p>

            <p>{respuestaUsuario.descripcion}</p>

            <div className="aside-user__buttons">
            <Button variant="default" color="oscuro">Editar perfil</Button>

            <Button variant="default" color="rojo">Cerrar sesión</Button>
            </div>
        </aside>

        
    );
    {/*
        <div>
            <p>{JSON.stringify(respuestaUsuario, null, 2)}</p>
            <p>{loadingUsuario && "Cargando Usuario..."}</p>
            <p>{errorUsuario}</p>

            <button onClick={handleLogout}>Cerrar sesión</button>
            {loadingLogout && <p>Cerrando sesión...</p>}
            {errorLogout && <p>{errorLogout}</p>}
            {respuestaLogout && <p>{JSON.stringify(respuestaLogout, null, 2)}</p>}
        </div>*/}
};