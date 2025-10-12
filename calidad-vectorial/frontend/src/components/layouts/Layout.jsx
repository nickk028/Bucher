import { Outlet } from "react-router-dom";
import Header from "../elements/header/Header";
import { Footer } from "../elements/footer/Footer";
import './Layout.css';

const Layout = () => {
    return (
        <>
        <div className="body-layout">
            <Header />
            <div className="body-layout__content">
                <Outlet />
            </div>
        </div>
        <Footer />
        </>
    );
};
export default Layout;