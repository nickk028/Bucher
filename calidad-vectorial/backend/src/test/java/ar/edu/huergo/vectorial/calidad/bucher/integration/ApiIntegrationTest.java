package ar.edu.huergo.vectorial.calidad.bucher.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.Estado;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.Publicacion;
import ar.edu.huergo.vectorial.calidad.bucher.repository.publication.PublicacionRepository;
import ar.edu.huergo.vectorial.calidad.bucher.service.payment.MedioPagoService;

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
    @DisplayName("contratar plan -> pedir préstamo -> admin devuelve")
    void flujoCompletoPrestamo() throws Exception {
        Publicacion pub = publicacionRepository.findByLibroTituloIgnoreCase("Orgullo y prejuicio").orElseThrow();
        String lector = token("lector2@gmail.com", "LectorSuperSegura@123");
        String admin = token("admin@gmail.com", "AdminSuperSegura@123");

        String contratar = om.writeValueAsString(Map.of(
            "suscripcionId", 2, "medioPagoId", MedioPagoService.ID_MEDIO_PAGO_VISA,
            "numeroTarjeta", "4111111111111111", "vencimiento", "12/30",
            "cvv", "123", "titular", "TITULAR DE PRUEBA"));
        mockMvc.perform(
            post("/registro-suscripcion/contratar")
                .header("Authorization", "Bearer " + lector)
                .contentType(MediaType.APPLICATION_JSON)
                .content(contratar)
        ).andExpect(status().isOk());

        mockMvc.perform(conToken(post("/publicacion/prestamo/" + pub.getId()), lector))
            .andExpect(status().isOk());
        assertEquals(Estado.solicitado, pub.getEstadoPublicacion());

        mockMvc.perform(conToken(post("/publicacion/devolucion/" + pub.getId()), admin))
            .andExpect(status().isOk());
        assertEquals(Estado.disponible, pub.getEstadoPublicacion());
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
}