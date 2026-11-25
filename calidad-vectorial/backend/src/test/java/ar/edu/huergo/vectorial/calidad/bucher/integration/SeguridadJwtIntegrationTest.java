package ar.edu.huergo.vectorial.calidad.bucher.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.web.servlet.MockMvc;

import ar.edu.huergo.vectorial.calidad.bucher.service.security.JwtTokenService;

@SpringBootTest(properties = "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class SeguridadJwtIntegrationTest {
    @Autowired MockMvc mockMvc;

    @Test
    @DisplayName("Sin token: 401 con ProblemDetail, sin stack trace")
    void sinToken() throws Exception {
        mockMvc.perform(get("/libro/todos"))
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.title").value("No autorizado"));
    }

    @Test 
    @DisplayName("Token con firma alterada: 401")
    void tokenManipulado() throws Exception {
        mockMvc.perform(get("/libro/todos").header("Authorization", "Bearer abc.def.ghi"))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Token firmado con otra clave: 401")
    void tokenOtraClave() throws Exception {
        String falso = new JwtTokenService("OtraClaveSecretaDeTreintaYDosCaracteres!!", 3600000)
            .generarToken(User.withUsername("lector@gmail.com").password("x").roles("ADMIN").build(),
                          Set.of("ROLE_ADMIN"));
        mockMvc.perform(get("/admin/estadisticas/libros").header("Authorization", "Bearer " + falso))
            .andExpect(status().isUnauthorized());
    }

    @Test 
    @DisplayName("Logout expira la cookie JWT")
    void logout() throws Exception {
        mockMvc.perform(post("/auth/logout"))
            .andExpect(status().isOk())
            .andExpect(cookie().maxAge("JWT_TOKEN", 0));
    }

    @Test
    @DisplayName("CORS: origen con autorización recibe cabecera; origen ajeno es rechazado")
    void cors() throws Exception {
        mockMvc.perform(options("/libro/todos")
                .header("Origin", "http://localhost:5173")
                .header("Access-Control-Request-Method", "GET"))
            .andExpect(header().string("Access-Control-Allow-Origin", "http://localhost:5173"));
        mockMvc.perform(options("/libro/todos")
                .header("Origin", "http://sitio-malicioso.com")
                .header("Access-Control-Request-Method", "GET"))
            .andExpect(status().isForbidden());
    }
}
