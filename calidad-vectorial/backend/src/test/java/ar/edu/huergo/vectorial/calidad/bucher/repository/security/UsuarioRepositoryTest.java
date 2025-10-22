package ar.edu.huergo.vectorial.calidad.bucher.repository.security;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Avatar;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Rol;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;

@DataJpaTest
@DisplayName("Tests de Integración - UsuarioRepository")
class UsuarioRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Rol rolAdmin;
    private Rol rolLector;

    @BeforeEach
    void setUp() {
        rolAdmin = new Rol("ADMIN");
        rolLector = new Rol("LECTOR");

        entityManager.persistAndFlush(rolAdmin);
        entityManager.persistAndFlush(rolLector);
        entityManager.clear();
    }

    private Usuario crearUsuarioBase() {
        Usuario usuario = new Usuario();
        usuario.setUsername("usuario@ejemplo.com");
        usuario.setNickname("UsuarioEjemplo");
        usuario.setPassword("Contraseña@123!!");
        usuario.setAvatar(Avatar.ALICIA);
        usuario.setRoles(Set.of(rolLector));
        usuario.setDescripcion("Un usuario de ejemplo.");
        usuario.setPronombres(Set.of("él", "lo"));
        return usuario;
    }

    @Test
    @DisplayName("Debería guardar y recuperar un usuario correctamente")
    void deberiaGuardarYRecuperarUsuario() {
        Usuario usuario = crearUsuarioBase();

        Usuario guardado = usuarioRepository.save(usuario);
        entityManager.flush();
        entityManager.clear();

        Optional<Usuario> recuperado = usuarioRepository.findById(guardado.getId());

        assertTrue(recuperado.isPresent());
        assertEquals("usuario@ejemplo.com", recuperado.get().getUsername());
        assertEquals("UsuarioEjemplo", recuperado.get().getNickname());
        assertEquals(Avatar.ALICIA, recuperado.get().getAvatar());
        assertEquals(1, recuperado.get().getRoles().size());
    }

    @Test
    @DisplayName("Debería lanzar excepción si falta un campo obligatorio o viola validaciones")
    void deberiaValidarRestriccionesDeEntidad() {
        Usuario usuario = new Usuario();
        usuario.setUsername("noemail"); // formato inválido
        usuario.setNickname(""); // violación de @NotBlank
        usuario.setPassword("123"); // demasiado corta y no cumple patrón
        usuario.setAvatar(null); // nulo
        usuario.setRoles(new HashSet<>()); // vacío

        assertThrows(Exception.class, () -> {
            entityManager.persistAndFlush(usuario);
        });
    }

    @Test
    @DisplayName("No debería permitir dos usuarios con el mismo username")
    void noDeberiaPermitirUsernameDuplicado() {
        Usuario u1 = crearUsuarioBase();
        Usuario u2 = crearUsuarioBase();
        u2.setNickname("OtroNick");

        usuarioRepository.save(u1);
        entityManager.flush();

        assertThrows(Exception.class, () -> {
            usuarioRepository.saveAndFlush(u2);
        });
    }

    @Test
    @DisplayName("Debería encontrar usuario por username correctamente")
    void deberiaEncontrarPorUsername() {
        Usuario usuario = crearUsuarioBase();
        usuarioRepository.saveAndFlush(usuario);
        entityManager.clear();

        Optional<Usuario> encontrado = usuarioRepository.findByUsername("usuario@ejemplo.com");

        assertTrue(encontrado.isPresent());
        assertEquals("UsuarioEjemplo", encontrado.get().getNickname());
    }

    @Test
    @DisplayName("existsByUsername debería devolver true o false según corresponda")
    void deberiaValidarExistenciaPorUsername() {
        Usuario usuario = crearUsuarioBase();
        usuarioRepository.saveAndFlush(usuario);

        assertTrue(usuarioRepository.existsByUsername("usuario@ejemplo.com"));
        assertFalse(usuarioRepository.existsByUsername("otro@correo.com"));
    }

    @Test
    @DisplayName("Debería guardar usuario con múltiples roles y pronombres opcionales")
    void deberiaGuardarUsuarioConMultiplesRolesYPronombres() {
        Usuario usuario = crearUsuarioBase();
        usuario.setRoles(Set.of(rolAdmin, rolLector));
        usuario.setPronombres(Set.of("ella", "le"));

        Usuario guardado = usuarioRepository.saveAndFlush(usuario);

        assertNotNull(guardado.getId());
        assertEquals(2, guardado.getRoles().size());
        assertTrue(guardado.getPronombres().contains("ella"));
    }
}