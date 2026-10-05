import { Outlet, useLocation, Link } from 'react-router-dom';
import { useBook } from '../context/LibroContexto';
import { useFetch } from '../components/utils/FetchUtils';
import Header from '../components/elements/header/Header';
import { LibroAnimado } from '../components/elements/animatedbook/LibroAnimado';
import { SideBar } from '../components/elements/sidebar/SideBar';
import { SideBarCard } from '../components/elements/sidebar/SideBarCard';
import './Layout.css';
import { getConfig } from '../components/utils/ConfigUtils';
import { useEffect, useState } from 'react';
import { Buscador } from '../components/elements/search/Buscador';
import Ajustes from '../assets/img/icons/configuracion/ajustes.svg?react';
import Soporte from '../assets/img/icons/configuracion/soporte.svg?react';
import CrearLibro from '../assets/img/icons/crear/crearLibro.svg?react';
import CrearPosteo from '../assets/img/icons/crear/crearPosteo.svg?react';
import CrearPrestamo from '../assets/img/icons/crear/crearPrestamo.svg?react';


export const Layout = () => {
    const { libroMensaje } = useBook();
    const [configuracion, setConfiguracion] = useState(() => getConfig());
    const [mostrarCrear, setMostrarCrear] = useState(false);
    const location = useLocation();

    const mostrarConfig = location.pathname.startsWith('/configuracion');

    const {
        data: respuestaUsuario,
        loading: loadingUsuario,
        error: errorUsuario,
    } = useFetch('usuario/propio');

    // Actualiza la configuración al cambiar de ruta
    useEffect(() => {
        setConfiguracion(getConfig());
    }, [location.key]);

    useEffect(() => {
        if (mostrarConfig) {
            setMostrarCrear(false);
        }
    }, [mostrarConfig]);

    useEffect(() => {
        if (mostrarConfig) {
            setConfiguracion(getConfig());
        }
    }, [mostrarCrear]);

    return (
        <div className="body-layout">
            <Header
                onToggleCrear={() => setMostrarCrear((prev) => !prev)}
                mostrarCrear={mostrarCrear}
            />

            {mostrarCrear ? (
                <SideBar>
                    <SideBarCard
                        variant='button'
                        titulo="Préstamo"
                        opciones={[
                            {
                                to: '/crear-prestamo',
                                text: 'Compartí uno de tus libros y expandí la lectura',
                            },
                        ]}
                        img={<CrearPrestamo />}
                    />
                    <SideBarCard
                        variant='button'
                        titulo="Posteo"
                        opciones={[
                            {
                                to: '/comentarios-social',
                                text: 'Escribí sobre tus lecturas y opiniones.',
                            },
                        ]}
                        img={<CrearPosteo />}
                    />
                    <SideBarCard
                        variant='button'
                        titulo="Libro"
                        opciones={[
                            { to: '/crear-libro', text: 'Mostra tus obras a nuevos lectores.' },
                        ]}
                        img={<CrearLibro />}
                    />
                </SideBar>
            ) : mostrarConfig ? (
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
            ) : null}
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
    );
};
