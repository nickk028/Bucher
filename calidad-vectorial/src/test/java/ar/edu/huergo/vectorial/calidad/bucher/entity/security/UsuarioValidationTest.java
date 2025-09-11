package ar.edu.huergo.vectorial.calidad.bucher.entity.security;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@DisplayName("Tests de Validación - Entidad Usuario")
public class UsuarioValidationTest {
    private Validator validator;
    private Rol rolEjemplo;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        rolEjemplo = new Rol();
        rolEjemplo.setId(1L);
        rolEjemplo.setNombre("LECTOR");
    }

    @Test
    @DisplayName("Debería validar usuario correcto sin errores")
    void deberiaValidarUsuarioCorrectoSinErrores() {
        // Given
        Usuario usuario = new Usuario();
        usuario.setUsername("usuario@gmail.com");
        usuario.setPassword("Contraseña@Segura123456");
        usuario.setRoles(new HashSet<>(Arrays.asList(rolEjemplo)));

        // When
        Set<ConstraintViolation<Usuario>> violaciones = validator.validate(usuario);

        // Then
        assertTrue(violaciones.isEmpty(),
            "No debería haber violaciones de validación para un usuario válido");
    }

    // -------username-------

    @Test
    @DisplayName("Debería fallar validación con nombre null")
    void deberiaFallarValidacionConNombreNull() {
        // Given
        Usuario usuario = new Usuario();
        usuario.setUsername(null);
        usuario.setPassword("Contraseña@Segura123456");
        usuario.setRoles(new HashSet<>(Arrays.asList(rolEjemplo)));

        // When
        Set<ConstraintViolation<Usuario>> violaciones = validator.validate(usuario);

        // Then
        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("username")));
        assertTrue(violaciones.stream().anyMatch(v -> v.getMessage().contains("obligatorio")));
    }

    @Test
    @DisplayName("Debería fallar validación con nombre vacío")
    void deberiaFallarValidacionConNombreVacio() {
        // Given
        Usuario usuario = new Usuario();
        usuario.setUsername("");
        usuario.setPassword("Contraseña@Segura123456");
        usuario.setRoles(new HashSet<>(Arrays.asList(rolEjemplo)));

        // When
        Set<ConstraintViolation<Usuario>> violaciones = validator.validate(usuario);

        // Then
        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream()
            .anyMatch(v -> v.getPropertyPath().toString().equals("username")));
    }

    @Test
    @DisplayName("Debería fallar validación con nombre solo espacios")
    void deberiaFallarValidacionConNombreSoloEspacios() {
        // Given
        Usuario usuario = new Usuario();
        usuario.setUsername("   ");
        usuario.setPassword("Contraseña@Segura123456");
        usuario.setRoles(new HashSet<>(Arrays.asList(rolEjemplo)));

        // When
        Set<ConstraintViolation<Usuario>> violaciones = validator.validate(usuario);

        // Then
        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("username")));
    }

    @ParameterizedTest
    @ValueSource(strings = {"A"})
    @DisplayName("Debería fallar validación con nombres muy cortos")
    void deberiaFallarValidacionConNombresMuyCortos(String nombreCorto) {
        // Given
        Usuario usuario = new Usuario();
        usuario.setUsername(nombreCorto);
        usuario.setPassword("Contraseña@Segura123456");
        usuario.setRoles(new HashSet<>(Arrays.asList(rolEjemplo)));

        // When
        Set<ConstraintViolation<Usuario>> violaciones = validator.validate(usuario);

        // Then
        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("username")));
        assertTrue(violaciones.stream()
                .anyMatch(v -> v.getMessage().contains("El username debe tener entre 2 y 100 digitos.")));
    }

    @Test
    @DisplayName("Debería fallar validación con nombre muy largo")
    void deberiaFallarValidacionConNombreMuyLargo() {
        // Given
        String nombreLargo = "A".repeat(101); // 101 caracteres
        Usuario usuario = new Usuario();
        usuario.setUsername(nombreLargo);
        usuario.setPassword("Contraseña@Segura123456");
        usuario.setRoles(new HashSet<>(Arrays.asList(rolEjemplo)));

        // When
        Set<ConstraintViolation<Usuario>> violaciones = validator.validate(usuario);

        // Then
        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("username")));
        assertTrue(violaciones.stream()
                .anyMatch(v -> v.getMessage().contains("El username debe tener entre 2 y 100 digitos.")));
    }

    @Test
    @DisplayName("Debería fallar validación con nombre muy largo")
    void deberiaFallarValidacionConNombreSinArroba() {
        // Given
        String nombreSinArroba = "username"; // 101 caracteres
        Usuario usuario = new Usuario();
        usuario.setUsername(nombreSinArroba);
        usuario.setPassword("Contraseña@Segura123456");
        usuario.setRoles(new HashSet<>(Arrays.asList(rolEjemplo)));

        // When
        Set<ConstraintViolation<Usuario>> violaciones = validator.validate(usuario);

        // Then
        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("username")));
        assertTrue(violaciones.stream()
                .anyMatch(v -> v.getMessage().contains("El nombre debe ser un mail con un formato válido.")));
    }

    @Test
    @DisplayName("Debería aceptar nombres en el límite válido")
    void deberiaAceptarNombresEnLimiteValidoConArroba() {
        // Given - Nombres de exactamente 3 y 100 caracteres
        String nombreMinimo = "A@B"; // 3 caracteres
        String nombreMaximo = "A".repeat(49) + "@" + "A".repeat(50); // 100 caracteres totales

        Usuario usuarioMin = new Usuario();
        usuarioMin.setUsername(nombreMinimo);
        usuarioMin.setPassword("Contraseña@Segura123456");
        usuarioMin.setRoles(new HashSet<>(Arrays.asList(rolEjemplo)));

        Usuario usuarioMax = new Usuario();
        usuarioMax.setUsername(nombreMaximo);
        usuarioMax.setPassword("Contraseña@Segura123456");
        usuarioMax.setRoles(new HashSet<>(Arrays.asList(rolEjemplo)));

        // When
        Set<ConstraintViolation<Usuario>> violaciones1 = validator.validate(usuarioMin);
        Set<ConstraintViolation<Usuario>> violaciones2 = validator.validate(usuarioMax);

        // Then
        assertTrue(violaciones1.isEmpty());
        assertTrue(violaciones2.isEmpty());
    }

    // -------password-------

    @Test
    @DisplayName("Debería fallar validación con contraseña muy larga")
    void deberiaFallarValidacionConPasswordMuyLarga() {
        // Given
        String passwordLarga = "A".repeat(61);
        Usuario usuario = new Usuario();
        usuario.setUsername("usuario@gmail.com");
        usuario.setPassword(passwordLarga);
        usuario.setRoles(new HashSet<>(Arrays.asList(rolEjemplo)));

        // When
        Set<ConstraintViolation<Usuario>> violaciones = validator.validate(usuario);

        // Then
        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("password")));
        assertTrue(violaciones.stream()
                .anyMatch(v -> v.getMessage().contains("La contraseña debe tener entre 16 y 60 digitos.")));
    }

    @Test
    @DisplayName("Debería fallar validación con contraseña muy larga")
    void deberiaFallarValidacionConPasswordMuyCorta() {
        // Given
        String passwordCorta = "Aa@1";
        Usuario usuario = new Usuario();
        usuario.setUsername("usuario@gmail.com");
        usuario.setPassword(passwordCorta);
        usuario.setRoles(new HashSet<>(Arrays.asList(rolEjemplo)));

        // When
        Set<ConstraintViolation<Usuario>> violaciones = validator.validate(usuario);

        // Then
        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("password")));
        assertTrue(violaciones.stream()
                .anyMatch(v -> v.getMessage().contains("La contraseña debe tener entre 16 y 60 digitos.")));
    }

    @Test
    @DisplayName("Debería fallar validación con contraseña sin caracteres especiales, números o mayúsculas")
    void deberiaFallarValidacionPorNoCumplirPatternPassword() {
        // Given
        String passwordInvalida = "a".repeat(16);
        Usuario usuario = new Usuario();
        usuario.setUsername("usuario@gmail.com");
        usuario.setPassword(passwordInvalida);
        usuario.setRoles(new HashSet<>(Arrays.asList(rolEjemplo)));

        // When
        Set<ConstraintViolation<Usuario>> violaciones = validator.validate(usuario);

        // Then
        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("password")));
        assertTrue(violaciones.stream()
                .anyMatch(v -> v.getMessage().contains("La contraseña debe contener al menos una mayuscula, una minuscula, un numero y un caracter especial.")));
    }

    @Test
    @DisplayName("Debería fallar validación con contraseña null")
    void deberiaFallarValidacionConPasswordNullYVacia() {
        // Given
        Usuario usuario1 = new Usuario();
        usuario1.setUsername("usuario@gmail.com");
        usuario1.setPassword(null);
        usuario1.setRoles(new HashSet<>(Arrays.asList(rolEjemplo)));

        Usuario usuario2 = new Usuario();
        usuario2.setUsername("usuario@gmail.com");
        usuario2.setPassword("");
        usuario2.setRoles(new HashSet<>(Arrays.asList(rolEjemplo)));

        // When
        Set<ConstraintViolation<Usuario>> violaciones1 = validator.validate(usuario1);
        Set<ConstraintViolation<Usuario>> violaciones2 = validator.validate(usuario2);

        // Then
        assertFalse(violaciones1.isEmpty());
        assertFalse(violaciones2.isEmpty());
    }

    // -------roles-------

    @Test
    @DisplayName("Debería fallar validación con roles null")
    void deberiaFallarValidacionConRolesNull() {
        // Given
        Usuario usuario = new Usuario();
        usuario.setUsername("usuario@gmail.com");
        usuario.setPassword("Contraseña@Segura123456");
        usuario.setRoles(null);

        // When
        Set<ConstraintViolation<Usuario>> violaciones = validator.validate(usuario);

        // Then
        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("roles")));
        assertTrue(violaciones.stream().anyMatch(v -> v.getMessage().contains("obligatorios")));
    }

    @Test
    @DisplayName("Debería fallar validación con lista de roles vacía")
    void deberiaFallarValidacionConListaRolesVacia() {
        // Given
        Usuario usuario = new Usuario();
        usuario.setUsername("usuario@gmail.com");
        usuario.setPassword("Contraseña@Segura123456");
        usuario.setRoles(new HashSet<>(Arrays.asList()));

        // When
        Set<ConstraintViolation<Usuario>> violaciones = validator.validate(usuario);

        // Then
        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("roles")));
        assertTrue(violaciones.stream().anyMatch(v -> v.getMessage().contains("obligatorios")));
    }

    // -------múltiples errores-------

    @Test
    @DisplayName("Debería validar múltiples errores simultáneamente")
    void deberiaValidarMultiplesErroresEnUsuario() {
        // Given - Usuario con múltiples errores
        Usuario usuarioInvalido = new Usuario();
        usuarioInvalido.setUsername(""); // Nombre vacío
        usuarioInvalido.setRoles(new HashSet<>(Arrays.asList())); // Lista vacía

        // When
        Set<ConstraintViolation<Usuario>> violaciones = validator.validate(usuarioInvalido);

        // Then
        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.size() >= 3); // Al menos 3 errores

        List<String> propiedadesConError =
                violaciones.stream().map(v -> v.getPropertyPath().toString()).toList();

        assertTrue(propiedadesConError.contains("username"));
        assertTrue(propiedadesConError.contains("roles"));
    }
}