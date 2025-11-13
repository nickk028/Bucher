import { Link, useParams } from "react-router-dom";
import { useFetch, usePost } from "../../../../utils/FetchUtils"
import { useEffect, useState } from "react";
import "./LibroUsuario.css";
import { Input } from "../../../../elements/input/Input";
import { Button } from "../../../../elements/buttons/Button";

export const LibroUsuario = () => {
    const { posicion } = useParams()
    const [editando, setEditando] = useState(false);
    const { data : getBookUser, loading : loadingGetBookUser, error : errorGetBookUser } = useFetch("biblioteca/" + posicion);
    const [formData, setFormData] = useState({
        paginaActual: 0,
        estadoLectura:  "",
        puntuacion: 0
    });
    const { data : putBookUser, loading : loadingPutBookUser, error : errorPutBookUser, execute } = usePost("biblioteca/" + posicion, "PUT" );

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

    useEffect(() => {
        if (putBookUser && !errorPutBookUser) {
            setEditando(false);
        }
    }, [putBookUser, errorPutBookUser])

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
        } else {
            setEditando(false);
        }
    };

    return (
        <main>
            {loadingGetBookUser ? (
                <p>Cargando Libro Usuario...</p>
            ) : getBookUser ? (
                <article className="libro-user">
                    <img className="libro-user__aside" src={getBookUser.urlFoto} alt={`Foto del libro ${getBookUser.titulo}`}/>

                    <div>
                        <h1 className="libro-user__text__title">{getBookUser.titulo}</h1>
                    
                        <form className="libro-user__text__form" onSubmit = {handleSubmitDataBookUser}>
                            <div>
                                <label className={`libro-user__text__form__label libro-user__text__form__label--${editando ? "editando" : ""}`}>Página actual</label>
                                <Input type="number" name="paginaActual" value={formData.paginaActual} onChange={handleChange} disabled={!editando} required={false}/>
                            </div>
                            <div>
                                <label className={`libro-user__text__form__label libro-user__text__form__label--${editando ? "editando" : ""}`}>Estado de lectura</label>
                                <Input type="text" name="estadoLectura" value={formData.estadoLectura} onChange={handleChange} disabled={!editando} required={false}/>
                            </div>
                            <div>
                                <label className={`libro-user__text__form__label libro-user__text__form__label--${editando ? "editando" : ""}`}>Puntuación</label>
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
                    </div>
                </article>
            ) : (
                <p>{errorGetBookUser}</p>
            )}
        </main>
    );
};