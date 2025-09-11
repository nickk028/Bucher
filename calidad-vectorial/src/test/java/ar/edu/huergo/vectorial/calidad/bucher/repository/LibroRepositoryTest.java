package ar.edu.huergo.vectorial.calidad.bucher.repository;

import java.time.LocalDate;
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
import ar.edu.huergo.vectorial.calidad.bucher.repository.book.LibroRepository;

@DataJpaTest
@DisplayName("Tests de Integración - LibroRepository")
public class LibroRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private LibroRepository libroRepository;

    private Libro libro1;
    private Libro libro2;
    private Libro libro3;

    private Autor autorEjemplo;
    private Editorial editorialEjemplo;

    @BeforeEach
    void setUp() {

        autorEjemplo = new Autor("Autor de ejemplo", "https://es.wikipedia.org/wiki/autor_ejemplo");
        entityManager.persist(autorEjemplo);
        
        editorialEjemplo = new Editorial("Editorial de ejemplo", "https://es.wikipedia.org/wiki/editorial_ejemplo");
        entityManager.persist(editorialEjemplo);

        // Crear libros de prueba
        libro1 = new Libro();
        libro1.setTitulo("Cien Años de Soledad");
        libro1.setDescripcion("Una novela emblemática del realismo mágico.");
        libro1.setPaginas(417);
        libro1.setEdicion("Primera edición");
        libro1.setCalificacion(90);
        libro1.setFechaPublicacion(LocalDate.of(1967, 5, 30));
        libro1.setUrlFoto("http://imagen.com/portada.jpg");
        libro1.setPrecio(1500.00);
        libro1.setCategoria(Set.of(Categoria.Realismo_Mágico));
        libro1.setAutor(autorEjemplo);
        libro1.setEditorial(editorialEjemplo);
        libro1 = entityManager.persistAndFlush(libro1);

        libro2 = new Libro();
        libro2.setTitulo("Cien Poemas de Amor");
        libro2.setDescripcion("Una colección de poemas apasionados e intensos.");
        libro2.setPaginas(250);
        libro2.setEdicion("Segunda edición");
        libro2.setCalificacion(85);
        libro2.setFechaPublicacion(LocalDate.of(1995, 2, 14));
        libro2.setUrlFoto("http://imagen.com/cien_poemas.jpg");
        libro2.setPrecio(980.00);
        libro2.setCategoria(Set.of(Categoria.Romance));
        libro2.setAutor(autorEjemplo);
        libro2.setEditorial(editorialEjemplo);
        libro2 = entityManager.persistAndFlush(libro2);

        libro3 = new Libro();
        libro3.setTitulo("El Nombre del Viento");
        libro3.setDescripcion("La historia de un joven músico con un pasado legendario.");
        libro3.setPaginas(662);
        libro3.setEdicion("Primera edición");
        libro3.setCalificacion(95);
        libro3.setFechaPublicacion(LocalDate.of(2007, 3, 27));
        libro3.setUrlFoto("http://imagen.com/nombre_viento.jpg");
        libro3.setPrecio(1800.00);
        libro3.setCategoria(Set.of(Categoria.Fantástico));
        libro3.setAutor(autorEjemplo);
        libro3.setEditorial(editorialEjemplo);
        libro3 = entityManager.persistAndFlush(libro3);

        entityManager.clear();
    }

    @Test
    @DisplayName("Debería encontrar el libro con búsqueda case insensitive")
    void deberiaEncontrarLibroCaseInsensitive() {
        // When - Buscar con diferentes casos
        Optional<Libro> resultadoMinuscula =
                libroRepository.findByTituloIgnoringCase("cien años de soledad");
        Optional<Libro> resultadoMayuscula =
                libroRepository.findByTituloIgnoringCase("CIEN AÑOS DE SOLEDAD");
        Optional<Libro> resultadoMixto =
                libroRepository.findByTituloIgnoringCase("CiEn AÑos de SOlEDad");

        // Then - Todos deberían dar el mismo resultado
        assertNotNull(resultadoMinuscula);
        assertNotNull(resultadoMayuscula);
        assertNotNull(resultadoMixto);

        assertEquals("Cien Años de Soledad", resultadoMinuscula.get().getTitulo());
        assertEquals("Cien Años de Soledad", resultadoMayuscula.get().getTitulo());
        assertEquals("Cien Años de Soledad", resultadoMixto.get().getTitulo());
    }

    @Test
    @DisplayName("Debería guardar y recuperar libro correctamente")
    void deberiaGuardarYRecuperarLibro() {
        // Given
        Libro nuevoLibro = new Libro();
        nuevoLibro.setTitulo("Título de un libro nuevo");
        nuevoLibro.setDescripcion("Descripción");
        nuevoLibro.setPaginas(100);
        nuevoLibro.setEdicion("Primera edición");
        nuevoLibro.setCalificacion(85);
        nuevoLibro.setFechaPublicacion(LocalDate.of(2002, 2, 2));
        nuevoLibro.setUrlFoto("http://imagen.com/nuevo_libro.jpg");
        nuevoLibro.setPrecio(100.00);
        nuevoLibro.setCategoria(Set.of(Categoria.Terror));
        nuevoLibro.setAutor(autorEjemplo);
        nuevoLibro.setEditorial(editorialEjemplo);

        // When
        Libro libroGuardado = libroRepository.save(nuevoLibro);
        entityManager.flush();
        entityManager.clear();

        // Then
        assertNotNull(libroGuardado.getId());

        Optional<Libro> libroRecuperado =
                libroRepository.findById(libroGuardado.getId());

        assertTrue(libroRecuperado.isPresent());
        assertEquals("Título de un libro nuevo", libroRecuperado.get().getTitulo());
    }

    @Test
    @DisplayName("Debería eliminar libro correctamente")
    void deberiaEliminarLibro() {
        // Given
        Long libroId = libro1.getId();
        assertTrue(libroRepository.existsById(libroId));

        // When
        libroRepository.deleteById(libroId);
        entityManager.flush();

        // Then
        assertFalse(libroRepository.existsById(libroId));
        Optional<Libro> ingredienteEliminado = libroRepository.findById(libroId);
        assertFalse(ingredienteEliminado.isPresent());
    }

    @Test
    @DisplayName("Debería contar libros correctamente")
    void deberiaContarLibros() {
        // When
        long cantidadLibros = libroRepository.count();

        // Then
        assertEquals(3, cantidadLibros);

        // Agregar un ingrediente más y verificar
        Libro nuevoLibro = new Libro();
        nuevoLibro.setTitulo("Título de un libro nuevo");
        nuevoLibro.setDescripcion("Descripción");
        nuevoLibro.setPaginas(100);
        nuevoLibro.setEdicion("Primera edición");
        nuevoLibro.setCalificacion(85);
        nuevoLibro.setFechaPublicacion(LocalDate.of(2002, 2, 2));
        nuevoLibro.setUrlFoto("http://imagen.com/nuevo_libro.jpg");
        nuevoLibro.setPrecio(100.00);
        nuevoLibro.setCategoria(Set.of(Categoria.Terror));
        nuevoLibro.setAutor(autorEjemplo);
        nuevoLibro.setEditorial(editorialEjemplo);

        entityManager.persistAndFlush(nuevoLibro);

        assertEquals(4, libroRepository.count());
    }

    @Test
    @DisplayName("Debería validar restricciones de la entidad")
    void deberiaValidarRestricciones() {
        // Given - Crear ingrediente con nombre vacío
        Libro nuevoLibro = new Libro();
        nuevoLibro.setTitulo(""); // No cumple el @NotBlank
        nuevoLibro.setDescripcion("Descripción");
        nuevoLibro.setPaginas(100);
        nuevoLibro.setEdicion("Primera edición");
        nuevoLibro.setCalificacion(85);
        nuevoLibro.setFechaPublicacion(LocalDate.of(2002, 2, 2));
        nuevoLibro.setUrlFoto("http://imagen.com/nuevo_libro.jpg");
        nuevoLibro.setPrecio(100.00);
        nuevoLibro.setCategoria(Set.of(Categoria.Terror));
        nuevoLibro.setAutor(autorEjemplo);
        nuevoLibro.setEditorial(editorialEjemplo);

        // When & Then
        assertThrows(Exception.class, () -> {
            entityManager.persistAndFlush(nuevoLibro);
        });
    }
}