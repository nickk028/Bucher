package ar.edu.huergo.vectorial.calidad.bucher.service.publication;

import java.time.LocalDate;
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

    public Set<Publicacion> obtenerTodasLasPublicaciones() {
        return new HashSet<>(publicacionRepository.findAll());
    }

    public Publicacion obtenerPublciacionPorId(Long id) throws EntityNotFoundException {
        return publicacionRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Publicacion no encontrada"));
    }

    public boolean verificarPublicacionUsuario(Usuario usario, Long id) {
        Publicacion publicacion = obtenerPublciacionPorId(id);
        return (usario.equals(publicacion.getUsuario()));
    }

    public Set<Publicacion> obtenerPublicacionesPorUsuario(Usuario usuario) {
        return new HashSet<>(publicacionRepository.findAllByUsuario(usuario));
    }

    public Publicacion crearPublicacion(Publicacion publicacion, String titulo, String username) {
        Usuario usuarioIngresado = usuarioService.obtenerUsuarioPorNombre(username);
        Libro libroIngresado = libroService.obtenerLibroPorTitulo(titulo);
        LocalDate fechaActual = LocalDate.now();

        publicacion.setUsuario(usuarioIngresado);
        publicacion.setFechaCreacion(fechaActual);
        publicacion.setEstadoPublicacion(Estado.Disponible);
        publicacion.setDetallesEstadoLibro("Desconocido");
        publicacion.setLibro(libroIngresado);

        return publicacionRepository.save(publicacion);
    }

    public void eliminarPublicacion(Long id) {
        Publicacion publicacion = obtenerPublciacionPorId(id);
        publicacionRepository.delete(publicacion);
    }

    // Completar con ifs de validacion
    public Publicacion modificarPublicacionAdmin(Long id, Publicacion publicacion) {
        Publicacion publicacionExistente = obtenerPublciacionPorId(id);

        publicacionExistente.setLimiteDias(publicacion.getLimiteDias());
        publicacionExistente.setDescripcion(publicacion.getDescripcion());
        publicacionExistente.setDetallesEstadoLibro(publicacion.getDetallesEstadoLibro());
        publicacionExistente.setEstadoPublicacion(publicacion.getEstadoPublicacion());

        return publicacionRepository.save(publicacionExistente);
    }

    //Completar con ifs de validacion
    public Publicacion modificarPublicacionUsuario(Long id, Publicacion publicacion, Usuario usuario) {
        Publicacion publicacionExistente = obtenerPublciacionPorId(id);

        if (!publicacionExistente.getUsuario().getId().equals(usuario.getId())){
            throw new EntityNotFoundException("Publicación no encontrada.");
        }

        publicacionExistente.setLimiteDias(publicacion.getLimiteDias());
        publicacionExistente.setDescripcion(publicacion.getDescripcion());

        return publicacionRepository.save(publicacionExistente);
    }
}