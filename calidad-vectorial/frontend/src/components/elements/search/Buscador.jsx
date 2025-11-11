import lupa from "../../../assets/img/lupa.png";
import "./Buscador.css";
import { useFetch } from "../../utils/FetchUtils";
import { useState } from "react";
import { Autocompletar } from "../autocomplete/Autocompletar";
import { useNavigate } from "react-router-dom";

const Buscador = () => {
    const { data : dataLibros , errorLibros , loadingLibros  } = useFetch("libro/todos");
    const [titulo, setTitulo] = useState("");
    const navigate = useNavigate();

    const handleBuscarLibro = (e) => {
        e.preventDefault();
        const id = dataLibros.find(libro => libro.titulo === titulo)?.id || '';
        navigate(`/libros/${id}`);
    }

    return (
        <nav className="buscador">
            <form className="buscador__input" onSubmit={handleBuscarLibro}>
                <Autocompletar
                    options={dataLibros ? dataLibros.map(libro => [libro.urlFoto ,libro.titulo]) : []}
                    type = "text"
                    tipo = "doble"
                    imgHeight = "100px"
                    imagWidth = "60px"
                    value = {titulo}
                    name = "titulo"
                    onChange = {e => setTitulo(e.target.value)}>
                </Autocompletar>
                <button type = "submit" style={{border: "hidden"}}><img className="buscador__input__lupa" src={lupa} alt="Lupa" /></button>
            </form>
        </nav>
    )
}
export default Buscador