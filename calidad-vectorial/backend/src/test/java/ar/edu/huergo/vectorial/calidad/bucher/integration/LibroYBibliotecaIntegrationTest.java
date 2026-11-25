package ar.edu.huergo.vectorial.calidad.bucher.integration;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import java.util.Map;

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

    @Test
    @DisplayName("POST /libro-usuario: sin estado y página 0 queda Pendiente")
    void crearPendiente() throws Exception {
        mockMvc.perform(conJson(post("/libro-usuario"), tokenDe("lector2@gmail.com"),
                Map.of("titulo", "Dune", "paginaActual", 0, "puntuacion", 0)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].titulo").value("Dune"))
            .andExpect(jsonPath("$[0].estadoLectura").value("Pendiente"));
    }

    @Test
    @DisplayName("POST /libro-usuario: sin estado y página > 0 queda Leyendo")
    void crearLeyendo() throws Exception {
        mockMvc.perform(conJson(post("/libro-usuario"), tokenDe("lector2@gmail.com"),
                Map.of("titulo", "Dune", "paginaActual", 10, "puntuacion", 0)))
            .andExpect(jsonPath("$[0].estadoLectura").value("Leyendo"));
    }

    @Test
    @DisplayName("POST /libro-usuario con libro inexistente: 404")
    void crearLibroInexistente() throws Exception {
        mockMvc.perform(conJson(post("/libro-usuario"), tokenDe("lector2@gmail.com"),
                Map.of("titulo", "Libro que no existe", "paginaActual", 0, "puntuacion", 0)))
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET /libro-usuario/estado/{e}: filtra por estado de lectura")
    void porEstado() throws Exception {
        String t = tokenDe("jrolando@gmail.com");     // seed: 2 Leyendo y 2 Pendiente
        mockMvc.perform(conToken(get("/libro-usuario/estado/Leyendo"), t)).andExpect(jsonPath("$.length()").value(2));
        mockMvc.perform(conToken(get("/libro-usuario/estado/Pendiente"), t)).andExpect(jsonPath("$.length()").value(2));
        mockMvc.perform(conToken(get("/libro-usuario/estado/Abandonado"), t)).andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    @DisplayName("PUT /libro-usuario/{id}: actualiza página y puntuación; fuera de rango 400; ajeno 404")
    void modificarLibroUsuario() throws Exception {
        String dueno = tokenDe("jrolando@gmail.com");
        String lista = mockMvc.perform(conToken(get("/libro-usuario"), dueno)).andReturn().getResponse().getContentAsString();
        long id = om.readTree(lista).get(0).get("id").asLong();

        mockMvc.perform(conJson(put("/libro-usuario/" + id), dueno,
                Map.of("paginaActual", 200, "estadoLectura", "Leyendo", "puntuacion", 80)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.paginaActual").value(200))
            .andExpect(jsonPath("$.puntuacion").value(80));
        mockMvc.perform(conJson(put("/libro-usuario/" + id), dueno, Map.of("puntuacion", 500)))
            .andExpect(status().isBadRequest());
        mockMvc.perform(conJson(put("/libro-usuario/" + id), tokenDe("lector2@gmail.com"), Map.of("puntuacion", 50)))
            .andExpect(status().isNotFound());
    }
}