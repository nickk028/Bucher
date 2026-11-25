package ar.edu.huergo.vectorial.calidad.bucher.service.book;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Editorial;
import ar.edu.huergo.vectorial.calidad.bucher.repository.book.EditorialRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests Unitarios - EditorialService")
class EditorialServiceTest {

    @Mock EditorialRepository repo;
    @InjectMocks EditorialService service;

    private Editorial propia;
    private Editorial externa;

    @BeforeEach
    void setUp() {
        propia = new Editorial();
        propia.setId(EditorialService.ID_EDITORIAL_PROPIA);
        propia.setNombre("Editorial Bucher");

        externa = new Editorial();
        externa.setId(2L);
        externa.setNombre("Sudamericana");
    }

    @Test
    @DisplayName("Devuelve ID 1L o lanza EntityNotFoundException")
    void obtenerEditorialPropia() {
        when(repo.findById(1L))
            .thenReturn(Optional.of(propia))
            .thenReturn(Optional.empty());

        assertEquals("Editorial Bucher", service.obtenerEditorialPropia().getNombre());
        assertThrows(EntityNotFoundException.class, () -> service.obtenerEditorialPropia());
    }

    @Test
    @DisplayName("Devuelve true solo si ID es 1L")
    void esEditorialPropia() {
        assertTrue(service.esEditorialPropia(propia));
        assertFalse(service.esEditorialPropia(externa));
        assertFalse(service.esEditorialPropia(null));
    }
}
