package ar.edu.huergo.vectorial.calidad.bucher.service.bookuser;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
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

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests de Unidad - LibroUsuarioService")
public class LibroUsuarioServiceTest {

    @InjectMocks
    private LibroUsuarioService libroUsuarioService;

    private Biblioteca bibliotecaEjemplo;
    private LibroUsuario libroUsuario1;
    private LibroUsuario libroUsuario2;
    private Libro libroEjemplo;

    @BeforeEach
    void setUp() {
        // Crear usuario
        Usuario usuario = new Usuario("usuario@example.com", "Password123!@#$%^&");
        usuario.setId(1L);
        usuario.setNickname("lector123");
        usuario.setAvatar(Avatar.ALICIA);
        usuario.setRoles(Set.of(new Rol("LECTOR")));

        // Crear biblioteca
        bibliotecaEjemplo = new Biblioteca();
        bibliotecaEjemplo.setId(1L);
        bibliotecaEjemplo.setUsuario(usuario);
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
        libroUsuario2.setPaginaActual(417);
        libroUsuario2.setEstadoLectura(EstadoLectura.leido);
        libroUsuario2.setPuntuacion(95);
        libroUsuario2.setLibro(libroEjemplo);
        libroUsuario2.setBiblioteca(bibliotecaEjemplo);

        bibliotecaEjemplo.getLibrosUsuario().add(libroUsuario1);
        bibliotecaEjemplo.getLibrosUsuario().add(libroUsuario2);
    }

    @Test
    @DisplayName("Debería extraer libros usuario de la biblioteca")
    void deberiaExtraerLibrosUsuario() {
        // When
        List<LibroUsuario> resultado = libroUsuarioService.extraerLibrosUsuario(bibliotecaEjemplo);

        // Then
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals(libroUsuario1.getId(), resultado.get(0).getId());
        assertEquals(libroUsuario2.getId(), resultado.get(1).getId());
    }

    @Test
    @DisplayName("Debería modificar libro usuario correctamente")
    void deberiaModificarLibroUsuario() {
        // Given
        LibroUsuario libroUsuarioAModificar = new LibroUsuario();
        libroUsuarioAModificar.setId(1L);
        libroUsuarioAModificar.setPaginaActual(50);
        libroUsuarioAModificar.setEstadoLectura(EstadoLectura.leyendo);
        libroUsuarioAModificar.setPuntuacion(70);

        LibroUsuario libroUsuarioNuevo = new LibroUsuario();
        libroUsuarioNuevo.setPaginaActual(150);
        libroUsuarioNuevo.setEstadoLectura(EstadoLectura.leido);
        libroUsuarioNuevo.setPuntuacion(90);

        // When
        LibroUsuario resultado = libroUsuarioService.modificarLibroUsuario(libroUsuarioAModificar, libroUsuarioNuevo);

        // Then
        assertNotNull(resultado);
        assertEquals(EstadoLectura.leido, resultado.getEstadoLectura());
        assertEquals(150, resultado.getPaginaActual());
        assertEquals(90, resultado.getPuntuacion());
    }
}