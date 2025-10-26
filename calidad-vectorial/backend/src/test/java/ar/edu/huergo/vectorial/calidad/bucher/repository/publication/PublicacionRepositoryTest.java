package ar.edu.huergo.vectorial.calidad.bucher.repository.publication;

import java.time.LocalDate;
import java.util.HashSet;
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
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.Estado;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.Publicacion;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Avatar;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Rol;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;

@DataJpaTest
@DisplayName("Tests de Integración - PublicacionRepository")
public class PublicacionRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PublicacionRepository publicacionRepository;

    private Publicacion publicacion1;
    private Publicacion publicacion2;
    private Publicacion publicacion3;

    private Usuario usuarioEjemplo1;
    private Usuario usuarioEjemplo2;

    @BeforeEach
    void setUp() {
        // Crear rol
        Rol rol = new Rol();
        rol.setNombre("ESCRITOR");
        entityManager.persist(rol);
        entityManager.flush();

        // Crear usuarios
        usuarioEjemplo1 = new Usuario();
        usuarioEjemplo1.setUsername("usuario1@example.com");
        usuarioEjemplo1.setPassword("Password123!@#$%^&");
        usuarioEjemplo1.setNickname("escritor123");
        usuarioEjemplo1.setAvatar(Avatar.ALICIA);
        Set<Rol> roles1 = new HashSet<>();
        roles1.add(rol);
        usuarioEjemplo1.setRoles(roles1);
        entityManager.persist(usuarioEjemplo1);
        entityManager.flush();

        usuarioEjemplo2 = new Usuario();
        usuarioEjemplo2.setUsername("usuario2@example.com");
        usuarioEjemplo2.setPassword("Password456!@#$%^&");
        usuarioEjemplo2.setNickname("escritor456");
        usuarioEjemplo2.setAvatar(Avatar.GANDALF);
        Set<Rol> roles2 = new HashSet<>();
        roles2.add(rol);
        usuarioEjemplo2.setRoles(roles2);
        entityManager.persist(usuarioEjemplo2);
        entityManager.flush();

        // Crear autor y editorial
        Autor autor = new Autor();
        autor.setNombre("Autor de ejemplo");
        autor.setUrlWikipedia("https://es.wikipedia.org/wiki/autor_ejemplo");
        entityManager.persist(autor);
        entityManager.flush();

        Editorial editorial = new Editorial();
        editorial.setNombre("Editorial de ejemplo");
        editorial.setUrlWikipedia("https://es.wikipedia.org/wiki/editorial_ejemplo");
        entityManager.persist(editorial);
        entityManager.flush();

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
        Set<Categoria> categorias1 = new HashSet<>();
        categorias1.add(Categoria.realismomagico);
        libro1.setCategoria(categorias1);
        libro1.setAutor(autor);
        libro1.setEditorial(editorial);
        entityManager.persist(libro1);
        entityManager.flush();

        Libro libro2 = new Libro();
        libro2.setTitulo("El Nombre del Viento");
        libro2.setDescripcion("La historia de un joven músico con un pasado legendario.");
        libro2.setPaginas(662);
        libro2.setEdicion("Primera edición");
        libro2.setCalificacion(95);
        libro2.setFechaPublicacion(LocalDate.of(2007, 3, 27));
        libro2.setUrlFoto("http://imagen.com/nombre_viento.jpg");
        libro2.setPrecio(1800.00);
        Set<Categoria> categorias2 = new HashSet<>();
        categorias2.add(Categoria.fantastico);
        libro2.setCategoria(categorias2);
        libro2.setAutor(autor);
        libro2.setEditorial(editorial);
        entityManager.persist(libro2);
        entityManager.flush();

        Libro libro3 = new Libro();
        libro3.setTitulo("1984");
        libro3.setDescripcion("Una distopía totalitaria.");
        libro3.setPaginas(328);
        libro3.setEdicion("Primera edición");
        libro3.setCalificacion(88);
        libro3.setFechaPublicacion(LocalDate.of(1949, 6, 8));
        libro3.setUrlFoto("http://imagen.com/1984.jpg");
        libro3.setPrecio(1200.00);
        Set<Categoria> categorias3 = new HashSet<>();
        categorias3.add(Categoria.cienciaficcion);
        libro3.setCategoria(categorias3);
        libro3.setAutor(autor);
        libro3.setEditorial(editorial);
        entityManager.persist(libro3);
        entityManager.flush();

        // Crear publicaciones de prueba
        publicacion1 = new Publicacion();
        publicacion1.setUsuario(usuarioEjemplo1);
        publicacion1.setDescripcion("Libro en excelente estado, listo para prestar");
        publicacion1.setLimiteDias(14);
        publicacion1.setDetallesEstadoLibro("Nuevo - Sin uso");
        publicacion1.setEstadoPublicacion(Estado.Disponible);
        publicacion1.setLibro(libro1);
        publicacion1 = entityManager.persistAndFlush(publicacion1);

        publicacion2 = new Publicacion();
        publicacion2.setUsuario(usuarioEjemplo1);
        publicacion2.setDescripcion("Libro de fantasía épica disponible para préstamo");
        publicacion2.setLimiteDias(21);
        publicacion2.setDetallesEstadoLibro("Usado - Buen estado");
        publicacion2.setEstadoPublicacion(Estado.Prestado);
        publicacion2.setLibro(libro2);
        publicacion2 = entityManager.persistAndFlush(publicacion2);

        publicacion3 = new Publicacion();
        publicacion3.setUsuario(usuarioEjemplo2);
        publicacion3.setDescripcion("Clásico de la literatura distópica");
        publicacion3.setLimiteDias(30);
        publicacion3.setDetallesEstadoLibro("Usado - Estado aceptable");
        publicacion3.setEstadoPublicacion(Estado.Disponible);
        publicacion3.setLibro(libro3);
        publicacion3 = entityManager.persistAndFlush(publicacion3);

        entityManager.clear();
    }

    @Test
    @DisplayName("Debería encontrar todas las publicaciones por usuario")
    void deberiaEncontrarPublicacionesPorUsuario() {
        // When - Buscar publicaciones del usuario 1
        List<Publicacion> publicacionesUsuario1 =
                publicacionRepository.findAllByUsuario(usuarioEjemplo1);

        // Then
        assertNotNull(publicacionesUsuario1);
        assertEquals(2, publicacionesUsuario1.size());

        // When - Buscar publicaciones del usuario 2
        List<Publicacion> publicacionesUsuario2 =
                publicacionRepository.findAllByUsuario(usuarioEjemplo2);

        // Then
        assertNotNull(publicacionesUsuario2);
        assertEquals(1, publicacionesUsuario2.size());
    }

    @Test
    @DisplayName("Debería encontrar todas las publicaciones por categoría")
    void deberiaEncontrarPublicacionesPorCategoria() {
        // When - Buscar publicaciones de categoría realismo mágico
        List<Publicacion> publicacionesRealismo =
                publicacionRepository.findAllByCategoria(Categoria.realismomagico);

        // Then
        assertNotNull(publicacionesRealismo);
        assertEquals(1, publicacionesRealismo.size());

        // When - Buscar publicaciones de categoría fantástico
        List<Publicacion> publicacionesFantastico =
                publicacionRepository.findAllByCategoria(Categoria.fantastico);

        // Then
        assertNotNull(publicacionesFantastico);
        assertEquals(1, publicacionesFantastico.size());
    }

    @Test
    @DisplayName("Debería encontrar todas las publicaciones por estado")
    void deberiaEncontrarPublicacionesPorEstado() {
        // When - Buscar publicaciones disponibles
        List<Publicacion> publicacionesDisponibles =
                publicacionRepository.findAllByEstadoPublicacion(Estado.Disponible);

        // Then
        assertNotNull(publicacionesDisponibles);
        assertEquals(2, publicacionesDisponibles.size());

        // When - Buscar publicaciones prestadas
        List<Publicacion> publicacionesPrestadas =
                publicacionRepository.findAllByEstadoPublicacion(Estado.Prestado);

        // Then
        assertNotNull(publicacionesPrestadas);
        assertEquals(1, publicacionesPrestadas.size());
    }

    @Test
    @DisplayName("Debería guardar y recuperar publicación correctamente")
    void deberiaGuardarYRecuperarPublicacion() {
        // Given
        Libro libroExistente = publicacion1.getLibro();
        
        Publicacion nuevaPublicacion = new Publicacion();
        nuevaPublicacion.setUsuario(usuarioEjemplo1);
        nuevaPublicacion.setDescripcion("Trilogía completa en excelente estado");
        nuevaPublicacion.setLimiteDias(45);
        nuevaPublicacion.setDetallesEstadoLibro("Nuevo - Sin uso");
        nuevaPublicacion.setEstadoPublicacion(Estado.Disponible);
        nuevaPublicacion.setLibro(libroExistente);

        // When
        Publicacion publicacionGuardada = publicacionRepository.save(nuevaPublicacion);
        entityManager.flush();
        entityManager.clear();

        // Then
        assertNotNull(publicacionGuardada.getId());

        Optional<Publicacion> publicacionRecuperada =
                publicacionRepository.findById(publicacionGuardada.getId());

        assertTrue(publicacionRecuperada.isPresent());
        assertEquals("Trilogía completa en excelente estado", publicacionRecuperada.get().getDescripcion());
        assertEquals(45, publicacionRecuperada.get().getLimiteDias());
    }

    @Test
    @DisplayName("Debería eliminar publicación correctamente")
    void deberiaEliminarPublicacion() {
        // Given
        Long publicacionId = publicacion1.getId();
        assertTrue(publicacionRepository.existsById(publicacionId));

        // When
        publicacionRepository.deleteById(publicacionId);
        entityManager.flush();

        // Then
        assertFalse(publicacionRepository.existsById(publicacionId));
    }

    @Test
    @DisplayName("Debería contar publicaciones correctamente")
    void deberiaContarPublicaciones() {
        // When
        long cantidadPublicaciones = publicacionRepository.count();

        // Then
        assertEquals(3, cantidadPublicaciones);
    }

    @Test
    @DisplayName("Debería validar restricciones de la entidad")
    void deberiaValidarRestricciones() {
        // Given - Crear publicación con descripción vacía
        Publicacion nuevaPublicacion = new Publicacion();
        nuevaPublicacion.setUsuario(usuarioEjemplo1);
        nuevaPublicacion.setDescripcion(""); // No cumple @NotBlank
        nuevaPublicacion.setLimiteDias(14);
        nuevaPublicacion.setDetallesEstadoLibro("Nuevo");
        nuevaPublicacion.setEstadoPublicacion(Estado.Disponible);
        nuevaPublicacion.setLibro(publicacion1.getLibro());

        // When & Then
        assertThrows(Exception.class, () -> {
            entityManager.persistAndFlush(nuevaPublicacion);
        });
    }
}