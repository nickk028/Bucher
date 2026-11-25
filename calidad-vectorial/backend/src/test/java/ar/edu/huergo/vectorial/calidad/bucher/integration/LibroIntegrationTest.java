package ar.edu.huergo.vectorial.calidad.bucher.integration;

import static org.hamcrest.Matchers.greaterThan;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.huergo.vectorial.calidad.bucher.repository.book.LibroRepository;

@Transactional
class LibroIntegrationTest extends BaseApiTest {

    @Autowired LibroRepository libroRepository;

    private Long idDe(String titulo) { return libroRepository.findByTituloIgnoringCase(titulo).orElseThrow().getId(); }

    @Test
    @DisplayName("GET /libro/todos")
    void todos() throws Exception {
        mockMvc.perform(conToken(get("/libro/todos"), tokenLector()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()", greaterThan(50)));
    }

    @Test
    @DisplayName("GET /libro/titulo/{t}: encontrado 200 / inexistente 404")
    void porTitulo() throws Exception {
        String t = tokenLector();
        mockMvc.perform(conToken(get("/libro/titulo/{t}", "Dune"), t))
            .andExpect(status().isOk()).andExpect(jsonPath("$.titulo").value("Dune"));
        mockMvc.perform(conToken(get("/libro/titulo/{t}", "No existe este libro"), t))
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET /libro/categoria/{c}: lista libros de la categoría")
    void porCategoria() throws Exception {
        mockMvc.perform(conToken(get("/libro/categoria/terror"), tokenLector()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()", greaterThan(0)));
    }

    @Test
    @DisplayName("GET /libro/categoria/{c} con categoría inexistente: 400")
    void categoriaInvalida() throws Exception {
        mockMvc.perform(conToken(get("/libro/categoria/xxx"), tokenLector()))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /libro/tendencias: devuelve una lista")
    void tendencias() throws Exception {
        mockMvc.perform(conToken(get("/libro/tendencias"), tokenLector()))
            .andExpect(status().isOk()).andExpect(jsonPath("$").isArray());
    }

    @Test
    @DisplayName("GET /libro/ordenados: mapa por categoría con todas las claves")
    void ordenados() throws Exception {
        mockMvc.perform(conToken(get("/libro/ordenados"), tokenLector()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.terror").isArray())
            .andExpect(jsonPath("$.infantil").isArray());
    }

    @Test
    @DisplayName("GET /libro/filtrar/titulo/{t}: filtra por título parcial sin importar mayúsculas")
    void filtrar() throws Exception {
        mockMvc.perform(conToken(get("/libro/filtrar/titulo/{t}", "harry"), tokenLector()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(6));
    }

    @Test
    @DisplayName("PUT /libro/{id}/precio como admin: nuevo registro en el historial")
    void cambiarPrecio() throws Exception {
        Long id = idDe("Dune");
        String admin = tokenAdmin();
        mockMvc.perform(conJson(put("/libro/" + id + "/precio"), admin, Map.of("precio", 50.5)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.precio").value(50.5));

        mockMvc.perform(conToken(get("/libro/" + id + "/precio/historico"), admin))
            .andExpect(status().isOk())
            
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].precio").value(50.5));
    }

    @Test
    @DisplayName("PUT /libro/{id}/precio: igual al actual, negativo, o 0 en libro de editorial: 400")
    void precioInvalido() throws Exception {
        Long id = idDe("Dune");
        String admin = tokenAdmin();
        mockMvc.perform(conJson(put("/libro/" + id + "/precio"), admin, Map.of("precio", 39.99)))
            .andExpect(status().isBadRequest());
        mockMvc.perform(conJson(put("/libro/" + id + "/precio"), admin, Map.of("precio", -5)))
            .andExpect(status().isBadRequest());
        mockMvc.perform(conJson(put("/libro/" + id + "/precio"), admin, Map.of("precio", 0)))
            .andExpect(status().isBadRequest());
    }
}