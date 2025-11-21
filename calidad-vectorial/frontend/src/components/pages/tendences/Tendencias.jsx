import podioImg from "../../../assets/img/podioImg.png";
import { useFetch } from "../../utils/FetchUtils";
import { LibroCard } from "../../elements/book/LibroCard";
import Buscador from "../../elements/search/Buscador";
import "./Tendencias.css";

export const Tendencias = () => {
    const { data, loading, error } = useFetch("libro/tendencias");

    return (
        <main>
            <Buscador></Buscador>
            <div className="tend-body">
                <div className="tend-body__podio">
                    {data.slice(0, 3).map((libro) => (
                        <LibroCard key={libro.id} titulo={libro.titulo} urlFoto={libro.urlFoto}/>
                    ))}
                </div>
                
                <p className="tend-body__titulo">Libros más prestados de la semana.</p>

                <div className="tend-body__space" />

                <aside className="tend-body__lista">
                    {data.slice(0, 10).map((libro, index) => (
                        <p key={libro.id}> <strong>{index + 1}.</strong> {libro.titulo}</p>
                    ))}
                </aside>
            </div>
        </main>
    )
}