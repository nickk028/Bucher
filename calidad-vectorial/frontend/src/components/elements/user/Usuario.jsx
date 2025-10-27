import { useState, useEffect } from "react";
import { useFetch, usePost } from "../../utils/FetchUtils";
import "./Usuario.css";
import { Button } from "../buttons/Button";
import { Input } from "../input/Input";

export const Usuario = () => {
    const { data: respuestaUsuario, loading: loadingUsuario, error: errorUsuario } = useFetch("usuario/propio");
    const { data: respuestaLogout, error: errorLogout, loading: loadingLogout, execute : executeLogout} = usePost("auth/logout");
    //const { data: respuestaDataUsuario, error: errorDataUsuario, loading: loadingDataUsuario, execute: executeDataUsuario } = usePost("URL", "PUT");
    //Cambiar URL del controller

    const [editando, setEditando] = useState(false);
    const [formData, setFormData] = useState({
        pronombres: "",
        descripcion: ""
    });

    useEffect(() => {
        if (respuestaUsuario) {
            setFormData({
                pronombres: respuestaUsuario.pronombres || "",
                descripcion: respuestaUsuario.descripcion || ""
            });
        }
    }, [respuestaUsuario]);

    const handleLogout = async () => {
        await executeLogout();
    };

    const handleSubmitDataUser = (e) => {
        e.preventDefault();
        if (formData.pronombres !== respuestaUsuario.pronombres || formData.descripcion !== respuestaUsuario.descripcion) {
            //await executeDataUsuario(); (agregar async)
            console.log("Datos cambiados");
        }
        setEditando(false);
    };

    const handleChange = (e) => {
        const { name, value, type } = e.target;
        if (type === "number") {
            setFormData({ ...formData, [name]: value === "" ? "" : Number(value) });
        } else {
            setFormData({ ...formData, [name]: value });
        }
    };

    const avatarUsuario = (respuestaUsuario?.avatar?.toLowerCase() ?? "logoUsuario") + ".png";

    return (
        <aside className="aside-user">
            <div className="aside-user__content">
                <div className="aside-user__content__data">
                    <img className="aside-user__img" src={`/assets/img/avatares/${avatarUsuario}`} alt="Avatar del usuario" />
                    <form className="aside-user__content__data__text" onSubmit={handleSubmitDataUser}>

                        <h1 className="aside-user__content__data__text__username">{respuestaUsuario.username }</h1>

                        <div>
                            <label className={`aside-user__content__data__text__subtitle aside-user__content__data__text__subtitle--${editando ? "editando" : ""}`}>Pronombres:</label>
                            <Input type="text" name="pronombres" value={formData.pronombres} onChange={handleChange} disabled={!editando} required={false}/>
                        </div>

                        <div>
                            <label className={`aside-user__content__data__text__subtitle aside-user__content__data__text__subtitle--${editando ? "editando" : ""}`}>Descripción:</label>
                            <Input className="aside-user__content__data__text__descripcion" variant="medio" type="text" name="descripcion" value={formData.descripcion} onChange={handleChange} disabled={!editando} required={false}/>
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

                        <Button variant="default" color="rojo" onClick={handleLogout}>
                            Cerrar sesión
                        </Button>
                    </form>
                </div>
                <div className="aside-user__barra" />
            </div>
        </aside>
    );
};