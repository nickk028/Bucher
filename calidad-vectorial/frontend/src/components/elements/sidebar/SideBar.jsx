import { useFetch, usePost } from "../../utils/FetchUtils";
import { useNavigate } from "react-router-dom";
import Logout from "../../../assets/img/icons/configuracion/logout.svg?react";
import"./SideBar.css";

export const SideBar = ({children, titulo}) => {
    const navigate = useNavigate();
    const { data: respuestaLogout, error: errorLogout, loading: loadingLogout, execute : executeLogout} = usePost("auth/logout");

    const handleLogout = async () => {
        await executeLogout();
        navigate("/");
    };

    return (
        <nav className="sidebar">
            <div className="sidebar__content">
                <div>
                    <h1>{titulo}</h1>
                    <div className="sidebar__cards">
                        {children}
                        <div className="sidebar__content__card" onClick={handleLogout}>
                            <div className="sidebar__content__card__logout">
                                <Logout />
                            </div>
                            <p>Cerrar sesión</p>
                        </div>
                    </div>
                </div>
                <div className="sidebar__content__footer">
                    <p>Bücher</p>
                </div>
            </div>
        </nav>
    )
}