package ar.edu.huergo.vectorial.calidad.bucher.entity.publication;

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

import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Autor;
import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Categoria;
import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Editorial;
import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Libro;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Rol;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;



class PublicacionValidationTest {
    private Validator validator;
    private Rol rolEjemplo;
    private Usuario usuarioEjemplo;
    private Autor autorEjemplo;
    private Editorial editorialEjemplo;
    private Libro libroEjemplo;

    @BeforeEach
    void setUp() {
        // Configurar el validador
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        // Crear ejemplo de Rol
        rolEjemplo = new Rol("LECTOR");

        // Crear ejemplo de Usuario
        usuarioEjemplo = new Usuario();
        usuarioEjemplo.setUsername("usuario@example.com");
        usuarioEjemplo.setPassword("Contraseña@Segura123456");
        usuarioEjemplo.setRoles(Set.of(rolEjemplo));

        // Crear ejemplo Autor y Editorial
        autorEjemplo = new Autor("Gabriel García Márquez", "https://es.wikipedia.org/wiki/Gabriel_García_Márquez");
        editorialEjemplo = new Editorial("Editorial Sudamericana", "https://es.wikipedia.org/wiki/Editorial_Sudamericana");

        // Crear ejemplo de Libro
        libroEjemplo = new Libro();
        libroEjemplo.setTitulo("Cien Años de Soledad");
        libroEjemplo.setDescripcion("Una novela emblemática del realismo mágico.");
        libroEjemplo.setPaginas(417);
        libroEjemplo.setEdicion("Primera edición");
        libroEjemplo.setCalificacion(90);
        libroEjemplo.setFechaPublicacion(LocalDate.of(1967, 5, 30));
        libroEjemplo.setUrlFoto("http://imagen.com/portada.jpg");
        libroEjemplo.setPrecio(1500.00);
        libroEjemplo.setCategoria(Set.of(Categoria.realismomagico));
        libroEjemplo.setAutor(autorEjemplo);
        libroEjemplo.setEditorial(editorialEjemplo);
    }

    private Publicacion crearPublicacionValida() {
        Publicacion publicacion = new Publicacion();
        publicacion.setFechaCreacion(LocalDate.now());
        publicacion.setDescripcion("Publicación válida");
        publicacion.setLimiteDias(30);
        publicacion.setDetallesEstadoLibro("Nuevo");
        publicacion.setEstadoPublicacion(Estado.Disponible);
        publicacion.setUsuario(usuarioEjemplo);
        publicacion.setLibro(libroEjemplo);

        return publicacion;
    }

    @Test
    @DisplayName("Debería validar publicación correcta sin errores")
    void deberiaValidarPublicacionCorrectaSinErrores() {
        Publicacion publicacion = crearPublicacionValida();

        Set<ConstraintViolation<Publicacion>> violaciones = validator.validate(publicacion);
        assertTrue(violaciones.isEmpty(), "No debería haber violaciones para una publicación válida.");
    }

    @Test
    @DisplayName("Debería fallar validación con fecha de creación nula")
    void deberiaFallarValidacionConFechaCreacionNula() {
        Publicacion publicacion = crearPublicacionValida();
        publicacion.setFechaCreacion(null);

        Set<ConstraintViolation<Publicacion>> violaciones = validator.validate(publicacion);
        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream()
            .anyMatch(v -> v.getPropertyPath().toString().equals("fechaCreacion")));
    }

    @Test
    @DisplayName("Debería fallar validación con descripción vacía")
    void deberiaFallarValidacionConDescripcionVacia() {
        Publicacion publicacion = crearPublicacionValida();
        publicacion.setDescripcion("");

        Set<ConstraintViolation<Publicacion>> violaciones = validator.validate(publicacion);
        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream()
            .anyMatch(v -> v.getPropertyPath().toString().equals("descripcion")));
    }

    @Test
    @DisplayName("Debería fallar validación con límite de días negativo")
    void deberiaFallarValidacionConLimiteDiasIncorrecto() {
        Publicacion publicacion = crearPublicacionValida();
        publicacion.setLimiteDias(-1);

        Set<ConstraintViolation<Publicacion>> violaciones = validator.validate(publicacion);
        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream()
            .anyMatch(v -> v.getPropertyPath().toString().equals("limiteDias")));
    }

    @Test
    @DisplayName("Debería fallar validación con estado de publicación nulo")
    void deberiaFallarValidacionConEstadoPublicacionNulo() {
        Publicacion publicacion = crearPublicacionValida();

        publicacion.setEstadoPublicacion(null);

        Set<ConstraintViolation<Publicacion>> violaciones = validator.validate(publicacion);
        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream()
            .anyMatch(v -> v.getPropertyPath().toString().equals("estadoPublicacion")));
    }
}