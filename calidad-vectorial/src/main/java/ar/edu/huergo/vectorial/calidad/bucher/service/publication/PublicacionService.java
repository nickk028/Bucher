package ar.edu.huergo.vectorial.calidad.bucher.service.publication;

import java.util.HashSet;
import java.util.Set;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Libro;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.Estado;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.Publicacion;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;
import ar.edu.huergo.vectorial.calidad.bucher.repository.publication.PublicacionRepository;
import ar.edu.huergo.vectorial.calidad.bucher.service.book.LibroService;
import ar.edu.huergo.vectorial.calidad.bucher.service.security.UsuarioService;

@Service
public class PublicacionService {

    @Autowired
    private PublicacionRepository publicacionRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private LibroService libroService;

    public Set<Publicacion> obtenerTodosPublicaciones() {
        return new HashSet<>(publicacionRepository.findAll());
    }

    public Publicacion crearPublicacion(Publicacion publicacion, String titulo, String username) {
        Usuario usuarioIngresado = usuarioService.obtenerUsuarioPorNombre(username);
        Libro libroIngresado = libroService.obtenerLibroPorTitulo(titulo);
        LocalDate fechaActual = LocalDate.now();

        publicacion.setUsuarioCreador(usuarioIngresado);
        publicacion.setFechaCreacion(fechaActual);
        publicacion.setEstadoPublicacion(Estado.Disponible);
        publicacion.setLibro(libroIngresado);

        return publicacion;
    }
}