import { Outlet } from "react-router-dom";
import Header from "../elements/header/Header";
import { Footer } from "../elements/footer/Footer";
import { LibroAnimado } from "../elements/animatedbook/LibroAnimado";
import './Layout.css';

export const Layout = () => {
    return (
        <>
        <div className="body-layout">
            <Header />
            <div className="body-layout__content">
                <Outlet />
            </div>
            <div className="body-layout__büchi">
                <LibroAnimado  variant="büchi" color="azul">B</LibroAnimado>
            </div>
        </div>
        <Footer />
        </>
    );
};