package ar.edu.huergo.vectorial.calidad.bucher.service.bookuser;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Libro;
import ar.edu.huergo.vectorial.calidad.bucher.entity.bookuser.EstadoLectura;
import ar.edu.huergo.vectorial.calidad.bucher.entity.bookuser.LibroUsuario;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;
import ar.edu.huergo.vectorial.calidad.bucher.repository.bookuser.LibroUsuarioRepository;
import ar.edu.huergo.vectorial.calidad.bucher.repository.security.UsuarioRepository;
import ar.edu.huergo.vectorial.calidad.bucher.service.book.LibroService;
import ar.edu.huergo.vectorial.calidad.bucher.service.security.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests Unitarios - LibroUsuarioService")
class LibroUsuarioServiceTest {

    @Mock LibroUsuarioRepository repo;
    @Mock UsuarioRepository usuarioRepo;
    @Mock UsuarioService usuarioService;
    @Mock LibroService libroService;
    @InjectMocks LibroUsuarioService service;

    private Usuario usuario;
    private LibroUsuario lu1;

    @BeforeEach
    void setUp() {
        lu1 = new LibroUsuario();
        lu1.setId(10L);
        lu1.setEstadoLectura(EstadoLectura.Leyendo);
        lu1.setPaginaActual(50);
        lu1.setPuntuacion(8);

        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUsername("lector@gmail.com");
        usuario.setLibrosUsuario(new ArrayList<>(List.of(lu1)));
    }

    @Test 
    @DisplayName("Consultas: obtiene lista, por ID (o 404 si ajeno), por estado y cantidad")
    void consultas() {
        when(usuarioService.obtenerUsuarioPorNombre("lector@gmail.com")).thenReturn(usuario);
        when(repo.findByUsuarioIdAndEstadoLectura(1L, EstadoLectura.Leyendo)).thenReturn(List.of(lu1));
        when(repo.countByUsuarioId(1L)).thenReturn(1L);

        assertEquals(1, service.obtenerLibrosUsuario("lector@gmail.com").size());
        assertEquals(lu1, service.obtenerLibroUsuarioPorId("lector@gmail.com", 10L));
        assertThrows(EntityNotFoundException.class,
            () -> service.obtenerLibroUsuarioPorId("lector@gmail.com", 99L));
        assertEquals(1, service.obtenerLibrosPorEstado("lector@gmail.com", EstadoLectura.Leyendo).size());
        assertEquals(1L, service.obtenerCantidadLibrosUsuarioPorUsuario("lector@gmail.com"));
    }

    @Test 
    @DisplayName("Subir: Asigna Pendiente si página 0, Leyendo si página > 0")
    void subir() {
        Libro libro = new Libro();
        when(usuarioService.obtenerUsuarioPorNombre("lector@gmail.com")).thenReturn(usuario);
        when(libroService.obtenerLibroPorTitulo("Dune")).thenReturn(libro);
        when(usuarioRepo.save(usuario)).thenReturn(usuario);

        LibroUsuario nuevoCero = new LibroUsuario();
        nuevoCero.setEstadoLectura(EstadoLectura.Indefinido);
        nuevoCero.setPaginaActual(0);

        service.subirLibroUsuario("lector@gmail.com", nuevoCero, "Dune");
        assertEquals(EstadoLectura.Pendiente, nuevoCero.getEstadoLectura());
        assertSame(libro, nuevoCero.getLibro());

        LibroUsuario nuevoAvanzado = new LibroUsuario();
        nuevoAvanzado.setEstadoLectura(EstadoLectura.Indefinido);
        nuevoAvanzado.setPaginaActual(25);

        service.subirLibroUsuario("lector@gmail.com", nuevoAvanzado, "Dune");
        assertEquals(EstadoLectura.Leyendo, nuevoAvanzado.getEstadoLectura());
    }

    @Test 
    @DisplayName("Modificar el libroUsuario: actualiza solo campos informados y rechaza ID inexistente")
    void modificar() {
        when(usuarioService.obtenerUsuarioPorNombre("lector@gmail.com")).thenReturn(usuario);
        when(repo.save(any())).thenAnswer(i -> i.getArgument(0));

        LibroUsuario cambios = new LibroUsuario();
        cambios.setEstadoLectura(EstadoLectura.Leido);
        cambios.setPaginaActual(0); // no pisa el 50 actual
        cambios.setPuntuacion(10);

        LibroUsuario mod = service.modificarLibroUsuario("lector@gmail.com", 10L, cambios);
        assertEquals(EstadoLectura.Leido, mod.getEstadoLectura());
        assertEquals(50, mod.getPaginaActual());
        assertEquals(10, mod.getPuntuacion());

        assertThrows(EntityNotFoundException.class,
            () -> service.modificarLibroUsuario("lector@gmail.com", 99L, cambios));
    }

    @Test 
    @DisplayName("Eliminar el libroUsuario: borra si pertenece al usuario o lanza EntityNotFoundException")
    void eliminar() {
        when(usuarioService.obtenerUsuarioPorNombre("lector@gmail.com")).thenReturn(usuario);

        service.eliminarLibroUsuario("lector@gmail.com", 10L);
        verify(repo).delete(lu1);

        assertThrows(EntityNotFoundException.class,
            () -> service.eliminarLibroUsuario("lector@gmail.com", 99L));
    }
}