import { Outlet, useLocation, Link } from "react-router-dom";
import { useBook } from "../context/LibroContexto";
import Header from "../components/elements/header/Header";
import { LibroAnimado } from "../components/elements/animatedbook/LibroAnimado";
import { SideBar } from "../components/elements/sidebar/SideBar";
import { SideBarCard } from "../components/elements/sidebar/SideBarCard";
import "./Layout.css";
import { getConfig } from "../components/utils/ConfigUtils";
import { useEffect, useState } from "react";
import { Buscador } from "../components/elements/search/Buscador";
import cenicienta from "../assets/img/avatares/cenicienta.png";
import Ajustes from "../assets/img/icons/configuracion/ajustes.svg?react";
import Soporte from "../assets/img/icons/configuracion/soporte.svg?react";

export const Layout = () => {
    const { libroMensaje } = useBook();
    const [configuracion, setConfiguracion] = useState(() => getConfig());
    const location = useLocation();

    const mostrarConfig =
        location.pathname.startsWith("/configuracion");

    const mostrarCrear =
        location.pathname.startsWith("/crear");

    // Actualiza la configuración al cambiar de ruta
    useEffect(() => {
        setConfiguracion(getConfig());
    }, [location.key]);


    return (
        <div className="body-layout">
            <Header />
            {mostrarConfig && (
                <SideBar titulo="Configuración">
                        <SideBarCard
                            titulo="Ajustes"
                            opciones={[
                                    {to: "/configuracion/editar-perfil", text: "Editar perfil"},
                                    {to: "/configuracion/apariencia", text: "Apariencia"},
                                    {to: "/configuracion/suscripcion", text: "Suscripción"},
                                    {to: "/configuracion/notificaciones", text: "Notificaciones"}
                                ]}
                            img={<Ajustes />}
                        />
                        <SideBarCard
                            titulo="Soporte"
                            opciones={[
                                    {to: "/apariencia", text: "Apariencia"},
                                    {to: "/suscripcion", text: "Suscripción"},
                                    {to: "/notificaciones", text: "Notificaciones"}
                                ]}
                            img={<Soporte />}
                        />
                </SideBar>
            )}

            {mostrarCrear && (
                <SideBar>
                    <SideBarCard
                            titulo="Préstamo"
                            opciones={[
                                    {to: "/crear-publicacion", text: "Compartí uno de tus libros y expandí la lectura"}
                                ]}
                            img={<Ajustes />}
                        />
                    <SideBarCard
                            titulo="Posteo"
                            opciones={[
                                    {to: "/crear-posteo", text: "Escribí sobre tus lecturas y opiniones."}
                                ]}
                            img={<Ajustes />}
                        />
                    <SideBarCard
                            titulo="Libro"
                            opciones={[
                                    {to: "/crear-libro", text: "Mostra tus obras a nuevos lectores."}
                                ]}
                            img={<Ajustes />}
                        />
                </SideBar>
            )}
            <div className="body-layout__content">
                <div className="body-layout__content__barra">
                    <Buscador/>
                    <Link to="/usuario" className="body-layout__content__barra__img">
                        <img src={cenicienta} alt="Foto de usuario" />
                    </Link>
                </div>
                <Outlet />
            </div>
            {configuracion.buchy && (
                <div className="body-layout__büchi">
                    <LibroAnimado  variant="büchi" color= {configuracion.colorBuchy} mensaje={libroMensaje} mostrarMensaje={true}>B</LibroAnimado>
                </div>
            )}
        </div>
    );
};