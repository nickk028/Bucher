package ar.edu.huergo.vectorial.calidad.bucher.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

import ar.edu.huergo.vectorial.calidad.bucher.repository.publication.PublicacionSocialRepository;
import ar.edu.huergo.vectorial.calidad.bucher.repository.publication.RegistroPrestamoRepository;

@Transactional
class AdminIntegrationTest extends BaseApiTest {

    @Autowired PublicacionSocialRepository socialRepository;
    @Autowired RegistroPrestamoRepository registroPrestamoRepository;

    @Test
    @DisplayName("/admin/estadisticas/posteos")
    void estadisticasPosteos() throws Exception {
        mockMvc.perform(conToken(get("/admin/estadisticas/posteos"), tokenAdmin()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.posteosPublicados").value(6))
            .andExpect(jsonPath("$.posteosEliminados").value(0))
            .andExpect(jsonPath("$.posteosPorMes.length()").value(12))
            .andExpect(jsonPath("$.categoriasMasMencionadas.length()").value(6));
    }

    @Test
    @DisplayName("/admin/estadisticas/prestamos")
    void estadisticasPrestamos() throws Exception {
        mockMvc.perform(conToken(get("/admin/estadisticas/prestamos"), tokenAdmin()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.prestamosTotales").value(10))
            .andExpect(jsonPath("$.prestamosIncompletos").value(6))
            .andExpect(jsonPath("$.prestamosCompletadosPorMes.length()").value(12))
            .andExpect(jsonPath("$.categoriasPrestamos.length()").value(13));
    }

    @Test
    @DisplayName("/admin/estadisticas/usuarios")
    void estadisticasUsuarios() throws Exception {
        mockMvc.perform(conToken(get("/admin/estadisticas/usuarios"), tokenAdmin()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.usuariosTotales").value(12))
            .andExpect(jsonPath("$.porcentajeUsuariosInactivos").value(0.0))
            .andExpect(jsonPath("$.usuariosNuevosPorMes.length()").value(12));
    }

    @Test
    @DisplayName("Todos los endpoints /admin/** rechazan a un LECTOR (403)")
    void lectorRechazado() throws Exception {
        String t = tokenLector();
        for (String url : List.of("/admin/estadisticas/posteos", "/admin/estadisticas/prestamos",
                "/admin/estadisticas/usuarios", "/admin/usuarios", "/admin/prestamos", "/admin/publicacionSocial")) {
            mockMvc.perform(conToken(get(url), t)).andExpect(status().isForbidden());
        }
    }

    @Test
    @DisplayName("GET /admin/usuarios: paginado, con orden por denuncias y tamaño máximo 50")
    void tablaUsuarios() throws Exception {
        String admin = tokenAdmin();
        mockMvc.perform(conToken(get("/admin/usuarios"), admin))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.totalElements").value(12))
            .andExpect(jsonPath("$.content[0].cantPublicaciones").exists());
        mockMvc.perform(conToken(get("/admin/usuarios?orden=DENUNCIAS"), admin)).andExpect(status().isOk());
        mockMvc.perform(conToken(get("/admin/usuarios?size=1000"), admin)).andExpect(jsonPath("$.size").value(50));
    }

    @Test
    @DisplayName("GET /admin/prestamos: página de préstamos activos")
    void tablaPrestamos() throws Exception {
        mockMvc.perform(conToken(get("/admin/prestamos"), tokenAdmin()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content").isArray())
            .andExpect(jsonPath("$.size").value(20));
    }

    @Test
    @DisplayName("GET /admin/publicacionSocial: todas, y filtro de eliminadas")
    void tablaPublicacionesSociales() throws Exception {
        String admin = tokenAdmin();
        mockMvc.perform(conToken(get("/admin/publicacionSocial"), admin))
            .andExpect(jsonPath("$.totalElements").value(6));
        mockMvc.perform(conToken(get("/admin/publicacionSocial?eliminadas=true"), admin))
            .andExpect(jsonPath("$.totalElements").value(0));

        Long id = socialRepository.findAll().get(0).getId();
        mockMvc.perform(conJson(delete("/publicacionSocial/eliminar/" + id), admin, Map.of("razon", "Spam")))
            .andExpect(status().isOk());

        mockMvc.perform(conToken(get("/admin/publicacionSocial?eliminadas=true"), admin))
            .andExpect(jsonPath("$.totalElements").value(1))
            .andExpect(jsonPath("$.content[0].eliminada").value(true));
        mockMvc.perform(conToken(get("/admin/publicacionSocial?orden=DENUNCIAS"), admin)).andExpect(status().isOk());
        mockMvc.perform(conToken(get("/admin/publicacionSocial?orden=USUARIO"), admin)).andExpect(status().isOk());
    }

    @Test
    @DisplayName("PUT /admin/prestamos/control/{id} y POST .../completar/{id}: cambian el estado de la publicación")
    void controlDePrestamo() throws Exception {
        Long id = registroPrestamoRepository.findAll().get(0).getId();
        String admin = tokenAdmin();

        mockMvc.perform(conJson(put("/admin/prestamos/control/" + id), admin,
                Map.of("confirmacionTitulo", "OK", "confirmacionAutor", "OK", "comentariosAdicionales", "Sin daños",
                       "estaEnCondiciones", "Si")))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.comentariosAdicionales").value("Sin daños"))
            .andExpect(jsonPath("$.publicacion.estadoPublicacion").value("enrevision"));

        mockMvc.perform(conToken(post("/admin/prestamos/control/completar/" + id), admin))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.publicacion.estadoPublicacion").value("aentregar"));
    }

    @Test
    @DisplayName("Control de préstamo con id inexistente: 400; LECTOR: 403")
    void controlInvalido() throws Exception {
        mockMvc.perform(conJson(put("/admin/prestamos/control/99999"), tokenAdmin(), Map.of("estaEnCondiciones", "Si")))
            .andExpect(status().isBadRequest());
        mockMvc.perform(conJson(put("/admin/prestamos/control/1"), tokenLector(), Map.of("estaEnCondiciones", "Si")))
            .andExpect(status().isForbidden());
    }
}