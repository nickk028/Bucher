package ar.edu.huergo.vectorial.calidad.bucher.service.bookuser;

import ar.edu.huergo.vectorial.calidad.bucher.entity.bookuser.EstadoLectura;
import ar.edu.huergo.vectorial.calidad.bucher.entity.bookuser.LibroUsuario;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;
import ar.edu.huergo.vectorial.calidad.bucher.repository.bookuser.LibroUsuarioRepository;
import ar.edu.huergo.vectorial.calidad.bucher.repository.security.UsuarioRepository;
import ar.edu.huergo.vectorial.calidad.bucher.service.book.LibroService;
import ar.edu.huergo.vectorial.calidad.bucher.service.security.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//Clase que maneja la lógica de LibroUsuario
@Service
public class LibroUsuarioService {

    @Autowired
    private LibroUsuarioRepository libroUsuarioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private LibroService libroService;

    /**
     * Obtiene la List de LibroUsuario de un Usuario
     * @param username El username del Usuario
     * @return La List de LibroUsuario del Usuario
     * @throws EntityNotFoundException No encuentra el Usuario
     */
    public List<LibroUsuario> obtenerLibrosUsuario(String username)
        throws EntityNotFoundException {
        return usuarioService.obtenerUsuarioPorNombre(username).getLibrosUsuario();
    }

    /**
     * Obtiene el LibroUsuario según su id
     * @param username El username del Usuario
     * @param id El id del LibroUsuario
     * @return El LibroUsuario encontrado
     * @throws EntityNotFoundException No encuentra el LibroUsuario
     */
    public LibroUsuario obtenerLibroUsuarioPorId(String username, Long id)
        throws EntityNotFoundException {
        List<LibroUsuario> librosUsuario = obtenerLibrosUsuario(username);
        return validarLibroUsuario(id, librosUsuario);
    }

    /**
     * Obtiene una lista de LibroUsuario de un Usuario filtrado por EstadoLectura
     * @param username El username del Usuario
     * @param estado El EstadoLectura ingresado
     * @return La lista de LibroUsuario de un EstadoLectura
     * @throws EntityNotFoundException No encuentra el Usuario
     */
    public List<LibroUsuario> obtenerLibrosPorEstado(String username, EstadoLectura estado)
        throws EntityNotFoundException {
        Usuario usuario = usuarioService.obtenerUsuarioPorNombre(username);
        return libroUsuarioRepository.findByUsuarioIdAndEstadoLectura(usuario.getId(), estado);
    }

    public long obtenerCantidadLibrosUsuarioPorUsuario(String username)
        throws EntityNotFoundException {
        Usuario usuario = usuarioService.obtenerUsuarioPorNombre(username);
        return libroUsuarioRepository.countByUsuarioId(usuario.getId());
    }

    /**
     * Sube un nuevo LibroUsuario a la lista del Usuario
     * @param username El username del Usuario
     * @param libroUsuarioIngresado El LibroUsuario ingresado
     * @param titulo El titulo del Libro al que le corresponde el LibroUsuario
     * @return La List de LibroUsuario actualizada del Usuario
     */
    public List<LibroUsuario> subirLibroUsuario(
        String username,
        LibroUsuario libroUsuarioIngresado,
        String titulo
    ) {
        Usuario usuario = usuarioService.obtenerUsuarioPorNombre(username);
        libroUsuarioIngresado.setLibro(libroService.obtenerLibroPorTitulo(titulo));

        // Se define el Estado de Lectura en caso de que no se haya ingresado
        if (libroUsuarioIngresado.getEstadoLectura() == EstadoLectura.Indefinido) {
            if (libroUsuarioIngresado.getPaginaActual() == 0) {
                libroUsuarioIngresado.setEstadoLectura(EstadoLectura.Pendiente);
            } else {
                libroUsuarioIngresado.setEstadoLectura(EstadoLectura.Leyendo);
            }
        }

        libroUsuarioIngresado.setUsuario(usuario);
        usuario.getLibrosUsuario().add(libroUsuarioIngresado);

        return usuarioRepository.save(usuario).getLibrosUsuario();
    }

    /**
     * Modifica los atributos del LibroUsuario con ese id y de ese usuario
     * @param username El username del Usuario
     * @param id El id del LibroUsuario a modificar
     * @param libroUsuarioNuevo El LibroUsuario con los nuevos datos
     * @return El LibroUsuario modificado
     * @throws EntityNotFoundException No encuentra el LibroUsuario
     */
    public LibroUsuario modificarLibroUsuario(
        String username,
        Long id,
        LibroUsuario libroUsuarioNuevo
    ) throws EntityNotFoundException {
        Usuario usuario = usuarioService.obtenerUsuarioPorNombre(username);
        List<LibroUsuario> librosUsuario = usuario.getLibrosUsuario();
        LibroUsuario libroUsuarioAModificar = validarLibroUsuario(id, librosUsuario);

        if (libroUsuarioNuevo.getEstadoLectura() != EstadoLectura.Indefinido) {
            libroUsuarioAModificar.setEstadoLectura(libroUsuarioNuevo.getEstadoLectura());
        }
        if (libroUsuarioNuevo.getPaginaActual() != 0) {
            libroUsuarioAModificar.setPaginaActual(libroUsuarioNuevo.getPaginaActual());
        }
        if (libroUsuarioNuevo.getPuntuacion() != 0) {
            libroUsuarioAModificar.setPuntuacion(libroUsuarioNuevo.getPuntuacion());
        }

        return libroUsuarioRepository.save(libroUsuarioAModificar);
    }

    /**
     * Elimina el LibroUsuario con ese id
     * @param username El username del Usuario
     * @param id El id del LibroUsuario a eliminar
     * @throws EntityNotFoundException No encuentra el LibroUsuario
     */
    public void eliminarLibroUsuario(String username, Long id)
        throws EntityNotFoundException {
        Usuario usuario = usuarioService.obtenerUsuarioPorNombre(username);
        LibroUsuario libroUsuario = validarLibroUsuario(id, usuario.getLibrosUsuario());
        libroUsuarioRepository.delete(libroUsuario);
    }

    /**
     * Valida que el id exista dentro de la lista de LibroUsuario
     * @param id El id a validar
     * @param librosUsuario La lista de LibroUsuario
     * @throws EntityNotFoundException Si el id no pertenece a la lista
     */
    private LibroUsuario validarLibroUsuario(Long id, List<LibroUsuario> librosUsuario)
        throws EntityNotFoundException {
            return librosUsuario.stream().filter(n -> n.getId() == id).findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Libro usuario no encontrada"));
        
    }
}