package ar.edu.huergo.vectorial.calidad.bucher.entity.bookuser;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Autor;
import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Categoria;
import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Editorial;
import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Libro;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Avatar;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Rol;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@DisplayName("Tests de Validación - Entidad LibroUsuario")
class LibroUsuarioValidationTest {

    private Validator validator;
    private Libro libroEjemplo;
    private Biblioteca bibliotecaEjemplo;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        // Crear libro de ejemplo
        Autor autor = new Autor("Gabriel García Márquez", "https://es.wikipedia.org/wiki/Gabriel_García_Márquez");
        Editorial editorial = new Editorial("Editorial Sudamericana", "https://es.wikipedia.org/wiki/Editorial_Sudamericana");
        
        libroEjemplo = new Libro();
        libroEjemplo.setId(1L);
        libroEjemplo.setTitulo("Cien Años de Soledad");
        libroEjemplo.setDescripcion("Una novela emblemática del realismo mágico.");
        libroEjemplo.setPaginas(417);
        libroEjemplo.setEdicion("Primera edición");
        libroEjemplo.setCalificacion(90);
        libroEjemplo.setFechaPublicacion(LocalDate.of(1967, 5, 30));
        libroEjemplo.setUrlFoto("http://imagen.com/portada.jpg");
        libroEjemplo.setPrecio(1500.00);
        libroEjemplo.setCategoria(Set.of(Categoria.realismomagico));
        libroEjemplo.setAutor(autor);
        libroEjemplo.setEditorial(editorial);

        // Crear biblioteca de ejemplo
        Usuario usuario = new Usuario("usuario@example.com", "Password123!@#$");
        usuario.setId(1L);
        usuario.setNickname("lector123");
        usuario.setAvatar(Avatar.ALICIA);
        usuario.setRoles(Set.of(new Rol("LECTOR")));

        bibliotecaEjemplo = new Biblioteca();
        bibliotecaEjemplo.setId(1L);
        bibliotecaEjemplo.setUsuario(usuario);
    }

    private LibroUsuario crearLibroUsuarioValido() {
        LibroUsuario libroUsuario = new LibroUsuario();
        libroUsuario.setPaginaActual(50);
        libroUsuario.setEstadoLectura(EstadoLectura.leyendo);
        libroUsuario.setPuntuacion(85);
        libroUsuario.setLibro(libroEjemplo);
        libroUsuario.setBiblioteca(bibliotecaEjemplo);
        return libroUsuario;
    }

    @Test
    @DisplayName("Debería validar libro usuario correcto sin errores")
    void deberiaValidarLibroUsuarioCorrectoSinErrores() {
        LibroUsuario libroUsuario = crearLibroUsuarioValido();
        Set<ConstraintViolation<LibroUsuario>> violaciones = validator.validate(libroUsuario);
        assertTrue(violaciones.isEmpty());
    }

    // -------página actual-------

    @Test
    @DisplayName("Debería fallar validación con página actual negativa")
    void deberiaFallarPaginaActualNegativa() {
        LibroUsuario libroUsuario = crearLibroUsuarioValido();
        libroUsuario.setPaginaActual(-1);

        Set<ConstraintViolation<LibroUsuario>> violaciones = validator.validate(libroUsuario);
        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream().anyMatch(v -> v.getPropertyPath().toString().equals("paginaActual")));
    }

    @Test
    @DisplayName("Debería validar página actual en 0")
    void deberiaValidarPaginaActualEnCero() {
        LibroUsuario libroUsuario = crearLibroUsuarioValido();
        libroUsuario.setPaginaActual(0);

        Set<ConstraintViolation<LibroUsuario>> violaciones = validator.validate(libroUsuario);
        assertTrue(violaciones.isEmpty());
    }

    // -------puntuación-------

    @Test
    @DisplayName("Debería fallar validación con puntuación fuera de rango")
    void deberiaFallarPuntuacionFueraDeRango() {
        LibroUsuario bajo = crearLibroUsuarioValido();
        bajo.setPuntuacion(-1);

        LibroUsuario alto = crearLibroUsuarioValido();
        alto.setPuntuacion(101);

        assertFalse(validator.validate(bajo).isEmpty());
        assertFalse(validator.validate(alto).isEmpty());
    }

    @Test
    @DisplayName("Debería validar puntuación en límites válidos")
    void deberiaValidarPuntuacionEnLimitesValidos() {
        LibroUsuario minimo = crearLibroUsuarioValido();
        minimo.setPuntuacion(0);

        LibroUsuario maximo = crearLibroUsuarioValido();
        maximo.setPuntuacion(100);

        assertTrue(validator.validate(minimo).isEmpty());
        assertTrue(validator.validate(maximo).isEmpty());
    }

    // -------múltiples errores-------

    @Test
    @DisplayName("Debería validar múltiples errores simultáneamente en libro usuario")
    void deberiaValidarMultiplesErroresEnLibroUsuario() {
        LibroUsuario libroUsuario = crearLibroUsuarioValido();
        // Redefiniendo a un libro usuario inválido
        libroUsuario.setPaginaActual(-10);
        libroUsuario.setPuntuacion(150);

        Set<ConstraintViolation<LibroUsuario>> violaciones = validator.validate(libroUsuario);

        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.size() >= 2);

        List<String> propiedadesConError = violaciones.stream()
                .map(v -> v.getPropertyPath().toString())
                .toList();

        assertTrue(propiedadesConError.contains("paginaActual"));
        assertTrue(propiedadesConError.contains("puntuacion"));
    }
}