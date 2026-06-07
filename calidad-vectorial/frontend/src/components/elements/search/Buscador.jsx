import "./Buscador.css";
import { useState } from "react";
import { AutoCompletarLibro } from "../autocomplete/types/AutoCompletarLibro";
import { Input } from "../input/Input";
import { useNavigate } from "react-router-dom";

export const Buscador = () => {
    const [filtro, setFiltro] = useState("");
    const [libro, setLibro] = useState(null);
    const [opcionFiltrado, setOpcionFiltrado] = useState("prestamos")
    const navigate = useNavigate()

    const handleBuscarLibro = (e) => {
        e.preventDefault();
        console.log(libro)
        if ( opcionFiltrado == "libros" ) {
            navigate("/libros/" + libro.id)
        } else if ( opcionFiltrado == "prestamos" ) {
            
        } else if ( opcionFiltrado == "usuarios" ) {
            
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
                        onSelect = {(libro) => setLibro(libro)}
                    />
                ) : opcionFiltrado == "usuarios" && (
                    <Input type="text" value={filtro} name="filtro" variant="buscador" placeholder = "Buscar usuario por nick" onChange = {e => setFiltro(e.target.value)}></Input>
                )}
            </form>
        </nav>
    )
}