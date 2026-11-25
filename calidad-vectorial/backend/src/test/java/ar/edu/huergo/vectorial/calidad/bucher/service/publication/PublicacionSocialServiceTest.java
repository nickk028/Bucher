package ar.edu.huergo.vectorial.calidad.bucher.service.publication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Libro;
import ar.edu.huergo.vectorial.calidad.bucher.entity.deletion.RegistroEliminacionPublicacionSocial;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.PublicacionSocial;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;
import ar.edu.huergo.vectorial.calidad.bucher.repository.publication.PublicacionSocialRepository;
import ar.edu.huergo.vectorial.calidad.bucher.repository.security.UsuarioRepository;
import ar.edu.huergo.vectorial.calidad.bucher.service.book.LibroService;
import ar.edu.huergo.vectorial.calidad.bucher.service.security.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests Unitarios - PublicacionSocialService")
class PublicacionSocialServiceTest {

    @Mock PublicacionSocialRepository repo;
    @Mock UsuarioRepository usuarioRepo;
    @Mock UsuarioService usuarioService;
    @Mock LibroService libroService;
    @InjectMocks PublicacionSocialService service;

    private Usuario autor;
    private Usuario otro;

    @BeforeEach
    void setUp() {
        autor = new Usuario();
        autor.setId(1L);
        autor.setUsername("autor@gmail.com");
        autor.setNickname("AutorNick");

        otro = new Usuario();
        otro.setId(2L);
        otro.setUsername("otro@gmail.com");
        otro.setNickname("OtroNick");
    }

    @Test 
    @DisplayName("Obtener todas: diferencia admin (incluye eliminadas) de lector")
    void obtenerTodas() {
        Page<PublicacionSocial> pagina = new PageImpl<>(List.of(new PublicacionSocial()));
        when(repo.findAllByPublicacionReferenciadaIsNull(any(Pageable.class))).thenReturn(pagina);
        when(repo.findAllByPublicacionReferenciadaIsNullAndRegistroEliminacionIsNull(any(Pageable.class)))
            .thenReturn(pagina);

        assertEquals(1, service.obtenerTodasLasPublicacionesSociales("RECIENTES", 0, 10, true).getTotalElements());
        assertEquals(1, service.obtenerTodasLasPublicacionesSociales("POPULARES", 0, 10, false).getTotalElements());
        assertEquals(1, service.obtenerTodasLasPublicacionesSociales("ANTIGUAS", 0, 10, false).getTotalElements());
    }

    @Test 
    @DisplayName("Consultas por ID, respuestas, usuario y libro")
    void consultasFiltradas() {
        PublicacionSocial publicacion = new PublicacionSocial();
        publicacion.setId(5L);
        Page<PublicacionSocial> pagina = new PageImpl<>(List.of(publicacion));

        when(repo.findById(5L)).thenReturn(Optional.of(publicacion));
        when(repo.findById(99L)).thenReturn(Optional.empty());
        when(repo.findAllByPublicacionReferenciadaId(eq(5L), any())).thenReturn(pagina);

        when(usuarioRepo.existsById(1L)).thenReturn(true);
        when(usuarioRepo.existsById(99L)).thenReturn(false);
        when(repo.findAllByUsuarioId(eq(1L), any())).thenReturn(pagina);
        when(repo.findAllByUsuarioIdAndRegistroEliminacionIsNull(eq(1L), any())).thenReturn(pagina);

        when(libroService.obtenerLibroPorId(10L)).thenReturn(new Libro());
        when(libroService.obtenerLibroPorId(99L)).thenThrow(new EntityNotFoundException("No encontrado"));
        when(repo.findAllByLibroIdAndPublicacionReferenciadaIsNull(eq(10L), any())).thenReturn(pagina);
        when(repo.findAllByLibroIdAndPublicacionReferenciadaIsNullAndRegistroEliminacionIsNull(eq(10L), any()))
            .thenReturn(pagina);

        assertEquals(1, service.obtenerRespuestas(5L, 0, 10).getTotalElements());
        assertThrows(EntityNotFoundException.class, () -> service.obtenerRespuestas(99L, 0, 10));

        assertEquals(1, service.obtenerPublicacionesSocialesPorUsuario(1L, 0, 10, true).getTotalElements());
        assertEquals(1, service.obtenerPublicacionesSocialesPorUsuario(1L, 0, 10, false).getTotalElements());
        assertThrows(EntityNotFoundException.class,
            () -> service.obtenerPublicacionesSocialesPorUsuario(99L, 0, 10, false));

        assertEquals(1, service.obtenerPublicacionesSocialesPorLibro(10L, 0, 10, true).getTotalElements());
        assertEquals(1, service.obtenerPublicacionesSocialesPorLibro(10L, 0, 10, false).getTotalElements());
        assertThrows(EntityNotFoundException.class,
            () -> service.obtenerPublicacionesSocialesPorLibro(99L, 0, 10, false));
    }

