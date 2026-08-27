import { BrowserRouter as Router, Routes, Route } from "react-router-dom";

import { GlobalProvider } from "./context/GlobalProvider";

import { Login } from "./components/pages/login/Login";
import { AuthPage } from "./components/pages/auth/AuthPage";
import { SobreNosotros } from "./components/pages/aboutus/SobreNosotros";
import { Index } from "./components/pages/index/Index";
import { Prestamos } from "./components/pages/loans/Prestamos";
import { ComentariosSocial } from "./components/pages/socialcoments/ComentariosSocial";
import { Publicacion } from "./components/pages/publication/Publicacion";
import { LibroUsuario } from "./components/pages/user/bookshelf/bookuser/LibroUsuario";
import { PublicacionUsuario } from "./components/pages/user/userpublication/PublicacionUsuario";
import { Register } from "./components/pages/register/Register";
import { ProtectedRoute } from "./components/utils/TokenUtils";
import { Tendencias } from "./components/pages/tendences/Tendencias";
import { Biblioteca } from "./components/pages/bookshelf/Biblioteca";
import { CrearPrestamo } from "./components/pages/create/prestamo/CrearPrestamo";
import { CrearLibro } from "./components/pages/create/libro/CrearLibro";
import { EditarPerfil } from "./components/pages/configuration/editarperfil/EditarPerfil";
import { Apariencia } from "./components/pages/configuration/apariencia/Apariencia";
import { ComingSoon } from "./components/pages/errors/comingsoon/ComingSoon";
import { Libros } from "./components/pages/books/Libros";
import { Libro } from "./components/pages/books/book/Libro";
import { Usuario } from "./components/pages/user/Usuario";
import { NotFound } from "./components/pages/errors/notfound/NotFound";
import { ToS } from "./components/pages/tos/ToS";

export const App = () => {
	return (
        <GlobalProvider>
            <Router>
                <Routes>
                    <Route path="/" element={<SobreNosotros/>}/>
                    <Route path="*" element={<NotFound />} />

                    <Route element={<ProtectedRoute/>}>
                        <Route path="/coming-soon" element={<ComingSoon />} />
                        <Route path="/login" element={<AuthPage/>}/>
                        <Route path="/register" element={<AuthPage/>}/>
                        <Route path="/index" element={<Index />} />
                        <Route path="/prestamos" element={<Prestamos />} />
                        <Route path="/comentarios-social" element={<ComentariosSocial />} />
                        <Route path="/publicacion/:id" element={<Publicacion />} />
                        <Route path="/biblioteca" element={<Biblioteca />} />
                        <Route path="/crear-prestamo" element={<CrearPrestamo />} />
                        <Route path="/crear-libro" element={<CrearLibro />} />
                        <Route path="/tendencias" element={<Tendencias />} />
                        <Route path="/configuracion/editar-perfil" element={< EditarPerfil/>} />
                        <Route path="/configuracion/apariencia" element={< Apariencia/>} />
                        <Route path="/configuracion/ToS" element={< ToS/>} />
                        <Route path="/libros" element={<Libros />} />
                        <Route path="/libros/:id" element={<Libro />} />
                        <Route path="/usuario" element={<Usuario />} />
                    </Route>
                </Routes>
            </Router>
        </GlobalProvider>
    );
}