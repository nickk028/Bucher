package ar.edu.huergo.vectorial.calidad.bucher.integration;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import jakarta.servlet.http.Cookie;
import org.springframework.http.MediaType;


@Transactional
class AuthIntegrationTest extends BaseApiTest {

    @Test
    @DisplayName("validar-token con cookie valida: 200 con username y roles")
    void validarTokenOk() throws Exception {
        mockMvc.perform(get("/auth/validar-token").cookie(new Cookie("JWT_TOKEN", tokenLector())))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username").value("lector@gmail.com"))
            .andExpect(jsonPath("$.roles", hasItem("ESCRITOR")));
    }

    @Test
    @DisplayName("validar-token solo con header (sin cookie)")
    void validarTokenSoloHeader() throws Exception {
        mockMvc.perform(conToken(get("/auth/validar-token"), tokenLector()))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("validar-token con cookie manipulada")
    void validarTokenManipulado() throws Exception {
        mockMvc.perform(get("/auth/validar-token").cookie(new Cookie("JWT_TOKEN", "abc.def.ghi")))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Sesión por cookie: el JWT del login sirve sin header Authorization")
    void sesionPorCookie() throws Exception {
        Cookie cookie = mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(Map.of("username", "lector@gmail.com",
                                                      "password", "LectorSuperSegura@123"))))
            .andExpect(status().isOk()).andReturn().getResponse().getCookie("JWT_TOKEN");

        mockMvc.perform(get("/usuario/propio").cookie(cookie))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username").value("lector@gmail.com"));
    }

    @Test
    @DisplayName("Recurso protegido con cookie inválida: 401")
    void cookieInvalida() throws Exception {
        mockMvc.perform(get("/usuario/propio").cookie(new Cookie("JWT_TOKEN", "basura")))
            .andExpect(status().isUnauthorized());
    }
}