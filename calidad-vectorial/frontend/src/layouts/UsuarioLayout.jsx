import { Outlet, useLocation  } from "react-router-dom";
import './UsuarioLayout.css';
import { Usuario } from "../components/elements/user/Usuario";
import { ButtonGroup } from "../components/elements/buttons/ButtonGroup";
import { Button } from "../components/elements/buttons/Button";

export const UsuarioLayout = () => {
    const location = useLocation();
    const { pathname } = location;

    return (
        <>
            <div className="layout-user">
                <Usuario />
                <div className="layout-user__content">
                    <ButtonGroup>
                        <Button color={pathname.includes("/publicacion") ? "oscuro" : "claro"} to="/usuario/publicacion/propias">Publicaciones</Button>
                        <Button color={pathname.includes("/biblioteca") ? "oscuro" : "claro"} to="/usuario/biblioteca">Biblioteca</Button>
                        <Button color={pathname.startsWith("/**") ? "oscuro" : "claro"} to="/usuario/configuracion">Préstamos</Button>
                        <Button color={pathname.includes("/configuracion") ? "oscuro" : "claro"} to="/usuario/configuracion">Configuración</Button>
                    </ButtonGroup>
                    <Outlet />
                </div>
            </div>
        </>
    )
}