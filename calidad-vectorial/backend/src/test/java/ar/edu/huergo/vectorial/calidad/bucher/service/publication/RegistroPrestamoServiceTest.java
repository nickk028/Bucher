package ar.edu.huergo.vectorial.calidad.bucher.service.publication;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.RegistroPrestamo;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Avatar;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Rol;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;
import ar.edu.huergo.vectorial.calidad.bucher.repository.publication.RegistroPrestamoRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests de Unidad - RegistroPrestamoService")
public class RegistroPrestamoServiceTest {

    @Mock
    private RegistroPrestamoRepository registroPrestamoRepository;

    @InjectMocks
    private RegistroPrestamoService registroPrestamoService;

    private RegistroPrestamo registroPrestamoEjemplo;
    private Usuario usuarioEjemplo;
    private Publicacion publicacionEjemplo;
    private Libro libroEjemplo;

    @BeforeEach
    void setUp() {
        // Crear usuario
        usuarioEjemplo = new Usuario("usuario@example.com", "Password123!@#$%^&");
        usuarioEjemplo.setId(1L);
        usuarioEjemplo.setNickname("lector123");
        usuarioEjemplo.setAvatar(Avatar.ALICIA);
        usuarioEjemplo.setRoles(Set.of(new Rol("LECTOR")));
        usuarioEjemplo.setPrestamos(new ArrayList<>());

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
        publicacionEjemplo.setDescripcion("Libro en excelente estado");
        publicacionEjemplo.setLimiteDias(14);
        publicacionEjemplo.setDetallesEstadoLibro("Nuevo");
        publicacionEjemplo.setEstadoPublicacion(Estado.Disponible);
        publicacionEjemplo.setLibro(libroEjemplo);

        // Crear registro préstamo
        registroPrestamoEjemplo = new RegistroPrestamo();
        registroPrestamoEjemplo.setId(1L);
        registroPrestamoEjemplo.setFechaPrestamo(LocalDate.now());
        registroPrestamoEjemplo.setFechaDevolucion(null);
        registroPrestamoEjemplo.setPublicacion(publicacionEjemplo);
        registroPrestamoEjemplo.setUsuario(usuarioEjemplo);
    }

    @Test
    @DisplayName("Debería crear registro de préstamo correctamente")
    void deberiaCrearRegistroPrestamo() {
        // Given
        when(registroPrestamoRepository.save(any(RegistroPrestamo.class)))
                .thenReturn(registroPrestamoEjemplo);

        // When
        RegistroPrestamo resultado = registroPrestamoService.crearRegistroUsuario(usuarioEjemplo, publicacionEjemplo);

        // Then
        assertNotNull(resultado);
        assertEquals(publicacionEjemplo, resultado.getPublicacion());
        assertEquals(usuarioEjemplo, resultado.getUsuario());
        assertNull(resultado.getFechaDevolucion());
        verify(registroPrestamoRepository, times(1)).save(any(RegistroPrestamo.class));
    }

    @Test
    @DisplayName("Debería marcar registro como devuelto")
    void deberiaMarcarRegistroDevolucion() {
        // Given
        when(registroPrestamoRepository.save(any(RegistroPrestamo.class)))
                .thenReturn(registroPrestamoEjemplo);

        // When
        RegistroPrestamo resultado = registroPrestamoService.marcarRegistroDevolucion(registroPrestamoEjemplo);

        // Then
        assertNotNull(resultado);
        assertNotNull(resultado.getFechaDevolucion());
        assertEquals(LocalDate.now(), resultado.getFechaDevolucion());
        verify(registroPrestamoRepository, times(1)).save(registroPrestamoEjemplo);
    }

    @Test
    @DisplayName("Debería obtener registro de préstamo activo por publicación")
    void deberiaObtenerRegistroPrestamoActivo() {
        // Given
        when(registroPrestamoRepository.findByPublicacionAndFechaDevolucionIsNull(publicacionEjemplo))
                .thenReturn(Optional.of(registroPrestamoEjemplo));

        // When
        RegistroPrestamo resultado = registroPrestamoService.obtenerRegistroPrestamo(publicacionEjemplo);

        // Then
        assertNotNull(resultado);
        assertEquals(registroPrestamoEjemplo.getId(), resultado.getId());
        assertEquals(publicacionEjemplo, resultado.getPublicacion());
        assertNull(resultado.getFechaDevolucion());
        verify(registroPrestamoRepository, times(1))
                .findByPublicacionAndFechaDevolucionIsNull(publicacionEjemplo);
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando no encuentra registro activo")
    void deberiaLanzarExcepcionCuandoNoEncuentraRegistroActivo() {
        // Given
        when(registroPrestamoRepository.findByPublicacionAndFechaDevolucionIsNull(publicacionEjemplo))
                .thenReturn(Optional.empty());

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            registroPrestamoService.obtenerRegistroPrestamo(publicacionEjemplo);
        });
        verify(registroPrestamoRepository, times(1))
                .findByPublicacionAndFechaDevolucionIsNull(publicacionEjemplo);
    }

    @Test
    @DisplayName("Debería obtener todos los registros de préstamo por usuario")
    void deberiaObtenerRegistrosPrestamosPorUsuario() {
        // Given
        List<RegistroPrestamo> registros = List.of(registroPrestamoEjemplo);
        when(registroPrestamoRepository.findAllByUsuario(usuarioEjemplo))
                .thenReturn(registros);

        // When
        List<RegistroPrestamo> resultado = registroPrestamoService.obtenerRegistrosPrestamoPorUsuario(usuarioEjemplo);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(registroPrestamoEjemplo, resultado.get(0));
        verify(registroPrestamoRepository, times(1)).findAllByUsuario(usuarioEjemplo);
    }

    @Test
    @DisplayName("Debería obtener registros de préstamo de la última semana")
    void deberiaObtenerRegistrosPrestamosDeUltimaSemana() {
        // Given
        LocalDate fechaActual = LocalDate.now();
        LocalDate fechaInicioSemana = fechaActual.minusDays(7);
        List<RegistroPrestamo> registros = List.of(registroPrestamoEjemplo);
        
        when(registroPrestamoRepository.findByFechaPrestamoBetween(fechaInicioSemana, fechaActual))
                .thenReturn(registros);

        // When
        List<RegistroPrestamo> resultado = registroPrestamoService.obtenerRegistrosPrestamosDeLaSemana();

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(registroPrestamoEjemplo, resultado.get(0));
        verify(registroPrestamoRepository, times(1))
                .findByFechaPrestamoBetween(fechaInicioSemana, fechaActual);
    }
}