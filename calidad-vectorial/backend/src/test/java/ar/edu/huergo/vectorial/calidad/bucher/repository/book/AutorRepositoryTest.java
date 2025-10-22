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

import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Autor;

@DataJpaTest
@DisplayName("Tests de Integración - AutorRepository")
public class AutorRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AutorRepository autorRepository;

    private Autor autor1;
    private Autor autor2;
    private Autor autor3;

    @BeforeEach
    void setUp() {
        // Crear autores de prueba
        autor1 = new Autor("Gabriel García Márquez", "https://es.wikipedia.org/wiki/Gabriel_García_Márquez");
        autor1 = entityManager.persistAndFlush(autor1);

        autor2 = new Autor("Jorge Luis Borges", "https://es.wikipedia.org/wiki/Jorge_Luis_Borges");
        autor2 = entityManager.persistAndFlush(autor2);

        autor3 = new Autor("Isabel Allende", "https://es.wikipedia.org/wiki/Isabel_Allende");
        autor3 = entityManager.persistAndFlush(autor3);

        entityManager.clear();
    }

    @Test
    @DisplayName("Debería encontrar el autor con búsqueda case insensitive")
    void deberiaEncontrarAutorCaseInsensitive() {
        // When - Buscar con diferentes casos
        Optional<Autor> resultadoMinuscula =
                autorRepository.findByNombreIgnoringCase("gabriel garcía márquez");
        Optional<Autor> resultadoMayuscula =
                autorRepository.findByNombreIgnoringCase("GABRIEL GARCÍA MÁRQUEZ");
        Optional<Autor> resultadoMixto =
                autorRepository.findByNombreIgnoringCase("GaBrIeL GaRcÍa MáRqUeZ");

        // Then - Todos deberían dar el mismo resultado
        assertNotNull(resultadoMinuscula);
        assertNotNull(resultadoMayuscula);
        assertNotNull(resultadoMixto);

        assertEquals("Gabriel García Márquez", resultadoMinuscula.get().getNombre());
        assertEquals("Gabriel García Márquez", resultadoMayuscula.get().getNombre());
        assertEquals("Gabriel García Márquez", resultadoMixto.get().getNombre());
    }

    @Test
    @DisplayName("Debería guardar y recuperar autor correctamente")
    void deberiaGuardarYRecuperarAutor() {
        // Given
        Autor nuevoAutor = new Autor("Pablo Neruda", "https://es.wikipedia.org/wiki/Pablo_Neruda");

        // When
        Autor autorGuardado = autorRepository.save(nuevoAutor);
        entityManager.flush();
        entityManager.clear();

        // Then
        assertNotNull(autorGuardado.getId());

        Optional<Autor> autorRecuperado =
                autorRepository.findById(autorGuardado.getId());

        assertTrue(autorRecuperado.isPresent());
        assertEquals("Pablo Neruda", autorRecuperado.get().getNombre());
    }

    @Test
    @DisplayName("Debería eliminar autor correctamente")
    void deberiaEliminarAutor() {
        // Given
        Long autorId = autor1.getId();
        assertTrue(autorRepository.existsById(autorId));

        // When
        autorRepository.deleteById(autorId);
        entityManager.flush();

        // Then
        assertFalse(autorRepository.existsById(autorId));
        Optional<Autor> autorEliminado = autorRepository.findById(autorId);
        assertFalse(autorEliminado.isPresent());
    }

    @Test
    @DisplayName("Debería contar autores correctamente")
    void deberiaContarAutores() {
        // When
        long cantidadAutores = autorRepository.count();

        // Then
        assertEquals(3, cantidadAutores);

        // Agregar un autor más y verificar
        Autor nuevoAutor = new Autor("Julio Cortázar", "https://es.wikipedia.org/wiki/Julio_Cortázar");
        entityManager.persistAndFlush(nuevoAutor);

        assertEquals(4, autorRepository.count());
    }

    @Test
    @DisplayName("Debería validar restricciones de la entidad")
    void deberiaValidarRestricciones() {
        // Given - Crear autor con nombre vacío
        Autor nuevoAutor = new Autor("", "https://es.wikipedia.org/wiki/autor");

        // When & Then
        assertThrows(Exception.class, () -> {
            entityManager.persistAndFlush(nuevoAutor);
        });
    }
}