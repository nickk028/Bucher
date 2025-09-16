package ar.edu.huergo.vectorial.calidad.bucher.service.publication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.Publicacion;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.RegistroPrestamo;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;
import ar.edu.huergo.vectorial.calidad.bucher.repository.publication.PublicacionRepository;
import ar.edu.huergo.vectorial.calidad.bucher.service.book.LibroService;
import ar.edu.huergo.vectorial.calidad.bucher.service.security.UsuarioService;

@Service
public class RegistroPrestamoService {

    @Autowired
    private PublicacionRepository registroPrestamoRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private LibroService libroService;

    public RegistroPrestamo crearRegistro(Usuario usuario, Publicacion publicacion) {
        RegistroPrestamo registro = new RegistroPrestamo();
        registro.setPublicacion(publicacion);
        registro.setUsuario(usuario);
        registro.setFechaDevolucion(null);
        return registro;
    }
}