import { BrowserRouter as Router, Routes, Route } from "react-router-dom";

import { GlobalProvider } from "./context/GlobalProvider";

import { Login } from "./components/pages/login/Login";
import { SobreNosotros } from "./components/pages/aboutus/SobreNosotros";
import { Index } from "./components/pages/index/Index";
import { Publicacion } from "./components/pages/publication/Publicacion";
import { Biblioteca } from "./components/pages/user/bookshelf/Biblioteca";
import { LibroUsuario } from "./components/pages/user/bookshelf/bookuser/LibroUsuario";
import { PublicacionUsuario } from "./components/pages/user/userpublication/PublicacionUsuario";
import { Register } from "./components/pages/register/Register";
import { CrearPublicacion } from "./components/pages/publication/CrearPublicacion";
import { ProtectedRoute } from "./components/utils/TokenUtils";
import { CategoriaLibro } from "./components/pages/user/bookshelf/category/CategoriaLibro";
import { Tendencias } from "./components/pages/tendences/Tendencias";
import { Configuracion } from "./components/pages/user/configuration/Configuracion";
import { UsuarioLayout } from "./layouts/UsuarioLayout";
import { ComingSoon } from "./components/elements/errors/ComingSoon";
import { Prestamo } from "./components/pages/user/loan/Prestamo";
import { Libros } from "./components/pages/books/libros";
import { Libro } from "./components/pages/books/book/Libro";

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
                        <Route path="/publicacion/:id" element={<Publicacion />} />
                        <Route path="/crear-publicacion" element={<CrearPublicacion />} />
                        <Route path="/tendencias" element={<Tendencias />} />
                        <Route path="/usuario" element={<UsuarioLayout />} >
                            <Route path="/usuario/biblioteca" element={<Biblioteca />} />
                            <Route path="/usuario/biblioteca/:posicion" element={<LibroUsuario />} />
                            <Route path="/usuario/biblioteca/categoria/:categoria" element={<CategoriaLibro />} />
                            <Route path="/usuario/publicacion/propias" element={<PublicacionUsuario />} />
                            <Route path="/usuario/configuracion" element={<Configuracion />} />
                            <Route path="/usuario/prestamos" element={<Prestamo />} />
                        </Route>
                        <Route path="/libros" element={<Libros />} />
                        <Route path="/libros/:id" element={<Libro />} />
                    </Route>
                </Routes>
            </Router>
        </GlobalProvider>
    );
}