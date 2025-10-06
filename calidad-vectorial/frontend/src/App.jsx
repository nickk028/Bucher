import { useState } from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Login from "./components/pages/login/Login";
import { SobreNosotros } from "./components/pages/aboutus/SobreNosotros";
import { Index } from "./components/pages/index/Index";
import { Publicacion } from "./components/pages/publication/Publicacion";
import { Biblioteca } from "./components/pages/user/bookshelf/Biblioteca";
import { LibroUsuario } from "./components/pages/user/bookshelf/bookuser/LibroUsuario";
import { PublicacionUsuario } from "./components/pages/user/userpublication/userpublication";
import { Register } from "./components/pages/register/Register";
import { CrearPublicacion } from "./components/pages/publication/CrearPublicacion";
import { ProtectedRoute } from './components/utils/TokenUtils';
import { Usuario } from "./components/pages/user/Usuario";

const App = () => {
	return (
		<Router>
			<Routes>
                <Route path="/" element={<SobreNosotros/>}/>
                <Route path="/login" element={<Login/>}/>
                <Route path="/register" element={<Register/>}/>
                <Route element={<ProtectedRoute/>}>
                    <Route path="/index" element={<Index/>}/>
                    <Route path="/publicacion/:id" element={<Publicacion/>}/>
                    <Route path="/biblioteca" element={<Biblioteca/>}/>
                    <Route path="/biblioteca/:id" element={<LibroUsuario/>}/>
                    <Route path="/publicacion/propias" element={<PublicacionUsuario/>}/>
                    <Route path="/crear-publicacion" element={<CrearPublicacion/>}/>
                    <Route path="/usuario" element={<Usuario/>}/>
                </Route>
            </Routes>
		</Router>
    );
}
export default App;