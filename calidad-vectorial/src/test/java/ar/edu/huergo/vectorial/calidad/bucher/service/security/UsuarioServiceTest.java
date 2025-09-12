package ar.edu.huergo.vectorial.calidad.bucher.service.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Rol;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;
import ar.edu.huergo.vectorial.calidad.bucher.repository.security.RolRepository;
import ar.edu.huergo.vectorial.calidad.bucher.repository.security.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests de Unidad - UsuarioService")
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RolRepository rolRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuarioEjemplo;
    private Rol rolEjemplo;

    @BeforeEach
    void setUp() {
        usuarioEjemplo = new Usuario();
        usuarioEjemplo.setId(1L);
        usuarioEjemplo.setUsername("usuario@test.com");
        usuarioEjemplo.setPassword("Password@123456789");

        rolEjemplo = new Rol();
        rolEjemplo.setId(1L);
        rolEjemplo.setNombre("LECTOR");
    }

    @Test
    @DisplayName("Debería obtener todos los usuarios")
    void deberiaObtenerTodosLosUsuarios() {

        List<Usuario> usuariosEsperados = Arrays.asList(usuarioEjemplo);
        when(usuarioRepository.findAll()).thenReturn(usuariosEsperados);

        Set<Usuario> resultado = usuarioService.obtenerTodosUsuarios();
        List<Usuario> resultadoList = new ArrayList<>(resultado);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(usuarioEjemplo.getUsername(), resultadoList.get(0).getUsername());
        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debería registrar usuario correctamente")
    void deberiaRegistrarUsuarioCorrectamente() {
        // Given
        String password = "Password@123456789";
        String verificationPassword = "Password@123456789";
        String passwordEncriptada = "encrypted_password";

        when(usuarioRepository.existsByUsername(usuarioEjemplo.getUsername())).thenReturn(false);
        when(passwordEncoder.encode(password)).thenReturn(passwordEncriptada);
        when(rolRepository.findByNombre("LECTOR")).thenReturn(Optional.of(rolEjemplo));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioEjemplo);

        // When
        Usuario resultado = usuarioService.registrar(usuarioEjemplo, password, verificationPassword);

        // Then
        assertNotNull(resultado);
        verify(usuarioRepository, times(1)).existsByUsername(usuarioEjemplo.getUsername());
        verify(passwordEncoder, times(1)).encode(password);
        verify(rolRepository, times(1)).findByNombre("LECTOR");
        verify(usuarioRepository, times(1)).save(usuarioEjemplo);

        // Verificar que la contraseña fue encriptada
        assertEquals(passwordEncriptada, usuarioEjemplo.getPassword());
        // Verificar que se asignó el rol
        assertTrue(usuarioEjemplo.getRoles().contains(rolEjemplo));
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando las contraseñas no coinciden")
    void deberiaLanzarExcepcionCuandoPasswordsNoCoinciden() {
        // Given
        String password = "Password@123456789";
        String verificationPassword = "Password*123456789";

        // When & Then
        IllegalArgumentException excepcion = assertThrows(IllegalArgumentException.class,
                () -> usuarioService.registrar(usuarioEjemplo, password, verificationPassword));

        assertEquals("Las contraseñas no coinciden", excepcion.getMessage());

        // Verificar que no se realizaron operaciones adicionales
        verify(usuarioRepository, never()).existsByUsername(any());
        verify(passwordEncoder, never()).encode(any());
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando el username ya existe")
    void deberiaLanzarExcepcionCuandoUsernameYaExiste() {
        // Given
        String password = "Password@123456789";
        String verificationPassword = "Password@123456789";

        when(usuarioRepository.existsByUsername(usuarioEjemplo.getUsername())).thenReturn(true);

        // When & Then
        IllegalArgumentException excepcion = assertThrows(IllegalArgumentException.class,
                () -> usuarioService.registrar(usuarioEjemplo, password, verificationPassword));

        assertEquals("El nombre de usuario ya está en uso", excepcion.getMessage());

        // Verificar que se verificó la existencia pero no se continuó
        verify(usuarioRepository, times(1)).existsByUsername(usuarioEjemplo.getUsername());
        verify(passwordEncoder, never()).encode(any());
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    @DisplayName("Debería manejar contraseñas vacías correctamente")
    void deberiaManejarContraseniasVacias() {
        // Given
        String passwordVacio = "";
        String verificationPassword = "Password@123456789";

        // When & Then
        IllegalArgumentException excepcion =
                assertThrows(IllegalArgumentException.class, () -> usuarioService
                        .registrar(usuarioEjemplo, passwordVacio, verificationPassword));

        assertEquals("Las contraseñas no coinciden", excepcion.getMessage());
    }

    @Test
    @DisplayName("Debería manejar contraseñas null correctamente")
    void deberiaManejarContraseniasNull() {
        // Given
        String passwordNull = null;
        String verificationPasswordNull = null;

        // When & Then
        IllegalArgumentException excepcion =
                assertThrows(IllegalArgumentException.class, () -> usuarioService
                        .registrar(usuarioEjemplo, passwordNull, verificationPasswordNull));

        assertEquals("Las contraseñas no pueden ser null", excepcion.getMessage());
    }
}