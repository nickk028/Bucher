package ar.edu.huergo.vectorial.calidad.bucher.service.security;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

@DisplayName("Tests de Unidad - JwtTokenService")
public class JwtTokenServiceTest {

    private JwtTokenService jwtTokenService;
    private UserDetails userDetailsEjemplo;
    private Set<String> rolesEjemplo;

    @BeforeEach
    void setUp() {
        // Inicializar el servicio con valores de prueba
        String secretKey = "mi-clave-secreta-super-segura-para-jwt-testing-123456789";
        long expirationMillis = 3600000; // 1 hora

        jwtTokenService = new JwtTokenService(secretKey, expirationMillis);

        // Crear usuario de prueba
        userDetailsEjemplo = User.builder()
                .username("usuario@example.com")
                .password("Password@123456")
                .authorities("LECTOR")
                .build();

        rolesEjemplo = Set.of("LECTOR", "ADMIN");
    }

    @Test
    @DisplayName("Debería generar token JWT correctamente")
    void deberiaGenerarToken() {
        // When
        String token = jwtTokenService.generarToken(userDetailsEjemplo, rolesEjemplo);

        // Then
        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertTrue(token.split("\\.").length == 3); // JWT tiene 3 partes separadas por puntos
    }

    @Test
    @DisplayName("Debería extraer username del token correctamente")
    void deberiaExtraerUsername() {
        // Given
        String token = jwtTokenService.generarToken(userDetailsEjemplo, rolesEjemplo);

        // When
        String username = jwtTokenService.extraerUsername(token);

        // Then
        assertNotNull(username);
        assertEquals("usuario@example.com", username);
    }

    @Test
    @DisplayName("Debería validar token correctamente")
    void deberiaValidarToken() {
        // Given
        String token = jwtTokenService.generarToken(userDetailsEjemplo, rolesEjemplo);

        // When
        boolean esValido = jwtTokenService.esTokenValido(token, userDetailsEjemplo);

        // Then
        assertTrue(esValido);
    }

    @Test
    @DisplayName("Debería rechazar token inválido")
    void deberiaRechazarTokenInvalido() {
        // Given
        String tokenInvalido = "token.invalido.malformado";

        // When
        boolean esValido = jwtTokenService.esTokenValido(tokenInvalido, userDetailsEjemplo);

        // Then
        assertFalse(esValido);
    }

    @Test
    @DisplayName("Debería rechazar token para usuario diferente")
    void deberiaRechazarTokenParaUsuarioDiferente() {
        // Given
        String token = jwtTokenService.generarToken(userDetailsEjemplo, rolesEjemplo);
        
        UserDetails otroUsuario = User.builder()
                .username("otro@example.com")
                .password("Password@123456")
                .authorities("ADMIN")
                .build();

        // When
        boolean esValido = jwtTokenService.esTokenValido(token, otroUsuario);

        // Then
        assertFalse(esValido);
    }
}