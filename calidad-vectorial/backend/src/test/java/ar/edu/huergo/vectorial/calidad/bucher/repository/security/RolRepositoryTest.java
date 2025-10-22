package ar.edu.huergo.vectorial.calidad.bucher.repository.security;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Rol;

@DataJpaTest
@DisplayName("Tests de Integración - RolRepository")
class RolRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RolRepository rolRepository;

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

    @Test
    @DisplayName("Debería guardar y recuperar un rol correctamente")
    void deberiaGuardarYRecuperarRol() {
        Rol nuevoRol = new Rol("ESCRITOR");
        rolRepository.saveAndFlush(nuevoRol);
        entityManager.clear();

        Optional<Rol> encontrado = rolRepository.findByNombre("ESCRITOR");

        assertTrue(encontrado.isPresent());
        assertEquals("ESCRITOR", encontrado.get().getNombre());
    }

    @Test
    @DisplayName("Debería lanzar excepción al guardar un rol con nombre duplicado")
    void deberiaLanzarExcepcionPorNombreDuplicado() {
        Rol duplicado = new Rol("ADMIN");
        assertThrows(Exception.class, () -> {
            rolRepository.saveAndFlush(duplicado);
        });
    }

    @Test
    @DisplayName("Debería lanzar excepción al intentar guardar rol sin nombre")
    void deberiaLanzarExcepcionPorNombreNuloOVacio() {
        Rol sinNombre = new Rol();
        sinNombre.setNombre(null);

        assertThrows(Exception.class, () -> {
            entityManager.persistAndFlush(sinNombre);
        });
    }

    @Test
    @DisplayName("Debería contar correctamente la cantidad de roles")
    void deberiaContarRoles() {
        long cantidad = rolRepository.count();
        assertEquals(2, cantidad);

        rolRepository.saveAndFlush(new Rol("ESCRITOR"));
        assertEquals(3, rolRepository.count());
    }
}