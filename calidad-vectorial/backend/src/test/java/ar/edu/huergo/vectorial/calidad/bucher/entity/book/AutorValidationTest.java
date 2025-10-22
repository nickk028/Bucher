package ar.edu.huergo.vectorial.calidad.bucher.entity.book;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@DisplayName("Tests de Validación - Entidad Autor")
class AutorValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private Autor crearAutorValido() {
        Autor autor = new Autor();
        autor.setNombre("Gabriel García Márquez");
        autor.setDescripcion("Autor colombiano, ganador del Premio Nobel de Literatura.");
        autor.setUrlWikipedia("https://es.wikipedia.org/wiki/Gabriel_Garc%C3%ADa_M%C3%A1rquez");
        autor.setUrlFotoAutor("http://imagen.com/autor.jpg");
        return autor;
    }

    @Test
    @DisplayName("Debería validar autor correcto sin errores")
    void deberiaValidarAutorCorrectoSinErrores() {
        Autor autor = crearAutorValido();
        Set<ConstraintViolation<Autor>> violaciones = validator.validate(autor);
        assertTrue(violaciones.isEmpty());
    }

    // -------nombre-------

    @Test
    @DisplayName("Debería fallar validación con nombre null y vacío")
    void deberiaFallarNombreNullYVacio() {
        Autor autorNull = crearAutorValido();
        autorNull.setNombre(null);
        Autor autorVacio = crearAutorValido();
        autorVacio.setNombre("");

        assertFalse(validator.validate(autorNull).isEmpty());
        assertFalse(validator.validate(autorVacio).isEmpty());
    }

    @Test
    @DisplayName("Debería fallar validación con nombre muy corto y muy largo")
    void deberiaFallarNombreCortoYLargo() {
        Autor corto = crearAutorValido();
        corto.setNombre("A");

        Autor largo = crearAutorValido();
        largo.setNombre("A".repeat(101));

        assertFalse(validator.validate(corto).isEmpty());
        assertFalse(validator.validate(largo).isEmpty());
    }

    // -------descripción-------

    @Test
    @DisplayName("Debería fallar validación con descripción muy larga")
    void deberiaFallarDescripcionMuyLarga() {
        Autor autor = crearAutorValido();
        autor.setDescripcion("A".repeat(256));

        assertFalse(validator.validate(autor).isEmpty());
    }

    // -------múltiples errores-------

    @Test
    @DisplayName("Debería validar múltiples errores simultáneamente en autor")
    void deberiaValidarMultiplesErroresEnAutor() {
        Autor autor = crearAutorValido();
        // Redefiniendo a un autor inválido
        autor.setNombre("A");
        autor.setDescripcion("A".repeat(256));

        Set<ConstraintViolation<Autor>> violaciones = validator.validate(autor);

        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.size() >= 2);

        List<String> propiedadesConError = violaciones.stream()
                .map(v -> v.getPropertyPath().toString())
                .toList();

        assertTrue(propiedadesConError.contains("nombre"));
        assertTrue(propiedadesConError.contains("descripcion"));
    }
}