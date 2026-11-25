package ar.edu.huergo.vectorial.calidad.bucher.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.huergo.vectorial.calidad.bucher.repository.security.UsuarioRepository;
import ar.edu.huergo.vectorial.calidad.bucher.service.security.GoogleTokenService;


@Transactional
class AuthGoogleIntegrationTest extends BaseApiTest {

    @MockitoBean GoogleTokenService googleTokenService;
    @Autowired UsuarioRepository usuarioRepository;

    private GoogleIdToken.Payload payload(String email, String nombre) {
        GoogleIdToken.Payload p = new GoogleIdToken.Payload();
        p.setEmail(email);
        p.set("name", nombre);
        return p;
    }
    private static final String BODY = "{\"idToken\":\"tok\"}";

    @Test
    @DisplayName("Google: usuario nuevo se registra con plan gratuito y recibe token")
    void usuarioNuevo() throws Exception {
        when(googleTokenService.verificarToken("tok")).thenReturn(payload("nuevo.google@gmail.com", "Nuevo Google"));

        String resp = mockMvc.perform(post("/auth/google").contentType(MediaType.APPLICATION_JSON).content(BODY))
        .andDo(print())
        .andExpect(status().isOk())

            .andExpect(jsonPath("$.token").isNotEmpty())
            .andExpect(cookie().httpOnly("JWT_TOKEN", true))
            .andReturn().getResponse().getContentAsString();

        assertTrue(usuarioRepository.existsByUsername("nuevo.google@gmail.com"));
        String token = om.readTree(resp).get("token").asText();
        mockMvc.perform(conToken(get("/usuario/propio"), token))
            .andExpect(jsonPath("$.suscripcionActiva.nombre").value("Plan Gratis"));
    }

    @Test
    @DisplayName("Google: usuario existente inicia sesión sin duplicarse")
    void usuarioExistente() throws Exception {
        when(googleTokenService.verificarToken("tok")).thenReturn(payload("lector@gmail.com", "Lector"));
        long antes = usuarioRepository.count();

        mockMvc.perform(post("/auth/google").contentType(MediaType.APPLICATION_JSON).content(BODY))
            .andExpect(status().isOk());
        assertEquals(antes, usuarioRepository.count());
    }

    @Test
    @DisplayName("Google: token invalido: 400")
    void tokenInvalido() throws Exception {
        when(googleTokenService.verificarToken("tok")).thenThrow(new IllegalArgumentException("Token de Google inválido"));
        mockMvc.perform(post("/auth/google").contentType(MediaType.APPLICATION_JSON).content(BODY))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Google: idToken vacio: 400 y no se consulta a Google")
    void idTokenVacio() throws Exception {
        mockMvc.perform(post("/auth/google").contentType(MediaType.APPLICATION_JSON).content("{\"idToken\":\"\"}"))
            .andExpect(status().isBadRequest());
        verify(googleTokenService, never()).verificarToken(any());
    }
}