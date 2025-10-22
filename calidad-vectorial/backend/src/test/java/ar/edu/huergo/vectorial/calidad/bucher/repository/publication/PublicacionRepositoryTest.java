package ar.edu.huergo.vectorial.calidad.bucher.repository.publication;

import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Autor;
import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Categoria;
import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Editorial;
import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Libro;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.Publicacion;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.Estado;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Rol;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Avatar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("Tests de Integración - PublicacionRepository (con Usuario/Rol/Avatar)")
public class PublicacionRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PublicacionRepository publicacionRepository;

    private Rol rolLector;
    private Usuario usuarioValido;
    private Usuario usuarioInvalido; // para tests de validaciones
    private Autor autor;
    private Editorial editorial;
    private Libro libroFantasia;
    private Libro libroRomance;
    private Publicacion p1;
    private Publicacion p2;
    private Publicacion p3;

    @BeforeEach
    void setUp() {
        // Roles
        rolLector = new Rol("LECTOR");
        entityManager.persist(rolLector);

        // Usuarios válidos
        usuarioValido = new Usuario();
        usuarioValido.setUsername("usuario.valido@example.com"); // email obligatorio
        usuarioValido.setNickname("userValido");
        // contraseña válida: mayúscula, minúscula, número, simbolo, longitud >=16
        usuarioValido.setPassword("Aa1!aaaaaaaaaaaa");
        usuarioValido.setAvatar(Avatar.ALICIA);
        usuarioValido.setRoles(Set.of(rolLector));
        entityManager.persist(usuarioValido);

        // Usuario inválido: email mal formado y sin roles -> debería violar constraints
        usuarioInvalido = new Usuario();
        usuarioInvalido.setUsername("no-es-mail"); // inválido
        usuarioInvalido.setNickname("badUser");
        usuarioInvalido.setPassword("Aa1!aaaaaaaaaaaa");
        usuarioInvalido.setAvatar(Avatar.BRUJA);
        // intentionally no roles assigned -> @NotEmpty / @NotNull
        // persist en tests específicos para comprobar excepción

        // Autor & Editorial
        autor = new Autor("Autor Test", "https://wiki/autor_test");
        editorial = new Editorial("Editorial Test", "https://wiki/editorial_test");
        entityManager.persist(autor);
        entityManager.persist(editorial);

        // Libros
        libroFantasia = new Libro();
        libroFantasia.setTitulo("Libro Fantástico");
        libroFantasia.setDescripcion("Un libro de fantasía épica.");
        libroFantasia.setPaginas(400);
        libroFantasia.setEdicion("1ra");
        libroFantasia.setCalificacion(90);
        libroFantasia.setFechaPublicacion(LocalDate.of(2000, 1, 1));
        libroFantasia.setUrlFoto("http://img/fantasia.jpg");
        libroFantasia.setPrecio(1000.0);
        libroFantasia.setCategoria(Set.of(Categoria.fantastico));
        libroFantasia.setAutor(autor);
        libroFantasia.setEditorial(editorial);
        entityManager.persist(libroFantasia);

        libroRomance = new Libro();
        libroRomance.setTitulo("Libro Romántico");
        libroRomance.setDescripcion("Historia de amor");
        libroRomance.setPaginas(300);
        libroRomance.setEdicion("2da");
        libroRomance.setCalificacion(80);
        libroRomance.setFechaPublicacion(LocalDate.of(2010, 5, 5));
        libroRomance.setUrlFoto("http://img/romance.jpg");
        libroRomance.setPrecio(800.0);
        libroRomance.setCategoria(Set.of(Categoria.romance));
        libroRomance.setAutor(autor);
        libroRomance.setEditorial(editorial);
        entityManager.persist(libroRomance);

        // Publicaciones
        p1 = new Publicacion(null, usuarioValido, LocalDate.now(), "Fantasia en buen estado", 30,
                "Nuevo", Estado.Disponible, libroFantasia);
        p2 = new Publicacion(null, usuarioValido, LocalDate.now(), "Romance barato", 20,
                "Usado - Bueno", Estado.Disponible, libroRomance);
        p3 = new Publicacion(null, usuarioValido, LocalDate.now(), "Otra copia fantasía", 10,
                "Usado - Regular", Estado.Prestado, libroFantasia);

        entityManager.persist(p1);
        entityManager.persist(p2);
        entityManager.persist(p3);

        entityManager.flush();
        entityManager.clear();
    }

    @Test
    @DisplayName("Debería encontrar publicaciones por usuario (incluye Usuario con roles válidos)")
    void deberiaEncontrarPorUsuario() {
        // recuperar usuarioValido desde EM para evitar detached entity issues
        Usuario usuario = entityManager.find(Usuario.class, usuarioValido.getId());
        List<Publicacion> publicaciones = publicacionRepository.findAllByUsuario(usuario);
        assertEquals(3, publicaciones.size());
    }

    @Test
    @DisplayName("Debería encontrar publicaciones por categoría asociado al libro")
    void deberiaEncontrarPorCategoria() {
        List<Publicacion> porFantasia = publicacionRepository.findAllByCategoria(Categoria.fantastico);
        List<Publicacion> porRomance = publicacionRepository.findAllByCategoria(Categoria.romance);

        assertEquals(2, porFantasia.size()); // p1 y p3
        assertEquals(1, porRomance.size());  // p2
    }

    @Test
    @DisplayName("Debería guardar y recuperar una publicación correctamente (usuario y libro válidos)")
    void deberiaGuardarYRecuperarPublicacion() {
        Usuario usuario = entityManager.find(Usuario.class, usuarioValido.getId());
        Publicacion nueva = new Publicacion(null, usuario, LocalDate.now(),
                "Publicación de prueba", 7, "Nuevo", Estado.Disponible, libroRomance);

        Publicacion guardada = publicacionRepository.save(nueva);
        entityManager.flush();
        entityManager.clear();

        Optional<Publicacion> recuperada = publicacionRepository.findById(guardada.getId());
        assertTrue(recuperada.isPresent());
        assertEquals("Publicación de prueba", recuperada.get().getDescripcion());
        assertEquals(usuario.getId(), recuperada.get().getUsuario().getId());
    }

    @Test
    @DisplayName("Debería eliminar una publicación correctamente")
    void deberiaEliminarPublicacion() {
        Long id = p1.getId();
        assertTrue(publicacionRepository.existsById(id));
        publicacionRepository.deleteById(id);
        entityManager.flush();
        assertFalse(publicacionRepository.existsById(id));
    }

    @Test
    @DisplayName("Debería validar restricciones de Publicacion (descripcion vacía, limiteDias inválido, estado nulo, libro nulo, usuario nulo)")
    void deberiaValidarRestriccionesPublicacion() {
        // 1) Descripción vacía
        Publicacion malDesc = new Publicacion();
        malDesc.setUsuario(entityManager.find(Usuario.class, usuarioValido.getId()));
        malDesc.setDescripcion(""); // @NotBlank y @Size(min=5)
        malDesc.setLimiteDias(10);
        malDesc.setDetallesEstadoLibro("Bueno");
        malDesc.setEstadoPublicacion(Estado.Disponible);
        malDesc.setLibro(libroFantasia);

        assertThrows(Exception.class, () -> entityManager.persistAndFlush(malDesc));

        // 2) limiteDias = 0 (violación @Min/@Positive)
        Publicacion malLimite = new Publicacion();
        malLimite.setUsuario(entityManager.find(Usuario.class, usuarioValido.getId()));
        malLimite.setDescripcion("Descripción válida");
        malLimite.setLimiteDias(0);
        malLimite.setDetallesEstadoLibro("Bueno");
        malLimite.setEstadoPublicacion(Estado.Disponible);
        malLimite.setLibro(libroFantasia);

        assertThrows(Exception.class, () -> entityManager.persistAndFlush(malLimite));

        // 3) estadoPublicacion nulo
        Publicacion sinEstado = new Publicacion();
        sinEstado.setUsuario(entityManager.find(Usuario.class, usuarioValido.getId()));
        sinEstado.setDescripcion("Descripcion valida");
        sinEstado.setLimiteDias(5);
        sinEstado.setDetallesEstadoLibro("Bueno");
        sinEstado.setEstadoPublicacion(null); // @NotNull
        sinEstado.setLibro(libroFantasia);

        assertThrows(Exception.class, () -> entityManager.persistAndFlush(sinEstado));

        // 4) libro nulo
        Publicacion sinLibro = new Publicacion();
        sinLibro.setUsuario(entityManager.find(Usuario.class, usuarioValido.getId()));
        sinLibro.setDescripcion("Descripcion valida");
        sinLibro.setLimiteDias(5);
        sinLibro.setDetallesEstadoLibro("Bueno");
        sinLibro.setEstadoPublicacion(Estado.Disponible);
        sinLibro.setLibro(null); // @NotNull

        assertThrows(Exception.class, () -> entityManager.persistAndFlush(sinLibro));

        // 5) usuario nulo
        Publicacion sinUsuario = new Publicacion();
        sinUsuario.setUsuario(null); // @NotNull
        sinUsuario.setDescripcion("Descripcion valida");
        sinUsuario.setLimiteDias(5);
        sinUsuario.setDetallesEstadoLibro("Bueno");
        sinUsuario.setEstadoPublicacion(Estado.Disponible);
        sinUsuario.setLibro(libroFantasia);

        assertThrows(Exception.class, () -> entityManager.persistAndFlush(sinUsuario));
    }

    @Test
    @DisplayName("Debería validar restricciones de Usuario al persistir (usuario sin roles o con email inválido)")
    void deberiaValidarUsuarioEnPublicacion() {
        // intentar persistir usuario inválido: username no es email y no tiene roles
        assertThrows(Exception.class, () -> {
            entityManager.persistAndFlush(usuarioInvalido);
        });

        // si intento asociar una publicacion a ese usuario inválido (no persistido), también debe fallar
        Publicacion asociadaAInvalido = new Publicacion();
        asociadaAInvalido.setUsuario(usuarioInvalido); // usuario inválido / no persistido
        asociadaAInvalido.setDescripcion("Descripcion valida");
        asociadaAInvalido.setLimiteDias(10);
        asociadaAInvalido.setDetallesEstadoLibro("Bueno");
        asociadaAInvalido.setEstadoPublicacion(Estado.Disponible);
        asociadaAInvalido.setLibro(libroFantasia);

        assertThrows(Exception.class, () -> entityManager.persistAndFlush(asociadaAInvalido));
    }
}