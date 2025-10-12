import { Outlet } from "react-router-dom";
import Header from "../elements/header/Header";
import './Layout.css';

const Layout = () => {
    return (
        <div className="body-layout">
            <Header />
            <div className="body-layout__content">
                <Outlet />
            </div>
        </div>
    );
};
export default Layout;