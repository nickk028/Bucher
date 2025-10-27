import { Input } from "../input/Input";
import lupa from "../../../assets/img/lupa.png";
import "./Buscador.css";

const Buscador = () => {
    return (
        <nav className="buscador">
            <div className="buscador__input">
                <Input type="text" name="buscador" placeholder="Buscar..." ></Input>
                <label htmlFor="buscador"><img className="buscador__input__lupa" src={lupa} alt="Lupa" /></label>
            </div>
            
        </nav>
    )
}
export default Buscador