package ar.edu.huergo.vectorial.calidad.bucher.entity.bookuser;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Avatar;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Rol;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@DisplayName("Tests de Validación - Entidad Biblioteca")
class BibliotecaValidationsTest {

    private Validator validator;
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
    }

    private Biblioteca crearBibliotecaValida() {
        Biblioteca biblioteca = new Biblioteca();
        biblioteca.setUsuario(usuarioEjemplo);
        return biblioteca;
    }

    @Test
    @DisplayName("Debería validar biblioteca correcta sin errores")
    void deberiaValidarBibliotecaCorrectaSinErrores() {
        Biblioteca biblioteca = crearBibliotecaValida();
        Set<ConstraintViolation<Biblioteca>> violaciones = validator.validate(biblioteca);
        assertTrue(violaciones.isEmpty());
    }
}