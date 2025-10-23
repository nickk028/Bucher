import { Link, useParams } from "react-router-dom";
import { useFetch, usePost } from "../../../../utils/FetchUtils"
import { useEffect, useState } from "react";
import { Input } from "../../../../elements/input/Input";
import { Button } from "../../../../elements/buttons/Button";

export const LibroUsuario = () => {
    const { posicion } = useParams()
    const [editando, setEditando] = useState(false);
    const { data : getBookUser, loading : loadingGetBookUser, error : ErrorGetBookUser } = useFetch("biblioteca/" + posicion);
    const [formData, setFormData] = useState({
        paginaActual: 0,
        estadoLectura:  "",
        puntuacion: 0
    });
    const { data : putBookUser, loading : loadingPutBookUser, error : ErrorPutBookUser, execute } = usePost("biblioteca/" + posicion, "PUT" );
    
    useEffect(() => {
        if (getBookUser) {
            setFormData({
                id: getBookUser.id, 
                paginaActual: getBookUser.paginaActual || 0,
                estadoLectura: getBookUser.estadoLectura || "",
                puntuacion: getBookUser.puntuacion || 0
            });
        }
    }, [getBookUser]);

    const handleChange = (e) => {
        const { name, value, type } = e.target;
        if (type === "number") {
            setFormData({ ...formData, [name]: value === "" ? "" : Number(value) });
        } else {
            setFormData({ ...formData, [name]: value });
        }
    }

    const handleSubmitDataBookUser = async (e) => {
        e.preventDefault();
        
        if (formData.paginaActual !== getBookUser.paginaActual || formData.estadoLectura !== getBookUser.estadoLectura || formData.puntuacion !== getBookUser.puntuacion) {
            await execute(formData);
            console.log("Datos cambiados");
            console.log("data: ", formData);
            console.log("BookUser: ", getBookUser);
            
        } 
        
        setEditando(false);
    };

    return (
        <div>
            {loadingGetBookUser ? (
                <p>Cargando Libro Usuario...</p>
            ) : getBookUser ? (
                    <form onSubmit = {handleSubmitDataBookUser}>
                        <h1> Titulo: {getBookUser.titulo} </h1>
                        <div>
                            <label>Pagina actual:</label>
                            <Input type="number" name="paginaActual" value={formData.paginaActual} onChange={handleChange} disabled={!editando} required={false}/>
                        </div>
                        <div>
                            <label>Estado Lectura:</label>
                            <Input type="text" name="estadoLectura" value={formData.estadoLectura} onChange={handleChange} disabled={!editando} required={false}/>
                        </div>
                        <div>
                            <label>Puntuacion:</label>
                            <Input type="number" name="puntuacion" value={formData.puntuacion} onChange={handleChange} disabled={!editando} required={false}/>
                        </div>
                    
                        { editando && (
                            <Button type="submit" variant="default" color="oscuro">
                                Guardar cambios
                            </Button>
                        )} 
                        { !editando && (
                            <Button type="button" variant="default" color="oscuro" onClick={() => setEditando(!editando)}>
                                    Actualizar avances
                            </Button>
                        )}
                    </form>
            ) : (
                <p>{ErrorGetBookUser}</p>
            )}
            <Link to="/index">Index</Link>
            {putBookUser && JSON.stringify(putBookUser)}
            {formData && (JSON.stringify(formData))}
        </div>
    );
}