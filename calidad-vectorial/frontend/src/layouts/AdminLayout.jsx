import { Outlet, Link } from 'react-router-dom';
import { useFetch } from '../components/utils/FetchUtils';
import { HeaderAdmin } from '../components/elements/header/HeaderAdmin';
import { Buscador } from '../components/elements/search/Buscador';

import './Layout.css';

export const AdminLayout = () => {
    const {
        data: respuestaUsuario,
        loading: loadingUsuario,
        error: errorUsuario,
    } = useFetch('usuario/propio');

    return (
        <div className="body-layout">
            <HeaderAdmin />

            <div className="body-layout__content">
                <div className="body-layout__content__barra">
                    <Buscador />
                    <Link to="/usuario" className="body-layout__content__barra__img">
                        <img src={`/assets/img/avatares/${respuestaUsuario.avatar}`} alt="Foto de usuario" />
                    </Link>
                </div>
                <Outlet />
            </div>
        </div>
    )
}