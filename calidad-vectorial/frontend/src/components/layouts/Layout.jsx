import { Outlet } from "react-router-dom";
import Header from "../elements/header/Header";
import './Layout.css';

const Layout = () => {
    return (
        <div className="body">
            <Header />
            <Outlet />
        </div>
    );
};
export default Layout;