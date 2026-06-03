import lupa from "../../../assets/img/lupa.png";
import "./Buscador.css";
import { useState } from "react";
import { AutoCompletarLibro } from "../autocomplete/types/AutoCompletarLibro";
import { Input } from "../input/Input";
import { useNavigate } from "react-router-dom";

const Buscador = () => {
    const [filtro, setFiltro] = useState("");
    const [libro, setLibro] = useState("");
    const [opcionFiltrado, setOpcionFiltrado] = useState("")
    const navigate = useNavigate()

    const handleBuscarLibro = (e) => {
        e.preventDefault();
        console.log(libro)
        if ( opcionFiltrado == "libros" ) {
            navigate("/libros/" + libro.id)
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
                        onSelect = {(libro) => setLibro(libro)}
                    />
                ) : opcionFiltrado == "prestamos" ? (
                    <AutoCompletarLibro
                        placeholder = "Buscar prestamo por libro"
                        value = {filtro}
                        onChange = {e => setFiltro(e.target.value)}
                    />
                ) : opcionFiltrado == "usuarios" && (
                    <Input type="text" value={filtro} name="filtro" onChange = {e => setFiltro(e.target.value)}></Input>
                )}
                
                <button type = "submit" style={{border: "hidden"}}><img className="buscador__input__lupa" src={lupa} alt="Lupa" /></button>
            </form>
        </nav>
    )
}
export default Buscador