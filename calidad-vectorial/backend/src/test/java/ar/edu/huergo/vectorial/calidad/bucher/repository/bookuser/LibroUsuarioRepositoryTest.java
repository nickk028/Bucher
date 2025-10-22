package ar.edu.huergo.vectorial.calidad.bucher.repository.bookuser;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

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

@DataJpaTest
@DisplayName("Tests de Integración - LibroUsuarioRepository")
public class LibroUsuarioRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private LibroUsuarioRepository libroUsuarioRepository;

    private LibroUsuario libroUsuario1;
    private LibroUsuario libroUsuario2;
    private LibroUsuario libroUsuario3;

    private Biblioteca bibliotecaEjemplo;
    private Libro libroEjemplo1;
    private Libro libroEjemplo2;
    private Libro libroEjemplo3;

    @BeforeEach
    void setUp() {
        // Crear rol
        Rol rol = new Rol("LECTOR");
        entityManager.persist(rol);

        // Crear usuario
        Usuario usuario = new Usuario("usuario@example.com", "Password12345!@#$");
        usuario.setNickname("lector123");
        usuario.setAvatar(Avatar.ALICIA);
        usuario.setRoles(Set.of(rol));
        entityManager.persist(usuario);

        // Crear biblioteca
        bibliotecaEjemplo = new Biblioteca();
        bibliotecaEjemplo.setUsuario(usuario);
        bibliotecaEjemplo = entityManager.persistAndFlush(bibliotecaEjemplo);

        // Crear autor y editorial
        Autor autor = new Autor("Autor de ejemplo", "https://es.wikipedia.org/wiki/autor_ejemplo");
        entityManager.persist(autor);

        Editorial editorial = new Editorial("Editorial de ejemplo", "https://es.wikipedia.org/wiki/editorial_ejemplo");
        entityManager.persist(editorial);

        // Crear libros
        libroEjemplo1 = new Libro();
        libroEjemplo1.setTitulo("Cien Años de Soledad");
        libroEjemplo1.setDescripcion("Una novela emblemática del realismo mágico.");
        libroEjemplo1.setPaginas(417);
        libroEjemplo1.setEdicion("Primera edición");
        libroEjemplo1.setCalificacion(90);
        libroEjemplo1.setFechaPublicacion(LocalDate.of(1967, 5, 30));
        libroEjemplo1.setUrlFoto("http://imagen.com/portada.jpg");
        libroEjemplo1.setPrecio(1500.00);
        libroEjemplo1.setCategoria(Set.of(Categoria.realismomagico));
        libroEjemplo1.setAutor(autor);
        libroEjemplo1.setEditorial(editorial);
        entityManager.persist(libroEjemplo1);

        libroEjemplo2 = new Libro();
        libroEjemplo2.setTitulo("El Nombre del Viento");
        libroEjemplo2.setDescripcion("La historia de un joven músico con un pasado legendario.");
        libroEjemplo2.setPaginas(662);
        libroEjemplo2.setEdicion("Primera edición");
        libroEjemplo2.setCalificacion(95);
        libroEjemplo2.setFechaPublicacion(LocalDate.of(2007, 3, 27));
        libroEjemplo2.setUrlFoto("http://imagen.com/nombre_viento.jpg");
        libroEjemplo2.setPrecio(1800.00);
        libroEjemplo2.setCategoria(Set.of(Categoria.fantastico));
        libroEjemplo2.setAutor(autor);
        libroEjemplo2.setEditorial(editorial);
        entityManager.persist(libroEjemplo2);

        libroEjemplo3 = new Libro();
        libroEjemplo3.setTitulo("1984");
        libroEjemplo3.setDescripcion("Una distopía totalitaria.");
        libroEjemplo3.setPaginas(328);
        libroEjemplo3.setEdicion("Primera edición");
        libroEjemplo3.setCalificacion(88);
        libroEjemplo3.setFechaPublicacion(LocalDate.of(1949, 6, 8));
        libroEjemplo3.setUrlFoto("http://imagen.com/1984.jpg");
        libroEjemplo3.setPrecio(1200.00);
        libroEjemplo3.setCategoria(Set.of(Categoria.cienciaficcion));
        libroEjemplo3.setAutor(autor);
        libroEjemplo3.setEditorial(editorial);
        entityManager.persist(libroEjemplo3);

        // Crear libros usuario de prueba
        libroUsuario1 = new LibroUsuario();
        libroUsuario1.setPaginaActual(100);
        libroUsuario1.setEstadoLectura(EstadoLectura.leyendo);
        libroUsuario1.setPuntuacion(85);
        libroUsuario1.setLibro(libroEjemplo1);
        libroUsuario1.setBiblioteca(bibliotecaEjemplo);
        libroUsuario1 = entityManager.persistAndFlush(libroUsuario1);

        libroUsuario2 = new LibroUsuario();
        libroUsuario2.setPaginaActual(662);
        libroUsuario2.setEstadoLectura(EstadoLectura.leido);
        libroUsuario2.setPuntuacion(95);
        libroUsuario2.setLibro(libroEjemplo2);
        libroUsuario2.setBiblioteca(bibliotecaEjemplo);
        libroUsuario2 = entityManager.persistAndFlush(libroUsuario2);

        libroUsuario3 = new LibroUsuario();
        libroUsuario3.setPaginaActual(0);
        libroUsuario3.setEstadoLectura(EstadoLectura.pendiente);
        libroUsuario3.setPuntuacion(0);
        libroUsuario3.setLibro(libroEjemplo3);
        libroUsuario3.setBiblioteca(bibliotecaEjemplo);
        libroUsuario3 = entityManager.persistAndFlush(libroUsuario3);

        entityManager.clear();
    }

    @Test
    @DisplayName("Debería encontrar libros usuario por biblioteca y estado de lectura")
    void deberiaEncontrarLibrosUsuarioPorBibliotecaYEstado() {
        // When - Buscar libros en estado leyendo
        List<LibroUsuario> librosleyendo =
                libroUsuarioRepository.findByBibliotecaAndEstadoLectura(bibliotecaEjemplo, EstadoLectura.leyendo);

        // Then
        assertNotNull(librosleyendo);
        assertEquals(1, librosleyendo.size());
        assertEquals(EstadoLectura.leyendo, librosleyendo.get(0).getEstadoLectura());
        assertEquals("Cien Años de Soledad", librosleyendo.get(0).getLibro().getTitulo());

        // When - Buscar libros en estado leido
        List<LibroUsuario> librosleidos =
                libroUsuarioRepository.findByBibliotecaAndEstadoLectura(bibliotecaEjemplo, EstadoLectura.leido);

        // Then
        assertNotNull(librosleidos);
        assertEquals(1, librosleidos.size());
        assertEquals(EstadoLectura.leido, librosleidos.get(0).getEstadoLectura());

        // When - Buscar libros en estado pendiente
        List<LibroUsuario> librospendientes =
                libroUsuarioRepository.findByBibliotecaAndEstadoLectura(bibliotecaEjemplo, EstadoLectura.pendiente);

        // Then
        assertNotNull(librospendientes);
        assertEquals(1, librospendientes.size());
        assertEquals(EstadoLectura.pendiente, librospendientes.get(0).getEstadoLectura());
    }

    @Test
    @DisplayName("Debería guardar y recuperar libro usuario correctamente")
    void deberiaGuardarYRecuperarLibroUsuario() {
        // Given
        Libro nuevoLibro = new Libro();
        nuevoLibro.setTitulo("El Señor de los Anillos");
        nuevoLibro.setDescripcion("Una épica aventura fantástica.");
        nuevoLibro.setPaginas(1200);
        nuevoLibro.setEdicion("Primera edición");
        nuevoLibro.setCalificacion(98);
        nuevoLibro.setFechaPublicacion(LocalDate.of(1954, 7, 29));
        nuevoLibro.setUrlFoto("http://imagen.com/lotr.jpg");
        nuevoLibro.setPrecio(2500.00);
        nuevoLibro.setCategoria(Set.of(Categoria.fantastico));
        nuevoLibro.setAutor(entityManager.find(Autor.class, libroEjemplo1.getAutor().getId()));
        nuevoLibro.setEditorial(entityManager.find(Editorial.class, libroEjemplo1.getEditorial().getId()));
        entityManager.persist(nuevoLibro);

        LibroUsuario nuevoLibroUsuario = new LibroUsuario();
        nuevoLibroUsuario.setPaginaActual(50);
        nuevoLibroUsuario.setEstadoLectura(EstadoLectura.leyendo);
        nuevoLibroUsuario.setPuntuacion(90);
        nuevoLibroUsuario.setLibro(nuevoLibro);
        nuevoLibroUsuario.setBiblioteca(bibliotecaEjemplo);

        // When
        LibroUsuario libroUsuarioGuardado = libroUsuarioRepository.save(nuevoLibroUsuario);
        entityManager.flush();
        entityManager.clear();

        // Then
        assertNotNull(libroUsuarioGuardado.getId());

        Optional<LibroUsuario> libroUsuarioRecuperado =
                libroUsuarioRepository.findById(libroUsuarioGuardado.getId());

        assertTrue(libroUsuarioRecuperado.isPresent());
        assertEquals(50, libroUsuarioRecuperado.get().getPaginaActual());
        assertEquals(EstadoLectura.leyendo, libroUsuarioRecuperado.get().getEstadoLectura());
    }

    @Test
    @DisplayName("Debería eliminar libro usuario correctamente")
    void deberiaEliminarLibroUsuario() {
        // Given
        Long libroUsuarioId = libroUsuario1.getId();
        assertTrue(libroUsuarioRepository.existsById(libroUsuarioId));

        // When
        libroUsuarioRepository.deleteById(libroUsuarioId);
        entityManager.flush();

        // Then
        assertFalse(libroUsuarioRepository.existsById(libroUsuarioId));
        Optional<LibroUsuario> libroUsuarioEliminado = libroUsuarioRepository.findById(libroUsuarioId);
        assertFalse(libroUsuarioEliminado.isPresent());
    }

    @Test
    @DisplayName("Debería contar libros usuario correctamente")
    void deberiaContarLibrosUsuario() {
        // When
        long cantidadLibrosUsuario = libroUsuarioRepository.count();

        // Then
        assertEquals(3, cantidadLibrosUsuario);

        // Agregar un libro usuario más y verificar
        Libro nuevoLibro = new Libro();
        nuevoLibro.setTitulo("Harry Potter");
        nuevoLibro.setDescripcion("Un joven mago descubre su destino.");
        nuevoLibro.setPaginas(309);
        nuevoLibro.setEdicion("Primera edición");
        nuevoLibro.setCalificacion(92);
        nuevoLibro.setFechaPublicacion(LocalDate.of(1997, 6, 26));
        nuevoLibro.setUrlFoto("http://imagen.com/hp.jpg");
        nuevoLibro.setPrecio(1600.00);
        nuevoLibro.setCategoria(Set.of(Categoria.fantastico));
        nuevoLibro.setAutor(entityManager.find(Autor.class, libroEjemplo1.getAutor().getId()));
        nuevoLibro.setEditorial(entityManager.find(Editorial.class, libroEjemplo1.getEditorial().getId()));
        entityManager.persist(nuevoLibro);

        LibroUsuario nuevoLibroUsuario = new LibroUsuario();
        nuevoLibroUsuario.setPaginaActual(0);
        nuevoLibroUsuario.setEstadoLectura(EstadoLectura.pendiente);
        nuevoLibroUsuario.setPuntuacion(0);
        nuevoLibroUsuario.setLibro(nuevoLibro);
        nuevoLibroUsuario.setBiblioteca(bibliotecaEjemplo);

        entityManager.persistAndFlush(nuevoLibroUsuario);

        assertEquals(4, libroUsuarioRepository.count());
    }

    @Test
    @DisplayName("Debería validar restricciones de la entidad")
    void deberiaValidarRestricciones() {
        // Given - Crear libro usuario con página actual negativa
        LibroUsuario nuevoLibroUsuario = new LibroUsuario();
        nuevoLibroUsuario.setPaginaActual(-10); // No cumple @PositiveOrZero
        nuevoLibroUsuario.setEstadoLectura(EstadoLectura.leyendo);
        nuevoLibroUsuario.setPuntuacion(85);
        nuevoLibroUsuario.setLibro(libroEjemplo1);
        nuevoLibroUsuario.setBiblioteca(bibliotecaEjemplo);

        // When & Then
        assertThrows(Exception.class, () -> {
            entityManager.persistAndFlush(nuevoLibroUsuario);
        });
    }
}