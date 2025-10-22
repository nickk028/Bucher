package ar.edu.huergo.vectorial.calidad.bucher.service.bookuser;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
import ar.edu.huergo.vectorial.calidad.bucher.entity.bookuser.Biblioteca;
import ar.edu.huergo.vectorial.calidad.bucher.entity.bookuser.EstadoLectura;
import ar.edu.huergo.vectorial.calidad.bucher.entity.bookuser.LibroUsuario;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Avatar;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Rol;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;
import ar.edu.huergo.vectorial.calidad.bucher.repository.bookuser.BibliotecaRepository;
import ar.edu.huergo.vectorial.calidad.bucher.repository.bookuser.LibroUsuarioRepository;
import ar.edu.huergo.vectorial.calidad.bucher.service.book.LibroService;
import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests de Unidad - BibliotecaService")
public class BibliotecaServiceTest {

    @Mock
    private BibliotecaRepository bibliotecaRepository;

    @Mock
    private LibroUsuarioRepository libroUsuarioRepository;

    @Mock
    private LibroService libroService;

    @InjectMocks
    private BibliotecaService bibliotecaService;

    private Biblioteca bibliotecaEjemplo;
    private LibroUsuario libroUsuario1;
    private LibroUsuario libroUsuario2;
    private Libro libroEjemplo;
    private Usuario usuarioEjemplo;

    @BeforeEach
    void setUp() {
        // Crear usuario
        usuarioEjemplo = new Usuario("usuario@example.com", "Password123!@#$%^&");
        usuarioEjemplo.setId(1L);
        usuarioEjemplo.setNickname("lector123");
        usuarioEjemplo.setAvatar(Avatar.ALICIA);
        usuarioEjemplo.setRoles(Set.of(new Rol("LECTOR")));

        // Crear biblioteca
        bibliotecaEjemplo = new Biblioteca();
        bibliotecaEjemplo.setId(1L);
        bibliotecaEjemplo.setUsuario(usuarioEjemplo);
        bibliotecaEjemplo.setLibrosUsuario(new ArrayList<>());

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

        // Crear libros usuario
        libroUsuario1 = new LibroUsuario();
        libroUsuario1.setId(1L);
        libroUsuario1.setPaginaActual(100);
        libroUsuario1.setEstadoLectura(EstadoLectura.leyendo);
        libroUsuario1.setPuntuacion(85);
        libroUsuario1.setLibro(libroEjemplo);
        libroUsuario1.setBiblioteca(bibliotecaEjemplo);

        libroUsuario2 = new LibroUsuario();
        libroUsuario2.setId(2L);
        libroUsuario2.setPaginaActual(0);
        libroUsuario2.setEstadoLectura(EstadoLectura.pendiente);
        libroUsuario2.setPuntuacion(0);
        libroUsuario2.setLibro(libroEjemplo);
        libroUsuario2.setBiblioteca(bibliotecaEjemplo);

        bibliotecaEjemplo.getLibrosUsuario().add(libroUsuario1);
        bibliotecaEjemplo.getLibrosUsuario().add(libroUsuario2);
    }

    @Test
    @DisplayName("Debería obtener biblioteca por id de usuario")
    void deberiaObtenerBiblioteca() {
        // Given
        Long idUsuario = 1L;
        when(bibliotecaRepository.findById(idUsuario))
                .thenReturn(Optional.of(bibliotecaEjemplo));

        // When
        Biblioteca resultado = bibliotecaService.obtenerBiblioteca(idUsuario);

        // Then
        assertNotNull(resultado);
        assertEquals(bibliotecaEjemplo.getId(), resultado.getId());
        assertEquals(usuarioEjemplo.getId(), resultado.getUsuario().getId());
        verify(bibliotecaRepository, times(1)).findById(idUsuario);
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando no encuentra biblioteca")
    void deberiaLanzarExcepcionCuandoNoEncuentraBiblioteca() {
        // Given
        Long idUsuarioInexistente = 999L;
        when(bibliotecaRepository.findById(idUsuarioInexistente))
                .thenReturn(Optional.empty());

        // When & Then
        assertThrows(EntityNotFoundException.class, () -> {
            bibliotecaService.obtenerBiblioteca(idUsuarioInexistente);
        });
        verify(bibliotecaRepository, times(1)).findById(idUsuarioInexistente);
    }

    @Test
    @DisplayName("Debería obtener libro usuario por posición")
    void deberiaObtenerLibroUsuarioPorPosicion() {
        // Given
        int posicion = 1;

        // When
        LibroUsuario resultado = bibliotecaService.obtenerLibroUsuarioPorPosicion(posicion, bibliotecaEjemplo);

        // Then
        assertNotNull(resultado);
        assertEquals(libroUsuario1.getId(), resultado.getId());
        assertEquals(EstadoLectura.leyendo, resultado.getEstadoLectura());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando posición es inválida")
    void deberiaLanzarExcepcionCuandoPosicionInvalida() {
        // Given
        int posicionInvalida = 0;

        // When & Then
        assertThrows(EntityNotFoundException.class, () -> {
            bibliotecaService.obtenerLibroUsuarioPorPosicion(posicionInvalida, bibliotecaEjemplo);
        });
    }

    @Test
    @DisplayName("Debería obtener libros por estado de lectura")
    void deberiaObtenerLibrosPorEstado() {
        // Given
        EstadoLectura estado = EstadoLectura.leyendo;
        List<LibroUsuario> librosleyendo = List.of(libroUsuario1);
        when(libroUsuarioRepository.findByBibliotecaAndEstadoLectura(bibliotecaEjemplo, estado))
                .thenReturn(librosleyendo);

        // When
        List<LibroUsuario> resultado = bibliotecaService.obtenerLibrosPorEstado(bibliotecaEjemplo, estado);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(EstadoLectura.leyendo, resultado.get(0).getEstadoLectura());
        verify(libroUsuarioRepository, times(1))
                .findByBibliotecaAndEstadoLectura(bibliotecaEjemplo, estado);
    }
}