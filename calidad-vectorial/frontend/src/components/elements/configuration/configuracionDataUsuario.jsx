import { useEffect, useState } from "react";
import { Input } from "../input/Input";
import { Button } from "../buttons/Button";
import { useFetch, usePost } from "../../utils/FetchUtils";

export const ConfiguracionDataUsuario = () => {
    const { data: respuestaUsuario, loading: loadingUsuario, error: errorUsuario } = useFetch("usuario/propio");
    const { data: respuestaDataUsuario, error: errorDataUsuario, loading: loadingDataUsuario, execute: executeDataUsuario } = usePost("usuario/modificar", "PUT");
    
    const [editando, setEditando] = useState(false);
    const [formData, setFormData] = useState({
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
                pronombres: respuestaUsuario.pronombres || "",
                descripcion: respuestaUsuario.descripcion || "",
                nickname: "",
                direccion: "",
                piso: "",
                codigoPostal: ""
            });
        }
    }, [respuestaUsuario]);

    const  handleSubmitDataUser = async (e) => {
        e.preventDefault();
        if (formData.pronombres !== respuestaUsuario.pronombres || formData.descripcion !== respuestaUsuario.descripcion) {
            await executeDataUsuario({avatar : null, pronombres: formData.pronombres, descripcion: formData.descripcion});
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
        <div>
            <div className="">
                <h1>Configuracion del perfil</h1>
                <form onSubmit={handleSubmitDataUser}>
                    <div>
                        <label>Nickname:</label>
                        <Input type="text" name="nickname" value={formData.nickname} onChange={handleChange} disabled={!editando} required={false}/>                        
                    </div>

                    <div>
                        <label>Direccion:</label>
                        <Input type="text" name="direccion" value={formData.direccion} onChange={handleChange} disabled={!editando} required={false}/>                        
                    </div>

                    <div>
                        <label>Piso:</label>
                        <Input type="text" name="piso" value={formData.piso} onChange={handleChange} disabled={!editando} required={false}/>                        
                    </div>

                    <div>
                        <label>Codigo Postal:</label>
                        <Input type="text" name="codigoPostal" value={formData.codigoPostal} onChange={handleChange} disabled={!editando} required={false}/>                        
                    </div>

                    <div>
                        <label>Pronombres:</label>
                        <Input type="text" name="pronombres" value={formData.pronombres} onChange={handleChange} disabled={!editando} required={false}/>
                    </div>

                    <div>
                        <label>Descripción:</label>
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
        </div>
    );
};