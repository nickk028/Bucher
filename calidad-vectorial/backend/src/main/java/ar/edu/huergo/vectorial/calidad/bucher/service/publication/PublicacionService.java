package ar.edu.huergo.vectorial.calidad.bucher.service.publication;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Libro;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.Estado;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.Publicacion;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;
import ar.edu.huergo.vectorial.calidad.bucher.repository.publication.PublicacionRepository;
import ar.edu.huergo.vectorial.calidad.bucher.service.book.LibroService;
import ar.edu.huergo.vectorial.calidad.bucher.service.security.UsuarioService;
import jakarta.persistence.EntityNotFoundException;

@Service
public class PublicacionService {

    @Autowired
    private PublicacionRepository publicacionRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private LibroService libroService;

    /**
     * Obtiene todas las publicaciones
     * @return Un conjunto de todas las publicaciones
     */
    public Set<Publicacion> obtenerTodasLasPublicaciones() {
        return new HashSet<>(publicacionRepository.findAll());
    }

    /**
     * Obtiene una publicación por su ID
     * @param id El ID de la publicación
     * @return La publicación correspondiente al ID
     * @throws EntityNotFoundException Si no se encuentra la publicación
     */
    public Publicacion obtenerPublicacionPorId(Long id) throws EntityNotFoundException {
        return publicacionRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Publicacion no encontrada"));
    }

    /**
     * Verifica si una publicación pertenece a un usuario especifico
     * @param usario El usuario a verificar
     * @param id El ID de la publicación
     * @return boolean
     */
    public boolean verificarPublicacionUsuario(Usuario usario, Long id) {
        Publicacion publicacion = obtenerPublicacionPorId(id);
        return (usario.equals(publicacion.getUsuario()));
    }

    /**
     * Obtiene todas las publicaciones de un usuario especifico
     * @param usuario El usuario a buscar
     * @return El conjunto de publicaciones del usuario
     */
    public Set<Publicacion> obtenerPublicacionesPorUsuario(Usuario usuario) {
        return new HashSet<>(publicacionRepository.findAllByUsuario(usuario));
    }

    /**
     * Crea una nueva publicación
     * @param publicacion La publicación a crear
     * @param titulo El título del libro asociado a la publicación
     * @param username El nombre de usuario del usuario que crea la publicación
     * @return La publicación creada
     */
    public Publicacion crearPublicacion(Publicacion publicacion, String titulo, String username) {
        Usuario usuarioIngresado = usuarioService.obtenerUsuarioPorNombre(username);
        Libro libroIngresado = libroService.obtenerLibroPorTitulo(titulo);

        publicacion.setUsuario(usuarioIngresado);
        publicacion.setEstadoPublicacion(Estado.Disponible);
        publicacion.setDetallesEstadoLibro("Desconocido");
        publicacion.setLibro(libroIngresado);

        return publicacionRepository.save(publicacion);
    }

    /**
     * Elimina una publicación por su ID
     * @param id El ID de la publicación a eliminar
     */
    public void eliminarPublicacion(Long id) {
        Publicacion publicacion = obtenerPublicacionPorId(id);
        publicacionRepository.delete(publicacion);
    }

    /**
     * Modifica una publicación (solo para admin)
     * @param id El ID de la publicación a modificar
     * @param publicacion La publicación con los nuevos datos
     * @return La publicación modificada
     */
    public Publicacion modificarPublicacionAdmin(Long id, Publicacion publicacion) {
        Publicacion publicacionExistente = obtenerPublicacionPorId(id);

        if (!publicacion.getDescripcion().equals("nada")) {
            publicacionExistente.setDescripcion(publicacion.getDescripcion());
        }
        if (!publicacion.getDetallesEstadoLibro().equals("nada")) {
            publicacionExistente.setDetallesEstadoLibro(publicacion.getDetallesEstadoLibro());
        }
        if (publicacion.getLimiteDias() != 0) {
            publicacionExistente.setLimiteDias(publicacion.getLimiteDias());
        }

        if (publicacion.getEstadoPublicacion() != Estado.Indefinido) {
            publicacionExistente.setEstadoPublicacion(publicacion.getEstadoPublicacion());
        }

        return publicacionRepository.save(publicacionExistente);
    }

    /**
     * Modifica una publicación (solo para el usuario que la creo)
     * @param id El ID de la publicación a modificar
     * @param publicacion La publicación con los nuevos datos
     * @param usuario El usuario que intenta modificar la publicación
     * @return La publicación modificada
     * @throws EntityNotFoundException (404) Si la publicación no pertenece al usuario
     */
    public Publicacion modificarPublicacionUsuario(Long id, Publicacion publicacion, Usuario usuario) {
        Publicacion publicacionExistente = obtenerPublicacionPorId(id);
        if (!publicacionExistente.getUsuario().getId().equals(usuario.getId())){
            throw new EntityNotFoundException("Publicación no encontrada.");
        }

        if (!publicacion.getDescripcion().equals("nada")) {
            publicacionExistente.setDescripcion(publicacion.getDescripcion());
        }

        if (publicacion.getLimiteDias() != 0) {
            publicacionExistente.setLimiteDias(publicacion.getLimiteDias());
        }
        return publicacionRepository.save(publicacionExistente);
    }

    /**
     * Modifica el estado de una publicación
     * @param publicacion La publicación a modificar
     * @param estado El nuevo estado de la publicación
     * @return La publicación con el estado modificado
     */
    public Publicacion modificarEstadoPublicacion(Publicacion publicacion, Estado estado) {
        publicacion.setEstadoPublicacion(estado);
        return publicacionRepository.save(publicacion);
    }

    /**
     * Obtiene las publicaciones de una categoría
     * @param categoría La categoría que se filtra
     * @return Las publicaciones filtradas por la categoría
    
    public Set<Publicacion> obtenerPublicacionPorCategoria(Categoria categoria) {
        return new HashSet<>(publicacionRepository.findAllByCategoria(categoria));
    }*/
}