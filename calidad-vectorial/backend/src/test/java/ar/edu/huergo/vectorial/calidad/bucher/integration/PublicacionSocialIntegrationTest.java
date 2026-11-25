package ar.edu.huergo.vectorial.calidad.bucher.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.huergo.vectorial.calidad.bucher.repository.publication.PublicacionSocialRepository;


@SpringBootTest 
@AutoConfigureMockMvc 
@Transactional
class PublicacionSocialIntegrationTest extends BaseApiTest {
    @Autowired PublicacionSocialRepository repo;

    private Long idDe(String username) {
        return repo.findAll().stream()
            .filter(p -> p.getUsuario().getUsername().equals(username)).findFirst().orElseThrow().getId();
    }

    @Test 
    @DisplayName("Crear posteo válido: 200")
    void crear() throws Exception {
        String t = token("lector2@gmail.com", "LectorSuperSegura@123");
        mockMvc.perform(conToken(post("/publicacionSocial/crear"), t)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"textoPublicacion\":\"Hola a todos\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.textoPublicacion").value("Hola a todos"));
    }

    @Test 
    @DisplayName("Posteo vacío: 400 con errores por campo")
    void crearVacio() throws Exception {
        String t = token("lector2@gmail.com", "LectorSuperSegura@123");
        mockMvc.perform(conToken(post("/publicacionSocial/crear"), t)
                .contentType(MediaType.APPLICATION_JSON).content("{\"textoPublicacion\":\"\"}"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.errores.textoPublicacion").exists());
    }

    @Test 
    @DisplayName("Votar positivo incrementa el contador")
    void votar() throws Exception {
        Long id = idDe("lector@gmail.com");
        String t = token("lector2@gmail.com", "LectorSuperSegura@123");
        mockMvc.perform(conToken(post("/publicacionSocial/voto/" + id), t)
                .contentType(MediaType.APPLICATION_JSON).content("{\"tipo\":\"POSITIVO\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.votosPositivos").value(1))
            .andExpect(jsonPath("$.votoUsuario").value("POSITIVO"));
    }

    @Test 
    @DisplayName("Reportar publicación propia: 400")
    void reportarPropia() throws Exception {
        Long id = idDe("lector@gmail.com");
        String t = token("lector@gmail.com", "LectorSuperSegura@123");
        mockMvc.perform(conToken(post("/publicacionSocial/reporte/" + id), t))
            .andExpect(status().isBadRequest());
    }

    @Test 
    @DisplayName("Reportar dos veces la misma publicación: 400 la segunda")
    void reportarDuplicado() throws Exception {
        Long id = idDe("lector@gmail.com");
        String t = token("lector2@gmail.com", "LectorSuperSegura@123");
        mockMvc.perform(conToken(post("/publicacionSocial/reporte/" + id), t)).andExpect(status().isOk());
        mockMvc.perform(conToken(post("/publicacionSocial/reporte/" + id), t)).andExpect(status().isBadRequest());
    }

    @Test 
    @DisplayName("Eliminar posteo ajeno siendo LECTOR: 404")
    void eliminarAjeno() throws Exception {
        Long id = idDe("lector@gmail.com");
        String t = token("lector2@gmail.com", "LectorSuperSegura@123");
        mockMvc.perform(conToken(delete("/publicacionSocial/eliminar/" + id), t))
            .andExpect(status().isNotFound());
    }

    @Test 
    @DisplayName("Admin elimina posteo ajeno: sin razón 400, con razón 200 y queda marcado")
    void adminElimina() throws Exception {
        Long id = idDe("lector@gmail.com");
        String admin = token("admin@gmail.com", "AdminSuperSegura@123");
        mockMvc.perform(conToken(delete("/publicacionSocial/eliminar/" + id), admin))
            .andExpect(status().isBadRequest());
        mockMvc.perform(conToken(delete("/publicacionSocial/eliminar/" + id), admin)
                .contentType(MediaType.APPLICATION_JSON).content("{\"razon\":\"Spam\"}"))
            .andExpect(status().isOk());
    }

    @Test 
    @DisplayName("Un LECTOR no ve el contenido de un posteo eliminado")
    void eliminadoOculto() throws Exception {
        Long id = idDe("lector@gmail.com");
        String admin = token("admin@gmail.com", "AdminSuperSegura@123");
        mockMvc.perform(conToken(delete("/publicacionSocial/eliminar/" + id), admin)
                .contentType(MediaType.APPLICATION_JSON).content("{\"razon\":\"Spam\"}"));
        String lector = token("lector2@gmail.com", "LectorSuperSegura@123");
        mockMvc.perform(conToken(get("/publicacionSocial/" + id), lector))
            .andExpect(jsonPath("$.eliminada").value(true))
            .andExpect(jsonPath("$.textoPublicacion").doesNotExist());
    }

    @Test 
    @DisplayName("DELETE /publicacionSocial/voto/{id}: quita el voto y baja el contador")
    void quitarVoto() throws Exception {
        Long id = idDe("lector@gmail.com");
        String t = tokenDe("lector2@gmail.com");
        mockMvc.perform(conJson(post("/publicacionSocial/voto/" + id), t, Map.of("tipo", "POSITIVO")))
            .andExpect(jsonPath("$.votosPositivos").value(1));
        mockMvc.perform(conToken(delete("/publicacionSocial/voto/" + id), t))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.votosPositivos").value(0))
            .andExpect(jsonPath("$.votoUsuario").doesNotExist());
    }

    @Test 
    @DisplayName("DELETE /publicacionSocial/voto/{id} sin haber votado: 404")
    void quitarVotoInexistente() throws Exception {
        Long id = idDe("lector@gmail.com");
        mockMvc.perform(conToken(delete("/publicacionSocial/voto/" + id), tokenDe("lector2@gmail.com")))
            .andExpect(status().isNotFound());
    }
}