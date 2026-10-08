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
     * Obtiene el LibroUsuario según su posición en la lista del Usuario
     * @param username El username del Usuario
     * @param posicion La posición del LibroUsuario
     * @return El LibroUsuario encontrado
     * @throws EntityNotFoundException No encuentra el LibroUsuario
     */
    public LibroUsuario obtenerLibroUsuarioPorPosicion(String username, int posicion)
        throws EntityNotFoundException {
        List<LibroUsuario> librosUsuario = obtenerLibrosUsuario(username);
        validarPosicion(posicion, librosUsuario);
        return librosUsuario.get(posicion - 1);
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
     * Modifica los atributos del LibroUsuario ubicado en una posición de la lista del Usuario
     * @param username El username del Usuario
     * @param posicion La posición del LibroUsuario a modificar
     * @param libroUsuarioNuevo El LibroUsuario con los nuevos datos
     * @return El LibroUsuario modificado
     * @throws EntityNotFoundException No encuentra el LibroUsuario
     */
    public LibroUsuario modificarLibroUsuario(
        String username,
        int posicion,
        LibroUsuario libroUsuarioNuevo
    ) throws EntityNotFoundException {
        Usuario usuario = usuarioService.obtenerUsuarioPorNombre(username);
        List<LibroUsuario> librosUsuario = usuario.getLibrosUsuario();
        validarPosicion(posicion, librosUsuario);
        LibroUsuario libroUsuarioAModificar = librosUsuario.get(posicion - 1);

        if (libroUsuarioNuevo.getEstadoLectura() != EstadoLectura.Indefinido) {
            libroUsuarioAModificar.setEstadoLectura(libroUsuarioNuevo.getEstadoLectura());
        }
        if (libroUsuarioNuevo.getPaginaActual() != 0) {
            libroUsuarioAModificar.setPaginaActual(libroUsuarioNuevo.getPaginaActual());
        }
        if (libroUsuarioNuevo.getPuntuacion() != 0) {
            libroUsuarioAModificar.setPuntuacion(libroUsuarioNuevo.getPuntuacion());
        }

        return usuarioRepository.save(usuario).getLibrosUsuario().get(posicion - 1);
    }

    /**
     * Elimina el LibroUsuario ubicado en una posición de la lista del Usuario
     * @param username El username del Usuario
     * @param posicion La posición del LibroUsuario a eliminar
     * @throws EntityNotFoundException No encuentra el LibroUsuario
     */
    public void eliminarLibroUsuario(String username, int posicion)
        throws EntityNotFoundException {
        Usuario usuario = usuarioService.obtenerUsuarioPorNombre(username);
        validarPosicion(posicion, usuario.getLibrosUsuario());

        usuario.getLibrosUsuario().remove(posicion - 1);
        usuarioRepository.save(usuario);
    }

    /**
     * Valida que la posición exista dentro de la lista de LibroUsuario
     * @param posicion La posición a validar
     * @param librosUsuario La lista de LibroUsuario
     * @throws EntityNotFoundException Si la posición no existe en la lista
     */
    private void validarPosicion(int posicion, List<LibroUsuario> librosUsuario)
        throws EntityNotFoundException {
        if (posicion <= 0 || posicion > librosUsuario.size()) {
            throw new EntityNotFoundException("Libro usuario no encontrado");
        }
    }
}