package ar.edu.huergo.vectorial.calidad.bucher.repository.book;

import java.util.Optional;

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

import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Editorial;

@DataJpaTest
@DisplayName("Tests de Integración - EditorialRepository")
public class EditorialRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EditorialRepository editorialRepository;

    private Editorial editorial1;
    private Editorial editorial2;
    private Editorial editorial3;

    @BeforeEach
    void setUp() {
        // Crear editoriales de prueba
        editorial1 = new Editorial("Editorial Sudamericana", "https://es.wikipedia.org/wiki/Editorial_Sudamericana");
        editorial1 = entityManager.persistAndFlush(editorial1);

        editorial2 = new Editorial("Penguin Random House", "https://es.wikipedia.org/wiki/Penguin_Random_House");
        editorial2 = entityManager.persistAndFlush(editorial2);

        editorial3 = new Editorial("Anagrama", "https://es.wikipedia.org/wiki/Anagrama");
        editorial3 = entityManager.persistAndFlush(editorial3);

        entityManager.clear();
    }

    @Test
    @DisplayName("Debería encontrar la editorial con búsqueda case insensitive")
    void deberiaEncontrarEditorialCaseInsensitive() {
        // When - Buscar con diferentes casos
        Optional<Editorial> resultadoMinuscula =
                editorialRepository.findByNombreIgnoringCase("editorial sudamericana");
        Optional<Editorial> resultadoMayuscula =
                editorialRepository.findByNombreIgnoringCase("EDITORIAL SUDAMERICANA");
        Optional<Editorial> resultadoMixto =
                editorialRepository.findByNombreIgnoringCase("EdItOrIaL SuDaMeRiCaNa");

        // Then - Todos deberían dar el mismo resultado
        assertNotNull(resultadoMinuscula);
        assertNotNull(resultadoMayuscula);
        assertNotNull(resultadoMixto);

        assertEquals("Editorial Sudamericana", resultadoMinuscula.get().getNombre());
        assertEquals("Editorial Sudamericana", resultadoMayuscula.get().getNombre());
        assertEquals("Editorial Sudamericana", resultadoMixto.get().getNombre());
    }

    @Test
    @DisplayName("Debería guardar y recuperar editorial correctamente")
    void deberiaGuardarYRecuperarEditorial() {
        // Given
        Editorial nuevaEditorial = new Editorial("Planeta", "https://es.wikipedia.org/wiki/Grupo_Planeta");

        // When
        Editorial editorialGuardada = editorialRepository.save(nuevaEditorial);
        entityManager.flush();
        entityManager.clear();

        // Then
        assertNotNull(editorialGuardada.getId());

        Optional<Editorial> editorialRecuperada =
                editorialRepository.findById(editorialGuardada.getId());

        assertTrue(editorialRecuperada.isPresent());
        assertEquals("Planeta", editorialRecuperada.get().getNombre());
    }

    @Test
    @DisplayName("Debería eliminar editorial correctamente")
    void deberiaEliminarEditorial() {
        // Given
        Long editorialId = editorial1.getId();
        assertTrue(editorialRepository.existsById(editorialId));

        // When
        editorialRepository.deleteById(editorialId);
        entityManager.flush();

        // Then
        assertFalse(editorialRepository.existsById(editorialId));
        Optional<Editorial> editorialEliminada = editorialRepository.findById(editorialId);
        assertFalse(editorialEliminada.isPresent());
    }

    @Test
    @DisplayName("Debería contar editoriales correctamente")
    void deberiaContarEditoriales() {
        // When
        long cantidadEditoriales = editorialRepository.count();

        // Then
        assertEquals(3, cantidadEditoriales);

        // Agregar una editorial más y verificar
        Editorial nuevaEditorial = new Editorial("Alfaguara", "https://es.wikipedia.org/wiki/Alfaguara");
        entityManager.persistAndFlush(nuevaEditorial);

        assertEquals(4, editorialRepository.count());
    }

    @Test
    @DisplayName("Debería validar restricciones de la entidad")
    void deberiaValidarRestricciones() {
        // Given - Crear editorial con nombre vacío
        Editorial nuevaEditorial = new Editorial("", "https://es.wikipedia.org/wiki/editorial");

        // When & Then
        assertThrows(Exception.class, () -> {
            entityManager.persistAndFlush(nuevaEditorial);
        });
    }
}