    @Test 
    @DisplayName("Conteos: publicaciones por usuario y mapa de respuestas")
    void conteos() {
        when(usuarioService.obtenerUsuarioPorNombre("autor@gmail.com")).thenReturn(autor);
        when(repo.countByUsuarioIdAndRegistroEliminacionIsNull(1L)).thenReturn(4L);

        PublicacionSocialRepository.ConteoRespuestas conteo = mock(PublicacionSocialRepository.ConteoRespuestas.class);
        when(conteo.getPublicacionId()).thenReturn(5L);
        when(conteo.getCantidad()).thenReturn(3L);
        when(repo.contarRespuestasPorPublicacion(List.of(5L))).thenReturn(List.of(conteo));

        assertEquals(4L, service.obtenerCantidadPublicacionesSocialesPorUsuario("autor@gmail.com"));
        assertTrue(service.obtenerCantidadRespuestas(List.of()).isEmpty());

        Map<Long, Long> respuesta = service.obtenerCantidadRespuestas(List.of(5L));
        assertEquals(3L, respuesta.get(5L));
    }

    @Test 
    @DisplayName("crearPublicacionSocial: crea con libro y etiquetas (ignora duplicados y auto-etiqueta)")
    void crearConLibroYEtiquetas() {
        Libro libro = new Libro();
        when(usuarioService.obtenerUsuarioPorNombre("autor@gmail.com")).thenReturn(autor);
        when(libroService.obtenerLibroPorId(10L)).thenReturn(libro);
        when(usuarioRepo.findByNickname("OtroNick")).thenReturn(Optional.of(otro));
        when(usuarioRepo.findByNickname("NoExiste")).thenReturn(Optional.empty());
        when(repo.save(any())).thenAnswer(i -> i.getArgument(0));

        PublicacionSocial nueva = new PublicacionSocial();
        PublicacionSocial creada = service.crearPublicacionSocial(
            nueva, 10L, null, List.of("OtroNick", "OtroNick", "AutorNick", "NoExiste"), "autor@gmail.com"
        );

        assertSame(autor, creada.getUsuario());
        assertSame(libro, creada.getLibro());
        assertEquals(2, creada.getEtiquetas().size()); // OtroNick (con usuario) y NoExiste (usuario null)
    }

    @Test 
    @DisplayName("crearPublicacionSocial en respuesta: hereda libro; rechaza libro explícito o padre eliminado")
    void crearRespuesta() {
        Libro libroPadre = new Libro();
        PublicacionSocial padre = new PublicacionSocial();
        padre.setId(5L);
        padre.setLibro(libroPadre);

        when(usuarioService.obtenerUsuarioPorNombre("autor@gmail.com")).thenReturn(autor);
        when(repo.findById(5L)).thenReturn(Optional.of(padre));
        when(repo.save(any())).thenAnswer(i -> i.getArgument(0));

        // Respuesta con libroId -> error
        assertThrows(IllegalArgumentException.class,
            () -> service.crearPublicacionSocial(new PublicacionSocial(), 10L, 5L, null, "autor@gmail.com"));

        // Respuesta valida -> hereda libro
        PublicacionSocial respuesta = service.crearPublicacionSocial(
            new PublicacionSocial(), null, 5L, null, "autor@gmail.com"
        );
        assertSame(padre, respuesta.getPublicacionReferenciada());
        assertSame(libroPadre, respuesta.getLibro());

        // Padre eliminado -> error
        padre.setRegistroEliminacion(new RegistroEliminacionPublicacionSocial());
        assertThrows(IllegalArgumentException.class,
            () -> service.crearPublicacionSocial(new PublicacionSocial(), null, 5L, null, "autor@gmail.com"));
    }

    @Test
    @DisplayName("Eliminar publicacion: autor usa razón automatica; admin exige razón")
    void eliminar() {
        PublicacionSocial publicacion = new PublicacionSocial();
        publicacion.setId(5L);
        publicacion.setUsuario(autor);

        when(usuarioService.obtenerUsuarioPorNombre("autor@gmail.com")).thenReturn(autor);
        when(usuarioService.obtenerUsuarioPorNombre("otro@gmail.com")).thenReturn(otro);
        when(repo.findById(5L)).thenReturn(Optional.of(publicacion));

        // Lector no admin -> EntityNotFoundException
        assertThrows(EntityNotFoundException.class,
            () -> service.eliminarPublicacionSocial(5L, "razon", "otro@gmail.com", false));

        // Admin sin razón -> IllegalArgumentException
        assertThrows(IllegalArgumentException.class,
            () -> service.eliminarPublicacionSocial(5L, "   ", "otro@gmail.com", true));

        // Autor elimina -> razón automática
        service.eliminarPublicacionSocial(5L, null, "autor@gmail.com", false);
        assertEquals("Eliminada por usuario", publicacion.getRegistroEliminacion().getRazon());
        assertSame(autor, publicacion.getRegistroEliminacion().getUsuarioEliminador());

        // Ya eliminada -> IllegalArgumentException
        assertThrows(IllegalArgumentException.class,
            () -> service.eliminarPublicacionSocial(5L, "Spam", "otro@gmail.com", true));
    }
}
