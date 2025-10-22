package ar.edu.huergo.vectorial.calidad.bucher.entity.publication;

import java.time.LocalDate;
import java.util.Set;

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

@DisplayName("Tests de Validación - Entidad RegistroPrestamo")
class RegistroPrestamoValidationTest {

    private Validator validator;
    private Publicacion publicacionEjemplo;
    private Usuario usuarioEjemplo;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        // Crear usuario de ejemplo
        usuarioEjemplo = new Usuario("usuario@example.com", "Password123!@#$");
        usuarioEjemplo.setId(1L);
        usuarioEjemplo.setNickname("lector123");
        usuarioEjemplo.setAvatar(Avatar.ALICIA);
        usuarioEjemplo.setRoles(Set.of(new Rol("LECTOR")));

        // Crear libro de ejemplo
        Autor autor = new Autor("Gabriel García Márquez", "https://es.wikipedia.org/wiki/Gabriel_García_Márquez");
        Editorial editorial = new Editorial("Editorial Sudamericana", "https://es.wikipedia.org/wiki/Editorial_Sudamericana");
        
        Libro libro = new Libro();
        libro.setId(1L);
        libro.setTitulo("Cien Años de Soledad");
        libro.setDescripcion("Una novela emblemática del realismo mágico.");
        libro.setPaginas(417);
        libro.setEdicion("Primera edición");
        libro.setCalificacion(90);
        libro.setFechaPublicacion(LocalDate.of(1967, 5, 30));
        libro.setUrlFoto("http://imagen.com/portada.jpg");
        libro.setPrecio(1500.00);
        libro.setCategoria(Set.of(Categoria.realismomagico));
        libro.setAutor(autor);
        libro.setEditorial(editorial);

        // Crear publicación de ejemplo
        publicacionEjemplo = new Publicacion();
        publicacionEjemplo.setId(1L);
        publicacionEjemplo.setLibro(libro);
        publicacionEjemplo.setUsuario(usuarioEjemplo);
    }

    private RegistroPrestamo crearRegistroPrestamoValido() {
        RegistroPrestamo registroPrestamo = new RegistroPrestamo();
        registroPrestamo.setFechaPrestamo(LocalDate.now());
        registroPrestamo.setFechaDevolucion(LocalDate.now().plusDays(14));
        registroPrestamo.setPublicacion(publicacionEjemplo);
        registroPrestamo.setUsuario(usuarioEjemplo);
        return registroPrestamo;
    }

    @Test
    @DisplayName("Debería validar registro préstamo correcto sin errores")
    void deberiaValidarRegistroPrestamoCorrectoSinErrores() {
        RegistroPrestamo registroPrestamo = crearRegistroPrestamoValido();
        Set<ConstraintViolation<RegistroPrestamo>> violaciones = validator.validate(registroPrestamo);
        assertTrue(violaciones.isEmpty());
    }

    @Test
    @DisplayName("Debería validar registro préstamo sin fecha devolución")
    void deberiaValidarRegistroPrestamoSinFechaDevolucion() {
        RegistroPrestamo registroPrestamo = crearRegistroPrestamoValido();
        registroPrestamo.setFechaDevolucion(null);

        Set<ConstraintViolation<RegistroPrestamo>> violaciones = validator.validate(registroPrestamo);
        assertTrue(violaciones.isEmpty());
    }
}