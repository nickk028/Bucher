import { Outlet } from "react-router-dom";
import { useBook } from "../context/LibroContexto";
import Header from "../components/elements/header/Header";
import { Footer } from "../components/elements/footer/Footer";
import { LibroAnimado } from "../components/elements/animatedbook/LibroAnimado";
import './Layout.css';

export const Layout = () => {
    const { libroMensaje } = useBook();

    return (
        <>
        <div className="body-layout">
            <Header />
            <div className="body-layout__content">
                <Outlet />
            </div>
            <div className="body-layout__büchi">
                <LibroAnimado  variant="büchi" color="dorado" mensaje={libroMensaje} mostrarMensaje={true}>B</LibroAnimado>
            </div>
        </div>
        <Footer />
        </>
    );
};