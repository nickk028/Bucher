import { useEffect, useState } from 'react';
import { Outlet, Link, useLocation } from 'react-router-dom';
import { useFetch } from '../components/utils/FetchUtils';
import { getConfig } from '../components/utils/ConfigUtils';
import { useBook } from '../context/LibroContexto';
import { HeaderAdmin } from '../components/elements/header/HeaderAdmin';
import { Buscador } from '../components/elements/search/Buscador';
import { SideBar } from '../components/elements/sidebar/SideBar';
import { SideBarCard } from '../components/elements/sidebar/SideBarCard';
import { LibroAnimado } from '../components/elements/animatedbook/LibroAnimado';
import Ajustes from '../assets/img/icons/configuracion/ajustes.svg?react';
import Soporte from '../assets/img/icons/configuracion/soporte.svg?react';

import './Layout.css';

export const AdminLayout = () => {
    const { libroMensaje } = useBook();
    const [configuracion, setConfiguracion] = useState(() => getConfig());
    const location = useLocation();
    const mostrarConfig = location.pathname.startsWith('/configuracion');

    const {
        data: respuestaUsuario,
        loading: loadingUsuario,
        error: errorUsuario,
    } = useFetch('usuario/propio');

    useEffect(() => {
        setConfiguracion(getConfig());
    }, [location.key]);

    return (
        <div className="body-layout">
            <HeaderAdmin />

            {mostrarConfig && (
                <SideBar titulo="Configuración">
                    <SideBarCard
                        titulo="Ajustes"
                        opciones={[
                            { to: '/configuracion/editar-perfil', text: 'Editar perfil' },
                            { to: '/configuracion/apariencia', text: 'Apariencia' },
                            { to: '/configuracion/suscripcion', text: 'Suscripción' },
                            { to: '/coming-soon', text: 'Notificaciones' },
                        ]}
                        img={<Ajustes />}
                    />
                    <SideBarCard
                        titulo="Soporte"
                        opciones={[
                            { to: '/configuracion/preguntas-frecuentes', text: 'Preguntas Frecuentes' },
                            { to: '/configuracion/ToS', text: 'Términos y condiciones' },
                        ]}
                        img={<Soporte />}
                    />
                </SideBar>
            )}

            <div className="body-layout__content">
                <div className="body-layout__content__barra">
                    <Buscador />
                    <Link to="/usuario" className="body-layout__content__barra__img">
                        <img src={`/assets/img/avatares/${respuestaUsuario.avatar}`} alt="Foto de usuario" />
                    </Link>
                </div>
                <Outlet />
            </div>

            {configuracion.buchy && (
                <div className="body-layout__büchi">
                    <LibroAnimado
                        variant="büchi"
                        color={configuracion.colorBuchy}
                        mensaje={libroMensaje}
                        mostrarMensaje={true}
                    >
                        B
                    </LibroAnimado>
                </div>
            )}
        </div>
    )
}