package ar.edu.huergo.vectorial.calidad.bucher.integration;

import static org.hamcrest.Matchers.greaterThan;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.huergo.vectorial.calidad.bucher.repository.book.AutorRepository;

@Transactional
class CatalogosIntegrationTest extends BaseApiTest {

    @Autowired AutorRepository autorRepository;

    @Test
    @DisplayName("GET /autor/todos y /autor/{id}")
    void autores() throws Exception {
        String t = tokenLector();
        mockMvc.perform(conToken(get("/autor/todos"), t))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()", greaterThan(20)));

        Long id = autorRepository.findByNombreIgnoringCase("J.K. Rowling").orElseThrow().getId();
        mockMvc.perform(conToken(get("/autor/" + id), t))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nombre").value("J.K. Rowling"));
    }


    @Test
    @DisplayName("GET /autor/{id} inexistente: 404")
    void autorInexistente() throws Exception {
        mockMvc.perform(conToken(get("/autor/99999"), tokenLector())).andExpect(status().isNotFound());
    }


    @Test
    @DisplayName("GET /medio-pago, /{id} y /nombre/{n}")
    void mediosPagoLectura() throws Exception {
        String t = tokenLector();
        mockMvc.perform(conToken(get("/medio-pago"), t)).andExpect(jsonPath("$.length()").value(2));
        mockMvc.perform(conToken(get("/medio-pago/2"), t)).andExpect(jsonPath("$.nombre").value("Visa"));
        mockMvc.perform(conToken(get("/medio-pago/nombre/visa"), t)).andExpect(jsonPath("$.porcentajeComision").value(3));
        mockMvc.perform(conToken(get("/medio-pago/nombre/xxx"), t)).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /medio-pago/crear: admin crea; LECTOR 403; comisión > 100 → 400")
    void crearMedioPago() throws Exception {
        Map<String, Object> mp = Map.of("nombre", "Mastercard", "porcentajeComision", 4);
        mockMvc.perform(conJson(post("/medio-pago/crear"), tokenAdmin(), mp))
            .andExpect(status().isOk()).andExpect(jsonPath("$.nombre").value("Mastercard"));
        mockMvc.perform(conJson(post("/medio-pago/crear"), tokenLector(), mp)).andExpect(status().isForbidden());
        mockMvc.perform(conJson(post("/medio-pago/crear"), tokenAdmin(), Map.of("nombre", "Otro", "porcentajeComision", 150)))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /medio-pago/crear con nombre repetido: 400")
    void medioPagoDuplicado() throws Exception {
        mockMvc.perform(conJson(post("/medio-pago/crear"), tokenAdmin(), Map.of("nombre", "Visa", "porcentajeComision", 3)))
            .andExpect(status().isBadRequest());     // ver hallazgo 3: hoy probablemente da 500
    }

    
    @Test 
    @DisplayName("GET /suscripcion: lista las 2 del seed")
    void suscripciones() throws Exception {
        mockMvc.perform(conToken(get("/suscripcion"), tokenLector()))
            .andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @DisplayName("POST /suscripcion/crear: admin crea; nombre repetido 400; precio 0 400; LECTOR 403")
    void crearSuscripcion() throws Exception {
        Map<String, Object> plan = Map.of("nombre", "Plan pago", "porcentajeCobertura", 80, "limiteLibros", 10, "precio", 9999.99);
        mockMvc.perform(conJson(post("/suscripcion/crear"), tokenAdmin(), plan))
            .andExpect(status().isOk()).andExpect(jsonPath("$.precio").value(9999.99));
        mockMvc.perform(conJson(post("/suscripcion/crear"), tokenAdmin(),
                Map.of("nombre", "Plan Gratis", "porcentajeCobertura", 0, "limiteLibros", 0, "precio", 10)))
            .andExpect(status().isBadRequest());
        mockMvc.perform(conJson(post("/suscripcion/crear"), tokenAdmin(),
                Map.of("nombre", "Plan X", "porcentajeCobertura", 10, "limiteLibros", 1, "precio", 0)))
            .andExpect(status().isBadRequest());
        mockMvc.perform(conJson(post("/suscripcion/crear"), tokenLector(), plan)).andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("PUT /suscripcion/{id}: modifica datos; nombre de otra suscripción → 400")
    void modificarSuscripcion() throws Exception {
        String admin = tokenAdmin();
        mockMvc.perform(conJson(put("/suscripcion/2"), admin,
                Map.of("nombre", "Plan Pago", "porcentajeCobertura", 60, "limiteLibros", 8)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.limiteLibros").value(8));
        mockMvc.perform(conJson(put("/suscripcion/2"), admin,
                Map.of("nombre", "Plan Gratis", "porcentajeCobertura", 60, "limiteLibros", 8)))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PUT /suscripcion/precio/{id}: nuevo precio OK; mismo precio y plan gratuito → 400")
    void modificarPrecioSuscripcion() throws Exception {
        String admin = tokenAdmin();
        mockMvc.perform(conJson(put("/suscripcion/precio/2"), admin, Map.of("precio", 5500.5)))
            .andExpect(status().isOk()).andExpect(jsonPath("$.precio").value(5500.5));
        mockMvc.perform(conJson(put("/suscripcion/precio/2"), admin, Map.of("precio", 5500.5)))
            .andExpect(status().isBadRequest());
        mockMvc.perform(conJson(put("/suscripcion/precio/1"), admin, Map.of("precio", 10)))
            .andExpect(status().isBadRequest());
    }
}