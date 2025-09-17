package ar.edu.huergo.vectorial.calidad.bucher.service.publication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.Publicacion;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.RegistroPrestamo;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;
import ar.edu.huergo.vectorial.calidad.bucher.repository.publication.PublicacionRepository;
import ar.edu.huergo.vectorial.calidad.bucher.repository.publication.RegistroPrestamoRepository;
import ar.edu.huergo.vectorial.calidad.bucher.repository.security.UsuarioRepository;
import ar.edu.huergo.vectorial.calidad.bucher.service.book.LibroService;
import ar.edu.huergo.vectorial.calidad.bucher.service.security.UsuarioService;

@Service
public class RegistroPrestamoService {

    @Autowired
    private RegistroPrestamoRepository registroPrestamoRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private LibroService libroService;

    /**
     * Crea un nuevo registro de préstamo
     * @param usuario El usuario que realiza el préstamo
     * @param publicacion La publicación que se presta
     * @return El registro de préstamo creado
     */
    public RegistroPrestamo crearRegistroUsuario(Usuario usuario, Publicacion publicacion) {
        RegistroPrestamo registro = new RegistroPrestamo();
        registro.setPublicacion(publicacion);
        registro.setUsuario(usuario);
        registro.setFechaDevolucion(null);
        return registroPrestamoRepository.save(registro);
    }

    /**
     * Marca un registro de préstamo como devuelto
     * @param registro El registro de préstamo a marcar como devuelto
     * @return El registro de préstamo actualizado
     */
    public RegistroPrestamo marcarRegistroDevolucion(RegistroPrestamo registro) {
        return registroPrestamoRepository.save(registro);
    }

    /**
     * Obtiene el ultimo registro de préstamo activo para un usuario y publicación específicos
     * @param usuario El usuario del registro de préstamo
     * @param publicacion La publicación del registro de préstamo
     * @return El registro de préstamo activo
     * @throws IllegalArgumentException Si no existe un registro de préstamo activo para el usuario y publicación dados
     */
    public RegistroPrestamo obtenerRegistroPrestamo(Usuario usuario, Publicacion publicacion) {
        return registroPrestamoRepository.findByUsuarioAndPublicacionAndFechaDevolucionIsNull(usuario, publicacion)
            .orElseThrow(() -> new IllegalArgumentException("No existe un registro de préstamo activo para este usuario y publicación"));
    }
}