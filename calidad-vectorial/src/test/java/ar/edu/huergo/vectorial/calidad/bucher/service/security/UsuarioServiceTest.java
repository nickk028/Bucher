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
        // Given
        List<Usuario> usuariosEsperados = Arrays.asList(usuarioEjemplo);
        when(usuarioRepository.findAll()).thenReturn(usuariosEsperados);

        // When
        Set<Usuario> resultado = usuarioService.obtenerTodosUsuarios();
        List<Usuario> resultadoList = new ArrayList<>(resultado);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(usuarioEjemplo.getUsername(), resultadoList.get(0).getUsername());
        verify(usuarioRepository, times(1)).findAll();
    }
}