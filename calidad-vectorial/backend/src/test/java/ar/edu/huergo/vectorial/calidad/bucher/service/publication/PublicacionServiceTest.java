package ar.edu.huergo.vectorial.calidad.bucher.service.publication;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Autor;
import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Categoria;
import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Editorial;
import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Libro;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.Estado;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.Publicacion;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Avatar;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Rol;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;
import ar.edu.huergo.vectorial.calidad.bucher.repository.publication.PublicacionRepository;
import ar.edu.huergo.vectorial.calidad.bucher.service.book.LibroService;
import ar.edu.huergo.vectorial.calidad.bucher.service.security.UsuarioService;
import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests de Unidad - PublicacionService")
public class PublicacionServiceTest {

    @Mock
    private PublicacionRepository publicacionRepository;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private LibroService libroService;

    @InjectMocks
    private PublicacionService publicacionService;

    private Publicacion publicacionEjemplo;
    private Usuario usuarioEjemplo;
    private Libro libroEjemplo;

    @BeforeEach
    void setUp() {
        // Crear usuario
        usuarioEjemplo = new Usuario("usuario@example.com", "Password123!@#$%^&");
        usuarioEjemplo.setId(1L);
        usuarioEjemplo.setNickname("lector123");
        usuarioEjemplo.setAvatar(Avatar.ALICIA);
        usuarioEjemplo.setRoles(Set.of(new Rol("LECTOR")));

        // Crear libro
        Autor autor = new Autor("Gabriel García Márquez", "https://es.wikipedia.org/wiki/Gabriel_García_Márquez");
        Editorial editorial = new Editorial("Editorial Sudamericana", "https://es.wikipedia.org/wiki/Editorial_Sudamericana");
        
        libroEjemplo = new Libro();
        libroEjemplo.setId(1L);
        libroEjemplo.setTitulo("Cien Años de Soledad");
        libroEjemplo.setDescripcion("Una novela emblemática del realismo mágico.");
        libroEjemplo.setPaginas(417);
        libroEjemplo.setEdicion("Primera edición");
        libroEjemplo.setCalificacion(90);
        libroEjemplo.setFechaPublicacion(LocalDate.of(1967, 5, 30));
        libroEjemplo.setUrlFoto("http://imagen.com/portada.jpg");
        libroEjemplo.setPrecio(1500.00);
        libroEjemplo.setCategoria(Set.of(Categoria.realismomagico));
        libroEjemplo.setAutor(autor);
        libroEjemplo.setEditorial(editorial);

        // Crear publicación
        publicacionEjemplo = new Publicacion();
        publicacionEjemplo.setId(1L);
        publicacionEjemplo.setUsuario(usuarioEjemplo);
        publicacionEjemplo.setFechaCreacion(LocalDate.now());
        publicacionEjemplo.setDescripcion("Libro en excelente estado, listo para prestar");
        publicacionEjemplo.setLimiteDias(14);
        publicacionEjemplo.setDetallesEstadoLibro("Nuevo - Sin uso");
        publicacionEjemplo.setEstadoPublicacion(Estado.Disponible);
        publicacionEjemplo.setLibro(libroEjemplo);
    }

    @Test
    @DisplayName("Debería obtener publicación por id")
    void deberiaObtenerPublicacionPorId() {
        // Given
        Long idPublicacion = 1L;
        when(publicacionRepository.findById(idPublicacion))
                .thenReturn(Optional.of(publicacionEjemplo));

        // When
        Publicacion resultado = publicacionService.obtenerPublicacionPorId(idPublicacion);

        // Then
        assertNotNull(resultado);
        assertEquals(publicacionEjemplo.getId(), resultado.getId());
        assertEquals(publicacionEjemplo.getDescripcion(), resultado.getDescripcion());
        verify(publicacionRepository, times(1)).findById(idPublicacion);
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando no encuentra publicación")
    void deberiaLanzarExcepcionCuandoNoEncuentraPublicacion() {
        // Given
        Long idPublicacionInexistente = 999L;
        when(publicacionRepository.findById(idPublicacionInexistente))
                .thenReturn(Optional.empty());

        // When & Then
        assertThrows(EntityNotFoundException.class, () -> {
            publicacionService.obtenerPublicacionPorId(idPublicacionInexistente);
        });
        verify(publicacionRepository, times(1)).findById(idPublicacionInexistente);
    }

    @Test
    @DisplayName("Debería obtener todas las publicaciones por usuario")
    void deberiaObtenerPublicacionesPorUsuario() {
        // Given
        List<Publicacion> publicaciones = List.of(publicacionEjemplo);
        when(publicacionRepository.findAllByUsuario(usuarioEjemplo))
                .thenReturn(publicaciones);

        // When
        Set<Publicacion> resultado = publicacionService.obtenerPublicacionesPorUsuario(usuarioEjemplo);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertTrue(resultado.contains(publicacionEjemplo));
        verify(publicacionRepository, times(1)).findAllByUsuario(usuarioEjemplo);
    }

    @Test
    @DisplayName("Debería crear publicación correctamente")
    void deberiaCrearPublicacion() {
        // Given
        String titulo = "Cien Años de Soledad";
        String username = "usuario@example.com";
        
        Publicacion nuevaPublicacion = new Publicacion();
        nuevaPublicacion.setDescripcion("Nueva publicación de prueba");
        nuevaPublicacion.setLimiteDias(21);

        when(usuarioService.obtenerUsuarioPorNombre(username))
                .thenReturn(usuarioEjemplo);
        when(libroService.obtenerLibroPorTitulo(titulo))
                .thenReturn(libroEjemplo);
        when(publicacionRepository.save(any(Publicacion.class)))
                .thenReturn(nuevaPublicacion);

        // When
        Publicacion resultado = publicacionService.crearPublicacion(nuevaPublicacion, titulo, username);

        // Then
        assertNotNull(resultado);
        assertEquals(usuarioEjemplo, nuevaPublicacion.getUsuario());
        assertEquals(Estado.Disponible, nuevaPublicacion.getEstadoPublicacion());
        assertEquals(libroEjemplo, nuevaPublicacion.getLibro());
        verify(usuarioService, times(1)).obtenerUsuarioPorNombre(username);
        verify(libroService, times(1)).obtenerLibroPorTitulo(titulo);
        verify(publicacionRepository, times(1)).save(nuevaPublicacion);
    }

    @Test
    @DisplayName("Debería modificar estado de publicación")
    void deberiaModificarEstadoPublicacion() {
        // Given
        Estado nuevoEstado = Estado.Prestado;
        when(publicacionRepository.save(any(Publicacion.class)))
                .thenReturn(publicacionEjemplo);

        // When
        Publicacion resultado = publicacionService.modificarEstadoPublicacion(publicacionEjemplo, nuevoEstado);

        // Then
        assertNotNull(resultado);
        assertEquals(Estado.Prestado, publicacionEjemplo.getEstadoPublicacion());
        verify(publicacionRepository, times(1)).save(publicacionEjemplo);
    }

    @Test
    @DisplayName("Debería obtener publicaciones por categoría")
    void deberiaObtenerPublicacionesPorCategoria() {
        // Given
        Categoria categoria = Categoria.realismomagico;
        List<Publicacion> publicaciones = List.of(publicacionEjemplo);
        when(publicacionRepository.findAllByCategoria(categoria))
                .thenReturn(publicaciones);

        // When
        Set<Publicacion> resultado = publicacionService.obtenerPublicacionesPorCategoria(categoria);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertTrue(resultado.contains(publicacionEjemplo));
        verify(publicacionRepository, times(1)).findAllByCategoria(categoria);
    }
}