package ar.edu.huergo.vectorial.calidad.bucher.integration;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.huergo.vectorial.calidad.bucher.repository.publication.PublicacionRepository;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DisplayName("Integración")
class ApiIntegrationTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper om;
    @Autowired PublicacionRepository publicacionRepository;

    private String token(String user, String pass) throws Exception {
        String json = mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(Map.of("username", user, "password", pass))))
            .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        return om.readTree(json).get("token").asText();
    }
    private RequestBuilder conToken(MockHttpServletRequestBuilder b, String t) {
        return b.header("Authorization", "Bearer " + t);
    }

    @Test 
    @DisplayName("Login válido: 200, token y cookie HttpOnly")
    void loginValido() throws Exception {
        mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"lector@gmail.com\",\"password\":\"LectorSuperSegura@123\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").isNotEmpty())
            .andExpect(cookie().httpOnly("JWT_TOKEN", true));
    }

    @Test 
    @DisplayName("Login con contraseña incorrecta: 401 sin datos internos")
    void loginIncorrecto() throws Exception {
        mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"lector@gmail.com\",\"password\":\"OtraContrasena@123456\"}"))
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.title").value("Credenciales inválidas"))
            .andExpect(jsonPath("$.stackTrace").doesNotExist());
    }

    @Test 
    @DisplayName("Registro con contraseña corta: 400 con detalle por campo")
    void registroInvalido() throws Exception {
        mockMvc.perform(post("/usuario/registrar").contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"nuevo@test.com\",\"password\":\"corta\","
                       + "\"verificationPassword\":\"corta\",\"nickname\":\"Nuevo\"}"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.errores.password").exists());
    }

    @Test 
    @DisplayName("LECTOR en /admin: 403; sin token: 401; ADMIN: 200")
    void controlDeAcceso() throws Exception {
        String lector = token("lector@gmail.com", "LectorSuperSegura@123");
        String admin = token("admin@gmail.com", "AdminSuperSegura@123");

        mockMvc.perform(conToken(get("/admin/estadisticas/libros"), lector)).andExpect(status().isForbidden());
        mockMvc.perform(get("/admin/estadisticas/libros")).andExpect(status().isUnauthorized());
        mockMvc.perform(conToken(get("/admin/estadisticas/libros"), admin)).andExpect(status().isOk());
    }

    @Test 
    @DisplayName("Plan gratuito no puede pedir préstamos: 422")
    void planGratuitoNoPuedePedirPrestamo() throws Exception {
        Long id = publicacionRepository.findByLibroTituloIgnoreCase("Orgullo y prejuicio").orElseThrow().getId();
        String t = token("lector2@gmail.com", "LectorSuperSegura@123");

        mockMvc.perform(conToken(post("/publicacion/prestamo/" + id), t))
            .andExpect(status().isUnprocessableEntity());
    }

    @Test 
    @DisplayName("Eliminar publicación ajena: 404 y no se borra")
    void eliminarAjena() throws Exception {
        Long id = publicacionRepository.findByLibroTituloIgnoreCase("Orgullo y prejuicio").orElseThrow().getId();
        String t = token("lector2@gmail.com", "LectorSuperSegura@123");

        mockMvc.perform(conToken(delete("/publicacion/eliminar/" + id), t)).andExpect(status().isNotFound());
        assertTrue(publicacionRepository.existsById(id));
    }

    @Test 
    @DisplayName("LECTOR no puede devolver préstamos (solo admin): 403")
    void lectorNoPuedeDevolver() throws Exception {
        String t = token("lector@gmail.com", "LectorSuperSegura@123");
        mockMvc.perform(conToken(post("/publicacion/devolucion/1"), t)).andExpect(status().isForbidden());
    }
}