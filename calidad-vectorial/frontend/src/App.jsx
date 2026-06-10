import { BrowserRouter as Router, Routes, Route } from "react-router-dom";

import { GlobalProvider } from "./context/GlobalProvider";

import { Login } from "./components/pages/login/Login";
import { SobreNosotros } from "./components/pages/aboutus/SobreNosotros";
import { Index } from "./components/pages/index/Index";
import { Prestamos } from "./components/pages/loans/Prestamos";
import { ComentariosSocial } from "./components/pages/socialcoments/ComentariosSocial";
import { Publicacion } from "./components/pages/publication/Publicacion";
//import { Biblioteca } from "./components/pages/user/bookshelf/Biblioteca";
import { LibroUsuario } from "./components/pages/user/bookshelf/bookuser/LibroUsuario";
import { PublicacionUsuario } from "./components/pages/user/userpublication/PublicacionUsuario";
import { Register } from "./components/pages/register/Register";
import { ProtectedRoute } from "./components/utils/TokenUtils";
//import { CategoriaLibro } from "./components/pages/user/bookshelf/category/CategoriaLibro";
import { Tendencias } from "./components/pages/tendences/Tendencias";
import { Biblioteca } from "./components/pages/bookshelf/Biblioteca";
import { Crear } from "./components/pages/create/Crear";
import { CrearPrestamo } from "./components/pages/create/prestamo/CrearPrestamo";
import { CrearLibro } from "./components/pages/create/libro/CrearLibro";
//import { Configuracion } from "./components/pages/user/configuration/Configuracion";
import { EditarPerfil } from "./components/pages/configuration/editarperfil/EditarPerfil";
//import { UsuarioLayout } from "./layouts/UsuarioLayout";
import { ComingSoon } from "./components/elements/errors/ComingSoon";
//import { Prestamo } from "./components/pages/user/loan/Prestamo";
import { Libros } from "./components/pages/books/Libros";
import { Libro } from "./components/pages/books/book/Libro";
import { Usuario } from "./components/pages/user/Usuario";

export const App = () => {
	return (
        <GlobalProvider>
            <Router>
                <Routes>
                    <Route path="/" element={<SobreNosotros/>}/>
                    <Route element={<ProtectedRoute/>}>
                        <Route path="/coming-soon" element={<ComingSoon />} />
                        <Route path="/login" element={<Login/>}/>
                        <Route path="/register" element={<Register/>}/>
                        <Route path="/index" element={<Index />} />
                        <Route path="/prestamos" element={<Prestamos />} />
                        <Route path="/comentarios-social" element={<ComentariosSocial />} />
                        <Route path="/publicacion/:id" element={<Publicacion />} />
                        <Route path="/biblioteca" element={<Biblioteca />} />
                        <Route path="/crear" element={<Crear />} />
                        <Route path="/crear-prestamo" element={<CrearPrestamo />} />
                        <Route path="/crear-libro" element={<CrearLibro />} />
                        <Route path="/tendencias" element={<Tendencias />} />
                        <Route path="/configuracion/editar-perfil" element={< EditarPerfil/>} />
                        <Route path="/libros" element={<Libros />} />
                        <Route path="/libros/:id" element={<Libro />} />
                        <Route path="/usuario" element={<Usuario />} />
                    </Route>
                </Routes>
            </Router>
        </GlobalProvider>
    );
}