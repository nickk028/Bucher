import { Outlet, useLocation } from "react-router-dom";
import { useBook } from "../context/LibroContexto";
import Header from "../components/elements/header/Header";
import { Footer } from "../components/elements/footer/Footer";
import { LibroAnimado } from "../components/elements/animatedbook/LibroAnimado";
import './Layout.css';
import { getConfig } from "../components/utils/ConfigUtils";
import { useEffect, useState } from "react";

export const Layout = () => {
    const { libroMensaje } = useBook();
    const [configuracion, setConfiguracion] = useState(() => getConfig());
    const location = useLocation();

    // Actualiza la configuración al cambiar de ruta
    useEffect(() => {
        setConfiguracion(getConfig());
    }, [location.key]);


    return (
        <>
        <div className="body-layout">
            <Header />
            <div className="body-layout__content">
                <Outlet />
            </div>
            {configuracion.buchy && (
                <div className="body-layout__büchi">
                    <LibroAnimado  variant="büchi" color="dorado" mensaje={libroMensaje} mostrarMensaje={true}>B</LibroAnimado>
                </div>
            )}
        </div>
        <Footer />
        </>
    );
};