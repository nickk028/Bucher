package ar.edu.huergo.vectorial.calidad.bucher.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest 
@AutoConfigureMockMvc 
@Transactional
class LibroYBibliotecaIntegrationTest extends BaseApiTest {

    private static final String LIBRO_JSON = "{\"titulo\":\"Mi novela\",\"descripcion\":\"Una historia\","
        + "\"paginas\":120,\"edicion\":\"Primera\",\"categorias\":[\"drama\"]}";

    @Test 
    @DisplayName("ESCRITOR crea libro: 200 y queda con su autor")
    void escritorCrea() throws Exception {
        String t = token("lector@gmail.com", "LectorSuperSegura@123"); // tiene ESCRITOR
        mockMvc.perform(conToken(post("/libro/crear"), t)
                .contentType(MediaType.APPLICATION_JSON).content(LIBRO_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.precio").value(0));
    }

    @Test 
    @DisplayName("LECTOR sin rol ESCRITOR no puede crear libros: 403")
    void lectorNoCrea() throws Exception {
        String t = token("lector2@gmail.com", "LectorSuperSegura@123");
        mockMvc.perform(conToken(post("/libro/crear"), t)
                .contentType(MediaType.APPLICATION_JSON).content(LIBRO_JSON))
            .andExpect(status().isForbidden());
    }

    @Test 
    @DisplayName("Solo admin modifica precios: LECTOR recibe 403")
    void precioSoloAdmin() throws Exception {
        String t = token("lector@gmail.com", "LectorSuperSegura@123");
        mockMvc.perform(conToken(put("/libro/1/precio"), t)
                .contentType(MediaType.APPLICATION_JSON).content("{\"precio\":10}"))
            .andExpect(status().isForbidden());
    }

    @Test 
    @DisplayName("Libro inexistente: 404 con ProblemDetail")
    void libroInexistente() throws Exception {
        String t = token("lector@gmail.com", "LectorSuperSegura@123");
        mockMvc.perform(conToken(get("/libro/99999"), t))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.title").value("Recurso no encontrado"));
    }

    @Test 
    @DisplayName("Biblioteca: un usuario no puede ver el LibroUsuario de otro")
    void bibliotecaAjena() throws Exception {
        String dueno = token("jrolando@gmail.com", "LectorSuperSegura@123");
        String json = mockMvc.perform(conToken(get("/libro-usuario"), dueno))
            .andReturn().getResponse().getContentAsString();
        long idAjeno = om.readTree(json).get(0).get("id").asLong();

        String otro = token("lector2@gmail.com", "LectorSuperSegura@123");
        mockMvc.perform(conToken(get("/libro-usuario/" + idAjeno), otro))
            .andExpect(status().isNotFound());
        mockMvc.perform(conToken(delete("/libro-usuario/" + idAjeno), otro))
            .andExpect(status().isNotFound());
    }

    @Test 
    @DisplayName("Puntuación fuera de rango: 400")
    void puntuacionInvalida() throws Exception {
        String t = token("lector2@gmail.com", "LectorSuperSegura@123");
        mockMvc.perform(conToken(post("/libro-usuario"), t)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"titulo\":\"Dune\",\"paginaActual\":1,\"puntuacion\":500}"))
            .andExpect(status().isBadRequest());
    }
}