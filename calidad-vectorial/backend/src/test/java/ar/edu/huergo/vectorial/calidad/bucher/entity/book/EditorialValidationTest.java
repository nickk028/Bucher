package ar.edu.huergo.vectorial.calidad.bucher.entity.book;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@DisplayName("Tests de Validación - Entidad Editorial")
class EditorialValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private Editorial crearEditorialValida() {
        return new Editorial("Editorial Sudamericana", "https://es.wikipedia.org/wiki/Editorial_Sudamericana");
    }

    @Test
    @DisplayName("Debería validar editorial correcta sin errores")
    void deberiaValidarEditorialCorrectaSinErrores() {
        Editorial editorial = crearEditorialValida();
        Set<ConstraintViolation<Editorial>> violaciones = validator.validate(editorial);
        assertTrue(violaciones.isEmpty());
    }

    // -------nombre-------

    @Test
    @DisplayName("Debería fallar validación con nombre null y vacío")
    void deberiaFallarNombreNullYVacio() {
        Editorial editorialNull = crearEditorialValida();
        editorialNull.setNombre(null);

        Editorial editorialVacia = crearEditorialValida();
        editorialVacia.setNombre("");

        assertFalse(validator.validate(editorialNull).isEmpty());
        assertFalse(validator.validate(editorialVacia).isEmpty());
    }

    @Test
    @DisplayName("Debería fallar validación con nombre muy corto y muy largo")
    void deberiaFallarNombreCortoYLargo() {
        Editorial corto = crearEditorialValida();
        corto.setNombre("A");

        Editorial largo = crearEditorialValida();
        largo.setNombre("A".repeat(101));

        assertFalse(validator.validate(corto).isEmpty());
        assertFalse(validator.validate(largo).isEmpty());
    }

    // -------urlWikipedia-------

    @Test
    @DisplayName("Debería permitir urlWikipedia nula o vacía")
    void deberiaPermitirUrlWikipediaNullOVacia() {
        Editorial sinUrl = crearEditorialValida();
        sinUrl.setUrlWikipedia(null);

        Editorial vacia = crearEditorialValida();
        vacia.setUrlWikipedia("");

        // No debería haber violaciones porque la URL no es obligatoria
        assertTrue(validator.validate(sinUrl).isEmpty());
        assertTrue(validator.validate(vacia).isEmpty());
    }

    @Test
    @DisplayName("Debería aceptar urlWikipedia con formato de URL válida")
    void deberiaAceptarUrlWikipediaValida() {
        Editorial editorial = crearEditorialValida();
        editorial.setUrlWikipedia("https://es.wikipedia.org/wiki/Planeta");

        assertTrue(validator.validate(editorial).isEmpty());
    }

    // -------múltiples errores-------

    @Test
    @DisplayName("Debería detectar múltiples errores simultáneamente en editorial")
    void deberiaDetectarMultiplesErroresSimultaneamente() {
        Editorial editorial = crearEditorialValida();
        editorial.setNombre("A");
        editorial.setUrlWikipedia(" "); // aunque esto no da error, probamos conjunto

        Set<ConstraintViolation<Editorial>> violaciones = validator.validate(editorial);

        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.size() >= 1);

        boolean errorEnNombre = violaciones.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("nombre"));
        assertTrue(errorEnNombre);
    }
}