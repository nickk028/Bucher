import { useState, useEffect } from "react";
import { useFetch, usePost } from "../../utils/FetchUtils";
import { Button } from "../buttons/Button";
import './Usuario.css';

export const Usuario = () => {
    const { data: respuestaUsuario, loading: loadingUsuario, error: errorUsuario } = useFetch("usuario/propio");
    const { data: respuestaLogout, error: errorLogout, loading: loadingLogout, execute : executeLogout} = usePost("auth/logout", {});
    //const { data: respuestaDataUsuario, error: errorDataUsuario, loading: loadingDataUsuario, execute: executeDataUsuario } = usePost("URL", {});
    //Cambiar URL del controller

    const [editando, setEditando] = useState(false);
    const [formData, setFormData] = useState({
        pronombres: '',
        descripcion: ''
    });

    useEffect(() => {
        if (respuestaUsuario) {
            setFormData({
                pronombres: respuestaUsuario.pronombres || '',
                descripcion: respuestaUsuario.descripcion || ''
            });
        }
    }, [respuestaUsuario]);

    const handleLogout = () => {
        executeLogout();
    };

    const handleSubmitDataUser = (e) => {
        e.preventDefault();
        if (formData.pronombres !== respuestaUsuario.pronombres || formData.descripcion !== respuestaUsuario.descripcion) {
            //executeDataUsuario();
            console.log("Datos cambiados");
        }
        setEditando(false);
    };

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const avatarUsuario = (respuestaUsuario?.avatar?.toLowerCase() ?? "logoUsuario") + ".png";

    return (
        <aside className="aside-user">
            <div className="aside-user__content">
                <div className="aside-user__content__data">
                    <img className="aside-user__img" src={`/assets/img/avatares/${avatarUsuario}`} alt="Avatar del usuario" />
                    <div className="aside-user__content__data__text">
                        <h1 className="aside-user__content__data__text__username">{respuestaUsuario.username }</h1>
                        <form onSubmit={handleSubmitDataUser}>
                            <div>
                                <label>Pronombres:</label>
                                <input type="text" name="pronombres" value={formData.pronombres} onChange={handleChange} disabled={!editando} />
                            </div>

                            <div>
                                <label className="aside-user__content__data__text__subtitle">Descripción:</label>
                                <input className="aside-user__content__data__text__descripcion" type="text" name="descripcion" value={formData.descripcion} onChange={handleChange} disabled={!editando} />
                            </div>
                            <div className="aside-user__buttons"> 
                                {editando && (
                                    <Button type="submit" variant="default" color="oscuro">
                                        Guardar cambios
                                    </Button>
                                )}
                                {!editando && (
                                    <Button type="button" variant="default" color="oscuro" onClick={() => setEditando(!editando)}>
                                        Editar perfil
                                    </Button>
                                )}
                            </div>
                        </form>
                    </div>
                    <div className="aside-user__buttons">        
                        <Button variant="default" color="oscuro" onClick={handleLogout}>
                            Cerrar sesión
                        </Button>                        
                    </div>                    
                </div>
                <div className="aside-user__barra" />
            </div>  
        </aside>
    );
};