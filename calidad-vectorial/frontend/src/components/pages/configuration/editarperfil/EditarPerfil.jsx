import { useEffect, useState } from "react";
import { Input } from "../../../elements/input/Input";
import { Button } from "../../../elements/buttons/Button";
import { useFetch, usePost } from "../../../utils/FetchUtils";
import cenicienta from "../../../../../public/assets/img/avatares/cenicienta.png";
import dorothy from "../../../../../public/assets/img/avatares/dorothy.png";
import detective from "../../../../../public/assets/img/avatares/detective.png";
import katniss from "../../../../../public/assets/img/avatares/katniss.png";
import gandalf from "../../../../../public/assets/img/avatares/gandalf.png";
import dracula from "../../../../../public/assets/img/avatares/dracula.png";
import "./EditarPerfil.css";

export const EditarPerfil = () => {
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
            <div>
                <h1 className="config-content__title">Editar perfil</h1>
                <p>Modifica tu usuario a tu gusto</p>
            </div>
            <form className="config-content__form" onSubmit={handleSubmitDataUser}>
                <h2>Tu usuario</h2>
                <div >
                    <label className={`config-content__form__label config-content__form__label--${editando ? "editando" : ""}`}>Correo electrónico</label>
                    <Input type="text" name="username"onChange={handleChange} disabled={!editando} required={false}/>
                </div>

                <div className="config-content__form__apodo">
                    <label className={`config-content__form__label config-content__form__label--${editando ? "editando" : ""}`}>Apodo</label>
                    <Input type="text" name="nickname" onChange={handleChange} disabled={!editando} required={false}/>
                </div>

                <div className="config-content__form__pronombres">
                    <label className={`config-content__form__label config-content__form__label--${editando ? "editando" : ""}`}>Pronombres</label>
                    <Input type="text" name="pronombres" onChange={handleChange} disabled={!editando} required={false}/>
                </div>

                <div>
                    <label className={`config-content__form__label config-content__form__label--${editando ? "editando" : ""}`}>Descripción</label>
                    <Input variant="medio" type="text" name="descripcion" value={formData.descripcion} onChange={handleChange} disabled={!editando} required={false}/>
                </div>

                <div>
                    <label className={`config-content__form__label config-content__form__label--${editando ? "editando" : ""}`}>Avatar</label>
                    <div className="carrusel">
                        <img src={dorothy} alt="" />
                        <img src={cenicienta} alt="" />
                        <img src={detective} alt="" />
                        <img src={gandalf} alt="" />
                        <img src={dracula} alt="" />
                        <img src={katniss} alt="" />
                    </div>
                </div>
            </form>

            <form className="config-content__form" onSubmit={handleSubmitDataUser}>
                <h2>Tu dirección</h2>
                <div>
                    <label className={`config-content__form__label config-content__form__label--${editando ? "editando" : ""}`}>Calle</label>
                    <Input type="text" name="direccion" value={formData.direccion} onChange={handleChange} disabled={!editando} required={false}/>
                </div>

                <div className="contenedor">
                    <div>
                        <label className={`config-content__form__label config-content__form__label--${editando ? "editando" : ""}`}>Altura</label>
                        <Input type="text" name="altura" onChange={handleChange} disabled={!editando} required={false}/>
                    </div>

                    <div>
                        <label className={`config-content__form__label config-content__form__label--${editando ? "editando" : ""}`}>Piso</label>
                        <Input type="text" name="piso" onChange={handleChange} disabled={!editando} required={false}/>
                    </div>

                    <div>
                        <label className={`config-content__form__label config-content__form__label--${editando ? "editando" : ""}`}>Departamento</label>
                        <Input type="text" name="departamento" onChange={handleChange} disabled={!editando} required={false}/>
                    </div>
                </div>
                

                <div className="contenedor-2">
                    <div>
                        <label className={`config-content__form__label config-content__form__label--${editando ? "editando" : ""}`}>Altura</label>
                        <Input type="text" name="altura" onChange={handleChange} disabled={!editando} required={false}/>
                    </div>

                    <div>
                        <label className={`config-content__form__label config-content__form__label--${editando ? "editando" : ""}`}>Piso</label>
                        <Input type="text" name="piso" onChange={handleChange} disabled={!editando} required={false}/>
                    </div>
                </div>

            </form>

                {/*<div>
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
                </div>*/}
        </div>
    );
};