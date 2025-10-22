package ar.edu.huergo.vectorial.calidad.bucher.repository.bookuser;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import ar.edu.huergo.vectorial.calidad.bucher.entity.bookuser.Biblioteca;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Avatar;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Rol;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;

@DataJpaTest
@DisplayName("Tests de Integración - BibliotecaRepository")
public class BibliotecaRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BibliotecaRepository bibliotecaRepository;

    private Biblioteca biblioteca1;
    private Biblioteca biblioteca2;
    private Biblioteca biblioteca3;

    @BeforeEach
    void setUp() {
        // Crear rol
        Rol rol = new Rol("LECTOR");
        entityManager.persist(rol);

        // Crear usuarios
        Usuario usuario1 = new Usuario("usuario1@example.com", "Password12333!@#$");
        usuario1.setNickname("lector123");
        usuario1.setAvatar(Avatar.ALICIA);
        usuario1.setRoles(Set.of(rol));
        entityManager.persist(usuario1);

        Usuario usuario2 = new Usuario("usuario2@example.com", "Password45666!@#$");
        usuario2.setNickname("lector456");
        usuario2.setAvatar(Avatar.HARRYPOTTER);
        usuario2.setRoles(Set.of(rol));
        entityManager.persist(usuario2);

        Usuario usuario3 = new Usuario("usuario3@example.com", "Password78999!@#$");
        usuario3.setNickname("lector789");
        usuario3.setAvatar(Avatar.GANDALF);
        usuario3.setRoles(Set.of(rol));
        entityManager.persist(usuario3);

        // Crear bibliotecas de prueba
        biblioteca1 = new Biblioteca();
        biblioteca1.setUsuario(usuario1);
        biblioteca1 = entityManager.persistAndFlush(biblioteca1);

        biblioteca2 = new Biblioteca();
        biblioteca2.setUsuario(usuario2);
        biblioteca2 = entityManager.persistAndFlush(biblioteca2);

        biblioteca3 = new Biblioteca();
        biblioteca3.setUsuario(usuario3);
        biblioteca3 = entityManager.persistAndFlush(biblioteca3);

        entityManager.clear();
    }

    @Test
    @DisplayName("Debería guardar y recuperar biblioteca correctamente")
    void deberiaGuardarYRecuperarBiblioteca() {
        // Given
        Rol rol = entityManager.find(Rol.class, 1L);
        Usuario nuevoUsuario = new Usuario("nuevo@example.com", "Password9999!@#$");
        nuevoUsuario.setNickname("nuevolector");
        nuevoUsuario.setAvatar(Avatar.KATNISS);
        nuevoUsuario.setRoles(Set.of(rol));
        entityManager.persist(nuevoUsuario);

        Biblioteca nuevaBiblioteca = new Biblioteca();
        nuevaBiblioteca.setUsuario(nuevoUsuario);

        // When
        Biblioteca bibliotecaGuardada = bibliotecaRepository.save(nuevaBiblioteca);
        entityManager.flush();
        entityManager.clear();

        // Then
        assertNotNull(bibliotecaGuardada.getId());

        Optional<Biblioteca> bibliotecaRecuperada =
                bibliotecaRepository.findById(bibliotecaGuardada.getId());

        assertTrue(bibliotecaRecuperada.isPresent());
        assertEquals("nuevolector", bibliotecaRecuperada.get().getUsuario().getNickname());
    }
}