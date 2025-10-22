package ar.edu.huergo.vectorial.calidad.bucher.entity.security;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@DisplayName("Tests de Validación - Entidad Rol")
class RolValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private Rol crearRolValido() {
        Rol rol = new Rol();
        rol.setNombre("LECTOR");
        return rol;
    }

    @Test
    @DisplayName("Debería validar rol correcto sin errores")
    void deberiaValidarRolCorrectoSinErrores() {
        Rol rol = crearRolValido();
        Set<ConstraintViolation<Rol>> violaciones = validator.validate(rol);
        assertTrue(violaciones.isEmpty());
    }

    @Test
    @DisplayName("Debería validar rol ADMIN sin errores")
    void deberiaValidarRolAdminSinErrores() {
        Rol rol = new Rol("ADMIN");
        Set<ConstraintViolation<Rol>> violaciones = validator.validate(rol);
        assertTrue(violaciones.isEmpty());
    }

    @Test
    @DisplayName("Debería validar rol ESCRITOR sin errores")
    void deberiaValidarRolEscritorSinErrores() {
        Rol rol = new Rol("ESCRITOR");
        Set<ConstraintViolation<Rol>> violaciones = validator.validate(rol);
        assertTrue(violaciones.isEmpty());
    }
}