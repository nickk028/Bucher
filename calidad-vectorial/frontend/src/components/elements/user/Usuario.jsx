import { useFetch, usePost } from "../../utils/FetchUtils" 

export const Usuario = () => {
    const { data : respuestaUsuario, loading : loadingUsuario, error : errorUsuario } = useFetch("usuario/propio");
    const { data : respuestaLogout , error : errorLogout, loading : loadingLogout, execute } = usePost("auth/logout", {});
    
    const handleLogout = () => {
        execute();
    }

    return (
        <div>
            <p>{JSON.stringify(respuestaUsuario, null, 2)}</p>
            <p>{loadingUsuario && "Cargando Usuario..."}</p>
            <p>{errorUsuario}</p>

            <button onClick={handleLogout}>Cerrar sesión</button>
            {loadingLogout && <p>Cerrando sesión...</p>}
            {errorLogout && <p>{errorLogout}</p>}
            {respuestaLogout && <p>{JSON.stringify(respuestaLogout, null, 2)}</p>}
        </div>
    );
}