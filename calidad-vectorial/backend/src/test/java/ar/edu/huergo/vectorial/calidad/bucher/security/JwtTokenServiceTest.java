package ar.edu.huergo.vectorial.calidad.bucher.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import ar.edu.huergo.vectorial.calidad.bucher.service.security.JwtTokenService;

/**
 * Tests de seguridad para JwtTokenService
 *
 * CONCEPTOS DEMOSTRADOS:
 * 1. Testing de servicios de seguridad
 * 2. Mocking de UserDetails
 * 3. Verificación de tokens JWT
 * 4. Tests de expiración y validación de tokens
 * 5. Manejo de excepciones de seguridad
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@DisplayName("Tests de Unidad - JwtTokenService")
public class JwtTokenServiceTest {

    private JwtTokenService jwtTokenService;
    private UserDetails userDetails;

    private static final String SECRET_KEY =
            "mi-clave-secreta-para-jwt-que-debe-ser-lo-suficientemente-larga-para-ser-segura";
    private static final long EXPIRATION_MS = 3600000; // 1 hora

    @SuppressWarnings({"unchecked", "rawtypes"})
    @BeforeEach
    void setUp() {
        // Crear el servicio con valores de prueba
        jwtTokenService = new JwtTokenService(SECRET_KEY, EXPIRATION_MS);

        // Crear mock de UserDetails
        userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("usuario@example.com");

        when(userDetails.getAuthorities())
                .thenReturn((Collection) Arrays.asList(
                        new SimpleGrantedAuthority("ROLE_LECTOR"),
                        new SimpleGrantedAuthority("ROLE_ADMIN")));
    }

    @Test
    @DisplayName("Debería generar token JWT válido")
    void deberiaGenerarTokenJwtValido() {
        // Given
        Set<String> roles = new HashSet<>(Arrays.asList("ROLE_LECTOR", "ROLE_ADMIN"));

        // When
        String token = jwtTokenService.generarToken(userDetails, roles);

        // Then
        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertTrue(token.contains("."), "El token JWT debería tener formato con puntos");

        // Verificar que el token tiene 3 partes (header.payload.signature)
        String[] partes = token.split("\\.");
        assertEquals(3, partes.length, "El token JWT debería tener 3 partes");
    }

    @Test
    @DisplayName("Debería extraer username del token correctamente")
    void deberiaExtraerUsernameDelToken() {
        // Given
        Set<String> roles = new HashSet<>(Arrays.asList("ROLE_LECTOR"));
        String token = jwtTokenService.generarToken(userDetails, roles);

        // When
        String usernameExtraido = jwtTokenService.extraerUsername(token);

        // Then
        assertEquals("usuario@example.com", usernameExtraido);
    }

    @Test
    @DisplayName("Debería validar token correctamente para usuario válido")
    void deberiaValidarTokenCorrectamente() {
        // Given
        Set<String> roles = new HashSet<>(Arrays.asList("ROLE_LECTOR"));
        String token = jwtTokenService.generarToken(userDetails, roles);

        // When
        boolean esValido = jwtTokenService.esTokenValido(token, userDetails);

        // Then
        assertTrue(esValido);
    }

    @Test
    @DisplayName("Debería rechazar token para usuario diferente")
    void deberiaRechazarTokenParaUsuarioDiferente() {
        // Given
        Set<String> roles = new HashSet<>(Arrays.asList("ROLE_LECTOR"));
        String token = jwtTokenService.generarToken(userDetails, roles);

        // Crear otro usuario
        UserDetails otroUsuario = mock(UserDetails.class);
        when(otroUsuario.getUsername()).thenReturn("otro@example.com");

        // When
        boolean esValido = jwtTokenService.esTokenValido(token, otroUsuario);

        // Then
        assertFalse(esValido);
    }

    @Test
    @DisplayName("Debería rechazar token malformado")
    void deberiaRechazarTokenMalformado() {
        // Given
        String tokenMalformado = "token.malformado.invalido";

        // When & Then
        assertThrows(Exception.class, () -> {
            jwtTokenService.extraerUsername(tokenMalformado);
        });
    }

    @Test
    @DisplayName("Debería rechazar token vacío")
    void deberiaRechazarTokenVacio() {
        // Given
        String tokenVacio = "";

        // When & Then
        assertThrows(Exception.class, () -> {
            jwtTokenService.extraerUsername(tokenVacio);
        });
    }

    @Test
    @DisplayName("Debería rechazar token null")
    void deberiaRechazarTokenNull() {
        // Given
        String tokenNull = null;

        // When & Then
        assertThrows(Exception.class, () -> {
            jwtTokenService.extraerUsername(tokenNull);
        });
    }

    @Test
    @DisplayName("Debería generar tokens diferentes para llamadas consecutivas")
    void deberiaGenerarTokensDiferentesParaLlamadasConsecutivas() {
        // Given
        Set<String> roles = new HashSet<>(Arrays.asList("ROLE_LECTOR"));

        // When
        String token1 = jwtTokenService.generarToken(userDetails, roles);
        // Esperar un poco para asegurar diferente timestamp
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        String token2 = jwtTokenService.generarToken(userDetails, roles);

        // Then
        assertNotEquals(token1, token2, "Los tokens deberían ser diferentes");
    }

    @Test
    @DisplayName("Debería manejar roles vacíos")
    void deberiaManejarRolesVacios() {
        // Given
        Set<String> rolesVacios = new HashSet<>(Arrays.asList());

        // When
        String token = jwtTokenService.generarToken(userDetails, rolesVacios);

        // Then
        assertNotNull(token);
        assertFalse(token.isEmpty());

        // Verificar que se puede extraer el username
        String usernameExtraido = jwtTokenService.extraerUsername(token);
        assertEquals("usuario@example.com", usernameExtraido);
    }

    @Test
    @DisplayName("Debería manejar múltiples roles")
    void deberiaManejarMultiplesRoles() {
        // Given
        Set<String> multiplesRoles = new HashSet<>(Arrays.asList("ROLE_LECTOR", "ROLE_ADMIN"));

        // When
        String token = jwtTokenService.generarToken(userDetails, multiplesRoles);

        // Then
        assertNotNull(token);
        assertFalse(token.isEmpty());

        String usernameExtraido = jwtTokenService.extraerUsername(token);
        assertEquals("usuario@example.com", usernameExtraido);
    }

    @Test
    @DisplayName("Debería rechazar token expirado")
    void deberiaRechazarTokenExpirado() {
        // Given - Crear servicio con expiración muy corta (1ms)
        JwtTokenService servicioConExpiracionCorta = new JwtTokenService(SECRET_KEY, 1);
        Set<String> roles = new HashSet<>(Arrays.asList("ROLE_LECTOR"));

        String token = servicioConExpiracionCorta.generarToken(userDetails, roles);

        // Esperar a que expire
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // When & Then
        assertThrows(Exception.class, () -> {
            servicioConExpiracionCorta.extraerUsername(token);
        });
    }

    @Test
    @DisplayName("Debería validar token dentro del tiempo de expiración")
    void deberiaValidarTokenDentroDelTiempoDeExpiracion() {
        // Given
        Set<String> roles = new HashSet<>(Arrays.asList("ROLE_LECTOR"));
        String token = jwtTokenService.generarToken(userDetails, roles);

        // When - Validar inmediatamente (debería estar válido)
        boolean esValido = jwtTokenService.esTokenValido(token, userDetails);

        // Then
        assertTrue(esValido);
    }

    @Test
    @DisplayName("Debería manejar usernames con caracteres especiales")
    void deberiaManejarUsernamesConCaracteresEspeciales() {
        // Given
        UserDetails usuarioEspecial = mock(UserDetails.class);
        when(usuarioEspecial.getUsername()).thenReturn("usuario.especial+test@dominio-test.com");

        Set<String> roles = new HashSet<>(Arrays.asList("ROLE_LECTOR"));

        // When
        String token = jwtTokenService.generarToken(usuarioEspecial, roles);
        String usernameExtraido = jwtTokenService.extraerUsername(token);

        // Then
        assertEquals("usuario.especial+test@dominio-test.com", usernameExtraido);
        assertTrue(jwtTokenService.esTokenValido(token, usuarioEspecial));
    }

    @Test
    @DisplayName("Debería generar token para rol LECTOR")
    void deberiaGenerarTokenParaRolLector() {
        // Given
        Set<String> roles = new HashSet<>(Arrays.asList("ROLE_LECTOR"));

        // When
        String token = jwtTokenService.generarToken(userDetails, roles);

        // Then
        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertTrue(jwtTokenService.esTokenValido(token, userDetails));
    }

    @Test
    @DisplayName("Debería generar token para rol ADMIN")
    void deberiaGenerarTokenParaRolAdmin() {
        // Given
        Set<String> roles = new HashSet<>(Arrays.asList("ROLE_ADMIN"));

        // When
        String token = jwtTokenService.generarToken(userDetails, roles);

        // Then
        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertTrue(jwtTokenService.esTokenValido(token, userDetails));
    }
}