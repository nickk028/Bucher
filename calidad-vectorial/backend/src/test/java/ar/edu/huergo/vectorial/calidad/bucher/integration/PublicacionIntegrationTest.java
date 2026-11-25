package ar.edu.huergo.vectorial.calidad.bucher.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.huergo.vectorial.calidad.bucher.repository.publication.PublicacionRepository;

@Transactional
class PublicacionIntegrationTest extends BaseApiTest {

    @Autowired PublicacionRepository publicacionRepository;

    private Long idOrgullo() {   // publicación de paulamartinez@gmail.com
        return publicacionRepository.findByLibroTituloIgnoreCase("Orgullo y prejuicio").orElseThrow().getId();
    }
    private Map<String, Object> nueva(String titulo, int limiteDias) {
        return Map.of("titulo", titulo, "descripcion", "Ejemplar en buen estado", "limiteDias", limiteDias,
                      "detallesEstadoLibro", "Muy bueno", "detallesAdicionales", List.of());
    }

    @Test @DisplayName("POST /publicacion/crear: queda disponible y a nombre del usuario")
    void crear() throws Exception {
        mockMvc.perform(conJson(post("/publicacion/crear"), tokenDe("lector2@gmail.com"), nueva("Dune", 14)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.titulo").value("Dune"))
            .andExpect(jsonPath("$.estadoPublicacion").value("disponible"))
            .andExpect(jsonPath("$.usuarioCreador").value("lector2@gmail.com"));
    }

    @Test @DisplayName("POST /publicacion/crear: límite de días 0 → 400; libro inexistente → 404")
    void crearInvalido() throws Exception {
        String t = tokenDe("lector2@gmail.com");
        mockMvc.perform(conJson(post("/publicacion/crear"), t, nueva("Dune", 0))).andExpect(status().isBadRequest());
        mockMvc.perform(conJson(post("/publicacion/crear"), t, nueva("Libro que no existe", 14))).andExpect(status().isNotFound());
    }

    @Test @DisplayName("PUT /publicacion/actualizar: el dueño cambia la descripción")
    void actualizarDueno() throws Exception {
        mockMvc.perform(conJson(put("/publicacion/actualizar/" + idOrgullo()), tokenDe("paulamartinez@gmail.com"),
                Map.of("descripcion", "Descripción actualizada")))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.descripcion").value("Descripción actualizada"));
    }

    @Test @DisplayName("PUT /publicacion/actualizar: publicación ajena → 404")
    void actualizarAjena() throws Exception {
        mockMvc.perform(conJson(put("/publicacion/actualizar/" + idOrgullo()), tokenDe("lector2@gmail.com"),
                Map.of("descripcion", "Intento de pisar")))
            .andExpect(status().isNotFound());
    }

    @Test @DisplayName("PUT /publicacion/actualizar: un usuario común no puede cambiar el estado (mass assignment)")
    void usuarioNoCambiaEstado() throws Exception {
        mockMvc.perform(conJson(put("/publicacion/actualizar/" + idOrgullo()), tokenDe("paulamartinez@gmail.com"),
                Map.of("estadoPublicacion", "demorado")))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.estadoPublicacion").value("disponible"));
    }

    @Test @DisplayName("PUT /publicacion/actualizar: el admin sí puede cambiar el estado")
    void adminCambiaEstado() throws Exception {
        mockMvc.perform(conJson(put("/publicacion/actualizar/" + idOrgullo()), tokenAdmin(),
                Map.of("estadoPublicacion", "demorado")))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.estadoPublicacion").value("demorado"));
    }

    @Test @DisplayName("PUT /publicacion/actualizar: límite de días > 365 → 400")
    void limiteExcedido() throws Exception {
        mockMvc.perform(conJson(put("/publicacion/actualizar/" + idOrgullo()), tokenDe("paulamartinez@gmail.com"),
                Map.of("limiteDias", 400)))
            .andExpect(status().isBadRequest());
    }
}