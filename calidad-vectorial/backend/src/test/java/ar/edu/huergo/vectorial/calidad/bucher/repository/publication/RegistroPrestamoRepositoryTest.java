package ar.edu.huergo.vectorial.calidad.bucher.repository.publication;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.Estado;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.Publicacion;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.RegistroPrestamo;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Avatar;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Rol;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;

@DataJpaTest
@DisplayName("Tests de Integración - RegistroPrestamoRepository")
public class RegistroPrestamoRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RegistroPrestamoRepository registroPrestamoRepository;

    private RegistroPrestamo registroPrestamo1;
    private RegistroPrestamo registroPrestamo2;
    private RegistroPrestamo registroPrestamo3;

    private Usuario usuarioEjemplo1;
    private Usuario usuarioEjemplo2;
    private Publicacion publicacionEjemplo1;
    private Publicacion publicacionEjemplo2;
    private Publicacion publicacionEjemplo3;

    @BeforeEach
    void setUp() {
        // Crear rol
        Rol rol = new Rol("LECTOR");
        entityManager.persist(rol);

        // Crear usuarios
        usuarioEjemplo1 = new Usuario("usuario1@example.com", "Password123!@#$%^&");
        usuarioEjemplo1.setNickname("lector123");
        usuarioEjemplo1.setAvatar(Avatar.ALICIA);
        usuarioEjemplo1.setRoles(Set.of(rol));
        entityManager.persist(usuarioEjemplo1);

        usuarioEjemplo2 = new Usuario("usuario2@example.com", "Password456!@#$%^&");
        usuarioEjemplo2.setNickname("lector456");
        usuarioEjemplo2.setAvatar(Avatar.GANDALF);
        usuarioEjemplo2.setRoles(Set.of(rol));
        entityManager.persist(usuarioEjemplo2);

        // Crear autor y editorial
        Autor autor = new Autor("Autor de ejemplo", "https://es.wikipedia.org/wiki/autor_ejemplo");
        entityManager.persist(autor);

        Editorial editorial = new Editorial("Editorial de ejemplo", "https://es.wikipedia.org/wiki/editorial_ejemplo");
        entityManager.persist(editorial);

        // Crear libros
        Libro libro1 = new Libro();
        libro1.setTitulo("Cien Años de Soledad");
        libro1.setDescripcion("Una novela emblemática del realismo mágico.");
        libro1.setPaginas(417);
        libro1.setEdicion("Primera edición");
        libro1.setCalificacion(90);
        libro1.setFechaPublicacion(LocalDate.of(1967, 5, 30));
        libro1.setUrlFoto("http://imagen.com/portada.jpg");
        libro1.setPrecio(1500.00);
        libro1.setCategoria(Set.of(Categoria.realismomagico));
        libro1.setAutor(autor);
        libro1.setEditorial(editorial);
        entityManager.persist(libro1);

        Libro libro2 = new Libro();
        libro2.setTitulo("El Nombre del Viento");
        libro2.setDescripcion("La historia de un joven músico con un pasado legendario.");
        libro2.setPaginas(662);
        libro2.setEdicion("Primera edición");
        libro2.setCalificacion(95);
        libro2.setFechaPublicacion(LocalDate.of(2007, 3, 27));
        libro2.setUrlFoto("http://imagen.com/nombre_viento.jpg");
        libro2.setPrecio(1800.00);
        libro2.setCategoria(Set.of(Categoria.fantastico));
        libro2.setAutor(autor);
        libro2.setEditorial(editorial);
        entityManager.persist(libro2);

        Libro libro3 = new Libro();
        libro3.setTitulo("1984");
        libro3.setDescripcion("Una distopía totalitaria.");
        libro3.setPaginas(328);
        libro3.setEdicion("Primera edición");
        libro3.setCalificacion(88);
        libro3.setFechaPublicacion(LocalDate.of(1949, 6, 8));
        libro3.setUrlFoto("http://imagen.com/1984.jpg");
        libro3.setPrecio(1200.00);
        libro3.setCategoria(Set.of(Categoria.cienciaficcion));
        libro3.setAutor(autor);
        libro3.setEditorial(editorial);
        entityManager.persist(libro3);

        // Crear publicaciones
        publicacionEjemplo1 = new Publicacion();
        publicacionEjemplo1.setUsuario(usuarioEjemplo1);
        publicacionEjemplo1.setFechaCreacion(LocalDate.now());
        publicacionEjemplo1.setDescripcion("Libro en excelente estado");
        publicacionEjemplo1.setLimiteDias(14);
        publicacionEjemplo1.setDetallesEstadoLibro("Nuevo");
        publicacionEjemplo1.setEstadoPublicacion(Estado.Prestado);
        publicacionEjemplo1.setLibro(libro1);
        entityManager.persist(publicacionEjemplo1);

        publicacionEjemplo2 = new Publicacion();
        publicacionEjemplo2.setUsuario(usuarioEjemplo1);
        publicacionEjemplo2.setFechaCreacion(LocalDate.now());
        publicacionEjemplo2.setDescripcion("Libro de fantasía disponible");
        publicacionEjemplo2.setLimiteDias(21);
        publicacionEjemplo2.setDetallesEstadoLibro("Usado - Buen estado");
        publicacionEjemplo2.setEstadoPublicacion(Estado.Disponible);
        publicacionEjemplo2.setLibro(libro2);
        entityManager.persist(publicacionEjemplo2);

        publicacionEjemplo3 = new Publicacion();
        publicacionEjemplo3.setUsuario(usuarioEjemplo2);
        publicacionEjemplo3.setFechaCreacion(LocalDate.now());
        publicacionEjemplo3.setDescripcion("Clásico distópico");
        publicacionEjemplo3.setLimiteDias(30);
        publicacionEjemplo3.setDetallesEstadoLibro("Usado");
        publicacionEjemplo3.setEstadoPublicacion(Estado.Prestado);
        publicacionEjemplo3.setLibro(libro3);
        entityManager.persist(publicacionEjemplo3);

        // Crear registros de préstamo de prueba
        registroPrestamo1 = new RegistroPrestamo();
        registroPrestamo1.setFechaPrestamo(LocalDate.now().minusDays(5));
        registroPrestamo1.setFechaDevolucion(null); // Préstamo activo
        registroPrestamo1.setPublicacion(publicacionEjemplo1);
        registroPrestamo1.setUsuario(usuarioEjemplo1);
        registroPrestamo1 = entityManager.persistAndFlush(registroPrestamo1);

        registroPrestamo2 = new RegistroPrestamo();
        registroPrestamo2.setFechaPrestamo(LocalDate.now().minusDays(10));
        registroPrestamo2.setFechaDevolucion(LocalDate.now().minusDays(3)); // Préstamo devuelto
        registroPrestamo2.setPublicacion(publicacionEjemplo2);
        registroPrestamo2.setUsuario(usuarioEjemplo1);
        registroPrestamo2 = entityManager.persistAndFlush(registroPrestamo2);

        registroPrestamo3 = new RegistroPrestamo();
        registroPrestamo3.setFechaPrestamo(LocalDate.now().minusDays(2));
        registroPrestamo3.setFechaDevolucion(null); // Préstamo activo
        registroPrestamo3.setPublicacion(publicacionEjemplo3);
        registroPrestamo3.setUsuario(usuarioEjemplo2);
        registroPrestamo3 = entityManager.persistAndFlush(registroPrestamo3);

        entityManager.clear();
    }

    @Test
    @DisplayName("Debería encontrar todos los préstamos por usuario")
    void deberiaEncontrarPrestamosPorUsuario() {
        // When - Buscar préstamos del usuario 1
        List<RegistroPrestamo> prestamosUsuario1 =
                registroPrestamoRepository.findAllByUsuario(usuarioEjemplo1);

        // Then
        assertNotNull(prestamosUsuario1);
        assertEquals(2, prestamosUsuario1.size());
        assertTrue(prestamosUsuario1.stream()
                .allMatch(p -> p.getUsuario().getId().equals(usuarioEjemplo1.getId())));

        // When - Buscar préstamos del usuario 2
        List<RegistroPrestamo> prestamosUsuario2 =
                registroPrestamoRepository.findAllByUsuario(usuarioEjemplo2);

        // Then
        assertNotNull(prestamosUsuario2);
        assertEquals(1, prestamosUsuario2.size());
        assertEquals(usuarioEjemplo2.getId(), prestamosUsuario2.get(0).getUsuario().getId());
    }

    @Test
    @DisplayName("Debería encontrar préstamo activo por publicación")
    void deberiaEncontrarPrestamoActivoPorPublicacion() {
        // When - Buscar préstamo activo de publicación 1
        Optional<RegistroPrestamo> prestamoActivo1 =
                registroPrestamoRepository.findByPublicacionAndFechaDevolucionIsNull(publicacionEjemplo1);

        // Then
        assertTrue(prestamoActivo1.isPresent());
        assertEquals(publicacionEjemplo1.getId(), prestamoActivo1.get().getPublicacion().getId());
        assertEquals(null, prestamoActivo1.get().getFechaDevolucion());

        // When - Buscar préstamo activo de publicación 2 (no debería existir porque fue devuelto)
        Optional<RegistroPrestamo> prestamoActivo2 =
                registroPrestamoRepository.findByPublicacionAndFechaDevolucionIsNull(publicacionEjemplo2);

        // Then
        assertFalse(prestamoActivo2.isPresent());

        // When - Buscar préstamo activo de publicación 3
        Optional<RegistroPrestamo> prestamoActivo3 =
                registroPrestamoRepository.findByPublicacionAndFechaDevolucionIsNull(publicacionEjemplo3);

        // Then
        assertTrue(prestamoActivo3.isPresent());
        assertEquals(publicacionEjemplo3.getId(), prestamoActivo3.get().getPublicacion().getId());
    }
}