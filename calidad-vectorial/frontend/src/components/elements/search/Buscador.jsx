import lupa from "../../../assets/img/lupa.png";
import "./Buscador.css";
import { useFetch } from "../../utils/FetchUtils";
import { useState } from "react";
import { AutoCompletarLibro } from "../autocomplete/types/AutoCompletarLibro";
import { useNavigate } from "react-router-dom";
import { Input } from "../input/Input";

const Buscador = () => {
    const { data : dataLibros , errorLibros , loadingLibros  } = useFetch("libro/todos");
    const { data : dataPrestamos , errorPrestamos , loadingPrestamos  } = useFetch("publicacion");
    const { data : dataUsuarios , errorUsuarios , loadingUsuarios  } = useFetch("usuario");
    const [filtro, setFiltro] = useState("");
    const [opcionFiltrado, setOpcionFiltrado] = useState("")
    const navigate = useNavigate();

    const handleBuscarLibro = (e) => {
        e.preventDefault();
        if ( opcionFiltrado == "libros" ) {
            const id = dataLibros.find(libro => libro.titulo === titulo)?.id || '';
            navigate(`/libros/${id}`);
        } else if ( opcionFiltrado == "prestamos" ){
        }
    }

    return (
        <nav className="buscador">
            <form className="buscador__input" onSubmit={handleBuscarLibro}>
                <select id="buscar" name="buscar" onChange={e => setOpcionFiltrado(e.target.value)}>
                    <option value="prestamos">Prestamos</option>
                    <option value="usuarios">Usuarios</option>
                    <option value="libros">Libros</option>
                </select>
                {opcionFiltrado == "libros" ?(
                    <AutoCompletarLibro
                    placeholder = "Buscar libro por título"
                    value = {filtro}
                    onChange = {e => setFiltro(e.target.value)}
                    />
                ) : opcionFiltrado == "prestamos" ? (
                    <Input type="text" value={filtro} name="filtro" onChange = {e => setFiltro(e.target.value)}></Input>
                ) : opcionFiltrado == "usuarios" && (
                    <Input type="text" value={filtro} name="filtro" onChange = {e => setFiltro(e.target.value)}></Input>
                )}
                
                <button type = "submit" style={{border: "hidden"}}><img className="buscador__input__lupa" src={lupa} alt="Lupa" /></button>
            </form>
        </nav>
    )
}
export default Buscador