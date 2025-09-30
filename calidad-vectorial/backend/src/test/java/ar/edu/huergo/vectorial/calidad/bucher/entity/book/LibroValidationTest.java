package ar.edu.huergo.vectorial.calidad.bucher.entity.book;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@DisplayName("Tests de Validación - Entidad Libro")
class LibroValidationTest {

    private Validator validator;
    private Autor autorEjemplo;
    private Editorial editorialEjemplo;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        autorEjemplo = new Autor("Gabriel García Márquez", "https://es.wikipedia.org/wiki/Gabriel_García_Márquez");
        editorialEjemplo = new Editorial("Editorial Sudamericana", "https://es.wikipedia.org/wiki/Editorial_Sudamericana");
    }

    private Libro crearLibroValido() {
        Libro libro = new Libro();
        libro.setTitulo("Cien Años de Soledad");
        libro.setDescripcion("Una novela emblemática del realismo mágico.");
        libro.setPaginas(417);
        libro.setEdicion("Primera edición");
        libro.setCalificacion(90);
        libro.setFechaPublicacion(LocalDate.of(1967, 5, 30));
        libro.setUrlFoto("http://imagen.com/portada.jpg");
        libro.setPrecio(1500.00);
        libro.setCategoria(Set.of(Categoria.realismomagico));
        libro.setAutor(autorEjemplo);
        libro.setEditorial(editorialEjemplo);
        return libro;
    }

    @Test
    @DisplayName("Debería validar libro correcto sin errores")
    void deberiaValidarLibroCorrectoSinErrores() {
        Libro libro = crearLibroValido();
        Set<ConstraintViolation<Libro>> violaciones = validator.validate(libro);
        assertTrue(violaciones.isEmpty());
    }

    // -------título-------

    @Test
    @DisplayName("Debería fallar validación con título null y vacío")
    void deberiaFallarTituloNullYVacio() {
        Libro libroNull = crearLibroValido();
        libroNull.setTitulo(null);
        Libro libroVacio = crearLibroValido();
        libroVacio.setTitulo("");

        assertFalse(validator.validate(libroNull).isEmpty());
        assertFalse(validator.validate(libroVacio).isEmpty());
    }

    @Test
    @DisplayName("Debería fallar validación con título muy corto y muy largo")
    void deberiaFallarTituloCortoYLargo() {
        Libro corto = crearLibroValido();
        corto.setTitulo("A");

        Libro largo = crearLibroValido();
        largo.setTitulo("A".repeat(101));

        assertFalse(validator.validate(corto).isEmpty());
        assertFalse(validator.validate(largo).isEmpty());
    }

    // -------descripción-------

    @Test
    @DisplayName("Debería fallar validación con descripción null y vacía")
    void deberiaFallarDescripcionNullYVacia() {
        Libro libroNull = crearLibroValido();
        libroNull.setDescripcion(null);
        Libro libroVacio = crearLibroValido();
        libroVacio.setDescripcion("");

        assertFalse(validator.validate(libroNull).isEmpty());
        assertFalse(validator.validate(libroVacio).isEmpty());
    }

    @Test
    @DisplayName("Debería fallar validación con descripción muy corta y muy larga")
    void deberiaFallarDescripcionCortaYLarga() {
        Libro corto = crearLibroValido();
        corto.setDescripcion("A");

        Libro largo = crearLibroValido();
        largo.setDescripcion("A".repeat(256));

        assertFalse(validator.validate(corto).isEmpty());
        assertFalse(validator.validate(largo).isEmpty());
    }

    // -------edición-------

    @Test
    @DisplayName("Debería fallar validación con edición null y vacía")
    void deberiaFallarEdicionNullYVacia() {
        Libro libroNull = crearLibroValido();
        libroNull.setEdicion(null);
        Libro libroVacio = crearLibroValido();
        libroVacio.setEdicion("");

        assertFalse(validator.validate(libroNull).isEmpty());
        assertFalse(validator.validate(libroVacio).isEmpty());
    }

    @Test
    @DisplayName("Debería fallar validación con edición muy corta y muy larga")
    void deberiaFallarEdicionCortaYLarga() {
        Libro corto = crearLibroValido();
        corto.setEdicion("A");

        Libro largo = crearLibroValido();
        largo.setEdicion("A".repeat(101));

        assertFalse(validator.validate(corto).isEmpty());
        assertFalse(validator.validate(largo).isEmpty());
    }

    // -------páginas-------

    @Test
    @DisplayName("Debería fallar validación con páginas <= 0")
    void deberiaFallarPaginasMenorOIgualACero() {
        Libro libro = crearLibroValido();
        libro.setPaginas(0);

        Set<ConstraintViolation<Libro>> violaciones = validator.validate(libro);
        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream().anyMatch(v -> v.getPropertyPath().toString().equals("paginas")));
    }

    // -------calificación-------

    @Test
    @DisplayName("Debería fallar validación con calificación fuera de rango")
    void deberiaFallarCalificacionFueraDeRango() {
        Libro bajo = crearLibroValido();
        bajo.setCalificacion(-1);

        Libro alto = crearLibroValido();
        alto.setCalificacion(101);

        assertFalse(validator.validate(bajo).isEmpty());
        assertFalse(validator.validate(alto).isEmpty());
    }

    // -------fecha publicación-------

    @Test
    @DisplayName("Debería fallar validación con fecha publicación null")
    void deberiaFallarFechaPublicacionNull() {
        Libro libro = crearLibroValido();
        libro.setFechaPublicacion(null);

        assertFalse(validator.validate(libro).isEmpty());
    }

    // -------precio-------

    @Test
    @DisplayName("Debería fallar validación con precio <= 0")
    void deberiaFallarPrecioMenorOIgualACero() {
        Libro libro = crearLibroValido();
        libro.setPrecio(0);

        assertFalse(validator.validate(libro).isEmpty());
    }

    // -------categoría-------

    @Test
    @DisplayName("Debería fallar validación con categoría null o vacía o muy corta")
    void deberiaFallarCategoriaNullOVacia() {
        Libro nullCategoria = crearLibroValido();
        nullCategoria.setCategoria(null);

        Libro vacia = crearLibroValido();
        vacia.setCategoria(new HashSet<>());

        assertFalse(validator.validate(nullCategoria).isEmpty());
        assertFalse(validator.validate(vacia).isEmpty());
    }

    // -------múltiples errores-------

    @Test
    @DisplayName("Debería validar múltiples errores simultáneamente en libro")
    void deberiaValidarMultiplesErroresEnLibro() {
        Libro libro = crearLibroValido();
        // Redefiniendo a un libro inválido
        libro.setTitulo("A");
        libro.setDescripcion(null);
        libro.setPaginas(-1);
        libro.setEdicion("");
        libro.setCalificacion(-10);
        libro.setFechaPublicacion(null);
        libro.setUrlFoto("http://imagen.com/portada.jpg");
        libro.setPrecio(0);

        Set<ConstraintViolation<Libro>> violaciones = validator.validate(libro);

        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.size() >= 6);

        List<String> propiedadesConError = violaciones.stream()
                .map(v -> v.getPropertyPath().toString())
                .toList();

        assertTrue(propiedadesConError.contains("titulo"));
        assertTrue(propiedadesConError.contains("descripcion"));
        assertTrue(propiedadesConError.contains("edicion"));
        assertTrue(propiedadesConError.contains("paginas"));
        assertTrue(propiedadesConError.contains("precio"));
    }
}