import "./ComingSoon.css"
import { useNavigate } from "react-router-dom";
import { useFetch} from "../../../utils/FetchUtils";

export const ComingSoon = () => {
    const navigate = useNavigate();
    const { data, error, loading } = useFetch("publicacionSocial");
    console.log(data);
    return (
        <div className="coming-soon">
            <p>{data}</p>
            <div className="coming-soon__box">
                <h1 className="coming-soon__title">Próximamente</h1>
                <p className="coming-soon__subtitle">Este capítulo aún se esta escribiendo.</p>
            </div>
        </div>
    );
}
