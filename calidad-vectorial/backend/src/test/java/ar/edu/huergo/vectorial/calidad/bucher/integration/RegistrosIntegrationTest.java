package ar.edu.huergo.vectorial.calidad.bucher.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;
import ar.edu.huergo.vectorial.calidad.bucher.repository.publication.RegistroPrestamoRepository;
import ar.edu.huergo.vectorial.calidad.bucher.repository.security.UsuarioRepository;

@Transactional
class RegistrosIntegrationTest extends BaseApiTest {

    @Autowired UsuarioRepository usuarioRepository;
    @Autowired RegistroPrestamoRepository registroPrestamoRepository;

    private Long idPrestamoDeLector2() {
        Usuario u = usuarioRepository.findByUsername("lector2@gmail.com").orElseThrow();
        return registroPrestamoRepository.findAllByUsuario(u).get(0).getId();
    }
    private String contratar() throws Exception {
        return om.writeValueAsString(Map.of("suscripcionId", 2, "medioPagoId", 2,
            "numeroTarjeta", "4111111111111111", "vencimiento", "12/30", "cvv", "123", "titular", "TITULAR DE PRUEBA"));
    }


    @Test
    @DisplayName("GET /registro: lista solo los préstamos del usuario")
    void misPrestamos() throws Exception {
        mockMvc.perform(conToken(get("/registro"), tokenDe("lector2@gmail.com")))
            .andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @DisplayName("GET /registro/{id}: dueño y admin ven el registro; otro usuario no")
    void prestamoPorId() throws Exception {
        Long id = idPrestamoDeLector2();
        mockMvc.perform(conToken(get("/registro/" + id), tokenDe("lector2@gmail.com"))).andExpect(status().isOk());
        mockMvc.perform(conToken(get("/registro/" + id), tokenAdmin())).andExpect(status().isOk());
        mockMvc.perform(conToken(get("/registro/" + id), tokenLector())).andExpect(status().isBadRequest()); // ver hallazgo 5
    }

    @Test
    @DisplayName("GET /registro/publicacion/{id}: dueño del préstamo")
    void prestamoPorPublicacion() throws Exception {
        mockMvc.perform(conToken(get("/registro/publicacion/" + idPrestamoDeLector2()), tokenDe("lector2@gmail.com")))
            .andExpect(status().isOk());
    }

    
    @Test
    @DisplayName("Usuario sin compras: listado con solo el plan gratuito")
    void listadoSinCompras() throws Exception {
        mockMvc.perform(conToken(get("/registro-suscripcion"), tokenLector()))
            .andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    @DisplayName("Tras contratar: listado de 2 y plan activo = Bücher+")
    void listadoTrasContratar() throws Exception {
        String t = tokenDe("lector2@gmail.com");
        mockMvc.perform(conJson(post("/registro-suscripcion/contratar"), t, contratar())).andExpect(status().isOk());
        mockMvc.perform(conToken(get("/registro-suscripcion"), t)).andExpect(jsonPath("$.length()").value(2));
        // se compara por id y no por nombre para evitar problemas de encoding con la "ü"
        mockMvc.perform(conToken(get("/registro-suscripcion/activa"), t)).andExpect(status().isOk())
            .andExpect(jsonPath("$.limiteLibros").value(5));
    }

    @Test
    @DisplayName("GET /registro-suscripcion/{id}: dueño 200, admin 200, otro usuario 404")
    void registroPorId() throws Exception {
        String dueno = tokenDe("lector2@gmail.com");
        String resp = mockMvc.perform(conJson(post("/registro-suscripcion/contratar"), dueno, contratar()))
            .andReturn().getResponse().getContentAsString();
        long id = om.readTree(resp).get("id").asLong();

        mockMvc.perform(conToken(get("/registro-suscripcion/" + id), dueno))
            .andExpect(status().isOk()).andExpect(jsonPath("$.usuario").value("lector2@gmail.com"));
        mockMvc.perform(conToken(get("/registro-suscripcion/" + id), tokenAdmin())).andExpect(status().isOk());
        mockMvc.perform(conToken(get("/registro-suscripcion/" + id), tokenLector())).andExpect(status().isNotFound());
        mockMvc.perform(conToken(get("/registro-suscripcion/99999"), tokenAdmin())).andExpect(status().isNotFound());
    }
}