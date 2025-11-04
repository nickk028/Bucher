import { useEffect, useState } from "react";
import { Input } from "../input/Input";
import { Button } from "../buttons/Button";
import { useFetch, usePost } from "../../utils/FetchUtils";
import "./Configuracion.css";

export const ConfiguracionDataUsuario = () => {
    const { data: respuestaUsuario, loading: loadingUsuario, error: errorUsuario } = useFetch("usuario/propio");
    const { data: respuestaDataUsuario, error: errorDataUsuario, loading: loadingDataUsuario, execute: executeDataUsuario } = usePost("usuario/modificar", "PUT");
    
    const [editando, setEditando] = useState(false);
    const [formData, setFormData] = useState({
        avatar : "",
        pronombres: "",
        descripcion: "",
        nickname: "",
        direccion: "",
        piso: "",
        codigoPostal: "",
    });

    useEffect(() => {
        if (respuestaUsuario) {
            setFormData({
                avatar : respuestaUsuario.avatar,
                pronombres: respuestaUsuario.pronombres ?? "",
                descripcion: respuestaUsuario.descripcion ?? "",
                nickname: respuestaUsuario.nickname ?? "",
                direccion: respuestaUsuario.direccion ?? "",
                piso: respuestaUsuario.piso ?? "",
                codigoPostal: respuestaUsuario.codigoPostal ?? ""
            });
        }
    }, [respuestaUsuario]);

    const  handleSubmitDataUser = async (e) => {
        e.preventDefault();
        if (!(formData in respuestaUsuario)) {
            await executeDataUsuario(formData);
            console.log(respuestaUsuario);
            console.log(formData);
            console.log(respuestaDataUsuario);

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

    return (
        <div className="config-content">
            <h1 className="config-content__title">Configuración del perfil</h1>
            <form className="config-content__form" onSubmit={handleSubmitDataUser}>
                <div>
                    <label className={`config-content__form__label config-content__form__label--${editando ? "editando" : ""}`}>Nickname</label>
                    <Input type="text" name="nickname" value={formData.nickname} onChange={handleChange} disabled={!editando} required={false}/>
                </div>

                <div>
                    <label className={`config-content__form__label config-content__form__label--${editando ? "editando" : ""}`}>Dirección</label>
                    <Input type="text" name="direccion" value={formData.direccion} onChange={handleChange} disabled={!editando} required={false}/>
                </div>

                <div>
                    <label className={`config-content__form__label config-content__form__label--${editando ? "editando" : ""}`}>Piso</label>
                    <Input type="text" name="piso" value={formData.piso} onChange={handleChange} disabled={!editando} required={false}/>
                </div>

                <div>
                    <label className={`config-content__form__label config-content__form__label--${editando ? "editando" : ""}`}>Código postal</label>
                    <Input type="text" name="codigoPostal" value={formData.codigoPostal} onChange={handleChange} disabled={!editando} required={false}/>
                </div>

                <div>
                    <label className={`config-content__form__label config-content__form__label--${editando ? "editando" : ""}`}>Pronombres</label>
                    <Input type="text" name="pronombres" value={formData.pronombres} onChange={handleChange} disabled={!editando} required={false}/>
                </div>

                <div>
                    <label className={`config-content__form__label config-content__form__label--${editando ? "editando" : ""}`}>Descripción</label>
                    <Input variant="medio" type="text" name="descripcion" value={formData.descripcion} onChange={handleChange} disabled={!editando} required={false}/>
                </div>

                <div>
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
    );
};